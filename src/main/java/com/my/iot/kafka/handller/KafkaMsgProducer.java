package com.my.iot.kafka.handller;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Service
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
/**
 * Kafka로 메세지 전송 기능 제공하는 클래스
 * @author 효민영♥
 *
 */
public class KafkaMsgProducer {
	
	private Producer<String, byte[]> producer;
	
	@Value("${kafka.bootstrap.servers}")
	private String bootstrapServers;
	@Value("${kafka.acks}")
	private String acks;
	@Value("${kafka.retries}")
	private String retries;
	@Value("${kafka.batch.size}")
	private String batchSize;
	@Value("${kafka.linger.ms}")
	private String lingerMs;
	@Value("${kafka.buffer.memory}")
	private String bufferMemory;
	
	
	private Producer<String, byte[]> producer(){
		Properties props = new Properties();
		props.put("bootstrap.servers", bootstrapServers);
		props.put("acks", acks);
		props.put("retries", retries);
		props.put("batch.size", batchSize);
		props.put("linger.ms", lingerMs);
		props.put("buffer.memory", bufferMemory);
		props.put("key.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.ByteArraySerializer");

		Thread.currentThread().setContextClassLoader(null);
		Producer<String, byte[]> producer = new KafkaProducer<String, byte[]>(props);
		
		return producer;
	}
	
	public void sendToKafkaQueue(String topicName, byte[] msg) throws Exception {
		// 최초 1회에만 초기화 한다.
		if(this.producer == null) {
			producer = producer();
		}
		producer.send(new ProducerRecord<String, byte[]>(topicName, msg));
	}
}
