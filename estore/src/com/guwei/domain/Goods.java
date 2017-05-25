package com.guwei.domain;

public class Goods {
	private int id;
	private String name;
	private double marketprice;
	private double estoreprice;
	private String category;
	private int num;
	private String imgurl;
	private String description;
	// 业务需求添加的字段
	private int buynum;
	private int SaleNum;

	public int getSaleNum() {
		return SaleNum;
	}

	public void setSaleNum(int saleNum) {
		SaleNum = saleNum;
	}

	public int getBuynum() {
		return buynum;
	}

	public void setBuynum(int buynum) {
		this.buynum = buynum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
