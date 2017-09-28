package com.zhangyifa.rest.controller;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.rest.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zyf on 2017/9/27.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public ShopResult getItemBaseInfo(@PathVariable Long itemId) {
        ShopResult result = itemService.getItemBaseInfo(itemId);
        return result;
    }

    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public ShopResult getItemDesc(@PathVariable Long itemId) {
        ShopResult result = itemService.getItemDesc(itemId);
        return result;
    }

    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public ShopResult getItemParam(@PathVariable Long itemId) {
        ShopResult result = itemService.getItemParam(itemId);
        return result;
    }


}
