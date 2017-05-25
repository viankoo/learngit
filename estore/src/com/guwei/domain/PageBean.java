package com.guwei.domain;

import java.util.List;

public class PageBean {
	// 当前页
	private int currPage = 1;
	// 总页数（尾页）
	private int totalPage;
	// 总记录数
	private int totalCount;
	// 每页条数
	private int pageSize = 10;
	// 当前页数据
	private List<?> data;
	// 开始页码（按钮上的数字）
	private int startPage;
	// 结束页码（按钮上的数字）
	private int endPage;
	// 分页控件展示的页码数（按钮个数）
	private int btnCount = 10;

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<?> getData() {
		return data;
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getBtnCount() {
		return btnCount;
	}

	public void setBtnCount(int btnCount) {
		this.btnCount = btnCount;
	}

	@Override
	public String toString() {
		return "PageBean [currPage=" + currPage + ", totalPage=" + totalPage
				+ ", totalCount=" + totalCount + ", pageSize=" + pageSize
				+ ", data=" + data + ", startPage=" + startPage + ", endPage="
				+ endPage + ", btnCount=" + btnCount + "]";
	}

}
