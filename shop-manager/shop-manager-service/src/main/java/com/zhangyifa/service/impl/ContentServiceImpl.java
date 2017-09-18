package com.zhangyifa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangyifa.common.pojo.EUDataGridResult;
import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.mapper.TbContentMapper;
import com.zhangyifa.pojo.TbContent;
import com.zhangyifa.pojo.TbContentExample;
import com.zhangyifa.pojo.TbContentExample.Criteria;
import com.zhangyifa.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zyf on 2017/9/18.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public ShopResult insertContent(TbContent content) {
        //补全pojo
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);

        return ShopResult.ok();
    }

    @Override
    public EUDataGridResult getItemList(long categoryId, int page, int rows) {
        //查询内容
        TbContentExample contentExample = new TbContentExample();
        Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        //分页处理
        PageHelper.startPage(page, rows);
        List<TbContent> list = contentMapper.selectByExample(contentExample);
        //返回对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
        result.setTotal(pageInfo.getTotal());

        return result;
    }
}
