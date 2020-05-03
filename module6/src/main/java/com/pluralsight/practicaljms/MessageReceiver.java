package com.pluralsight.practicaljms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

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

    @EJB
    MessageConverter messageConverter;

    @Override
    public void onMessage(Message message) {
        Email email = messageConverter.deserialize(message);
        LOG.info("Sending email - Subject: "
                + email.getSubject()
                + " To: "
                + email.getEmailAddress()
                + " Content: "
                + email.getText());
    }
}
