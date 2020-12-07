package com.lvbok;

import com.lvbok.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = SpringBootEsApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class TestEs {

    @Autowired
    BookService bookService;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test01() {
        System.out.println(bookService.findByTitle("哈喽"));
    }
}
