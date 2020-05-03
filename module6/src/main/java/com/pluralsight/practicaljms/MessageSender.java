package com.pluralsight.practicaljms;

import javax.ejb.Local;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.Session;
import java.util.function.Function;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@Local
public interface MessageSender {

    void sendMessage(Destination destination, Function<Session, Message> messageCreator);


}
