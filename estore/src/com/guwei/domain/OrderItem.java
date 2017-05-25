package com.guwei.domain;

public class OrderItem {
	private String oid;
	private int gid;
	private int buynum;

	private String name;
	private double marketprice;
	private double estoreprice;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMarketprice() {
		return marketprice;
	}

	public void setMarketprice(double marketprice) {
		this.marketprice = marketprice;
	}

	public double getEstoreprice() {
		return estoreprice;
	}

	public void setEstoreprice(double estoreprice) {
		this.estoreprice = estoreprice;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public int getBuynum() {
		return buynum;
	}

	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}

}
