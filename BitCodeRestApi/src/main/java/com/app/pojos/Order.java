package com.app.pojos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name = "Orders")
public class Order extends BaseEntity {

	@CreationTimestamp
	private LocalDate date;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	@JsonIgnore
	private User user;
	
	private double amount;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	@JoinTable(
	        name = "Order_Course", 
	        joinColumns = { @JoinColumn(name = "orderId") }, 
	        inverseJoinColumns = {@JoinColumn(name = "courseId")})
	private List<Course> orderCourses = new ArrayList<>();
	
	public void addCartCourseToOrder(List<Course> course) {
		orderCourses.addAll(course);
	}
	
	@OneToOne(mappedBy = "order", cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY, orphanRemoval = true)
	@JsonIgnore
	private TransactionDetail transactionDetail;
	
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<Course> getOrderCourses() {
		return orderCourses;
	}

	public void setOrderCourses(List<Course> orderCourses) {
		this.orderCourses = orderCourses;
	}

	public TransactionDetail getTransactionDetail() {
		return transactionDetail;
	}

	public void setTransactionDetail(TransactionDetail transactionDetail) {
		this.transactionDetail = transactionDetail;
	}

	public Order(LocalDate date, User user, double amount, OrderStatus orderStatus, List<Course> orderCourses,
			TransactionDetail transactionDetail) {
		super();
		this.date = date;
		this.user = user;
		this.amount = amount;
		this.orderCourses = orderCourses;
		this.transactionDetail = transactionDetail;
		this.orderStatus = orderStatus;
	}

	public Order() {
		super();
	}
}