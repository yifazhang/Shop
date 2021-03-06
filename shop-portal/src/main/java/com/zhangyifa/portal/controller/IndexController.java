package com.zhangyifa.portal.controller;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.portal.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by zyf on 2017/9/9.
 */
@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        String adJson = contentService.getContentList();
        model.addAttribute("ad1", adJson);
        return "index";
    }

    @RequestMapping(value = "/httpclient/post",method = RequestMethod.POST)
    @ResponseBody
    public String testPost() {
        return "ok";
    }

    @RequestMapping(value = "/httpclient/post2",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE +";charset=utf-8")
    @ResponseBody
    public String testPost2(String username, String password) {
        return "{username:" + username + "\t, password:" + password +"}";
    }
}
