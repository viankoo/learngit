package com.guwei.DAO;

import java.sql.Connection;
import java.util.List;

import com.guwei.domain.OrderItem;

public interface OrderItemDao {

	void save(Connection conn, OrderItem item);

	List<OrderItem> selectOrderItemByoid(String id);

	void clearOrderItem(Connection conn, String id);

	void cancelOrderItem(Connection conn, String id);

	List<OrderItem> selectOrderItem(Connection conn, String id);

	/**
	 * @Title: save
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param item
	 * @return: void
	 * @throws
	 */
	void save(OrderItem item);

	/**
	 * @Title: cancelOrderItem
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param id
	 * @return: void
	 * @throws
	 */
	void cancelOrderItem(String id);

}
