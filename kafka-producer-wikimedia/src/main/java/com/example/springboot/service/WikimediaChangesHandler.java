package com.example.springboot.service;


import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WikimediaChangesHandler implements EventHandler {
    private KafkaTemplate<String,String> kafkaTemplate;
    private String topicName;
    WikimediaChangesHandler(KafkaTemplate<String,String> kafkaTemplate,String topicName){
        this.kafkaTemplate=kafkaTemplate;
        this.topicName=topicName;
    }


    /**
     * @throws Exception
     */
    @Override
    public void onOpen() throws Exception {

    }

    /**
     * @throws Exception
     */
    @Override
    public void onClosed() throws Exception {

    }

    /**
     * @param s
     * @param messageEvent
     * @throws Exception
     */
    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
           log.info(String.format("Event data -> %s",messageEvent.getData()));
           kafkaTemplate.send(topicName,messageEvent.getData());
    }

    /**
     * @param s
     * @throws Exception
     */
    @Override
    public void onComment(String s) throws Exception {

    }

    /**
     * @param throwable
     */
    @Override
    public void onError(Throwable throwable) {

    }
}


