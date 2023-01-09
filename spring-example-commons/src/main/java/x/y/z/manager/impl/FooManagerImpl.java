package x.y.z.manager.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.UsesJava8;
import org.springframework.stereotype.Component;
import x.y.z.manager.FooManager;
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class FooManagerImpl implements FooManager {

    private static final Logger logger = LoggerFactory.getLogger(FooManagerImpl.class);

    public static final String FOO = "#{'${foo}'}";

    @Value(FOO)
    private String foo;

    @Override
    public long count() {
        logger.debug(foo);
        return 1;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }
}
