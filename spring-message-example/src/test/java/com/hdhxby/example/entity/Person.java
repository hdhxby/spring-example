package com.hdhxby.example.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Person implements Speech {
    private static final Logger log = LoggerFactory.getLogger(Head.class);

//    @Autowired
//    private Head head;

    public Person() {
        log.debug("Person初始化");
    }

    @Override
    public void speech() {
        log.info("hello word.");
    }
}
