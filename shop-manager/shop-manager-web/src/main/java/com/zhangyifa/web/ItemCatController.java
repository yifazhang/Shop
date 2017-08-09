package com.zhangyifa.web;

import com.zhangyifa.common.pojo.EUTreeNode;
import com.zhangyifa.service.ItemCatService;
import com.zhangyifa.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zyf on 2017/8/9.
 * 商品分类管理
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    @ResponseBody
    private List<EUTreeNode> getCatList(@RequestParam(value = "id",defaultValue = "0") long parentId) {
        List<EUTreeNode> list = itemCatService.getCatList(parentId);
        return list;
    }

}
