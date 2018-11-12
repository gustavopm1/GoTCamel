package com.github.gustavopm1.gotcamel.queue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gustavopm1.gotcamel.models.Request;
import com.github.gustavopm1.gotcamel.models.Response;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.Movie;
import com.github.gustavopm1.gotcamel.models.themoviedb.movie.TypeValueData;
import com.github.gustavopm1.gotcamel.models.themoviedb.person.Person;
import com.github.gustavopm1.gotcamel.test.utils.TestNameUtils;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQMessage;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.IOException;

@Slf4j
public abstract class AbstractQueueTest {

    @Rule
    public TestName name = TestNameUtils.getTestName(AbstractQueueTest.class);

    @Autowired
    @Setter
    protected JmsTemplate jmsTemplate;

    protected Response<Movie> exchangeMovie(String queueName, TypeValueData data) throws IOException{

        ActiveMQMessage mqMessage = (ActiveMQMessage) jmsTemplate.sendAndReceive(queueName, buildMessage(data));
        String body = new String(mqMessage.getContent().getData());
        body = body.substring(body.indexOf("{"));
        return new ObjectMapper().readValue(body,new TypeReference<Response<Movie>>(){});

    }

    protected Response<Person> exchangePerson(String queueName, TypeValueData data) throws IOException{

        ActiveMQMessage mqMessage = (ActiveMQMessage) jmsTemplate.sendAndReceive(queueName, buildMessage(data));
        String body = new String(mqMessage.getContent().getData());
        body = body.substring(body.indexOf("{"));
        return new ObjectMapper().readValue(body,new TypeReference<Response<Person>>(){});

    }


    private MessageCreator buildMessage(TypeValueData data) throws JsonProcessingException {

        String messageString = new ObjectMapper().writeValueAsString(Request.<TypeValueData>builder().body(data).user("testUser-".concat(name.getMethodName())).build());

        return new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(messageString);
            }
        };
    }
}