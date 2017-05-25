package com.guwei.DAO.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.guwei.DAO.UserDao;
import com.guwei.domain.User;
import com.guwei.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {
	QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

	@Override
	public User findUserByEmail(String email) {
		String sql = "select * from users where email = ?";

		try {
			return qr.query(sql, new BeanHandler<User>(User.class), email);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void register(User u) {
		try {
			String sql = "insert into users values(null,?,?,?,?,?,null,?)";
			qr.update(sql, u.getNickname(), u.getEmail(), u.getPassword(),
					u.getActivecode(), u.getStatus(), u.getRole());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public User findUserByActivecode(String activecode) {
		String sql = "select * from users where activecode = ?";

		try {
			return qr.query(sql, new BeanHandler<User>(User.class), activecode);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteUser(User u) {
		try {
			String sql = "delete from users where id=?";
			qr.update(sql, u.getId());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void activeUser(int id) {
		try {
			String sql = "update users set status=1 where id=?";
			qr.update(sql, id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public User login(String email, String md5) {
		String sql = "select * from users where email=? and password=?";
		try {
			return qr.query(sql, new BeanHandler<User>(User.class), email, md5);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
