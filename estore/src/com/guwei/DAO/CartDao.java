package com.guwei.DAO;

import java.sql.Connection;
import java.util.List;

import com.guwei.domain.Cart;
import com.guwei.domain.Goods;

public interface CartDao {

	public Cart findGoodsById(int gid, int uid);

	public void addCart(Cart cart);

	public void updateCart(Cart cart);

	public List<Goods> queryCart(int id);

	public void del(int uid, int gid);

	public Long countGoodsNum(int id);

	public void clearCart(int id);

	public void clearCart(Connection conn, int uid);

}
