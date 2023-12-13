package com.sunlife.eventsourcing;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
public class StockController {
    @Autowired
    private EventService eventService;

    @PostMapping("/stock")
    public void addStock(@RequestBody Stock stockRequest) throws JsonProcessingException {
        StockAddedEvent event = StockAddedEvent.builder().stockDetails(stockRequest).build();
        eventService.addEvent(event);
    }

    @DeleteMapping("/stock")
    public void removeStock(@RequestBody Stock stock) throws JsonProcessingException {
        StockRemovedEvent event = StockRemovedEvent.builder().stockDetails(stock).build();
        eventService.addEvent(event);
    }

    @GetMapping("/stock")
    public Stock getStock(@RequestParam("name") String name) throws JsonProcessingException {
        Iterable<EventStore> events = eventService.fetchAllEvents(name);
        Stock currentStock = new Stock();
        currentStock.setName(name);
        currentStock.setUser("NA");
        for (EventStore event : events) {
            Stock stock = new Gson().fromJson(event.getEventData(), Stock.class);
            if (event.getEventType().equals("STOCK_ADDED")) {
                currentStock.setQuantity(currentStock.getQuantity() + stock.getQuantity());
            } else if (event.getEventType().equals("STOCK_REMOVED")) {
                currentStock.setQuantity(currentStock.getQuantity() - stock.getQuantity());
            }
        }
        return currentStock;
    }

    @GetMapping("/events")
    public Iterable<EventStore> getEvents(@RequestParam("name") String name) throws JsonProcessingException {
        Iterable<EventStore> events = eventService.fetchAllEvents(name);
        return events;
    }

    //History of events.
    @GetMapping("/stock/history")
    public Stock getStockUntilDate(@RequestParam("date") String date, @RequestParam("name") String name) throws JsonProcessingException {

        String[] dateArray = date.split("-");

        LocalDateTime dateTill = LocalDate.of(Integer.parseInt(dateArray[0]), Integer.parseInt(dateArray[1]), Integer.parseInt(dateArray[2])).atTime(23, 59);


        Iterable<EventStore> events = eventService.fetchAllEventsTillDate(name, dateTill);

        Stock currentStock = new Stock();

        currentStock.setName(name);
        currentStock.setUser("NA");

        for (EventStore event : events) {

            Stock stock = new Gson().fromJson(event.getEventData(), Stock.class);

            if (event.getEventType().equals("STOCK_ADDED")) {

                currentStock.setQuantity(currentStock.getQuantity() + stock.getQuantity());
            } else if (event.getEventType().equals("STOCK_REMOVED")) {

                currentStock.setQuantity(currentStock.getQuantity() - stock.getQuantity());
            }
        }

        return currentStock;

    }


}
