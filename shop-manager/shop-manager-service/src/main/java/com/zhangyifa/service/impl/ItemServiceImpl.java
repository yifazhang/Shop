package com.zhangyifa.service.impl;

import com.zhangyifa.mapper.TbItemMapper;
import com.zhangyifa.pojo.TbItem;
import com.zhangyifa.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zyf on 2017/8/8.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public TbItem getItemById(Long id) {
        return tbItemMapper.selectByPrimaryKey(id);
    }
}
