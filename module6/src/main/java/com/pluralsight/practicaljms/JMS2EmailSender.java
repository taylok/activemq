package com.pluralsight.practicaljms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.TextMessage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@SuppressWarnings("Duplicates")
@Singleton
@Startup
public class JMS2EmailSender {
    private static Log LOG = LogFactory.getLog(JMS2EmailSender.class);

    @EJB
    private JMS2MessageSender messageSender;
    @Resource(lookup = "java:/jms/queue/EmailsQueue")
    private Queue emailsQueue;

    void sendEmail(String emailAddress,
                          String subject, String text) {
        messageSender.sendMessage(emailsQueue, context -> {
            try {
                TextMessage textMessage = context.createTextMessage();
                textMessage.setStringProperty("emailAddress", emailAddress);
                textMessage.setStringProperty("subject", subject);
                textMessage.setText(text);
                return textMessage;
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Sending Business Object
     * @param email
     */
    void sendEmail(Email email) {
        messageSender.sendMessage(emailsQueue, email);
    }

    private ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void start() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            Email email = new Email("grant@grantlittle.me",
                    "Test Message",
                    "This is a test!");
            sendEmail(email);
            LOG.info("Sent email");

        }, 5000L, 5000L, TimeUnit.MILLISECONDS);
    }
    @PreDestroy
    public void stop() {
        if (null != scheduledExecutorService
                && !scheduledExecutorService.isShutdown()
                && !scheduledExecutorService.isTerminated()) {
            scheduledExecutorService.shutdown();
        }
    }

}
