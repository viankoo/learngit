package com.guwei.web;

import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.guwei.domain.User;
import com.guwei.service.UserService;
import com.guwei.utils.ApacheMailUtils;
import com.guwei.utils.ImplFactory;
import com.guwei.utils.MD5Utils;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	// 邮箱唯一性校验ajax
	public void validEmail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String email = request.getParameter("email");
		System.out.println(email);
		UserService us = ImplFactory.getInstance(UserService.class);
		User existUser = us.findUserByEmail(email);
		if (existUser == null) {
			response.getWriter().print("true");
		} else {
			response.getWriter().print("false");
		}
	}

	// 验证码校验ajax
	public void checkcode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String code1 = request.getParameter("code");
		String code2 = (String) request.getSession().getAttribute("code");
		System.out.println(code2);
		if (code1 != null && code1.trim().equalsIgnoreCase(code2)) {
			response.getWriter().print(0);
		} else {
			response.getWriter().print(1);
		}
	}

	// 注册，并发送邮件，账户处于未激活状态
	public String register(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			User user = new User();
			BeanUtils.populate(user, request.getParameterMap());
			// 提交成功后，验证码失效
			request.getSession().removeAttribute("code");
			// 注册状态和角色初始化生成，激活码设置
			String activecode = UUID.randomUUID().toString().replace("-", "");
			user.setActivecode(activecode);
			String md5 = MD5Utils.md5(user.getPassword());
			user.setPassword(md5);
			UserService us = ImplFactory.getInstance(UserService.class);
			us.register(user);
			// 发送邮件
			String msg = "<a href='http://localhost:8080/estore/userServlet?method=activecode&activecode="
					+ activecode + "'>请24小时之内，点击链接激活注册账号</a>";
			ApacheMailUtils.sendMail(user.getEmail(), "Estore商城激活邮件通知", msg);
			return "/tpslogin.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("register_error", "注册失败，请重新注册");
			return "/register.jsp";
		}
	}

	// 点击邮箱激活邮件，激活账户
	public String activecode(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String activecode = request.getParameter("activecode");
		UserService us = ImplFactory.getInstance(UserService.class);
		User existUser = us.findUserByActivecode(activecode);
		if (existUser == null) {
			request.setAttribute("activecode_error", "激活失败，请重新注册");
			return "/register.jsp";
		} else {
			// 已被激活
			if (existUser.getStatus() == 1) {
				return "/tpslogin.jsp";
			} else {
				// 激活码失效
				Timestamp registertime = existUser.getRegistertime();
				long currentTimeMillis = System.currentTimeMillis();
				long time = registertime.getTime();
				if ((currentTimeMillis - time) > (24 * 3600 * 1000)) {
					us.deleteUser(existUser);
					request.setAttribute("activecode_invalid", "您已经超时,请重新注册");
					return "/register.jsp";
				} else {
					us.activeUser(existUser.getId());
					return "/tpslogin.jsp";
				}
			}
		}
	}

	// 登录
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		User user = new User();
		BeanUtils.populate(user, request.getParameterMap());
		UserService us = ImplFactory.getInstance(UserService.class);
		User loginUser = us.login(user);
		if (loginUser == null) {
			request.setAttribute("login_error", "用户名或密码错误");
			return "/login.jsp";
		} else if (loginUser.getStatus() == 0) {
			response.getWriter().write("<script>alert(账号未激活，请即时激活);</script>");
			return null;
		} else {
			String remb = request.getParameter("remember");
			if (remb != null) {
				Cookie c = new Cookie("email", loginUser.getEmail());
				c.setPath("/");
				c.setMaxAge(99999);
				response.addCookie(c);
			} else {
				Cookie c = new Cookie("email", null);
				c.setPath("/");
				c.setMaxAge(0);
				response.addCookie(c);
			}
			request.getSession().setAttribute("loginUser", loginUser);
			return "/tpsindex.jsp";
		}
	}

	// 注销
	public String exist(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.getSession().removeAttribute("loginUser");
		return "/tpslogin.jsp";
	}

}
