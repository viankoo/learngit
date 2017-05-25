package com.guwei.DAO.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.guwei.DAO.CartDao;
import com.guwei.domain.Cart;
import com.guwei.domain.Goods;
import com.guwei.utils.JDBCUtils;

public class CartDaoImpl implements CartDao {
	private QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

	@Override
	public Cart findGoodsById(int gid, int uid) {
		String sql = "select * from cart where gid=? and uid=?";
		try {
			return qr.query(sql, new BeanHandler<Cart>(Cart.class), gid, uid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addCart(Cart cart) {
		String sql = "insert into cart values(?,?,?)";
		try {
			qr.update(sql, cart.getUid(), cart.getGid(), cart.getBuynum());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void updateCart(Cart cart) {
		String sql = "update cart set buynum=? where gid=? and uid=?";
		try {
			qr.update(sql, cart.getBuynum(), cart.getGid(), cart.getUid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Goods> queryCart(int id) {
		// 要把buynum封装到goods中所以一定要给c.buynum起个正确的别名
		String sql = "select g.*,c.buynum as buynum from cart as c,goods as g where c.gid=g.id and c.uid=?";
		try {
			return qr.query(sql, new BeanListHandler<Goods>(Goods.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void del(int uid, int gid) {
		String sql = "delete from cart where uid = ? and gid = ?";
		try {
			qr.update(sql, uid, gid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Long countGoodsNum(int id) {
		String sql = "select count(buynum) from cart where uid=?";
		try {
			return qr.query(sql, new ScalarHandler<Long>(1), id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void clearCart(int id) {
		String sql = "delete from cart where uid=?";
		try {
			qr.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void clearCart(Connection conn, int uid) {
		String sql = "delete from cart where uid = ?";
		QueryRunner runner = new QueryRunner();
		try {
			runner.update(conn, sql, uid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
