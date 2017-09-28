package com.zhangyifa.portal.service;

import com.zhangyifa.portal.pojo.SearchResult;

/**
 * Created by zyf on 2017/9/26.
 */
public interface SearchService {

    SearchResult search(String queryString, int page);
}
