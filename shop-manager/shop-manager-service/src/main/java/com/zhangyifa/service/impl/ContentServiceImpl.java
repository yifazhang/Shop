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
import com.zhangyifa.utils.CacheSyncUtils;
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

    @Autowired
    private CacheSyncUtils cacheSyncUtils;

    @Override
    public ShopResult insertContent(TbContent content) {
        //补全pojo
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);

        //添加缓存同步逻辑
        cacheSyncUtils.syncContent(content.getCategoryId());

        return ShopResult.ok();
    }

    @Override
    public ShopResult updateContent(TbContent content) {
        content.setUpdated(new Date());
        contentMapper.updateByPrimaryKeySelective(content);

        //添加缓存同步逻辑
        cacheSyncUtils.syncContent(content.getCategoryId());

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

    @Override
    public ShopResult deleteContent(String ids) {
        String[] split = ids.split(",");
        int delCount = 0;
        long cateGoryId = -1;
        int count = split.length;
        for (int i = 0; i < count ; i++) {
            long lid = Long.parseLong(split[i]);
            if ( i == 0 ) {
                TbContent tbContent = contentMapper.selectByPrimaryKey(lid);
                cateGoryId = tbContent.getCategoryId();
            }
            delCount += contentMapper.deleteByPrimaryKey(lid);
        }

        //添加缓存同步逻辑
        if (cateGoryId != -1) {
            cacheSyncUtils.syncContent(cateGoryId);
        }

        if (delCount == count) {
            return ShopResult.ok();
        } else {
            return ShopResult.build(500,"删除失败");
        }
    }

}
