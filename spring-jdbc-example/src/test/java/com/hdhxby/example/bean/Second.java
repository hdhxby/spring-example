package com.hdhxby.example.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Second {

    private static final Logger log = LoggerFactory.getLogger(Second.class);

    @Autowired
    private Third third;

    public Second() {
        log.debug("第二个Bean初始化");
    }

    public Third getThird() {
        return third;
    }

    public void setThird(Third third) {
        this.third = third;
    }
}
