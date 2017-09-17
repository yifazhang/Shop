package com.zhangyifa.service.impl;

import com.zhangyifa.common.pojo.EUTreeNode;
import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.mapper.TbContentCategoryMapper;
import com.zhangyifa.pojo.TbContentCategory;
import com.zhangyifa.pojo.TbContentCategoryExample;
import com.zhangyifa.pojo.TbContentCategoryExample.Criteria;
import com.zhangyifa.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zyf on 2017/9/17.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EUTreeNode> getCategoryList(long parentId) {
        //根据parentId查询节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
        List<EUTreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            EUTreeNode node = new EUTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");
            node.setParentId(tbContentCategory.getParentId());
            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public ShopResult insertContentCategory(long parentId, String name) {
        //创建一个pojo
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setIsParent(false);
        //'状态。可选值:1(正常),2(删除)',
        contentCategory.setStatus(1);
        contentCategory.setParentId(parentId);
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());

        //添加记录
        contentCategoryMapper.insert(contentCategory);

        //查看父节点的isParent列是否为true，如果不是true改成true
        TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
        //判断是否为true
        if (!parentCat.getIsParent()) {
            parentCat.setIsParent(true);
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentCat);
        }
        //返回结果
        return ShopResult.ok(contentCategory);
    }

    @Override
    public int updateContentCategory(long id, String name) {
        TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
        if (category == null) {
            return 0;
        }
        category.setName(name);
        category.setUpdated(new Date());
        int i = contentCategoryMapper.updateByPrimaryKey(category);
        return i;
    }

    @Override
    public int deleteContentCategory(long parentId, long id) {
        //删除当前id以及子类
        int i = deleteCategoryById(id);

        //修改父类
        TbContentCategory parentCat = contentCategoryMapper.selectByPrimaryKey(parentId);
        //判断是否为true
        if (parentCat.getIsParent()) {
            List<EUTreeNode> list = getCategoryList(parentId);
            if (list.size() > 1) {
                parentCat.setIsParent(true);
            } else {
                parentCat.setIsParent(false);
            }
            //更新父节点
            contentCategoryMapper.updateByPrimaryKey(parentCat);
        }

        return i;
    }

    //删除当前id以及子类
    public int deleteCategoryById(Long id) {
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
        int i = contentCategoryMapper.deleteByPrimaryKey(id);
        if (contentCategory.getIsParent()) {
            List<EUTreeNode> list = getCategoryList(id);
            for (EUTreeNode node : list) {
                deleteCategoryById(node.getId());
            }
        }
        return i;
    }

}
