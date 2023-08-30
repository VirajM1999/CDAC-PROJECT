package com.app.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dtos.PlaceOrderDto;
import com.app.pojos.Cart;
import com.app.pojos.Course;
import com.app.pojos.Order;
import com.app.pojos.OrderStatus;
import com.app.pojos.TransactionDetail;
import com.app.pojos.User;
import com.app.repositories.OrderRepository;
import com.app.repositories.TransactionsRepository;
import com.app.repositories.UserRepository;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {
	@Autowired
	private OrderRepository orderRepo;
	
	@Autowired
	private TransactionsRepository transactionsRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ModelMapper mapper;

	@Override
	public String placeOrder(PlaceOrderDto placeOrderDto) {
		User user = userRepo.findById(placeOrderDto.getUserid()).orElseThrow(() -> new RuntimeException("User not found"));
		Cart cart = user.getCart();
		Order order = mapper.map(placeOrderDto, Order.class);
		TransactionDetail transactionDetail = mapper.map(placeOrderDto, TransactionDetail.class);
		
		order.setOrderStatus(OrderStatus.PENDING);
		order.addCartCourseToOrder(cart.getCartCourses());
		order.setTransactionDetail(transactionDetail);
		order.setUser(user);
		
		transactionDetail.setOrder(order);
		
		user.addCourseToUser(cart.getCartCourses());
		
		cart.emptyCartCourses();
		
		orderRepo.save(order);
		transactionsRepo.save(transactionDetail);
		return "Order Placed Successfully";
	}

	@Override
	public String updateOrderStatus(Long orderid) {
		Order order = orderRepo.findById(orderid).orElseThrow(() -> new RuntimeException("Order not found"));
		order.setOrderStatus(OrderStatus.APPROVED);
		return "Order Status Updated Successfully";
	}

	@Override
	public List<Order> getAllPendingOrders() {
		List<Order> listPendingOrders = orderRepo.findAllByOrderStatus(OrderStatus.PENDING);
		return listPendingOrders;
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> listOrders = orderRepo.findAll();
		return listOrders;
	}
}
