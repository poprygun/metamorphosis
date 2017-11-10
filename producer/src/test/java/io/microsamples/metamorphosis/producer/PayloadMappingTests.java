package io.microsamples.metamorphosis.producer;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Test;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.support.json.Jackson2JsonMessageParser;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonInboundMessageMapper;
import org.springframework.messaging.Message;

import java.time.Instant;

public class PayloadMappingTests {

    private Jackson2JsonObjectMapper mapper;

    @Before
    public void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        objectMapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        mapper = new Jackson2JsonObjectMapper(objectMapper);
    }

    @Test
    public void should_parse_message_from_object() throws Exception {
        ChatMessage chatMessage = new ChatMessage("Hi there", Instant.now());

        Jackson2JsonMessageParser messageParser = new Jackson2JsonMessageParser(mapper);
        JsonInboundMessageMapper jsonInboundMessageMapper
                = new JsonInboundMessageMapper(ChatMessage.class, messageParser);

        Message<ChatMessage> message = MessageBuilder.withPayload(chatMessage).build();

        String jsonMessage = mapper.toJson(message);
        jsonInboundMessageMapper.toMessage(jsonMessage);
    }
}
