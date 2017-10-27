package com.zhangyifa.sso.service.impl;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.CookieUtils;
import com.zhangyifa.common.utils.JsonUtils;
import com.zhangyifa.mapper.TbUserMapper;
import com.zhangyifa.pojo.TbUser;
import com.zhangyifa.pojo.TbUserExample;
import com.zhangyifa.sso.dao.JedisClient;
import com.zhangyifa.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by zyf on 2017/9/29.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${REDIS_USER_SESSION_KEY}")
    private String REDIS_USER_SESSION_KEY;

    @Value("${SSO_SESSION_EXPIRE}")
    private Integer SSO_SESSION_EXPIRE;


    @Override
    public ShopResult checkData(String content, Integer type) {
        //创建查询对象
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //对数据进行校验：1、2、3分别代表username、phone、email
        //用户名校验
        if (1 == type) {
            criteria.andUsernameEqualTo(content);
        } else if (2 == type) {
            //电话校验
            criteria.andPhoneEqualTo(content);
        } else {
            //email校验
            criteria.andEmailEqualTo(content);
        }
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        if (null == list || 0 == list.size()) {
            return ShopResult.ok(true);
        }
        return ShopResult.ok(false);
    }

    @Override
    public ShopResult createUser(TbUser user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());

        //md5加密
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        userMapper.insert(user);
        return ShopResult.ok();
    }

    @Override
    public ShopResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(userExample);
        //如果没有此用户名
        if (null == list || 0 == list.size()) {
            return ShopResult.build(400, "用户名或密码错误");
        }
        TbUser user = list.get(0);
        //比对密码
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return ShopResult.build(400, "用户名或密码错误");
        }
        //生成token
        String token = UUID.randomUUID().toString();
        //保存用户之前，把用户对象中的密码清空
        user.setPassword(null);
        //把用户信息写入redis
        jedisClient.set(REDIS_USER_SESSION_KEY + ":" + token, JsonUtils.objectToJson(user));
        //设置session的过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY, SSO_SESSION_EXPIRE);

        //添加写cookie的逻辑，cookie的有效期是关闭浏览器就失效
        CookieUtils.setCookie(request, response, "TT_TOKEN", token);

        return ShopResult.ok(token);
    }

    @Override
    public ShopResult getUserByToken(String token) {
        //从redis中取用户信息
        String json = jedisClient.get(REDIS_USER_SESSION_KEY + ":" + token);
        //判断是否为空
        if (StringUtils.isBlank(json)) {
            return ShopResult.build(400, "");
        }
        //更新过期时间
        jedisClient.expire(REDIS_USER_SESSION_KEY + ":" + token, SSO_SESSION_EXPIRE);
        //返回用户信息
        return ShopResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
    }
}
