package com.guwei.DAO.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.guwei.DAO.OrderItemDao;
import com.guwei.domain.OrderItem;
import com.guwei.utils.JDBCUtils;
import com.guwei.utils.TranscationManager;

public class OrderItemDaoImpl implements OrderItemDao {

	@Override
	public void save(Connection conn, OrderItem item) {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orderitems values(?,?,?)";
		try {
			runner.update(conn, sql, item.getOid(), item.getGid(),
					item.getBuynum());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<OrderItem> selectOrderItemByoid(String oid) {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select oi.*,g.name,g.marketprice,g.estoreprice from orderitems as oi,goods as g where oi.gid = g.id and oi.oid=?";
		try {
			return qr.query(sql,
					new BeanListHandler<OrderItem>(OrderItem.class), oid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void clearOrderItem(Connection conn, String id) {
		QueryRunner runner = new QueryRunner();
		String sql = "delete from orderitems where oid=?";
		try {
			runner.update(conn, sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void cancelOrderItem(Connection conn, String id) {
		QueryRunner runner = new QueryRunner();
		String sql = "delete from orderitems where oid=?";
		try {
			runner.update(conn, sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<OrderItem> selectOrderItem(Connection conn, String id) {
		QueryRunner runner = new QueryRunner();
		String sql = "select oi.*,g.name,g.marketprice,g.estoreprice from orderitems as oi,goods as g where oi.gid = g.id and oi.oid=?";
		try {
			return runner.query(conn, sql, new BeanListHandler<OrderItem>(
					OrderItem.class), id);
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
	 * @param item
	 * @see com.guwei.DAO.OrderItemDao#save(com.guwei.domain.OrderItem)
	 */
	@Override
	public void save(OrderItem item) {
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orderitems values(?,?,?)";
		try {
			runner.update(TranscationManager.getCurrentThreadConnection(), sql,
					item.getOid(), item.getGid(), item.getBuynum());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * Title: cancelOrderItem
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * @see com.guwei.DAO.OrderItemDao#cancelOrderItem(java.lang.String)
	 */
	@Override
	public void cancelOrderItem(String id) {
		QueryRunner runner = new QueryRunner();
		String sql = "delete from orderitems where oid=?";
		try {
			runner.update(TranscationManager.getCurrentThreadConnection(), sql,
					id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
