package com.pluralsight.practicaljms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.jms.*;
import java.io.IOException;

/**
 * Created by Grant Little grant@grantlittle.me
 */
@Stateless
@Startup
public class JsonMessageConverter implements MessageConverter {

    @Override
    public <T> Message serialize(JMSContext context, T value) {
        try {
            TextMessage textMessage = context.createTextMessage();
            return writeContentsToTextMessage(value, textMessage);
        }catch (JMSException | RuntimeException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

//    @Override
//    public <T> TextMessage serialize(Session session, T value) {
//        try {
//            TextMessage textMessage = session.createTextMessage();
//            return writeContentsToTextMessage(value, textMessage);
//        } catch (JMSException | RuntimeException | JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }
//    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void start() {
        objectMapper.configure(
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private <T> TextMessage writeContentsToTextMessage(
            T value,
            TextMessage textMessage)
            throws JsonProcessingException, JMSException {

        String text = objectMapper.writeValueAsString(value);
        textMessage.setText(text);
        textMessage.setStringProperty("_messageType", value.getClass().getName());
        return textMessage;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(Message message) {
        try {
            String className = message.getStringProperty("_messageType");
            Class klass = Class.forName(className);
            return (T)objectMapper.readValue(((TextMessage)message).getText(), klass);
        }catch (JMSException | ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
