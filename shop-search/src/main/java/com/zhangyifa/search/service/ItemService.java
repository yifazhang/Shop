package com.zhangyifa.search.service;

import com.zhangyifa.common.pojo.ShopResult;
import org.apache.solr.client.solrj.SolrServerException;

import java.io.IOException;

/**
 * Created by zyf on 2017/9/25.
 */
public interface ItemService {

    ShopResult importAllItems();

}
