package com.zhangyifa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangyifa.common.pojo.EUDataGridResult;
import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.IDUtils;
import com.zhangyifa.mapper.TbItemMapper;
import com.zhangyifa.pojo.TbItem;
import com.zhangyifa.pojo.TbItemExample;
import com.zhangyifa.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

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

    /**
     * 商品列表查询
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EUDataGridResult getItemList(int page, int rows) {
        //查询商品列表
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(page, rows);
        List<TbItem> list = tbItemMapper.selectByExample(example);
        //返回对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        //取记录总条数
        PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
        result.setTotal(pageInfo.getTotal());

        return result;
    }

    @Override
    public ShopResult createItem(TbItem item) {
        //生成商品ID
        Long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //商品状态 1 正常 2 下架 3 删除
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //插入到数据库
        tbItemMapper.insert(item);

        return ShopResult.ok();
    }
}
