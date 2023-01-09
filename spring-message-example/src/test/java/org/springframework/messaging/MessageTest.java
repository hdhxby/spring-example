package org.springframework.messaging;

import org.junit.jupiter.api.Test;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

public class MessageTest {
    @Test
    public void testCreateMessage() {
        Message<UUID> message = MessageBuilder.createMessage(UUID.randomUUID(),null);
    }

    @Test
    public void testWithPayload() {
        Message<UUID> message = MessageBuilder.withPayload(UUID.randomUUID())
                .build();
    }
}
