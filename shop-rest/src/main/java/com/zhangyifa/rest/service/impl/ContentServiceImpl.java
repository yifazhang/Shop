package com.zhangyifa.rest.service.impl;

import com.zhangyifa.mapper.TbContentMapper;
import com.zhangyifa.pojo.TbContent;
import com.zhangyifa.pojo.TbContentExample;
import com.zhangyifa.pojo.TbContentExample.Criteria;
import com.zhangyifa.rest.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zyf on 2017/9/18.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public List<TbContent> getContentList(long contentId) {
        //根据分类id查询内容列表
        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();
        criteria.andCategoryIdEqualTo(contentId);

        List<TbContent> list = contentMapper.selectByExample(example);
        return list;
    }
}
