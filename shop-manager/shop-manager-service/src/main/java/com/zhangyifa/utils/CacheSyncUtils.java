package com.zhangyifa.utils;

import com.zhangyifa.common.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created by zyf on 2017/9/20.
 */
public class CacheSyncUtils {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_CONTENT_SYNC_URL}")
    private String REST_CONTENT_SYNC_URL;

    /**
     * 同步缓存
     * @param categoryId
     */
    public void syncContent(Long categoryId) {
        //添加缓存同步逻辑
        try {
            HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + categoryId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
