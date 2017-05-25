package com.guwei.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.guwei.domain.PCD;
import com.guwei.utils.JDBCUtils;

import flexjson.JSONSerializer;

/**
 * Servlet implementation class LoadPCD
 */
public class LoadPCDServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public void loadPcd(HttpServletRequest request, HttpServletResponse response) {
		String pid = request.getParameter("pid");
		String sql = "select * from province_city_district where pid=? ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		try {
			List<PCD> list = qr.query(sql, new BeanListHandler<PCD>(PCD.class),
					Integer.parseInt(pid));
			JSONSerializer ser = new JSONSerializer();
			ser = ser.exclude("class", "pid");
			String json = ser.serialize(list);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(json);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
