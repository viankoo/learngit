package com.guwei.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.guwei.domain.Goods;
import com.guwei.service.GoodsService;
import com.guwei.utils.DownLoadUtils;
import com.guwei.utils.ImplFactory;

/**
 * Servlet implementation class TotalSaleServlet
 */
public class TotalSaleServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public void totalSale(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		GoodsService gs = ImplFactory.getInstance(GoodsService.class);
		// 统计销量
		List<Goods> list = gs.totalsale();
		// 创建Excel
		HSSFWorkbook book = new HSSFWorkbook(); // 工作簿
		HSSFSheet sheet = book.createSheet("商品销售榜单");// 工作区
		HSSFRow firstrow = sheet.createRow(0);// 行
		firstrow.createCell(0).setCellValue("商品名称");// 单元格
		firstrow.createCell(1).setCellValue("市场价格");
		firstrow.createCell(2).setCellValue("商城价格");
		firstrow.createCell(3).setCellValue("商品类别");
		firstrow.createCell(4).setCellValue("商品库存");
		firstrow.createCell(5).setCellValue("商品销量");
		if (list != null && list.size() > 0) {
			for (Goods good : list) {
				int lastRowNum = sheet.getLastRowNum();
				HSSFRow row = sheet.createRow(lastRowNum + 1);
				row.createCell(0).setCellValue(good.getName());
				row.createCell(1).setCellValue(good.getMarketprice());
				row.createCell(2).setCellValue(good.getEstoreprice());
				row.createCell(3).setCellValue(good.getCategory());
				row.createCell(4).setCellValue(good.getNum());
				row.createCell(5).setCellValue(good.getSaleNum());
			}
		}
		// 下载 (2头1流)
		String filename = new Date().getMonth() + " Estore商城销售排行榜.xls";
		response.setContentType(getServletContext().getMimeType(filename));
		response.setHeader(
				"Content-Disposition",
				"attachment;filename="
						+ DownLoadUtils.getAttachmentFileName(filename,
								request.getHeader("user-agent")));
		book.write(response.getOutputStream());
	}

}
