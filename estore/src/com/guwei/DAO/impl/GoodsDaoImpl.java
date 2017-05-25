package com.guwei.DAO.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.guwei.DAO.GoodsDao;
import com.guwei.domain.Goods;
import com.guwei.domain.OrderItem;
import com.guwei.utils.JDBCUtils;
import com.guwei.utils.TranscationManager;

public class GoodsDaoImpl implements GoodsDao {
	QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
	QueryRunner runner = new QueryRunner();

	@Override
	public void save(Goods g) {
		String sql = "insert into goods values(null,?,?,?,?,?,?,?)";
		try {
			qr.update(sql, g.getName(), g.getMarketprice(), g.getEstoreprice(),
					g.getCategory(), g.getNum(), g.getImgurl(),
					g.getDescription());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Goods> selectGoods() {
		String sql = "select * from goods";
		try {
			return qr.query(sql, new BeanListHandler<Goods>(Goods.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Goods findGoodsById(String id) {
		String sql = "select * from goods where id=?";
		try {
			return qr.query(sql, new BeanHandler<Goods>(Goods.class), id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int queryCount() {
		String sql = "select count(*) from goods";
		try {
			return qr.query(sql, new ScalarHandler<Long>()).intValue();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Goods> queryPageData(int startIndex, int pageSize) {
		String sql = "select * from goods limit ?,?";
		try {
			return qr.query(sql, new BeanListHandler<Goods>(Goods.class),
					startIndex, pageSize);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public Goods selectNumById(Connection conn, int gid) {
		String sql = "select * from goods where id=?";
		try {
			return runner.query(conn, sql, new BeanHandler<Goods>(Goods.class),
					gid);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void mimusGoodsNum(Connection conn, OrderItem item) {
		String sql = "update goods set num = num-? where id=?";
		try {
			runner.update(conn, sql, item.getBuynum(), item.getGid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public void addGoodsNum(Connection conn, OrderItem item) {
		String sql = "update goods set num = num+? where id=?";
		try {
			runner.update(conn, sql, item.getBuynum(), item.getGid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * Title: totalsale
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see com.guwei.DAO.GoodsDao#totalsale()
	 */

	@Override
	public List<Goods> totalsale() {
		String sql = "SELECT SUM(oi.buynum) SaleNum,g.* FROM goods g,orderitems oi,orders o WHERE g.id = oi.gid AND o.id = oi.oid AND o.status=1 GROUP BY g.id ORDER BY SaleNum DESC";
		try {
			return qr.query(sql, new BeanListHandler<Goods>(Goods.class));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * <p>
	 * Title: addGoodsNum
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param item
	 * @see com.guwei.DAO.GoodsDao#addGoodsNum(com.guwei.domain.OrderItem)
	 */
	@Override
	public void addGoodsNum(OrderItem item) {
		String sql = "update goods set num = num+? where id=?";
		try {
			runner.update(TranscationManager.getCurrentThreadConnection(), sql,
					item.getBuynum(), item.getGid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
