package com.zhangyifa.portal.service;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by zyf on 2017/10/13.
 */
public interface CartService {

    ShopResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response);
    List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);
    ShopResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response);

}
