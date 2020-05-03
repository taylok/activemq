package com.pluralsight.practicaljms;

import javax.ejb.Local;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Message;
import java.util.function.Function;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@Local
public interface JMS2MessageSender {

    <T extends Object> void sendMessage(Destination destination, T businessObject);

    void sendMessage(Destination destination, Function<JMSContext, Message> messageCreator);

}
