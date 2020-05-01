package com.pluralsight.practicaljms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Grant Little grant@grantlittle.me
 */
public class EmailGenerator {

    private Log LOG = LogFactory.getLog(EmailGenerator.class);

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
                generateEmail();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }, 0L, 5000L, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        if (null != executorService && !executorService.isShutdown() && !executorService.isTerminated()) {
            executorService.shutdown();
        }
    }

    @SuppressWarnings("Duplicates")
    private void generateEmail() {
        Email email = new Email();
        email.setEmailAddress("test@example.com");
        email.setName("Grant");
        email.setMessage("This is an example email");
        LOG.info("Sending message to the Emails queue");
        jmsTemplate.convertAndSend("Emails", email);
    }
}
