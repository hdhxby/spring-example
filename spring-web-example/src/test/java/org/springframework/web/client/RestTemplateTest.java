package org.springframework.web.client;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockMvcClientHttpRequestFactory;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import x.y.z.web.rest.FooResource;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestTemplateTest {

    public static final Logger logger = LoggerFactory.getLogger(RestTemplateTest.class);

    @Test
    public void test() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(new FooResource())
                .build();
//        mockMvc.perform(MockMvcRequestBuilders.get("/api"));

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new MockMvcClientHttpRequestFactory(mockMvc));
        String result = restTemplate.getForObject("/api/string", String.class);
        logger.debug(result);
    }

    @Test
    public void test2(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> result = restTemplate.getForEntity("https://www.baidu.com", String.class);
        System.out.println(result);
    }
}
