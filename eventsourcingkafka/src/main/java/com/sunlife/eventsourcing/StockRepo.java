package com.sunlife.eventsourcing;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface StockRepo extends CrudRepository<Stock, Integer> {

    List<Stock> findByName(String name);
}