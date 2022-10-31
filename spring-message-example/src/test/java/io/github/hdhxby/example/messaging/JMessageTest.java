package io.github.hdhxby.example.messaging;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
public class JMessageTest {

    /**
     * 单播
     */
    @Test
    public void a(){
        Jm
        SubscribableChannel messageChannel = new DirectChannel();

        messageChannel.subscribe(msg -> {
            log.info("receive1: " + msg.getPayload());
        });

        messageChannel.subscribe(msg -> {
            log.info("receive2: " + msg.getPayload());
        });

        messageChannel.send(MessageBuilder.withPayload("msg from alibaba").build());
        messageChannel.send(MessageBuilder.withPayload("msg from alibaba").build());
    }

    /**
     * 广播
     */
    @Test
    public void b(){
        SubscribableChannel messageChannel = new PublishSubscribeChannel();

        messageChannel.subscribe(msg -> {
            System.out.println("receive1: " + msg.getPayload());
        });

        messageChannel.subscribe(msg -> {
            System.out.println("receive2: " + msg.getPayload());
        });

        messageChannel.send(MessageBuilder.withPayload("msg from alibaba").build());
        messageChannel.send(MessageBuilder.withPayload("msg from alibaba").build());
    }
}
