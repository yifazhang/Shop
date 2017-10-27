package com.zhangyifa.portal.service.impl;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.CookieUtils;
import com.zhangyifa.common.utils.HttpClientUtil;
import com.zhangyifa.common.utils.JsonUtils;
import com.zhangyifa.pojo.TbItem;
import com.zhangyifa.portal.pojo.CartItem;
import com.zhangyifa.portal.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyf on 2017/10/13.
 */
@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Override
    public ShopResult addCartItem(long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
        //取商品详情
        CartItem cartItem = null;
        //取购物车商品列表
        List<CartItem> itemList = getCartItemList(request, response);
        //判断购物车商品列表中是否存在此商品
        for (CartItem cItem: itemList) {
            //如果存在此商品
            if (cItem.getId() == itemId) {
                //增加商品数量
                cItem.setNum(cItem.getNum() + num);
                cartItem = cItem;
                break;
            }
        }
        if (cartItem == null){
            cartItem = new CartItem();
            //根据商品id查询商品基本信息
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
            //把json转化为java对象
            ShopResult shopResult = ShopResult.formatToPojo(json, TbItem.class);
            if (shopResult.getStatus() == 200) {
                TbItem item = (TbItem) shopResult.getData();
                cartItem.setId(item.getId());
                cartItem.setTitle(item.getTitle());
                cartItem.setImage(item.getImage() == null ? "" : item.getImage().split(",")[0]);
                cartItem.setNum(num);
                cartItem.setPrice(item.getPrice());
            }
            //添加到购物车列表
            itemList.add(cartItem);
        }
        //把购物车列表写入cookie
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
        return ShopResult.ok();
    }

    /**
     * 从Cookie中取商品列表
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> itemList = getCartItemList(request);
        return itemList;
    }

    public List<CartItem> getCartItemList(HttpServletRequest request) {
        //从cookie中取商品列表
        String cartJson = CookieUtils.getCookieValue(request, "TT_CART", true);
        if (cartJson == null) {
            return new ArrayList<>();
        }
        //把json转成商品列表
        try {
            List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    @Override
    public ShopResult deleteCartItem(long itemId, HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取购物车的商品列表
        List<CartItem> itemList = getCartItemList(request);
        //从列表中找到此商品
        for (CartItem cartItem : itemList) {
            if (cartItem.getId() == itemId) {
                itemList.remove(cartItem);
                break;
            }
        }
        //把购物车列表重新写入cookie中
        CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList), true);
        return ShopResult.ok();
    }
}
