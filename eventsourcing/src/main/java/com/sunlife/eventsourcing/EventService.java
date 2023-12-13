package com.sunlife.eventsourcing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EventService {

    @Autowired
    private EventRepository repository;

    public void addEvent(StockAddedEvent event) throws JsonProcessingException {
        EventStore eventStore = new EventStore();
        eventStore.setEventData(new ObjectMapper().writeValueAsString(event.getStockDetails()));
        eventStore.setEventType("STOCK_ADDED");
        eventStore.setEntityId(event.getStockDetails().getName());
        eventStore.setEventTime(LocalDateTime.now());
        repository.save(eventStore);
    }

    public void addEvent(StockRemovedEvent event) throws JsonProcessingException {
        EventStore eventStore = new EventStore();
        eventStore.setEventData(new ObjectMapper().writeValueAsString(event.getStockDetails()));
        eventStore.setEventType("STOCK_REMOVED");
        eventStore.setEntityId(event.getStockDetails().getName());
        eventStore.setEventTime(LocalDateTime.now());
        repository.save(eventStore);
    }

    public Iterable<EventStore> fetchAllEvents(String name) {
        return repository.findByEntityId(name);
    }

    public Iterable<EventStore> fetchAllEventsTillDate(String name, LocalDateTime date) {
        return repository.findByEntityIdAndEventTimeLessThanEqual(name, date);
    }
}
