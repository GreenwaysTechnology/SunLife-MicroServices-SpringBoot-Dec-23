package com.sunlife;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/publish")
public class StockController {

    @Autowired
    private StreamBridge streamBridge;

    @PostMapping
    public String publish(@RequestBody Stock stock){
            streamBridge.send("stockEvent-out-0",stock);
        return "Message Published";

    }
}
