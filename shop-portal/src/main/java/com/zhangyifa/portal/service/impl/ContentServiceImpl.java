package com.zhangyifa.portal.service.impl;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.HttpClientUtil;
import com.zhangyifa.common.utils.JsonUtils;
import com.zhangyifa.pojo.TbContent;
import com.zhangyifa.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zyf on 2017/9/19.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;


    @Override
    public String getContentList() {
        //调用服务层
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        
        try {
            //把字符串转化为ShopResult
            ShopResult shopResult = ShopResult.formatToList(result, TbContent.class);
            //取内容列表
            List<TbContent> list = (List<TbContent>) shopResult.getData();
            List<Map> resultList = new ArrayList<>();
            for (TbContent tbContent: list) {
                Map map = new HashMap();
                map.put("src", tbContent.getPic());
                map.put("height", 240);
                map.put("width",670);
                map.put("srcB",tbContent.getPic2());
                map.put("heightB", 550);
                map.put("widthB",240);
                map.put("href",tbContent.getUrl());
                map.put("alt",tbContent.getSubTitle());

                resultList.add(map);
            }
            return JsonUtils.objectToJson(resultList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
