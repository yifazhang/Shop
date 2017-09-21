package com.zhangyifa.rest.service.impl;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.ExceptionUtil;
import com.zhangyifa.rest.dao.JedisClient;
import com.zhangyifa.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by zyf on 2017/9/20.
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Override
    public ShopResult syncContent(long contentCid) {
        try {
            jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCid + "");
        } catch (Exception e) {
            e.printStackTrace();
            return ShopResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return ShopResult.ok();
    }
}
