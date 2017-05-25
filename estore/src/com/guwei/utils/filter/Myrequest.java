package com.guwei.utils.filter;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class Myrequest extends HttpServletRequestWrapper {
	private boolean flag = false;
	private HttpServletRequest request;

	public Myrequest(HttpServletRequest request) {
		super(request);
		this.request = request;
	}

	@Override
	public String getParameter(String name) {
		Map<String, String[]> map = getParameterMap();
		return map.get(name) == null ? null : map.get(name)[0];
	}

	@Override
	public Map<String, String[]> getParameterMap() {

		String method = request.getMethod();
		if ("get".equalsIgnoreCase(method)) {
			Map<String, String[]> map = request.getParameterMap();
			if (!flag) {
				for (String key : map.keySet()) {
					String[] values = map.get(key);
					if (values != null) {
						for (int i = 0; i < values.length; i++) {
							try {
								values[i] = new String(
										values[i].getBytes("iso-8859-1"),
										"UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
								throw new RuntimeException(e);
							}
						}
					}

				}
				flag = true;
			}
			return map;
		} else if ("post".equalsIgnoreCase(method)) {
			try {
				request.setCharacterEncoding("UTF-8");
				return request.getParameterMap();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return super.getParameterMap();
	}

	@Override
	public String[] getParameterValues(String name) {
		Map<String, String[]> map = getParameterMap();
		return map.get(name);
	}

}
