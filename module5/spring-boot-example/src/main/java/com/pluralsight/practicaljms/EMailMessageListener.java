package com.pluralsight.practicaljms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@Service
public class EMailMessageListener {

    @JmsListener(destination = "Emails", concurrency = "3-10")
    public void onMessage(Email email) {
        System.out.println("Received: " + email);
    }

}
