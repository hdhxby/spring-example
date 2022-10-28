package io.github.thinkframework.messaging;

import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.*;

public class A {
    public void a(){
        MessageChannel messageChannel = new DirectChannel();
        ((DirectChannel) messageChannel).subscribe(new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                System.out.println(message.getPayload());
            }
        })
        messageChannel.send(MessageBuilder.withPayload("msg from alibaba").build());
    }
}
