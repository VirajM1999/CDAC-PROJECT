package com.app.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dtos.PlaceOrderDto;
import com.app.pojos.Order;
import com.app.services.OrdersService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/orders")
public class OrdersController {

	@Autowired
	private OrdersService ordersService;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/getAllOrders")
	public ResponseEntity<?> getAllOrders() {
		List<Order> listPendingOrders = ordersService.getAllOrders();
		return new ResponseEntity<>(listPendingOrders, HttpStatus.OK);
	}
	
	@GetMapping("/getPendingOrders")
	public ResponseEntity<?> getAllPendingOrders() {
		List<Order> listPendingOrders = ordersService.getAllPendingOrders();
		return new ResponseEntity<>(listPendingOrders, HttpStatus.OK);
	}
	
	@PostMapping("/placeOrder")
	public ResponseEntity<?> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
		return new ResponseEntity<>(ordersService.placeOrder(placeOrderDto), HttpStatus.OK);
	}
	
	@GetMapping("/approveOrder/{orderid}")
	public ResponseEntity<?> approveOrder(@PathVariable("orderid") Long id) {
		return new ResponseEntity<>(ordersService.updateOrderStatus(id), HttpStatus.OK);
	}
}