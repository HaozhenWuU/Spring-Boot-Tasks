package com.example.starbucksrest;

import org.springframework.data.repository.CrudRepository;

import com.example.starbucksrest.Order;


public interface OrderRepo extends CrudRepository<Order, Integer> {

	Order findByOrderNumber(String orderNumber);
}
