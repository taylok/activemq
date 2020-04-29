package com.pluralsight.practicaljms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 0.1.0 Uses Generic Session Interface
 * 0.1.1 Uses QueueSession Interface
 * 0.1.2 Uses Generic Session Interface for topic
 * 0.1.3 Uses Topic Session Interface for topic
 * 0.2.0 Consuming messages from a Queue using polling (single message)
 * 0.2.1 Consuming messages from a Queue using polling (loop)
 * 0.2.2 Consuming messages from a Queue using listener (preferred way)
 * 0.2.3 Consuming messages from a Topic using listener
 * 0.2.4 Consuming messages from a Topic with Durable subscription using listener
 */
public class Application {

    public static void main(String... args) throws Exception {
        Application app = new Application();
        ConnectionFactory cf = app.createConnectionFactory();
        Connection conn = app.createConnection(cf);
        conn.setClientID("MyUniqueClientId");
        Session session = app.createSession(conn);
        TopicSubscriber topicSubscriber =
                app.consumeFromTopic(session,
                        "TEST_TOPIC",
                        (message -> {
                            if (message instanceof TextMessage) {
                                TextMessage txtMsg = (TextMessage)message;
                                try {
                                    System.out.println(txtMsg.getText());
                                } catch (JMSException e) {
                                    e.printStackTrace();
                                }
                            }
                        }));
        conn.start();

        //Free resources
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                try {
                    super.run();
                    conn.stop();
                    topicSubscriber.close();
                    session.close();
                    conn.close();

                    //If you are finished with the subscription
                    session.unsubscribe("test-subscription");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        while (true) {
            app.sendTextMessageToTopic("Test Message", session);
        }

    }


    public ConnectionFactory createConnectionFactory() {
        return new ActiveMQConnectionFactory(
                "tcp://localhost:61616"
        );
    }

    public QueueConnectionFactory createQueueConnectionFactory() {
        return new ActiveMQConnectionFactory(
                "tcp://localhost:61616"
        );
    }

    public TopicConnectionFactory createTopicConnectionFactory() {
        return new ActiveMQConnectionFactory(
                "tcp://localhost:61616"
        );
    }

    public Connection createConnection( ConnectionFactory cf )
            throws JMSException {
        return cf.createConnection();
    }

    public QueueConnection createQueueConnection( QueueConnectionFactory cf )
            throws JMSException {
        return cf.createQueueConnection();
    }

    public TopicConnection createTopicConnection( TopicConnectionFactory cf )
            throws JMSException {
        return cf.createTopicConnection();
    }

    public Session createSession( Connection connection )
            throws JMSException {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public QueueSession createQueueSession( QueueConnection connection )
            throws JMSException {
        return connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public TopicSession createTopicSession( TopicConnection connection )
            throws JMSException {
        return connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public void sendTextMessageToQueue( String message,
                                        Session session ) throws JMSException {
        Queue queue = session.createQueue("TEST_DESTINATION");
        TextMessage msg = session.createTextMessage(message);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.send(msg);
    }

    public void sendTextMessageToQueue( String message,
                                        QueueSession session ) throws JMSException {
        Queue queue = session.createQueue("TEST_DESTINATION");
        TextMessage msg = session.createTextMessage(message);
        QueueSender messageProducer = session.createSender(queue);
        messageProducer.send(msg);
    }

    public void sendTextMessageToTopic( String message,
                                        Session session ) throws JMSException {
        Topic queue = session.createTopic("TEST_TOPIC");
        TextMessage msg = session.createTextMessage(message);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.send(msg);
    }

    public void sendTextMessageToTopic( String message,
                                        TopicSession session ) throws JMSException {
        Topic topic = session.createTopic("TEST_TOPIC");
        TextMessage msg = session.createTextMessage(message);
        TopicPublisher topicPublisher = session.createPublisher(topic);
        topicPublisher.send(msg);
    }

    public MessageConsumer consumeFromQueue( Session session,
                                             String destination,
                                             MessageListener messageListener )
            throws JMSException {
        Queue queue = session.createQueue(destination);
        MessageConsumer consumer = session.createConsumer(queue);
        // Polling for messages using listener
        consumer.setMessageListener(messageListener);
        return consumer;
    }

    public TopicSubscriber consumeFromTopic(Session session,
                                            String destination,
                                            MessageListener messageListener)
            throws JMSException {
        Topic topic = session.createTopic(destination);
        TopicSubscriber consumer = session.createDurableSubscriber(topic,
                "test-subscription");
        consumer.setMessageListener(messageListener);
        return consumer;
    }
}

