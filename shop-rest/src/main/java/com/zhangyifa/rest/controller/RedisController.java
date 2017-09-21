package com.zhangyifa.rest.controller;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zyf on 2017/9/20.
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("/content/{contentCid}")
    @ResponseBody
    public ShopResult contentCacheSync(@PathVariable Long contentCid) {
        ShopResult result = redisService.syncContent(contentCid);
        return result;
    }


}
