package com.guwei.DAO;

import java.sql.Connection;
import java.util.List;

import com.guwei.domain.Order;

public interface OrderDao {

	void save(Connection conn, Order order);

	List<Order> queryOrder(int uid);

	Order queryDetailsOrder(String id);

	void cancelOrder(Connection conn, String id);

	void updateOrderStatus(String orderid);

	List<Order> findAllNoPayOrder();

	/**
	 * @Title: save
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param order
	 * @return: void
	 * @throws
	 */
	void save(Order order);

	/**
	 * @Title: cancelOrder
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param id
	 * @return: void
	 * @throws
	 */
	void cancelOrder(String id);

}
