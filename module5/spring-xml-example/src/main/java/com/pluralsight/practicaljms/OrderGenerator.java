package com.pluralsight.practicaljms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;

import javax.jms.JMSException;
import javax.jms.Message;
import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Grant Little grant@grantlittle.me
 */
public class OrderGenerator {

    private Log LOG = LogFactory.getLog(OrderGenerator.class);

    private JmsTemplate jmsTemplate;

    private ScheduledExecutorService executorService;

    @Required
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void start() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                generateOrder();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }, 0L, 3000L, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (null != executorService && !executorService.isShutdown() && !executorService.isTerminated()) {
            executorService.shutdown();
        }
    }

    @SuppressWarnings("Duplicates")
    private void generateOrder() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setAmount(BigDecimal.TEN);
        order.setReference("Reference1");
        LOG.info("Sending message to the Orders queue");

        jmsTemplate.convertAndSend("Orders", order, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws JMSException {
                message.setStringProperty("JMSXGroupID", order.getOrderId().toString());
                message.setJMSPriority(1);
                return message;
            }
        });
    }
}
