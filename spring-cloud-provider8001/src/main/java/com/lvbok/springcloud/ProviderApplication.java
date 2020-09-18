package com.lvbok.springcloud;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

/**
 * @author luwei
 * @date 2020年09月04 15:37
 * @desc
 **/
@SpringBootApplication
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
//        String applicationName = ctx.getApplicationName();
//        System.out.println(applicationName);
//        return args -> {
//            System.out.println("spring注入的beans");
//            String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
//            Arrays.sort(beanDefinitionNames);
//            for (String beanDefinitionName : beanDefinitionNames) {
//                Object bean = ctx.getBean(beanDefinitionName);
//                System.out.println(beanDefinitionName+"-----"+bean);
//            }
//        };
//    }
}
