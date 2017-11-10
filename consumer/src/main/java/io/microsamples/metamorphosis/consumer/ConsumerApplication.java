package io.microsamples.metamorphosis.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.util.function.Consumer;

@SpringBootApplication
@Slf4j
public class ConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

	@EnableBinding(Sink.class)
	@Configuration
	public class MessageHandler {

		@StreamListener
		public void handle(@Input(Sink.INPUT) Flux<ChatMessage> messages) {

			messages.log().subscribe(chatMessage -> log.info("Received: {}", chatMessage));

		}
	}
}
