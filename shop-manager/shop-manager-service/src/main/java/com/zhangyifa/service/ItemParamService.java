package com.zhangyifa.service;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.pojo.TbItemParam;

/**
 * Created by zyf on 2017/8/11.
 */
public interface ItemParamService {

    ShopResult getItemParamByCid(long cid);

    ShopResult insertItemParam(TbItemParam itemParam);

}
