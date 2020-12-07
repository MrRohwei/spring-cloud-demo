package com.lvbok;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootApplication
public class SpringBootEsApplication {

    public static void main(String[] args) {
//        SpringApplication.run(SpringBootEsApplication.class, args);
        test();
    }

    public static void test() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "looveh");
        map.put("age", "18");
        map.put("sex", "m");

        String url = map.keySet().stream()
                .map(key -> key.concat("=").concat(map.get(key)))
                .sorted(Comparator.comparing(key -> (String) key).reversed())
                .collect(Collectors.joining("&", "http://www.baidu.com/serarch?",""));
        System.out.println(url);
    }
}
