package com.guwei.DAO;

import com.guwei.domain.User;

public interface UserDao {

	User findUserByEmail(String email);

	void register(User user);

	User findUserByActivecode(String activecode);

	void deleteUser(User existUser);

	void activeUser(int id);

	User login(String email, String md5);

}
