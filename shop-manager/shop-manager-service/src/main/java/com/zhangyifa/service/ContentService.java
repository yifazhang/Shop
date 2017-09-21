package com.zhangyifa.service;

import com.zhangyifa.common.pojo.EUDataGridResult;
import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.pojo.TbContent;

/**
 * Created by zyf on 2017/9/17.
 */
public interface ContentService {

    ShopResult insertContent(TbContent content);

    ShopResult updateContent(TbContent content);

    EUDataGridResult getItemList(long categoryId, int page, int rows);

    ShopResult deleteContent(String ids);

}

