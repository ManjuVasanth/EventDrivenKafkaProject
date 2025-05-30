package com.manju.eda.kafka_producer_wikimedia;
import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
//import com.launchdarkly.eventsource.background.BackgroundEventHandler;
//import com.launchdarkly.eventsource.background.BackgroundEventSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
public class WikimediaChangesProducer {
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);
    private KafkaTemplate<String,String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    public void sendMessage() throws Exception{
   // String topic = "wikimedia_recentchange";
    // to read real time stream data from wikimedia we use Event Source
       // BackgroundEventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate,topicName);
        EventHandler eventHandler = (EventHandler) new WikimediaChangesHandler(kafkaTemplate, topicName);
        String url = "https://stream.wikimedia.org/v2/stream/recentchange";
       /* BackgroundEventSource eventSource = new BackgroundEventSource.Builder(eventHandler, URI.create(url))
                .reconnectTime(Duration.ofSeconds(5))  // Set reconnection time in case of failure
                .build();*/
        EventSource.Builder builder = new EventSource.Builder(eventHandler, URI.create(url));
        EventSource eventSource = builder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(10);
    }
}
