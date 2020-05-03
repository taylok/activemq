package com.pluralsight.practicaljms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import java.util.function.Function;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@SuppressWarnings("unused")
@Stateless
public class MessageSenderBean implements MessageSender{

    private Log LOG = LogFactory.getLog(MessageSenderBean.class);

    @Resource(lookup = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;

    public void sendMessage(Destination destination, Function<Session, Message> messageCreator)
    {
        LOG.info("Sending message");
        Connection connection = null;
        Session session = null;
        MessageProducer messageProducer = null;
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession();
            messageProducer = session.createProducer(destination);
            Message message = messageCreator.apply(session);
            messageProducer.send(message);
        } catch (JMSException e) {
            LOG.error("Exception encountered ");
        } finally {
            if (null != messageProducer) {
                try {
                    messageProducer.close();
                } catch (JMSException e) {
                    LOG.error(e);
                }
            }
            if (null != session) {
                try {
                    session.close();
                } catch (JMSException e) {
                    LOG.error(e);
                }
            }
            if (null != connection) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    LOG.error(e);
                }
            }
        }
    }

}
