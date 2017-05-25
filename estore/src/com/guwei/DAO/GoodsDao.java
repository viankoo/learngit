package com.guwei.DAO;

import java.sql.Connection;
import java.util.List;

import com.guwei.domain.Goods;
import com.guwei.domain.OrderItem;

public interface GoodsDao {

	void save(Goods goods);

	List<Goods> selectGoods();

	Goods findGoodsById(String id);

	int queryCount();

	List<Goods> queryPageData(int startIndex, int pageSize);

	Goods selectNumById(Connection conn, int i);

	void mimusGoodsNum(Connection conn, OrderItem item);

	void addGoodsNum(Connection conn, OrderItem item);

	/**
	 * @Title: totalsale
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @return
	 * @return: List<Goods>
	 * @throws
	 */
	List<Goods> totalsale();

	/**
	 * @Title: addGoodsNum
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param: @param item
	 * @return: void
	 * @throws
	 */
	void addGoodsNum(OrderItem item);

}
