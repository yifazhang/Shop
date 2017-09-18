package com.zhangyifa.rest.service;

import com.zhangyifa.pojo.TbContent;

import java.util.List;

/**
 * Created by zyf on 2017/9/18.
 */
public interface ContentService {

    List<TbContent> getContentList(long contentId);
}
