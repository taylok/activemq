package com.pluralsight.practicaljms;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by Grant Little grant@grantlittle.me
 */
public class SynchronousMessaging {

    private Log LOG = LogFactory.getLog(SynchronousMessaging.class);

    private JmsTemplate jmsTemplate;
    private int id=0;


    private ScheduledExecutorService executorService;

    @Required
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }


    public void start() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            try {
                doSynchronously();
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
    private void doSynchronously() {
        Request request = new Request();
        request.setId(id++);

        //Store a reference to the generated message so we can get the message Id
        // after  it has been sent
        final AtomicReference<Message> correlationId = new AtomicReference<>();
        jmsTemplate.convertAndSend("REQUEST_QUEUE", request, message -> {
            correlationId.set(message);
            return message;
        });

        try { // For debugging.
              // Another process would copy the messageId and set correlationId from it,
              // process the message and put it to the RESPONSE_QUEUE
            String messageId = correlationId.get().getJMSMessageID();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        LOG.info("sending Request " + String.valueOf(request.getId()) + " with MessageId " + correlationId);

        try {
            String messageId = correlationId.get().getJMSMessageID();
            Response response = (Response)jmsTemplate
                    .receiveSelectedAndConvert("RESPONSE_QUEUE",
                            "JMSCorrelationID = '" + messageId + "'");
            LOG.info("Receive response " + response);
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

}
