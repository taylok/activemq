package com.pluralsight.practicaljms;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@Service
public class OrderGenerator {

    private JmsTemplate jmsTemplate;

    public OrderGenerator(@Qualifier("topicJmsTemplate") JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @SuppressWarnings("Duplicates")
    @Scheduled(fixedDelay = 3000)
    public void generateOrder() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setAmount(BigDecimal.TEN);
        order.setReference("Reference1");
        jmsTemplate.convertAndSend("Orders", order);
    }
}
