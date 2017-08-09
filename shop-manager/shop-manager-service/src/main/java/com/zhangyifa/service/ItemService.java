package com.zhangyifa.service;

import com.zhangyifa.common.pojo.EUDataGridResult;
import com.zhangyifa.pojo.TbItem;

/**
 * Created by zyf on 2017/8/8.
 */
public interface ItemService {

    TbItem getItemById(Long id);

    EUDataGridResult getItemList(int page, int rows);

}
