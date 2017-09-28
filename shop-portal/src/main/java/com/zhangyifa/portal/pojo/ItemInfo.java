package com.zhangyifa.portal.pojo;

import com.zhangyifa.pojo.TbItem;

/**
 * Created by zyf on 2017/9/27.
 */
public class ItemInfo extends TbItem{

    public String[] getImages() {
        String image = getImage();
        if (image != null) {
            String[] split = image.split(",");
            return split;
        }
        return null;
    }
}
