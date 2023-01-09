package org.springframework.web.bind;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import x.y.z.bean.Foo;

public class WebDataBinderTest {

    @Test
    public void test(){
        Foo foo = new Foo();

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.request(HttpMethod.GET,
                "http://localhost:8080?name=foo&bar.name=bar")
                .contentType(MediaType.APPLICATION_JSON);
        MockHttpServletRequest mockHttpServletRequest = mockHttpServletRequestBuilder.buildRequest(new MockServletContext());
        WebDataBinder webDataBinder = new WebDataBinder(foo);
        webDataBinder.bind(new ServletRequestParameterPropertyValues(mockHttpServletRequest));

        System.out.println(foo);
    }

    @Test
    public void test1(){
        Foo foo = new Foo();

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.request(HttpMethod.POST,
                        "http://localhost:8080")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("""
                   name=foo&bar.name=bar
                """);
        MockHttpServletRequest mockHttpServletRequest = mockHttpServletRequestBuilder.buildRequest(new MockServletContext());
        WebDataBinder webDataBinder = new WebDataBinder(foo);
        webDataBinder.bind(new ServletRequestParameterPropertyValues(mockHttpServletRequest));

        System.out.println(foo);
    }

    @Test
    public void test2(){
        Foo foo = new Foo();

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.request(HttpMethod.POST,
                        "http://localhost:8080")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                        "name" : "foo",
                        "bar" : {
                            "name" : "bar"
                        }
                    }
                """);
        MockHttpServletRequest mockHttpServletRequest = mockHttpServletRequestBuilder.buildRequest(new MockServletContext());
        WebDataBinder webDataBinder = new WebDataBinder(foo);
        webDataBinder.bind(new ServletRequestParameterPropertyValues(mockHttpServletRequest));

        System.out.println(foo);
    }
}
