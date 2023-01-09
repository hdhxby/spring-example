package org.springframework.messaging;

import io.github.hdhxby.example.messaging.MessageTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

public class MessageChannelTest {
    private static final Logger logger = LoggerFactory.getLogger(MessageChannelTest.class);

    @Test
    public void testMessageChannel() throws InterruptedException {
        MessageChannel executorSubscribableChannel = new MessageChannel() {
            @Override
            public boolean send(Message<?> message, long timeout) {
                logger.debug("{}", message);
                return true;
            }
        };
        // Message
        executorSubscribableChannel.send(MessageBuilder.withPayload(UUID.randomUUID().toString()).build());
    }
}
