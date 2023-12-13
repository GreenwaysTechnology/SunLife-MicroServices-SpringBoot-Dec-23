package com.sunlife.eventsourcing;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface EventRepository extends CrudRepository<EventStore, Long> {

    Iterable<EventStore> findByEntityId(String entityId);

    Iterable<EventStore> findByEntityIdAndEventTimeLessThanEqual(String entityId, LocalDateTime date);
}
