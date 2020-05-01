package com.pluralsight.practicaljms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import javax.jms.Message;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@Service
public class OrderMessageListener {

    @JmsListener(destination = "Orders",
            containerFactory = "TopicListenerContainerFactory",
            subscription = "Orders")
    public void onMessage( Message message ) {
        System.out.println("OrderMessageListener is receiving: " + message);
    }
}
