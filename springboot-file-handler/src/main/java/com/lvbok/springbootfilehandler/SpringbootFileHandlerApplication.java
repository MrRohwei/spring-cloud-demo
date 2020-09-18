package com.lvbok.springbootfilehandler;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
@Log4j2
public class SpringbootFileHandlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootFileHandlerApplication.class, args);
    }


    @PostMapping(value = "/file/upload")
    public void uploadfile(MultipartFile file, HttpServletRequest request) {
        log.info("上传文件name = {}, originalFilename = {}", file.getName(), file.getOriginalFilename());
        System.out.println(System.getProperty("user.dir"));
        System.out.println(ClassUtils.getDefaultClassLoader().getResource("").getPath());
        /*String path = this.getClass().getResource("/").getPath();
        File dir = new File(path + "/file/");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File newFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @GetMapping("/file/download")
    public void downloadFile(HttpServletResponse response) {
        Map<String, String> headerMap = new HashMap<>();
        headerMap.put("content-type", "application/octet-stream");
//        headerMap.put("Content-Length", "application/octet-stream");
        test(response, headerMap);
    }

    @GetMapping("/file/preview")
    public void previewFile(HttpServletResponse response) {
        test(response, null);
    }

    private void test(HttpServletResponse response, Map<String, String> headerMap) {
        File file = new File(this.getClass().getResource("/").getPath() + "/file/banner.jpg");
        if (null != headerMap && headerMap.size() > 0) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
//                response.setContentType("application/octet-stream");
//                response.setContentLength((int) file.length());
                response.addHeader(entry.getKey(), entry.getValue());
            }
        }

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(file);
            os = response.getOutputStream();
            byte[] bytes = new byte[1024];
            int redLen;
            while ((redLen = is.read(bytes)) > 0) {
                os.write(bytes, 0, redLen);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
