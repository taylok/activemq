package com.pluralsight.practicaljms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@Service
public class OrderMessageListener {

    @JmsListener(destination = "Orders",
            containerFactory = "TopicListenerContainerFactory",
            subscription = "Orders")
    public void onMessage( Order order ) {
        System.out.println("OrderMessageListener is receiving: " + order);
    }
}
