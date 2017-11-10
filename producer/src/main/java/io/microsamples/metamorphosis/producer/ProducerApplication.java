package io.microsamples.metamorphosis.producer;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableBinding(Source.class)
@Slf4j
public class ProducerApplication {



    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }


    @RestController
    public class GreetingController {

        @Autowired
        private Source channels;

        private List<Message<ChatMessage>> sentMessages = new ArrayList<>();

        @GetMapping("/send")
        private List<Message<ChatMessage>> sendMessage(){
            Message<ChatMessage> hi_there = MessageBuilder.withPayload(randomMessage())
                    .setHeader(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                    .build();

            channels.output().send(hi_there);

            sentMessages.add(hi_there);

            return sentMessages;
        }

        private ChatMessage randomMessage(){
            EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                    .seed(123L)
                    .objectPoolSize(10)
                    .randomizationDepth(3)
                    .charset(Charset.forName("UTF-8"))
                    .stringLengthRange(5, 50)
                    .collectionSizeRange(1, 10)
                    .scanClasspathForConcreteTypes(false)
                    .overrideDefaultInitialization(false)
                    .build();

            return random.random(ChatMessage.class);
        }

    }

}


