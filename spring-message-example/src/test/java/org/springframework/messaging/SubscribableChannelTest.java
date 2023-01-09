package org.springframework.messaging;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

public class SubscribableChannelTest {
    private static final Logger logger = LoggerFactory.getLogger(SubscribableChannelTest.class);



    @Test
    public void testSubscribableChannel() {

        SubscribableChannel subscribableChannel = new SubscribableChannel(){

            private MessageHandler messageHandler;

            /**
             * Send a message, blocking until either the message is accepted or the
             * specified timeout period elapses.
             *
             * @param message the message to send
             * @param timeout the timeout in milliseconds or {@link #INDEFINITE_TIMEOUT}
             * @return {@code true} if the message is sent, {@code false} if not
             * including a timeout of an interrupt of the send
             */
            @Override
            public boolean send(Message<?> message, long timeout) {
                messageHandler.handleMessage(message);
                return false;
            }

            /**
             * Register a message handler.
             *
             * @param handler
             * @return {@code true} if the handler was subscribed or {@code false} if it
             * was already subscribed.
             */
            @Override
            public boolean subscribe(MessageHandler handler) {
                this.messageHandler = handler;
                return false;
            }

            /**
             * Un-register a message handler.
             *
             * @param handler
             * @return {@code true} if the handler was un-registered, or {@code false}
             * if was not registered.
             */
            @Override
            public boolean unsubscribe(MessageHandler handler) {
                this.messageHandler = null;
                return false;
            }
        };


        MessageHandler messageHandler = new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                logger.debug("{}", message);
            }
        };
        subscribableChannel.subscribe(messageHandler);
        // Message
        subscribableChannel.send(MessageBuilder.withPayload(UUID.randomUUID().toString()).build());
    }
}
