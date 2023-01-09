package org.springframework.messaging.simp;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.core.DestinationResolutionException;
import org.springframework.messaging.core.DestinationResolver;
import org.springframework.messaging.core.GenericMessagingTemplate;
import org.springframework.messaging.support.ExecutorSubscribableChannelTest;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

public class SimpMessagingTemplateTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpMessagingTemplateTest.class);
    @Test
    public void test() {
        SimpMessagingTemplate simpMessagingTemplate = new SimpMessagingTemplate(new MessageChannel() {
            @Override
            public boolean send(Message<?> message, long timeout) {
                return false;
            }
        });
        simpMessagingTemplate.send(MessageBuilder.withPayload(UUID.randomUUID()).build());
    }


}
