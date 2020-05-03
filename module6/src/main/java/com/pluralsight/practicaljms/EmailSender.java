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
public class EmailSender {
    private static Log LOG = LogFactory.getLog(EmailSender.class);

    @EJB
    private MessageSender messageSender;
    @Resource(lookup = "java:/jms/queue/EmailsQueue")
    private Queue emailsQueue;

    void sendEmail(String emailAddress,
                          String subject, String text) {
        messageSender.sendMessage(emailsQueue, session -> {
            try {
                TextMessage textMessage = session.createTextMessage();
                textMessage.setStringProperty("emailAddress", emailAddress);
                textMessage.setStringProperty("subject", subject);
                textMessage.setText(text);
                return textMessage;
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        });
    }



    private ScheduledExecutorService scheduledExecutorService;

    @PostConstruct
    public void start() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            sendEmail("grant@grantlittle.me",
                    "Test Message",
                    "This is a test!");
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
