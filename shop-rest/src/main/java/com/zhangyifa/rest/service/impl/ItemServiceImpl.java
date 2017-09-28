package com.zhangyifa.rest.service.impl;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.JsonUtils;
import com.zhangyifa.mapper.TbItemDescMapper;
import com.zhangyifa.mapper.TbItemMapper;
import com.zhangyifa.mapper.TbItemParamItemMapper;
import com.zhangyifa.pojo.*;
import com.zhangyifa.pojo.TbItemParamItemExample.Criteria;
import com.zhangyifa.rest.dao.JedisClient;
import com.zhangyifa.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zyf on 2017/9/27.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${REDIS_ITEM_EXPIRE}")
    private int REDIS_ITEM_EXPIRE;

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public ShopResult getItemBaseInfo(long itemId) {

        try {
            //添加缓存
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
            //判断是否有值
            if (!StringUtils.isBlank(json)) {
                //把json转换成对象
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return ShopResult.ok(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据商品id查询商品信息
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);


        try {
            //写入缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
            //设置缓存时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ShopResult.ok(item);
    }

    @Override
    public ShopResult getItemDesc(long itemId) {

        try {
            //取缓存
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
            if (!StringUtils.isBlank(json)) {
                //把json转对象
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return ShopResult.ok(itemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        //根据商品id查询商品详情
        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);
        
        try {
            //存缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":desc", JsonUtils.objectToJson(itemDesc));
            //设置缓存时间
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":desc", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ShopResult.ok(itemDesc);
    }

    @Override
    public ShopResult getItemParam(long itemId) {

        try {
            //取缓存
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
            if (!StringUtils.isBlank(json)) {
                TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return ShopResult.ok(paramItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据商品分类cid查询商品规格
        TbItemParamItemExample example = new TbItemParamItemExample();
        Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //执行查询
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size() > 0) {
            TbItemParamItem paramItem = list.get(0);
            try {
                //把商品信息写入缓存
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
                //设置缓存时间
                jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return ShopResult.ok(paramItem);
        }

        return ShopResult.build(400, "无此商品规格");
    }
}
