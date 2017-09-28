package com.zhangyifa.portal.pojo;

import java.util.List;

/**
 * Created by zyf on 2017/9/25.
 */
public class SearchResult {

    //商品列表
    private List<Item> itemList;
    //总记录数
    private long recordCount;
    //总页数
    private long pageCount;
    //当前页
    private long curCount;

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public long getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(long recordCount) {
        this.recordCount = recordCount;
    }

    public long getPageCount() {
        return pageCount;
    }

    public void setPageCount(long pageCount) {
        this.pageCount = pageCount;
    }

    public long getCurCount() {
        return curCount;
    }

    public void setCurCount(long curCount) {
        this.curCount = curCount;
    }
}
