package com.zhangyifa.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhangyifa.mapper.TbItemMapper;
import com.zhangyifa.pojo.TbItem;
import com.zhangyifa.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by zyf on 2017/8/9.
 */
public class PageControllerTest {

    @Test
    public void testPageHelper() {
        //Spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/application-dao.xml");
        //取得mapper
        TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
        //查询分页
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(2,15);
        List<TbItem> list = mapper.selectByExample(example);
        //取商品列表
        for (TbItem tbItem: list) {
            System.out.println(tbItem.getTitle());
        }
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo.toString());

    }
}