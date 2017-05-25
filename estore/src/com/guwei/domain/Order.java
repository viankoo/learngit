package com.guwei.domain;

import java.sql.Timestamp;
import java.util.List;

public class Order {
	private String id;
	private int uid;
	private double totalprice;
	private String address;
	private int status = 0;// 0 未支付 1已支付
	private Timestamp createtime;

	private String acceptperson;
	private String telephone;

	private List<OrderItem> orders;// 订单商品信息，gid oid buynum （中间表）

	public String getAcceptperson() {
		return acceptperson;
	}

	public void setAcceptperson(String acceptperson) {
		this.acceptperson = acceptperson;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public List<OrderItem> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderItem> orders) {
		this.orders = orders;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public double getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(double totalprice) {
		this.totalprice = totalprice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

}
