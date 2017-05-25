package com.guwei.domain;

import java.sql.Timestamp;

public class User {
	private int id;
	private String nickname;
	private String email;
	private String password;
	private String activecode;// uuid
	private int status = 0;// 0 注册信息 没有激活 1 激活 可以登录
	private Timestamp registertime;// 注册时间
	private String role = "user";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getActivecode() {
		return activecode;
	}

	public void setActivecode(String activecode) {
		this.activecode = activecode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getRegistertime() {
		return registertime;
	}

	public void setRegistertime(Timestamp registertime) {
		this.registertime = registertime;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
