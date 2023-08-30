package com.app.services;

import java.util.List;

import com.app.dtos.PlaceOrderDto;
import com.app.pojos.Order;

public interface OrdersService {
	String placeOrder(PlaceOrderDto placeOrderDto);
	String updateOrderStatus(Long orderid);
	List<Order> getAllPendingOrders();
	List<Order> getAllOrders();
}
