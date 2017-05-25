package com.guwei.service;

import java.sql.Connection;
import java.util.List;

import com.guwei.domain.Goods;
import com.guwei.domain.OrderItem;
import com.guwei.domain.PageBean;

public interface GoodsService {

	void save(Goods goods);

	List<Goods> selectGoods();

	Goods findGoodsById(String parameter);

	void queryPage(PageBean bean);

	void mimusGoodsNum(Connection conn, OrderItem item);

	/**
	 * @Title: totalsale
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @return
	 * @return: List<Goods>
	 * @throws
	 */
	List<Goods> totalsale();

	/**
	 * @Title: mimusGoodsNum
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param item
	 * @return: void
	 * @throws
	 */
	void mimusGoodsNum(OrderItem item);

}
