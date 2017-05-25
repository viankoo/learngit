/**
 * @author vian 
 * @version v1.0
 * 创建时间：2017年5月12日  下午3:07:31
 * 修改时间：
 * 修改原因：
 * 功能概述：
 */
package com.guwei.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.guwei.domain.PCD;
import com.guwei.utils.JDBCUtils;

import flexjson.JSONSerializer;

@SuppressWarnings("all")
public class PcdServelet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String pid = request.getParameter("pid");
		System.out.println(pid);
		String sql = "select * from province_city_district where pid=? ";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		try {
			List<PCD> list = qr.query(sql, new BeanListHandler<PCD>(PCD.class),
					Integer.parseInt(pid));
			JSONSerializer ser = new JSONSerializer();
			ser = ser.exclude("class", "pid");
			String json = ser.serialize(list);
			System.out.println(json);
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().print(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}