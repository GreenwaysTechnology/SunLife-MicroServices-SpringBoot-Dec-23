package com.example.orders;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrdersService service;

	@GetMapping
	public String orders(){
		return "order";
	}

	@PostMapping
	public Map<String,Object> createOrder(@RequestBody Map<String,Object> order) {

		return this.service.createOrder(order);

	}
}
