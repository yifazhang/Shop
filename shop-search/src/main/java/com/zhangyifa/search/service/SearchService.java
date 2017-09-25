package com.zhangyifa.search.service;


import com.zhangyifa.search.pojo.SearchResult;

/**
 * Created by zyf on 2017/9/25.
 */
public interface SearchService {

    SearchResult search(String queryString, int page, int rows) throws Exception;
}
