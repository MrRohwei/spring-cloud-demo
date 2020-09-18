package com.lvbok.springcloud.banner;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.PrintStream;

@Component
public class Mybanner implements Banner {

    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        out.println("自定义打印banner---------》environment = " + environment + "sourceClass = " + sourceClass + "out = " + out);
    }
}
