package com.example.springboot.service;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import static com.example.springboot.constants.Constants.WIKIMEDIA_URL;

@Service
@Slf4j
public class WikimediaChangesProducer {
    @Value("${spring.kafka.topic.name}")
    private String topicName;
    private KafkaTemplate<String, String> kafkaTemplate;

    WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String msg) throws InterruptedException {

        //Reading real time stream data from k=wikimedia
        //To read realtime data , we use event source

        //Lets instantiate handler, it will be called everytime some new event came to
        //wilikemdia
        EventHandler eventHandler=new WikimediaChangesHandler(kafkaTemplate,topicName);

        //Lest create eventsource which will connect to source(wikimedia)
        //to read event data from wikimedia site
        EventSource.Builder builder=new EventSource.Builder(eventHandler, URI.create(WIKIMEDIA_URL));
        EventSource eventSource=builder.build();
        eventSource.start();

        TimeUnit.MINUTES.sleep(10);
    }
}
