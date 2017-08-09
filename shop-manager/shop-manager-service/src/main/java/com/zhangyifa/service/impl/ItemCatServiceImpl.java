package com.zhangyifa.service.impl;

import com.zhangyifa.common.pojo.EUTreeNode;
import com.zhangyifa.mapper.TbItemCatMapper;
import com.zhangyifa.pojo.TbItemCat;
import com.zhangyifa.pojo.TbItemCatExample;
import com.zhangyifa.pojo.TbItemCatExample.Criteria;
import com.zhangyifa.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyf on 2017/8/9.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public List<EUTreeNode> getCatList(long parentId) {
        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //根据条件查询
        List<TbItemCat> list = itemCatMapper.selectByExample(example);
        List<EUTreeNode> resultList = new ArrayList<>();
        //把列表转换成treeNodeList
        for (TbItemCat tbItemCat: list) {
            EUTreeNode node = new EUTreeNode();
            node.setId(tbItemCat.getId());
            node.setText(tbItemCat.getName());
            node.setState(tbItemCat.getIsParent()?"closed":"open");
            resultList.add(node);
        }
        //返回结果
        return resultList;
    }
}
