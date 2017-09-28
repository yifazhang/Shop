package com.zhangyifa.service.impl;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.mapper.TbItemParamMapper;
import com.zhangyifa.pojo.TbItemParam;
import com.zhangyifa.pojo.TbItemParamExample;
import com.zhangyifa.pojo.TbItemParamExample.Criteria;
import com.zhangyifa.service.ItemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zyf on 2017/8/11.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public ShopResult getItemParamByCid(long cid) {
        TbItemParamExample example = new TbItemParamExample();
        Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if(list != null && list.size() > 0) {
            return ShopResult.ok(list.get(0));
        }
        return ShopResult.ok();
    }

    @Override
    public ShopResult insertItemParam(TbItemParam itemParam) {
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        //插入到规格参数模板表
        itemParamMapper.insert(itemParam);
        return ShopResult.ok();
    }
}
