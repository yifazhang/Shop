package com.zhangyifa.search.controller;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.search.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zyf on 2017/9/25.
 */
@RequestMapping("/manager")
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/importall")
    @ResponseBody
    public ShopResult importAllTime() {
        ShopResult shopResult = itemService.importAllItems();
        return shopResult;
    }

}
