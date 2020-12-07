package com.lvbok.controller;

import com.lvbok.util.QiniuUtil;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;

@RestController
@Log4j2
public class QiniuController {

    private static final String TEMP_PATH = "D:\\";

    @Autowired
    QiniuUtil qiniuUtil;

    @PostMapping("/qiniu/upload")
    public String upload(MultipartFile file) {
        String savePath = TEMP_PATH + file.getOriginalFilename();
        File tempFile = new File(savePath);
        try {
            file.transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String key = null;
        log.info("tempfile absolute path is {}", tempFile.getAbsolutePath());
        DefaultPutRet response = qiniuUtil.upload(tempFile.getAbsolutePath());
        if (response != null) {
            key = response.key;
        }
        return qiniuUtil.getDomain() + key;
    }

}
