package com.guwei.service.impl;

import java.sql.Connection;
import java.util.List;

import com.guwei.DAO.GoodsDao;
import com.guwei.domain.Goods;
import com.guwei.domain.OrderItem;
import com.guwei.domain.PageBean;
import com.guwei.service.GoodsService;
import com.guwei.utils.ImplFactory;
import com.guwei.utils.TranscationManager;

public class GoodsServiceImpl implements GoodsService {
	GoodsDao gd = ImplFactory.getInstance(GoodsDao.class);

	@Override
	public void save(Goods goods) {
		gd.save(goods);
	}

	@Override
	public List<Goods> selectGoods() {

		return gd.selectGoods();
	}

	@Override
	public Goods findGoodsById(String id) {
		return gd.findGoodsById(id);
	}

	@Override
	public void queryPage(PageBean bean) {
		// 查询总记录数
		int totalCount = gd.queryCount();
		if (totalCount == 0) {
			return;
		}
		// 计算总页数
		int totalPage = totalCount / bean.getPageSize();
		if (totalCount / bean.getPageSize() > 0) {
			totalPage += 1;
		}
		// 越界输入处理
		if (bean.getCurrPage() <= 1) {
			bean.setCurrPage(1);
		} else if (bean.getCurrPage() >= totalPage) {
			bean.setCurrPage(totalPage);
		}
		// 分页查询
		int startIndex = (bean.getCurrPage() - 1) * bean.getPageSize();
		List<Goods> Glist = gd.queryPageData(startIndex, bean.getPageSize());
		// 计算开始页码和结束页码
		int startPage = 0;
		int endPage = 0;
		if (totalPage <= bean.getBtnCount()) {
			startPage = 1;
			endPage = totalPage;
		} else {
			// 当前页靠前
			if (bean.getCurrPage() <= bean.getBtnCount() / 2 + 1) {
				startPage = 1;
				endPage = bean.getBtnCount();
			}
			// 当前页靠后
			else if (totalPage - bean.getCurrPage() <= (bean.getBtnCount() - 1) / 2) {
				endPage = totalPage;
				startPage = totalPage - bean.getBtnCount() + 1;
			}
			// 当前页靠中间
			else {
				startPage = bean.getCurrPage() - bean.getBtnCount() / 2;
				endPage = startPage + bean.getBtnCount() - 1;
			}
		}
		// 封装数据
		bean.setData(Glist);
		bean.setEndPage(endPage);
		bean.setStartPage(startPage);
		bean.setTotalCount(totalCount);
		bean.setTotalPage(totalPage);
	}

	@Override
	public void mimusGoodsNum(Connection conn, OrderItem item) {
		try {
			Goods goods = gd.selectNumById(conn, item.getGid());
			if (item.getBuynum() > goods.getNum()) {
				throw new RuntimeException("库存不足！");
			} else {
				gd.mimusGoodsNum(conn, item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("库存更新失败！");
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
	 * @see com.guwei.service.GoodsService#totalsale()
	 */
	@Override
	public List<Goods> totalsale() {
		return gd.totalsale();
	}

	/**
	 * <p>
	 * Title: mimusGoodsNum
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param item
	 * @see com.guwei.service.GoodsService#mimusGoodsNum(com.guwei.domain.OrderItem)
	 */
	@Override
	public void mimusGoodsNum(OrderItem item) {
		try {
			Goods goods = gd.selectNumById(
					TranscationManager.getCurrentThreadConnection(),
					item.getGid());
			if (item.getBuynum() > goods.getNum()) {
				throw new RuntimeException("库存不足！");
			} else {
				gd.mimusGoodsNum(
						TranscationManager.getCurrentThreadConnection(), item);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("库存更新失败！");
		}
	}

}
