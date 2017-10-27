package com.zhangyifa.portal.service;

import com.zhangyifa.pojo.TbUser;

/**
 * Created by zyf on 2017/10/27.
 */
public interface UserService {

    TbUser getUserByToken(String token);
}
