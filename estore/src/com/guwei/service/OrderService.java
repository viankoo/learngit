package com.guwei.service;

import java.util.List;

import com.guwei.domain.Order;

public interface OrderService {

	boolean save(Order order);

	List<Order> queryOrder(int id);

	Order queryDetailsOrder(String id);

	boolean cancelOrder(String string);

	boolean canceltimeoutOrder();

}
