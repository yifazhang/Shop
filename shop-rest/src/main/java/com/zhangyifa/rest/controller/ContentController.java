package com.zhangyifa.rest.controller;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.ExceptionUtil;
import com.zhangyifa.pojo.TbContent;
import com.zhangyifa.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zyf on 2017/9/18.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/list/{contentCategoryId}")
    @ResponseBody
    public ShopResult getContentList(@PathVariable Long contentCategoryId) {
        try {
            List<TbContent> list = contentService.getContentList(contentCategoryId);
            return ShopResult.ok(list);
        } catch (Exception e) {
            return ShopResult.build(500, ExceptionUtil.getStackTrace(e));
        }
    }

}
