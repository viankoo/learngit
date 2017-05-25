package com.guwei.utils.filter;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class Encoding
 */
public class EncodingFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		response.setContentType("text/html;charset=utf-8");

		HttpServletRequest proxyreq = (HttpServletRequest) Proxy
				.newProxyInstance(EncodingFilter.class.getClassLoader(),
						request.getClass().getInterfaces(),
						new InvocationHandler() {

							@SuppressWarnings("unchecked")
							@Override
							public Object invoke(Object arg0, Method method,
									Object[] arg2) throws Throwable {
								if ("getParameter".equals(method.getName())) {
									String paramer = (String) method.invoke(
											request, arg2);
									return new String(paramer
											.getBytes("iso-8859-1"), "utf-8");
								} else if ("getParameterValues".equals(method
										.getName())) {
									String[] values = (String[]) method.invoke(
											request, arg2);
									if (values != null && values.length > 0) {
										for (int i = 0; i < values.length; i++) {
											values[i] = new String(values[i]
													.getBytes("ISO-8859-1"),
													"UTF-8");
										}
									}
									return values;
								} else if ("getParameterMap".equals(method
										.getName())) {
									Map<String, String[]> map = (Map<String, String[]>) method
											.invoke(request, arg2);
									if (map != null && map.size() > 0) {
										for (String key : map.keySet()) {
											String[] values = map.get(key);
											if (values != null
													&& values.length > 0) {
												for (int i = 0; i < values.length; i++) {
													values[i] = new String(
															values[i]
																	.getBytes("ISO-8859-1"),
															"UTF-8");
												}
											}
										}
									}
									return map;
								}
								return method.invoke(request, arg2);
							}
						});
		chain.doFilter(proxyreq, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

	public void destroy() {
	}

}
