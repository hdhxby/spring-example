package x.y.z.factory;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;
import x.y.z.bean.Foo;

@Component
public class FooFactory implements FactoryBean<Foo> {

    @Override
    public Foo getObject() throws Exception {
        return new Foo();
    }

    @Override
    public Class<?> getObjectType() {
        return Foo.class;
    }
}
