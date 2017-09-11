package com.zhangyifa.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zyf on 2017/9/9.
 */
@Controller
public class IndexController {

    @RequestMapping("index")
    public String index() {
        return "index";
    }

}
