package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.pojos.Order;
import com.app.pojos.OrderStatus;
import com.app.pojos.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	//List<Order> findByOrderStatus(OrderStatus orderStatus);
	List<Order> findAllByOrderStatus(OrderStatus orderStatus);
	Optional<Order> findByIdAndUser(Long id, User user);
}
