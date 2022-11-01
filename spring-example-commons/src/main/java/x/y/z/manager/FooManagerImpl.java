package x.y.z.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class FooManagerImpl implements FooManager {

    private static final Logger logger = LoggerFactory.getLogger(FooManagerImpl.class);

    @Override
    public void foo() {
        logger.info("简单调用");
    }
}
