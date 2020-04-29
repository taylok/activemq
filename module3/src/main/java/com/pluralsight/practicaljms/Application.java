package com.pluralsight.practicaljms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 0.1.0 Uses Generic Session Interface
 * 0.1.1 Uses QueueSession Interface
 * 0.1.2 Uses Generic Session Interface for topic
 * 0.1.3 Uses Topic Session Interface for topic
 * 0.2.0 Consuming messages from a Queue using polling (single message)
 */
public class Application {

    public static void main( String... args ) throws Exception {
        Application app = new Application();
        QueueConnectionFactory cf = app.createQueueConnectionFactory();
        QueueConnection conn = app.createQueueConnection(cf);
        QueueSession session = app.createQueueSession(conn);
        conn.start();
        Message message = app.consumeFromQueue(session, "TEST_DESTINATION");
        // Cast to TextMessage to get just the text
        TextMessage textMessage = (TextMessage) message;
        System.out.println(textMessage.getText());
        session.close();
        conn.close();
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

    public Message consumeFromQueue( Session session, String destination )
            throws JMSException {
        Queue queue = session.createQueue(destination);
        MessageConsumer consumer = session.createConsumer(queue);
        // Polling on one message
        return consumer.receive();
    }
}

