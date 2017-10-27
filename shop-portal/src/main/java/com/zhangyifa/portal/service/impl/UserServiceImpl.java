package com.zhangyifa.portal.service.impl;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.HttpClientUtil;
import com.zhangyifa.pojo.TbUser;
import com.zhangyifa.portal.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zyf on 2017/10/27.
 */
@Service
public class UserServiceImpl implements UserService{

    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;
    @Value("${SSO_DOMAIN_BASE_URL}")
    public String SSO_DOMAIN_BASE_URL;
    @Value("${SSO_USER_TOKEN}")
    public String SSO_USER_TOKEN;
    @Value("${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;


    @Override
    public TbUser getUserByToken(String token) {
        try {
            //调用sso系统的服务，根据token获取用户信息
            String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
            //把json转换成ShopResult
            ShopResult result = ShopResult.formatToPojo(json, TbUser.class);
            if (result.getStatus() == 200) {
                TbUser user = (TbUser) result.getData();
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
