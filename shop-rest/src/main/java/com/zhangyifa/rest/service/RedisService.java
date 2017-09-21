package com.zhangyifa.rest.service;

import com.zhangyifa.common.pojo.ShopResult;

/**
 * Created by zyf on 2017/9/20.
 */
public interface RedisService {

    ShopResult syncContent(long contentCid);

}
