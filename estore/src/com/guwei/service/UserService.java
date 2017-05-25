package com.guwei.service;

import com.guwei.domain.User;

public interface UserService {

	User findUserByEmail(String email);

	void register(User user);

	User findUserByActivecode(String activecode);

	void deleteUser(User existUser);

	void activeUser(int id);

	User login(User user);

}
