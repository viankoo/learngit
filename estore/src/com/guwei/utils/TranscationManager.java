/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  TranscationManager.java   
 * @Package com.guwei.utils   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: gw  
 * @date:   2017年5月16日 下午3:40:41   
 * @version V1.0 
 * @Copyright: 2017 www.tydic.com Inc. All rights reserved.
 * 注意：本内容仅限于上海黑马内部传阅，禁止外泄以及用于其他的商业目
 */
package com.guwei.utils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @ClassName: TranscationManager
 * @Description:TODO(事务管理器)
 * @author: gw
 * @date: 2017年5月16日 下午3:40:41
 * @param:
 * @Copyright: 2017 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于上海黑马内部传阅，禁止外泄以及用于其他的商业目
 */
public class TranscationManager {
	private static ThreadLocal<Connection> local = new ThreadLocal<Connection>();

	public static Connection getCurrentThreadConnection() {
		Connection conn = local.get();
		if (conn == null) {
			conn = JDBCUtils.getConnection();
			local.set(conn);
		}
		return conn;
	}

	public static void start() {
		Connection conn = getCurrentThreadConnection();
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void rollback() {
		Connection conn = getCurrentThreadConnection();
		try {
			conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void commit() {
		Connection conn = getCurrentThreadConnection();
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
