package org.springframework.messaging.core;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplateTest;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

public class GenericMessagingTemplateTest {

    private static final Logger logger = LoggerFactory.getLogger(GenericMessagingTemplateTest.class);
    @Test
    public void test() {
        GenericMessagingTemplate genericMessagingTemplate = new GenericMessagingTemplate();
        genericMessagingTemplate.send(MessageBuilder.withPayload(UUID.randomUUID()).build());
    }


    @Test
    public void test1() {
        GenericMessagingTemplate genericMessagingTemplate = new GenericMessagingTemplate();
        genericMessagingTemplate.setDefaultDestination(new MessageChannel() {
            @Override
            public boolean send(Message<?> message, long timeout) {
                logger.debug("");
                return false;
            }
        });
        genericMessagingTemplate.sendAndReceive(new MessageChannel() {
            @Override
            public boolean send(Message<?> message, long timeout) {
                return false;
            }
        });
        genericMessagingTemplate.send(MessageBuilder.withPayload(UUID.randomUUID()).build());
    }


    @Test
    public void test2() {
        GenericMessagingTemplate genericMessagingTemplate = new GenericMessagingTemplate();
        genericMessagingTemplate.setDestinationResolver(new DestinationResolver<MessageChannel>() {
            @Override
            public MessageChannel resolveDestination(String name) throws DestinationResolutionException {
                return new MessageChannel() {
                    @Override
                    public boolean send(Message<?> message, long timeout) {
                        return false;
                    }
                };
            }
        });
        genericMessagingTemplate.send(MessageBuilder.withPayload(UUID.randomUUID()).build());
    }
}
