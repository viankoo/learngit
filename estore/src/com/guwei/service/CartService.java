package com.guwei.service;

import java.util.List;

import com.guwei.domain.Cart;
import com.guwei.domain.Goods;

public interface CartService {

	void addCart(Cart cart);

	List<Goods> queryCart(int id);

	void del(int uid, int gid);

	List<Goods> changeBuynum(Cart cart);

	Long countGoodsNum(int id);

	void clearCart(int id);

}
