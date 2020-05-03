package com.pluralsight.practicaljms;

import javax.jms.JMSContext;
import javax.jms.Message;

/**
 * Created by Grant Little grant@grantlittle.me
 */
public interface MessageConverter {

    <T extends Object> Message serialize(JMSContext context, T value);

//    <T extends Object> Message serialize(Session session, T value);
//
    <T extends Object> T deserialize(Message message);
}
