package com.guwei.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.guwei.DAO.CartDao;
import com.guwei.DAO.GoodsDao;
import com.guwei.DAO.OrderDao;
import com.guwei.DAO.OrderItemDao;
import com.guwei.domain.Order;
import com.guwei.domain.OrderItem;
import com.guwei.service.GoodsService;
import com.guwei.service.OrderService;
import com.guwei.utils.ImplFactory;

public class OrderServiceImpl implements OrderService {
	private OrderDao od = ImplFactory.getInstance(OrderDao.class);
	private OrderItemDao oid = ImplFactory.getInstance(OrderItemDao.class);
	private CartDao cd = ImplFactory.getInstance(CartDao.class);
	private GoodsDao gd = ImplFactory.getInstance(GoodsDao.class);
	private GoodsService gs = ImplFactory.getInstance(GoodsService.class);
	Logger logger = Logger.getLogger(OrderServiceImpl.class);

	@Override
	// 订单表添加数据 中间表添加数据 商品表更新库存 购物车表清空用户购买商品数据
	public boolean save(Order order) {
		logger.info("info:客户正在下单，订单编号：" + order.getId());
		logger.debug("debug:保存的订单对象" + order);
		od.save(order);
		List<OrderItem> orders = order.getOrders();
		if (orders != null && orders.size() > 0) {
			for (OrderItem item : orders) {
				oid.save(item);
				gs.mimusGoodsNum(item);
			}
		}
		cd.clearCart(order.getUid());
		logger.info("info:客户下单成功，订单编号：" + order.getId());
		return true;
	}

	@Override
	public List<Order> queryOrder(int id) {
		return od.queryOrder(id);
	}

	@Override
	// 根据uid查询order并封装订单中的商品信息给list<orderitem>
	public Order queryDetailsOrder(String id) {
		Order order = od.queryDetailsOrder(id);// 基本信息
		// 将商品信息 封装到List<OrderItem>中 select * from goods as g,oders as o where
		// g.id=o.gid and oid=?
		List<OrderItem> list = oid.selectOrderItemByoid(id);
		order.setOrders(list);
		return order;
	}

	@Override
	// 取消订单 中间表 恢复库存
	public boolean cancelOrder(String id) {
		logger.info("info:客户正在取消订单，订单编号：" + id);
		logger.debug("debug:取消订单，编号：" + id);
		Order order = queryDetailsOrder(id);// 返回的订单中含有商品信息 ??为什么不能加od.
		// 订单表是中间表的父表，不能先删父表，要先删中间表
		oid.cancelOrderItem(id);
		List<OrderItem> items = order.getOrders();
		for (OrderItem item : items) {
			gd.addGoodsNum(item);
		}
		od.cancelOrder(id);
		logger.info("info:客户取消订单成功，订单编号：" + id);
		return true;
	}

	@Override
	// 刪除超過一天未支付的訂單
	public boolean canceltimeoutOrder() {
		logger.info("info:系统开始扫描订单");
		List<Order> orders = od.findAllNoPayOrder();
		logger.debug("debug:扫描出未支付的订单集合" + orders);
		if (orders == null) {
			return false;
		}
		for (Order order : orders) {
			Order order2 = queryDetailsOrder(order.getId());
			// 订单表是中间表的父表，不能先删父表，要先删中间表
			if (System.currentTimeMillis() - order2.getCreatetime().getTime() > 24 * 3600 * 1000) {
				logger.info("info:系统扫描到过期未支付的订单，订单编号：" + order2.getId());
				oid.cancelOrderItem(order2.getId());
				List<OrderItem> items = order2.getOrders();
				for (OrderItem item : items) {
					gd.addGoodsNum(item);
				}
				od.cancelOrder(order2.getId());
				logger.info("info:系统成功删除过期订单，订单编号：" + order2.getId());
			}
			return true;
		}
		return false;
	}
}
