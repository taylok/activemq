package com.pluralsight.practicaljms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *  0.1.0 Uses Generic Session Interface
 *  0.1.1 Uses QueueSession Interface
 */
public class Application {

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

    public Connection createConnection(ConnectionFactory cf)
            throws JMSException {
        return cf.createConnection();
    }

    public QueueConnection createQueueConnection(QueueConnectionFactory cf)
            throws JMSException {
        return cf.createQueueConnection();
    }

    public Session createSession(Connection connection)
            throws JMSException {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public QueueSession createQueueSession(QueueConnection connection)
            throws JMSException {
        return connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public static void main( String... args ) throws Exception {
        Application app = new Application();
        QueueConnectionFactory cf = app.createQueueConnectionFactory();
        QueueConnection conn = app.createQueueConnection(cf);
        QueueSession session = app.createQueueSession(conn);
        app.sendTextMessageToQueue("Another Message", session);
        session.close();
        conn.close();
    }

    public void sendTextMessageToQueue(String message,
                                       Session session) throws JMSException {
        Queue queue = session.createQueue("TEST_DESTINATION");
        TextMessage msg = session.createTextMessage(message);
        MessageProducer messageProducer = session.createProducer(queue);
        messageProducer.send(msg);
    }

    public void sendTextMessageToQueue(String message,
                                       QueueSession session) throws JMSException {
        Queue queue = session.createQueue("TEST_DESTINATION");
        TextMessage msg = session.createTextMessage(message);
        QueueSender messageProducer = session.createSender(queue);
        messageProducer.send(msg);
    }
}
