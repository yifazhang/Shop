package com.zhangyifa.sso.service;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.pojo.TbUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zyf on 2017/9/29.
 */
public interface UserService {

    ShopResult checkData(String content, Integer type);
    ShopResult createUser(TbUser user);
    ShopResult userLogin(String username, String password, HttpServletRequest request, HttpServletResponse response);
    ShopResult getUserByToken(String token);

}
