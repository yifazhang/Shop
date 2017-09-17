package com.zhangyifa.service;

import com.zhangyifa.common.pojo.EUTreeNode;
import com.zhangyifa.common.pojo.ShopResult;

import java.util.List;

/**
 * Created by zyf on 2017/9/17.
 */
public interface ContentCategoryService {

    /**
     * 查询子类列表
     * @param parentId 父id
     * @return
     */
    List<EUTreeNode> getCategoryList(long parentId);

    /**
     * 插入子类
     * @param parentId 父id
     * @param name 子类名称
     * @return
     */
    ShopResult insertContentCategory(long parentId,String name);

    /**
     * 根据id修改名称
     * @param id id
     * @param name 名称
     * @return
     */
    int updateContentCategory(long id, String name);

    /**
     * 删除id
     * @param parentId 父id
     * @param id id
     * @return
     */
    int deleteContentCategory(long parentId, long id);
}
