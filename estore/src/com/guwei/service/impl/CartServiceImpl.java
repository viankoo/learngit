package com.guwei.service.impl;

import java.util.List;

import com.guwei.DAO.CartDao;
import com.guwei.domain.Cart;
import com.guwei.domain.Goods;
import com.guwei.service.CartService;
import com.guwei.utils.ImplFactory;

public class CartServiceImpl implements CartService {
	private CartDao cd = ImplFactory.getInstance(CartDao.class);

	@Override
	/**
	 * 先判断是不是第一次购买
	 * 是：添加sql   否：更新sql
	 */
	public void addCart(Cart cart) {
		Cart goods = cd.findGoodsById(cart.getGid(), cart.getUid());
		if (goods == null) {
			cd.addCart(cart);
		} else {
			int num = cart.getBuynum();
			num += goods.getBuynum();
			cart.setBuynum(num);
			cd.updateCart(cart);
		}
	}

	@Override
	public List<Goods> queryCart(int id) {
		return cd.queryCart(id);
	}

	@Override
	public void del(int uid, int gid) {
		cd.del(uid, gid);
	}

	@Override
	public List<Goods> changeBuynum(Cart cart) {
		cd.updateCart(cart);
		return cd.queryCart(cart.getUid());
	}

	@Override
	public Long countGoodsNum(int id) {
		return cd.countGoodsNum(id);
	}

	@Override
	public void clearCart(int id) {
		cd.clearCart(id);
	}

}
