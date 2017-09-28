package com.zhangyifa.portal.service.impl;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.HttpClientUtil;
import com.zhangyifa.portal.pojo.SearchResult;
import com.zhangyifa.portal.service.SearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by zyf on 2017/9/26.
 */
@Service
public class SearchServiceImpl implements SearchService {


    @Value("${SEARCH_BASE_URL}")
    private  String SEARCH_BASE_URL;

    @Override
    public SearchResult search(String queryString, int page) {
        //调用taotao-search的服务
        //查询参数
        HashMap<String, String> param = new HashMap<>();
        param.put("q", queryString);
        param.put("page", page + "");
        try {
            //调用服务
            String json = HttpClientUtil.doGet(SEARCH_BASE_URL, param);
            //把字符串转换成java对象
            ShopResult shopResult = ShopResult.formatToPojo(json, SearchResult.class);
            if (shopResult.getStatus() == 200) {
                SearchResult result = (SearchResult) shopResult.getData();
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
