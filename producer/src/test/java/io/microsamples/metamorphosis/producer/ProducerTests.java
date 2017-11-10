package io.microsamples.metamorphosis.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.cloud.stream.test.binder.MessageCollector;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.concurrent.BlockingQueue;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.cloud.stream.test.matcher.MessageQueueMatcher.receivesPayloadThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DirtiesContext
public class ProducerTests {


	@Autowired
	private Source channels;

	@Autowired
	private MessageCollector collector;

	@Test
	public void testMessages() {

		Message<ChatMessage> hi_there = MessageBuilder.withPayload(new ChatMessage("Hi There", Instant.now())).build();

		channels.output().send(hi_there);

		BlockingQueue<Message<?>> messages = this.collector.forChannel(channels.output());

		assertThat(messages, receivesPayloadThat(containsString("Hi There")));

	}

}
