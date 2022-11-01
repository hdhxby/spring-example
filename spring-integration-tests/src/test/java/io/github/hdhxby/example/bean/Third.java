package io.github.hdhxby.example.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Third {

    private static final Logger log = LoggerFactory.getLogger(Third.class);

    @Autowired
    private Second second;
    
    public Third() {
        log.debug("第三个Bean初始化");
    }

    public Second getSecond() {
        return second;
    }

    public void setSecond(Second second) {
        this.second = second;
    }
}
