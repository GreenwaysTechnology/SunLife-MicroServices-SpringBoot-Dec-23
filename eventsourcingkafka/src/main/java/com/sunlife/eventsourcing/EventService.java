package com.sunlife.eventsourcing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class EventService {

    @Autowired
    private KafkaTemplate<String, Object> template;

    public void addEvent(StockAddedEvent event) throws JsonProcessingException {
        EventRecord eventRecord = new EventRecord();
        eventRecord.setEventData(new ObjectMapper().writeValueAsString(event.getStockDetails()));
        eventRecord.setEventType(StockStatus.STOCK_ADDED.name());
        eventRecord.setEventId(UUID.randomUUID().getMostSignificantBits());
        eventRecord.setEntityId(event.getStockDetails().getName());
        eventRecord.setEventTime(LocalDateTime.now());
        CompletableFuture<SendResult<String, Object>> future = template.send("stock", eventRecord);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + eventRecord +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        eventRecord + "] due to : " + ex.getMessage());
            }
        });
    }

    public void addEvent(StockRemovedEvent event) throws JsonProcessingException {
        EventRecord eventRecord = new EventRecord();
        eventRecord.setEventData(new ObjectMapper().writeValueAsString(event.getStockDetails()));
        eventRecord.setEventType(StockStatus.STOCK_REMOVED.name());
        eventRecord.setEventId(UUID.randomUUID().getMostSignificantBits());
        eventRecord.setEntityId(event.getStockDetails().getName());
        eventRecord.setEventTime(LocalDateTime.now());
        CompletableFuture<SendResult<String, Object>> future = template.send("stock", eventRecord);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + eventRecord +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        eventRecord + "] due to : " + ex.getMessage());
            }
        });
    }


}
