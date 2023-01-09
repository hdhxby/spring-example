package org.springframework.messaging;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.support.MessageBuilder;

import java.util.Map;
import java.util.UUID;

public class MessageHeadersTest {
    @Test
    public void testCreateMessage() {
        Message<UUID> message = MessageBuilder.createMessage(UUID.randomUUID(),new MessageHeaders(Map.of("version","v1"),UUID.randomUUID(),System.currentTimeMillis()));
    }

    @Test
    public void testWithPayload() {
//        Message<UUID> message = MessageBuilder.withPayload(UUID.randomUUID())
//                .setHeaders()
//                .build();
    }
}
