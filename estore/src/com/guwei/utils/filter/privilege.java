/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  privilege.java   
 * @Package com.guwei.utils.filter   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: gw  
 * @date:   2017年5月15日 下午2:25:26   
 * @version V1.0 
 * @Copyright: 2017 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于上海黑马内部传阅，禁止外泄以及用于其他的商业目
 */
package com.guwei.utils.filter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.guwei.domain.User;

/**
 * @ClassName: privilege
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: gw
 * @date: 2017年5月15日 下午2:25:26
 * @param:
 * @Copyright: 2017 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于上海黑马内部传阅，禁止外泄以及用于其他的商业目
 */
public class privilege implements Filter {
	private List<String> userList = new ArrayList<>();
	private List<String> adminList = new ArrayList<String>();

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		// 容器创建 过滤器立刻创建 读取WEB-INF/下的配置文件
		try {
			BufferedReader bd = new BufferedReader(new FileReader(config
					.getServletContext().getRealPath("/WEB-INF/user.txt")));
			String line;
			while ((line = bd.readLine()) != null) {
				userList.add(line);
			}
			bd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			BufferedReader bd = new BufferedReader(new FileReader(config
					.getServletContext().getRealPath("/WEB-INF/admin.txt")));
			String line;
			while ((line = bd.readLine()) != null) {
				adminList.add(line);
			}
			bd.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String requesturi = req.getRequestURI().substring(
				req.getContextPath().length());
		String uripath = req.getQueryString();
		if (uripath != null && uripath.length() != 0) {
			if (uripath.contains("method") && uripath.contains("&")) {
				uripath = uripath.substring(0, uripath.indexOf("&"));
			}
		}
		requesturi = requesturi + (uripath == null ? "" : "?" + uripath);
		boolean isUser = userList.contains(requesturi);
		boolean isAdmin = adminList.contains(requesturi);
		// 不在配置文件的通用资源，如：login.jsp 不做过滤
		if (!isAdmin && !isUser) {
			chain.doFilter(req, res);
			return;
		} else { // 非通用资源 User loginUser = (User)
			User loginUser = (User) req.getSession().getAttribute("loginUser");
			if (loginUser == null) {
				res.sendRedirect(req.getContextPath() + "/login.jsp");
				return;
			} else {
				String role = loginUser.getRole();
				if ("user".equalsIgnoreCase(role) && isUser) {
					chain.doFilter(req, res);
				} else if ("admin".equalsIgnoreCase(role) && isAdmin) {
					chain.doFilter(req, res);
				} else {
					throw new RuntimeException("您目前没有权限访问！");
				}
			}
		}

	}

}
