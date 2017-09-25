package com.zhangyifa.search.dao;

import com.zhangyifa.search.pojo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by zyf on 2017/9/25.
 */
public interface SearchDao {

    SearchResult search(SolrQuery query) throws Exception;

}
