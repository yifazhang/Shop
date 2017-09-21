package com.zhangyifa.web;

import com.zhangyifa.common.pojo.EUDataGridResult;
import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.pojo.TbContent;
import com.zhangyifa.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zyf on 2017/9/18.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/save")
    @ResponseBody
    public ShopResult insertContent(TbContent content) {
        ShopResult result = contentService.insertContent(content);
        return result;
    }

    @RequestMapping("/query/list")
    @ResponseBody
    public EUDataGridResult seachId(Long categoryId, Integer page, Integer rows) {
        return contentService.getItemList(categoryId, page, rows);
    }


    @RequestMapping("/delete")
    @ResponseBody
    public ShopResult deleteContent(String ids) {
        ShopResult result = contentService.deleteContent(ids);
        return result;
    }

    @RequestMapping("/edit")
    @ResponseBody
    public ShopResult editContent(TbContent content) {
        ShopResult result = contentService.updateContent(content);
        return result;
    }

}
