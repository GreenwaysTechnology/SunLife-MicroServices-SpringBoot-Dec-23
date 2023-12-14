package com.sunlife.eventsourcing;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class StockController {
    @Autowired
    private EventService eventService;

    @Autowired
    private StockRepo repo;

    @PostMapping("/stock")
    public void addStock(@RequestBody Stock stockRequest) throws JsonProcessingException {
        StockAddedEvent event = StockAddedEvent.builder().stockDetails(stockRequest).build();

        List<Stock> existingStockList = repo.findByName(stockRequest.getName());

        if (existingStockList != null && existingStockList.size() > 0) {

            Stock existingStock = existingStockList.get(0);

            int newQuantity = existingStock.getQuantity() + stockRequest.getQuantity();

            existingStock.setQuantity(newQuantity);
            existingStock.setUserName(stockRequest.getUserName());
            repo.save(existingStock);

        } else {

            repo.save(stockRequest);
        }
        eventService.addEvent(event);
    }

    @DeleteMapping("/stock")
    public void removeStock(@RequestBody Stock stock) throws JsonProcessingException {
        StockRemovedEvent event = StockRemovedEvent.builder().stockDetails(stock).build();
        int newQuantity = 0;

        List<Stock> existingStockList = repo.findByName(stock.getName());

        if (existingStockList != null && existingStockList.size() > 0) {

            Stock existingStock = existingStockList.get(0);

            newQuantity = existingStock.getQuantity() - stock.getQuantity();

            if (newQuantity <= 0) {
                repo.delete(existingStock);
            } else {
                existingStock.setQuantity(newQuantity);
                existingStock.setUserName(stock.getUserName());
                repo.save(existingStock);
            }
        }
        eventService.addEvent(event);
    }

    @GetMapping("/stock")
    public List<Stock> getStock(@RequestParam("name") String name) throws JsonProcessingException {
        return repo.findByName(name);
    }


}
