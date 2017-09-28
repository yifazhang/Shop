package com.zhangyifa.portal.service.impl;

import com.zhangyifa.common.pojo.ShopResult;
import com.zhangyifa.common.utils.HttpClientUtil;
import com.zhangyifa.common.utils.JsonUtils;
import com.zhangyifa.pojo.TbItemDesc;
import com.zhangyifa.pojo.TbItemParamItem;
import com.zhangyifa.portal.pojo.Item;
import com.zhangyifa.portal.pojo.ItemInfo;
import com.zhangyifa.portal.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zyf on 2017/9/27.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Value("${ITEM_DESC_URL}")
    private String ITEM_DESC_URL;

    @Value("${ITEM_PARAM_URL}")
    private String ITEM_PARAM_URL;

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    /**
     * 查询基本信息
     * @param itemId
     * @return
     */
    @Override
    public ItemInfo getItemById(Long itemId) {

        try {
            //使用rest服务查询
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
            if (!StringUtils.isBlank(json)) {
                ShopResult result = ShopResult.formatToPojo(json, ItemInfo.class);
                if (result.getStatus() == 200) {
                    ItemInfo item = (ItemInfo) result.getData();
                    return item;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 查询详细信息
     * @param itemId
     * @return
     */
    @Override
    public String getItemDescById(Long itemId) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_DESC_URL + itemId);
            if (!StringUtils.isBlank(json)) {
                ShopResult result = ShopResult.formatToPojo(json, TbItemDesc.class);
                if (result.getStatus() == 200) {
                    TbItemDesc itemDesc = (TbItemDesc) result.getData();
                    //取商品描述信息
                    String desc = itemDesc.getItemDesc();
                    return desc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询规格
     * @param itemId
     * @return
     */
    @Override
    public String getItemParam(Long itemId) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_PARAM_URL + itemId);
            //把json转化成java对象
            if (!StringUtils.isBlank(json)) {
                ShopResult shopResult = ShopResult.formatToPojo(json, TbItemParamItem.class);
                if (shopResult.getStatus() == 200) {
                    TbItemParamItem itemParamItem = (TbItemParamItem) shopResult.getData();
                    String paramData = itemParamItem.getParamData();
                    //生成html
                    // 把规格参数json数据转换成java对象
                    List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
                    StringBuffer sb = new StringBuffer();
                    sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                    sb.append("    <tbody>\n");
                    for(Map m1:jsonList) {
                        sb.append("        <tr>\n");
                        sb.append("            <th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
                        sb.append("        </tr>\n");
                        List<Map> list2 = (List<Map>) m1.get("params");
                        for(Map m2:list2) {
                            sb.append("        <tr>\n");
                            sb.append("            <td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
                            sb.append("            <td>"+m2.get("v")+"</td>\n");
                            sb.append("        </tr>\n");
                        }
                    }
                    sb.append("    </tbody>\n");
                    sb.append("</table>");
                    //返回html片段
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
