package com.guwei.web;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.guwei.domain.Goods;
import com.guwei.domain.Order;
import com.guwei.domain.OrderItem;
import com.guwei.domain.User;
import com.guwei.service.CartService;
import com.guwei.service.OrderService;
import com.guwei.utils.ImplFactory;
import com.guwei.utils.ProxyUtils;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	OrderService os = ImplFactory.getInstance(OrderService.class);
	CartService cs = ImplFactory.getInstance(CartService.class);

	public String cancelOrder(HttpServletRequest request,
			HttpServletResponse response) {
		OrderService proxyOS = new ProxyUtils<OrderService>(os).getProxy();
		boolean b = proxyOS.cancelOrder(request.getParameter("id"));
		String msg = "訂單取消成功！";
		if (b) {
			// ApacheMailUtils.sendMail(loginUser.getEmail(), "Estore商城通知郵件",
			// msg);
		}
		return "/tpsorderServlet?method=createOrder";
	}

	public String orderDetail(HttpServletRequest request,
			HttpServletResponse response) {
		Order order = os.queryDetailsOrder(request.getParameter("id"));
		request.setAttribute("order", order);
		return "/orders_detail.jsp";
	}

	public String createOrder(HttpServletRequest request,
			HttpServletResponse response) {
		User existUser = (User) request.getSession().getAttribute("loginUser");
		if (existUser == null) {
			return "/login.jsp";
		} else {
			List<Order> oList = os.queryOrder(existUser.getId());
			request.setAttribute("orders", oList);
			return "/orders.jsp";
		}
	}

	public String orderlist(HttpServletRequest request,
			HttpServletResponse response) {
		User existUser = (User) request.getSession().getAttribute("loginUser");
		if (existUser == null) {
			return "/login.jsp";
		} else {
			List<Goods> list = cs.queryCart(existUser.getId());
			request.setAttribute("cList", list);
			return "/orders_submit.jsp";
		}
	}

	public void preparedOrder(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User existUser = (User) request.getSession().getAttribute("loginUser");
		if (existUser == null) {
			response.sendRedirect("/login.jsp");
		}
		Order order = new Order();
		String id = UUID.randomUUID().toString().replace("-", "");
		order.setId(id);
		order.setUid(existUser.getId());
		order.setTotalprice(Double.parseDouble(request
				.getParameter("totalprice")));
		String address = request.getParameter("add");
		order.setAddress(address);
		order.setAcceptperson(request.getParameter("acceptperson"));
		order.setTelephone(request.getParameter("telephone"));

		List<Goods> list = cs.queryCart(existUser.getId());
		if (list != null && list.size() > 0) {
			List<OrderItem> orders = new ArrayList<>();
			for (Goods goods : list) {
				OrderItem item = new OrderItem();
				item.setGid(goods.getId());
				item.setOid(id);
				item.setBuynum(goods.getBuynum());
				orders.add(item);
			}
			order.setOrders(orders);
		}

		OrderService proxy = new ProxyUtils<OrderService>(os).getProxy();
		boolean b = proxy.save(order);
		response.setContentType("text/html;charset=utf-8");
		if (b) {
			response.getWriter()
					.println(
							"<font color='green'>下单成功</font>&nbsp;<a href='javascript:;' onclick=g();>查询订单</a>");
		} else {
			response.getWriter().write("<script>alert('下单失败');</script>");
		}
	}

}
