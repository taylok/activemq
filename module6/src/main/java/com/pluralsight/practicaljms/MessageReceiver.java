package com.pluralsight.practicaljms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@SuppressWarnings("Duplicates")
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destinationType",
                propertyValue = "javax.jms.Queue"),
//        @ActivationConfigProperty(
//                propertyName = "messageSelector",
//                propertyValue = "emailAddress='grant@grantlittle.me'"),
//New in EJB 3.2 (JEE7)
        @ActivationConfigProperty(
                propertyName = "destinationLookup",
                propertyValue = "java:/jms/queue/EmailsQueue"),
//For Wildfly (EJB 3.1)
//        @ActivationConfigProperty(
//                propertyName = "destination",
//                propertyValue = "java:/jms/queue/EmailsQueue"),
//        @ActivationConfigProperty(
//                propertyName = "useJNDI",
//                propertyValue = "true"),
})
public class MessageReceiver implements MessageListener{

    private static final Log LOG = LogFactory.getLog(MessageReceiver.class);

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage)message;
                LOG.info("Recieving email - Subject: "
                        + textMessage.getStringProperty("subject")
                        + " To: "
                        + textMessage.getStringProperty("emailAddress")
                        + " Content: "
                        + textMessage.getText());
            }
        } catch (JMSException e) {
            LOG.error(e);
        }
    }
}
