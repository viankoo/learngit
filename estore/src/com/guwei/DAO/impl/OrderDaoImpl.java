package com.guwei.DAO.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.guwei.DAO.OrderDao;
import com.guwei.domain.Order;
import com.guwei.utils.JDBCUtils;
import com.guwei.utils.TranscationManager;

public class OrderDaoImpl implements OrderDao {

	QueryRunner runner = new QueryRunner();

	@Override
	public void save(Connection conn, Order o) {
		String sql = "insert into orders values(?,?,?,?,?,null,?,?)";
		try {
			runner.update(conn, sql, o.getId(), o.getUid(), o.getTotalprice(),
					o.getAddress(), o.getStatus(), o.getAcceptperson(),
					o.getTelephone());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Order> queryOrder(int id) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where uid=?";
		try {
			return qr.query(sql, new BeanListHandler<Order>(Order.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Order queryDetailsOrder(String id) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from orders where id=?";
		try {
			return qr.query(sql, new BeanHandler<Order>(Order.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void cancelOrder(Connection conn, String id) {
		String sql = "delete from orders where id=?";
		QueryRunner runner = new QueryRunner();
		try {
			runner.update(conn, sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateOrderStatus(String orderid) {
		String sql = "update orders set status = 1 where id=?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		try {
			qr.update(sql, orderid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Order> findAllNoPayOrder() {
		String sql = "select * from orders where status=0";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		try {
			return qr.query(sql, new BeanListHandler<Order>(Order.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * Title: save
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param order
	 * @see com.guwei.DAO.OrderDao#save(com.guwei.domain.Order)
	 */
	@Override
	public void save(Order o) {
		String sql = "insert into orders values(?,?,?,?,?,null,?,?)";
		try {
			runner.update(TranscationManager.getCurrentThreadConnection(), sql,
					o.getId(), o.getUid(), o.getTotalprice(), o.getAddress(),
					o.getStatus(), o.getAcceptperson(), o.getTelephone());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * Title: cancelOrder
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * @see com.guwei.DAO.OrderDao#cancelOrder(java.lang.String)
	 */
	@Override
	public void cancelOrder(String id) {
		String sql = "delete from orders where id=?";
		QueryRunner runner = new QueryRunner();
		try {
			runner.update(TranscationManager.getCurrentThreadConnection(), sql,
					id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
