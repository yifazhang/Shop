package com.zhangyifa.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by zyf on 2017/8/10.
 */
public interface PictureService {

    Map uploadPicture(MultipartFile uploadFile);

}
