package com.lvbok.springcloud;

import com.lvbok.springcloud.entities.Payment;
import com.lvbok.springcloud.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.TestTemplate;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author luwei
 * @date 2020年09月04 16:46
 * @desc
 **/
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class PaymentTest {

    @Autowired
    PaymentService paymentService;

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Before
    public void setUp() throws MalformedURLException {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void getHello() {
        ResponseEntity<String> response = template.getForEntity(base.toString(), String.class);
        System.out.println(response.getBody());
    }

    @Test
    public void createTest() {
//        int create = paymentService.create(new Payment(null, "NO123456789"));
        Payment payment = paymentService.getPaymentById(1L);
        System.out.println(payment);
    }
}
