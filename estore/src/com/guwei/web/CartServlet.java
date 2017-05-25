package com.guwei.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.guwei.domain.Cart;
import com.guwei.domain.Goods;
import com.guwei.domain.User;
import com.guwei.service.CartService;
import com.guwei.utils.ImplFactory;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private CartService cs = ImplFactory.getInstance(CartService.class);

	public String addCart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User existUser = (User) request.getSession().getAttribute("loginUser");
		if (existUser == null) {
			return "/login.jsp";
		} else {
			Cart cart = new Cart();
			BeanUtils.populate(cart, request.getParameterMap());
			cart.setUid(existUser.getId());
			cs.addCart(cart);
			return "/buyorcart.jsp";
		}
	}

	public String queryCart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User existUser = (User) request.getSession().getAttribute("loginUser");
		if (existUser == null) {
			return "/login.jsp";
		} else {
			List<Goods> list = cs.queryCart(existUser.getId());
			request.setAttribute("cList", list);
			return "/cart.jsp";
		}
	}

	public String del(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User existUser = (User) request.getSession().getAttribute("loginUser");
		if (existUser == null) {
			return "/login.jsp";
		} else {
			cs.del(existUser.getId(),
					Integer.parseInt(request.getParameter("gid")));
			return "/tpscartServlet?method=queryCart";
		}
	}

	public String changBuynum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User existUser = (User) request.getSession().getAttribute("loginUser");
		if (existUser == null) {
			return "/login.jsp";
		} else {
			Cart cart = new Cart();
			BeanUtils.populate(cart, request.getParameterMap());
			cart.setUid(existUser.getId());
			List<Goods> list = cs.changeBuynum(cart);
			double estoresum = 0;
			double marketsum = 0;
			double gidxiaoji = 0;
			for (Goods goods : list) {
				estoresum += goods.getBuynum() * goods.getEstoreprice();
				marketsum += goods.getBuynum() * goods.getMarketprice();
				if (goods.getId() == cart.getGid()) {
					gidxiaoji = goods.getBuynum() * goods.getEstoreprice();
				}
			}
			response.getWriter()
					.print(estoresum + "@" + (marketsum - estoresum) + "@"
							+ gidxiaoji);
			return null;
		}
	}

	public void countBuyGoodsNum(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User existUser = (User) request.getSession().getAttribute("loginUser");
		if (existUser == null) {
			response.getWriter().print(0);
		} else {
			Long count = cs.countGoodsNum(existUser.getId());
			response.getWriter().print(count);
		}
	}

	public String clearCart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		User existUser = (User) request.getSession().getAttribute("loginUser");
		if (existUser == null) {
			return "/tpslogin.jsp";
		} else {
			cs.clearCart(existUser.getId());
			return "/tpscartServlet?method=queryCart";
		}
	}

}
