package com.guwei.web;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	private static final long serialVersionUID = 3844311946039211814L;

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");

		String methodName = request.getParameter("method");
		try {
			Method method = this.getClass().getMethod(methodName,
					HttpServletRequest.class, HttpServletResponse.class);
			// 根据返回值，做出转发、重定向等操作
			String result = (String) method.invoke(this, request, response);
			if (result != null) {
				if (result.startsWith("/tps")) {
					String res = result.substring(4);
					response.sendRedirect(request.getContextPath() + "/" + res);
				} else {
					request.getRequestDispatcher(result).forward(request,
							response);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
