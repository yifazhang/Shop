package com.zhangyifa.web;

import com.zhangyifa.common.pojo.EUTreeNode;
import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zyf on 2017/9/17.
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getContentCatList(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
        return list;
    }

    @RequestMapping("/create")
    @ResponseBody
    public ShopResult createContentCategory(Long parentId, String name) {
        ShopResult result = contentCategoryService.insertContentCategory(parentId, name);
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public void updateContentCategory(Long id, String name) {
        contentCategoryService.updateContentCategory(id, name);
    }

    @RequestMapping("/delete")
    @ResponseBody
    public void deleteContentCategory(Long parentId, Long id) {
        contentCategoryService.deleteContentCategory(parentId, id);
    }

}

