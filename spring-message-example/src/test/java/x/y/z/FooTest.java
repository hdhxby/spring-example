package x.y.z;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import x.y.z.bean.Foo;
import x.y.z.config.FooConfiguration;
import x.y.z.service.FooService;

public class FooTest {
    @Test
    public void test (){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(FooConfiguration.class);
        applicationContext.refresh();
//        applicationContext.getBean(FooService.class).foo();
        applicationContext.getBean(Foo.class);
    }
}
