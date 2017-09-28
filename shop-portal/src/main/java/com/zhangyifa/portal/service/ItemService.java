package com.zhangyifa.portal.service;

import com.zhangyifa.portal.pojo.ItemInfo;

/**
 * Created by zyf on 2017/9/27.
 */
public interface ItemService {

    ItemInfo getItemById(Long itemId);
    String getItemDescById(Long itemId);
    String getItemParam(Long itemId);

}
