package com.lvbok;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@SpringBootApplication
public class MyPictureBedApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyPictureBedApplication.class, args);
    }
}
