package com.zhangyifa.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zyf on 2017/8/9.
 */
@Controller
public class PageController {

    /**
     * 打开首页
     */
    @RequestMapping("/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping("/{page}")
    public String showpage(@PathVariable String page) {
        return page;
    }








}
