package org.springframework.web.client;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;

public class RestTemplateTest {

    public static final Logger logger = LoggerFactory.getLogger(RestTemplateTest.class);

    @Test
    public void test(){
        RestTemplate restTemplate = new RestTemplateBuilder()
                .build();
        String result = restTemplate.getForObject("https://www.baidu.com", String.class);
        logger.debug(result);
    }

    @Test
    public void test2(){
        RestTemplate restTemplate = new RestTemplateBuilder()
                .build();
        ResponseEntity<String> result = restTemplate.getForEntity("https://www.baidu.com", String.class);
        System.out.println(result);
    }
}
