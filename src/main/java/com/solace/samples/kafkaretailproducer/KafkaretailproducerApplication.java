package com.solace.samples.kafkaretailproducer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class KafkaretailproducerApplication {

	public static String TOPIC_NAME = "solace.sample.retail.store.new.order";
	@Bean
	NewTopic testTopic() {
		return new NewTopic(TOPIC_NAME, 1, (short) 1);
	}

	public static void main(String[] args) {
		SpringApplication.run(KafkaretailproducerApplication.class, args);


	}

}
