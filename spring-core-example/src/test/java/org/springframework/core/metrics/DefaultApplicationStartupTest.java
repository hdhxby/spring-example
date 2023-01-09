package org.springframework.core.metrics;

import org.junit.jupiter.api.Test;

public class DefaultApplicationStartupTest {

    @Test
    public void test() {
        DefaultApplicationStartup defaultApplicationStartup = new DefaultApplicationStartup();
        DefaultApplicationStartup.DefaultStartupStep defaultStartupStep = defaultApplicationStartup.start(null);
        defaultStartupStep.end();
    }
}
