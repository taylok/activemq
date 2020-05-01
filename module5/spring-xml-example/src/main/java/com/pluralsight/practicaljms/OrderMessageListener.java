package com.pluralsight.practicaljms;

import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by Grant Little grant@grantlittle.me
 */
public class OrderMessageListener implements MessageListener {

    public void onMessage( Message order) {
        System.out.println("OrderMessageListener is receiving: " + order);
    }
}
