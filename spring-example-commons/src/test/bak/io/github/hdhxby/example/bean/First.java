package io.github.hdhxby.example.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class First {

    private static final Logger log = LoggerFactory.getLogger(First.class);

    public First() {
        log.debug("第一个Bean初始化");
    }
}
