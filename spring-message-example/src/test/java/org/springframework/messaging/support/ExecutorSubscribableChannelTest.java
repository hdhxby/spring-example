package org.springframework.messaging.support;

import io.github.hdhxby.example.messaging.MessageTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class ExecutorSubscribableChannelTest {

    private static final Logger logger = LoggerFactory.getLogger(ExecutorSubscribableChannelTest.class);

    @Test
    public void testMessageHandler() throws InterruptedException {
        // MessageChannel
        ExecutorSubscribableChannel executorSubscribableChannel = new ExecutorSubscribableChannel();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // MessageHandler
        executorSubscribableChannel.subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                logger.debug("{}", message);
                countDownLatch.countDown();
            }
        });

        // Message
        executorSubscribableChannel.send(MessageBuilder.withPayload(UUID.randomUUID().toString()).build());
        countDownLatch.await();
    }


    @Test
    public void testExecutorChannelInterceptor() throws InterruptedException {
        // MessageChannel
        ExecutorSubscribableChannel executorSubscribableChannel = new ExecutorSubscribableChannel();
        executorSubscribableChannel.addInterceptor(new ExecutorChannelInterceptor() {
            /**
             * Invoked inside the {@link Runnable} submitted to the Executor just before
             * calling the target MessageHandler to handle the message. Allows for
             * modification of the Message if necessary or when {@code null} is returned
             * the MessageHandler is not invoked.
             *
             * @param message the message to be handled
             * @param channel the channel on which the message was sent to
             * @param handler the target handler to handle the message
             * @return the input message, or a new instance, or {@code null}
             */
            @Override
            public Message<?> beforeHandle(Message<?> message, MessageChannel channel, MessageHandler handler) {
                logger.debug("beforeHandle");
                return message;
            }

            /**
             * Invoked inside the {@link Runnable} submitted to the Executor after calling
             * the target MessageHandler regardless of the outcome (i.e. Exception raised
             * or not) thus allowing for proper resource cleanup.
             * <p>Note that this will be invoked only if beforeHandle successfully completed
             * and returned a Message, i.e. it did not return {@code null}.
             *
             * @param message the message handled
             * @param channel the channel on which the message was sent to
             * @param handler the target handler that handled the message
             * @param ex      any exception that may have been raised by the handler
             */
            @Override
            public void afterMessageHandled(Message<?> message, MessageChannel channel, MessageHandler handler, Exception ex) {
                logger.debug("afterMessageHandled");
            }

            /**
             * Invoked before the Message is actually sent to the channel.
             * This allows for modification of the Message if necessary.
             * If this method returns {@code null} then the actual
             * send invocation will not occur.
             *
             * @param message
             * @param channel
             */
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                logger.debug("preSend");
                return message;
            }

            /**
             * Invoked immediately after the send invocation. The boolean
             * value argument represents the return value of that invocation.
             *
             * @param message
             * @param channel
             * @param sent
             */
            @Override
            public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
                logger.debug("postSend");
            }

            /**
             * Invoked after the completion of a send regardless of any exception that
             * have been raised thus allowing for proper resource cleanup.
             * <p>Note that this will be invoked only if {@link #preSend} successfully
             * completed and returned a Message, i.e. it did not return {@code null}.
             *
             * @param message
             * @param channel
             * @param sent
             * @param ex
             * @since 4.1
             */
            @Override
            public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
                logger.debug("afterSendCompletion");
            }

            /**
             * Invoked as soon as receive is called and before a Message is
             * actually retrieved. If the return value is 'false', then no
             * Message will be retrieved. This only applies to PollableChannels.
             *
             * @param channel
             */
            @Override
            public boolean preReceive(MessageChannel channel) {
                logger.debug("preReceive");
                return true;
            }

            /**
             * Invoked immediately after a Message has been retrieved but before
             * it is returned to the caller. The Message may be modified if
             * necessary; {@code null} aborts further interceptor invocations.
             * This only applies to PollableChannels.
             *
             * @param message
             * @param channel
             */
            @Override
            public Message<?> postReceive(Message<?> message, MessageChannel channel) {
                logger.debug("postReceive");
                return message;
            }

            /**
             * Invoked after the completion of a receive regardless of any exception that
             * have been raised thus allowing for proper resource cleanup.
             * <p>Note that this will be invoked only if {@link #preReceive} successfully
             * completed and returned {@code true}.
             *
             * @param message
             * @param channel
             * @param ex
             * @since 4.1
             */
            @Override
            public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
                logger.debug("afterReceiveCompletion");
            }
        });

        CountDownLatch countDownLatch = new CountDownLatch(1);
        // MessageHandler
        executorSubscribableChannel.subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                logger.debug("{}", message);
                countDownLatch.countDown();
            }
        });

        // Message
        executorSubscribableChannel.send(MessageBuilder.withPayload(UUID.randomUUID().toString()).build());
        countDownLatch.await();
    }
}
