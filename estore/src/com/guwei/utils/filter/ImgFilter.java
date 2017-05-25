package com.guwei.utils.filter;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class ImgFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest req = (HttpServletRequest) request;
		String uri = req.getRequestURI();

		// 对请求地址进行utf-8解码
		String decodeUrl = URLDecoder.decode(uri, "utf-8");

		if (uri.equals(decodeUrl)) {
			chain.doFilter(req, response);
		} else {
			// uri中含有中文
			decodeUrl = decodeUrl.substring(req.getContextPath().length());
			// 转发
			req.getRequestDispatcher(decodeUrl).forward(req, response);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
