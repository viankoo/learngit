package com.guwei.service.impl;

import com.guwei.DAO.UserDao;
import com.guwei.domain.User;
import com.guwei.service.UserService;
import com.guwei.utils.ImplFactory;
import com.guwei.utils.MD5Utils;

public class UserServiceImpl implements UserService {
	UserDao ud = ImplFactory.getInstance(UserDao.class);

	@Override
	public User findUserByEmail(String email) {
		return ud.findUserByEmail(email);
	}

	@Override
	public void register(User user) {
		ud.register(user);
	}

	@Override
	public User findUserByActivecode(String activecode) {
		return ud.findUserByActivecode(activecode);
	}

	@Override
	public void deleteUser(User existUser) {
		ud.deleteUser(existUser);
	}

	@Override
	public void activeUser(int id) {
		ud.activeUser(id);
	}

	@Override
	public User login(User user) {
		return ud.login(user.getEmail(), MD5Utils.md5(user.getPassword()));
	}

}
