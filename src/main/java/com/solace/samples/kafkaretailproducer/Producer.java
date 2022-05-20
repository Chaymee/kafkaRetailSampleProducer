package com.solace.samples.kafkaretailproducer;

import com.solace.sample.Order;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.log4j.Log4j2;
import lombok.RequiredArgsConstructor;


@Log4j2
@Component
@RequiredArgsConstructor
public class Producer {


    private final KafkaTemplate<String, Order> producer;
    private final NewTopic topic;

    @EventListener(ApplicationStartedEvent.class)
    public void produce() throws InterruptedException {
        // produce events
        boolean stop = false;
        while(stop != true) {
            final String key = "created";
            int recordNumber = 0;
            GenerateOrder order = new GenerateOrder();
             producer.send(topic.name(), key, order.getOrder()).addCallback(
                    result-> {
                        final RecordMetadata m;
                        if (result != null) {
                            m = result.getRecordMetadata();
                            log.info("Produced record to topic {} partition {} @ offset {}",
                                    m.topic(),
                                    m.partition(),
                                    m.offset());
                        }
                    },
                    exception-> log.error("Failed to produce message to kafka", exception));
            Thread.sleep(5000);
            }

        producer.flush();
    }

}
