package com.zhangyifa.web;

import com.zhangyifa.common.utils.FtpUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by zyf on 2017/8/10.
 */
public class FTPTest {

    @Test
    public void testFtpClient() throws IOException {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect("127.0.0.1",21);
        ftpClient.login("zyf","123456");
        //上传文件
        FileInputStream fileInputStream = new FileInputStream(new File("/Users/zyf/Desktop/55554574N5d173735.jpg"));
        //设置上传的路径
        ftpClient.changeWorkingDirectory("/usr/local/var/www/images");
        //修改上传文件的格式
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //第一个参数：服务器端文档名
        //第二个参数：上传文档的inputStream
        ftpClient.storeFile("hello.jpg",fileInputStream);
        //关闭连接
        ftpClient.logout();
    }

    @Test
    public void testFtpUtil() {
        try {
            FileInputStream in = new FileInputStream(new File("/Users/zyf/Desktop/57d0d400Nfd249af4.jpg"));
            boolean flag = FtpUtil.uploadFile("127.0.0.1", 21, "zyf", "123456", "/usr/local/var/www/images","/2017/08/10", "iphone7pulus.jpg", in);
            System.out.println(flag);
            Assert.assertTrue(flag);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
