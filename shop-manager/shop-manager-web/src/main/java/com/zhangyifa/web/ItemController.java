package com.zhangyifa.web;

import com.zhangyifa.common.pojo.EUDataGridResult;
import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.pojo.TbItem;
import com.zhangyifa.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zyf on 2017/8/8.
 * 商品管理
 */
@Controller
@RequestMapping("/item/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem seachId(@PathVariable Long itemId) {
        TbItem tbItem = itemService.getItemById(itemId);
        return tbItem;
    }

    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult seachId(Integer page, Integer rows) {
        return itemService.getItemList(page, rows);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ShopResult createItem(TbItem item, String desc) throws Exception {
        return itemService.createItem(item, desc);
    }

}
