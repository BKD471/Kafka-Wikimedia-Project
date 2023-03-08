package com.example.springboot.service;

import com.example.springboot.entity.WikimediaData;
import com.example.springboot.repository.WikimediaDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {
    private WikimediaDataRepository dataRepository;
    KafkaConsumer(WikimediaDataRepository dataRepository){
        this.dataRepository=dataRepository;
    }

    @KafkaListener(topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String eventMessage) {
        log.info(String.format("Event msg is -> %s", eventMessage));

        WikimediaData bigEventData=new WikimediaData();
        bigEventData.setWikiEventData(eventMessage);
        dataRepository.save(bigEventData);
    }
}
