package com.lvbok.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Log4j2
public class QiniuUtil {

    @Value("${qiniu.accesskey}")
    private String accessKey;
    @Value("${qiniu.secretkey}")
    private String secretkey;
    @Value("${qiniu.bucket}")
    private String bucket;

    public String getDomain() {
        return domain;
    }

    @Value("${qiniu.domain}")
    private String domain;

    public String getToken() {
        Auth auth = Auth.create(accessKey, secretkey);
        String token = auth.uploadToken(bucket);
        return token;
    }

    public UploadManager getUploadManager() {
        Configuration config = new Configuration();
        UploadManager manager = new UploadManager(config);
        return manager;
    }

    public DefaultPutRet upload(String localFilePath) {
        UploadManager manager = getUploadManager();
        DefaultPutRet result = null;
        String key = localFilePath.substring(localFilePath.lastIndexOf(File.separator) + 1);
        try {
            Response response = manager.put(localFilePath, key, getToken());
            //解析上传成功的结果
            result = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException e) {
            log.error("上传失败：{}", e);
            return null;
        }
        return result;
    }
}
