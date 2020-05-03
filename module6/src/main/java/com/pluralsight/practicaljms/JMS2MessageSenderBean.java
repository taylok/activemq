package com.pluralsight.practicaljms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;
import java.util.function.Function;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@SuppressWarnings("unused")
@Stateless
public class JMS2MessageSenderBean implements JMS2MessageSender {

    private Log LOG = LogFactory.getLog(JMS2MessageSenderBean.class);

    @Inject
    JMSContext context;

    @EJB
    MessageConverter messageConverter;

    public <T extends Object> void sendMessage(Destination destination, T businessObject) {
        LOG.info("Sending business object");
        Message message = messageConverter.serialize(context, businessObject);
        context.createProducer().send(destination, message);
    }

    // Implementation without MessageConverter
    public void sendMessage(Destination destination,
                            Function<JMSContext, Message> messageCreator) {
        try {
            LOG.info("Sending text message");
            JMSProducer jmsProducer = context.createProducer();
            jmsProducer.send(destination, messageCreator.apply(context));
        } catch (MessageFormatRuntimeException
                | InvalidDestinationRuntimeException
                | MessageNotWriteableRuntimeException
                | JMSSecurityRuntimeException e) {
            LOG.error(e);
        }
    }


}
