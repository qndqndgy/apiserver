package com.my.template.kafka.handler;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.my.template.kafka.handller.KafkaMsgProducer;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaMsgProducerTest {
	
	@Autowired
	KafkaMsgProducer producer;
	
	@Test
	public void allPropertiesInitializedTest() throws Exception {
		assertNotNull(producer.getBootstrapServers());
		assertNotNull(producer.getAcks());
		assertNotNull(producer.getRetries());
		assertNotNull(producer.getBatchSize());
		assertNotNull(producer.getLingerMs());
		assertNotNull(producer.getBufferMemory());
	}

	@Test
	public void sendToKafkaQueueTest() throws Exception {
		producer.sendToKafkaQueue("test", "Hello Kafka".getBytes());
	}
}
