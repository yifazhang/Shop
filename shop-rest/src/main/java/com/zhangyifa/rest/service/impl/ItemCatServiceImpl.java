package com.zhangyifa.rest.service.impl;

import com.zhangyifa.mapper.TbItemCatMapper;
import com.zhangyifa.pojo.TbItemCat;
import com.zhangyifa.pojo.TbItemCatExample;
import com.zhangyifa.pojo.TbItemCatExample.Criteria;
import com.zhangyifa.rest.pojo.CatNode;
import com.zhangyifa.rest.pojo.CatResult;
import com.zhangyifa.rest.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyf on 2017/9/11.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public CatResult getItemCatList() {
        CatResult catResult = new CatResult();
        catResult.setData(getCatList(0));
        return catResult;
    }

    private List<?> getCatList(long parentId) {
        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        //返回值list
        List resultList = new ArrayList();
        //向list添加节点
        int count = 0;
        for (TbItemCat tbItemCat : list) {
            //判断是否为父节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                } else {
                    catNode.setName(tbItemCat.getName());
                }
                catNode.setUrl("/products/"+tbItemCat.getId()+".html");
                catNode.setItem(getCatList(tbItemCat.getId()));

                resultList.add(catNode);
                count ++;
                //第一层之取14条
                if (parentId == 14 && parentId == 0) {
                    break;
                }
            } else {
                resultList.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
            }
        }
        return resultList;
    }

}
