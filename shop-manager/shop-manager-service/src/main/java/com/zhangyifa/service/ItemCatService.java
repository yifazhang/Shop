package com.zhangyifa.service;

import com.zhangyifa.common.pojo.EUTreeNode;

import java.util.List;

/**
 * Created by zyf on 2017/8/9.
 */
public interface ItemCatService {

    List<EUTreeNode> getCatList(long parentId);

}
