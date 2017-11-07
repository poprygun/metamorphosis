package io.microsamples.metamorphosis.producer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.annotation.InboundChannelAdapter;
import org.springframework.integration.annotation.Poller;

import java.time.Instant;

@SpringBootApplication
@EnableBinding(Source.class)
@Slf4j
public class ProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProducerApplication.class, args);
	}

	@InboundChannelAdapter(value = Source.OUTPUT, poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "1"))
	public ChatMessage timerMessageSource() {
		log.info("Sending...");
		return new ChatMessage("Hi There", Instant.now());
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class ChatMessage {
		private String message;
		private Instant when;
	}
}
