package com.zhangyifa.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangyifa.common.pojo.EUDataGridResult;
import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.IDUtils;
import com.zhangyifa.mapper.TbItemDescMapper;
import com.zhangyifa.mapper.TbItemMapper;
import com.zhangyifa.mapper.TbItemParamItemMapper;
import com.zhangyifa.pojo.TbItem;
import com.zhangyifa.pojo.TbItemDesc;
import com.zhangyifa.pojo.TbItemExample;
import com.zhangyifa.pojo.TbItemParamItem;
import com.zhangyifa.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static java.lang.System.in;

/**
 * Created by zyf on 2017/8/8.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private TbItemParamItemMapper tbItemParamItemMapper;

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
    public ShopResult createItem(TbItem item, String desc, String itemParam) throws Exception {
        //生成商品ID
        Long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //商品状态 1 正常 2 下架 3 删除
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //插入到数据库
        tbItemMapper.insert(item);
        //添加商品描述
        ShopResult result = insertItemDesc(itemId, desc);
        if (result.getStatus() != 200) {
            throw new Exception();
        }
        //添加规格参数
        result = insetItemParamItem(itemId, itemParam);
        if (result.getStatus() != 200) {
            throw new Exception();
        }

        return ShopResult.ok();
    }

    /**
     * 添加商品描述
     * @param itemId 商品id
     * @param desc 商品描述
     * @return
     */
    private ShopResult insertItemDesc(Long itemId, String desc) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        tbItemDescMapper.insert(itemDesc);
        return ShopResult.ok();
    }

    private ShopResult insetItemParamItem(Long itemId, String itemParam) {
        //创建一个pojo
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreated(new Date());
        itemParamItem.setUpdated(new Date());

        //向表中插入数据
        tbItemParamItemMapper.insert(itemParamItem);
        return ShopResult.ok();
    }


}
