package com.pluralsight.practicaljms;

/**
 * Created by Grant Little grant@grantlittle.me
 */
public class OrderMessageListener {

    public void onMessage(Order order) {
        System.out.println("OrderMessageListener is receiving: " + order);
    }
}
