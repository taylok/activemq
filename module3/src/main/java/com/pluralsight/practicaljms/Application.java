package com.pluarlsight.practicaljms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 *  0.1.0 Uses Generic Session Interface
 */
public class Application {

    public ConnectionFactory createConnectionFactory() {
        return new ActiveMQConnectionFactory(
                "tcp://localhost:61616"
        );
    }

    public Connection createConnection(ConnectionFactory cf)
            throws JMSException {
        return cf.createConnection();
    }

    public Session createSession(Connection connection)
            throws JMSException {
        return connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    public static void main( String... args ) throws Exception {
        Application app = new Application();
        ConnectionFactory cf = app.createConnectionFactory();
        Connection conn = app.createConnection(cf);
        Session session = app.createSession(conn);
        app.sendTextMessageToQueue("Test Message", session);
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
}
