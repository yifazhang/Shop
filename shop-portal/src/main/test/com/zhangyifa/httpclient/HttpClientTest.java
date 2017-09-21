package com.zhangyifa.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyf on 2017/9/19.
 */
public class HttpClientTest {

    @Test
    public void doGet() throws Exception {
        //创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //创建一个GET对象
        HttpGet get = new HttpGet("http://www.baidu.com");
        //执行请求
        CloseableHttpResponse response = httpClient.execute(get);
        //取响应结果
        int statusCode = response.getStatusLine().getStatusCode();
        System.out.println(statusCode);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "utf-8");
        System.out.println(string);

        response.close();
        httpClient.close();
    }

    @Test
    public void doGetWithParam() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        URIBuilder builder = new URIBuilder("http://www.baidu.com/s");
        builder.addParameter("wd", "生活大爆炸");
        HttpGet get = new HttpGet(builder.build());

        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "utf-8");
        System.out.println(string);

        response.close();
        httpClient.close();
    }

    @Test
    public void doPost() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost:8082/httpclient/post.action");
        CloseableHttpResponse response = httpClient.execute(post);

        HttpEntity entity = response.getEntity();
        String string = EntityUtils.toString(entity, "utf-8");
        System.out.println(string);

        response.close();
        httpClient.close();
    }

    @Test
    public void doPostWithParam() throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpPost post = new HttpPost("http://localhost:8082/httpclient/post2.action");
        List<NameValuePair> kvList = new ArrayList<>();
        kvList.add(new BasicNameValuePair("username", "zhangsan啊"));
        kvList.add(new BasicNameValuePair("password","123456"));

        //包装一个Entity对象
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(kvList, "utf-8");
        // 设置请求的内容
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);
        String string = EntityUtils.toString(response.getEntity());
        System.out.println(string);

    }


}
