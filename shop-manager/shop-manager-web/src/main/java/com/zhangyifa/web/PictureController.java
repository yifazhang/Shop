package com.zhangyifa.web;

import com.zhangyifa.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * Created by zyf on 2017/8/10.
 * 上传图片处理
 */
@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @RequestMapping("/pic/upload")
    @ResponseBody
    public Map pictureUpload(MultipartFile uploadFile) {
        return pictureService.uploadPicture(uploadFile);
    }

}
