package com.zhangyifa.web;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.pojo.TbItemParam;
import com.zhangyifa.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zyf on 2017/8/11.
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public ShopResult getItemParamByCid(@PathVariable Long itemCatId) {
        return itemParamService.getItemParamByCid(itemCatId);
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public ShopResult insetItemParam(@PathVariable Long cid, String paramData) {
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        return itemParamService.insertItemParam(itemParam);
    }


}

















