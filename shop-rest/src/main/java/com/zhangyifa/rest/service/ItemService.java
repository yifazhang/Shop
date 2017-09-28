package com.zhangyifa.rest.service;

import com.zhangyifa.common.pojo.ShopResult;

/**
 * Created by zyf on 2017/9/27.
 */
public interface ItemService {

    ShopResult getItemBaseInfo(long itemId);
    ShopResult getItemDesc(long itemId);
    ShopResult getItemParam(long itemId);

}
