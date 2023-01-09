package org.springframework.validation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import x.y.z.bean.Foo;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataBinderStandardBeanExpressionResolverTest {

    @Test
    public void test(){
        Foo foo = new Foo();
        DataBinder dataBinder = new DataBinder(foo);
        dataBinder.bind(new MutablePropertyValues()
                .addPropertyValues(Map
                        .of("name", "foo",
                                "bar.name","bar"
                        )));
        assertEquals("foo", foo.getName());
        assertEquals("bar", foo.getBar().getName());
    }

}
