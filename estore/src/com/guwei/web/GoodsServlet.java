package com.guwei.web;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.guwei.domain.Goods;
import com.guwei.domain.PageBean;
import com.guwei.service.GoodsService;
import com.guwei.utils.ImplFactory;
import com.guwei.utils.UploadUtils;

/**
 * Servlet implementation class GoodsServlet
 */
public class GoodsServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private GoodsService gs = ImplFactory.getInstance(GoodsService.class);

	// 分页
	public String queryPage(HttpServletRequest request,
			HttpServletResponse response) {
		PageBean bean = new PageBean();
		try {
			BeanUtils.populate(bean, request.getParameterMap());
			gs.queryPage(bean);
			request.setAttribute("bean", bean);
			return "/goods.jsp";
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}

		return null;
	}

	// 查询商品列表
	public String list(HttpServletRequest request, HttpServletResponse response) {
		List<Goods> list = gs.selectGoods();
		if (list != null) {
			request.setAttribute("list", list);
			return "/goods.jsp";
		}
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			List<Goods> historygoods = new LinkedList<>();
			for (Cookie cookie : cookies) {
				if (cookie.getName().startsWith("history")) {
					String goodsid = cookie.getValue();
					Goods goodsById = gs.findGoodsById(goodsid);
					historygoods.add(goodsById);
				}
			}
			request.setAttribute("historygoods", historygoods);
		}
		return null;
	}

	// 查看商品明细
	public String findGoodsById(HttpServletRequest request,
			HttpServletResponse response) {
		Goods goods = gs.findGoodsById(request.getParameter("id"));
		Cookie cookie = new Cookie("history" + goods.getId(), goods.getId()
				+ "");
		cookie.setMaxAge(3600 * 24 * 30);
		cookie.setPath("/");
		response.addCookie(cookie);
		request.setAttribute("goods", goods);
		return "goods_detail.jsp";
	}

	// 添加商品
	public void save(HttpServletRequest request, HttpServletResponse response) {
		Goods goods = new Goods();
		// 检查请求表单enctype是否编码
		boolean multipartContent = ServletFileUpload
				.isMultipartContent(request);
		if (!multipartContent) {
			throw new RuntimeException("表单提交格式错误！");
		}
		// 创建文件项 工厂DiskFileItemFactory (内存缓冲区，大文件保存设置临时文件)
		DiskFileItemFactory factory = new DiskFileItemFactory();

		ServletFileUpload upload = new ServletFileUpload(factory);
		/* 设置上传文件大小限制 */
		upload.setSizeMax(1024 * 1024 * 10);// 总大小
		// upload.setFileSizeMax(1024 * 1024 * 2);// 单个文件大小
		try {
			// 解析request
			List<FileItem> list = upload.parseRequest(request);
			for (FileItem item : list) {
				if (item.isFormField()) {
					// 普通文本域
					String name = item.getFieldName();
					if ("name".equalsIgnoreCase(name)) {
						goods.setName(item.getString("UTF-8"));
					} else if ("marketprice".equalsIgnoreCase(name)) {
						goods.setMarketprice(Double.parseDouble(item
								.getString()));
					} else if ("estoreprice".equalsIgnoreCase(name)) {
						goods.setEstoreprice(Double.parseDouble(item
								.getString()));
					} else if ("category".equalsIgnoreCase(name)) {
						goods.setCategory(item.getString("UTF-8"));
					} else if ("num".equalsIgnoreCase(name)) {
						goods.setNum(Integer.parseInt(item.getString()));
					} else if ("description".equalsIgnoreCase(name)) {
						goods.setDescription(item.getString("UTF-8"));
					}
				} else {
					// 文件上传域
					String fileName = item.getName();// 文件名
					/* 得到后缀名 */
					String realFileName = UploadUtils.subFileName(fileName);
					/* uuid解决文件重名覆盖问题 */
					String uuidFileName = UploadUtils
							.generateRandonFileName(realFileName);
					String mimeType = getServletContext().getMimeType("*.jpg");
					String contentType = item.getContentType();
					if (!mimeType.equals(contentType)) {
						throw new RuntimeException("文件类型不符合要求");
					}
					// 设置上传的服务器路径路径
					// 1,设置文件夹 /3/5
					String generateRandomDir = UploadUtils
							.generateRandomDir(uuidFileName);
					String realPath = getServletContext().getRealPath(
							"/upload" + generateRandomDir);
					File dirFile = new File(realPath);
					dirFile.mkdirs();
					// 2,设置文件路径
					File file = new File(dirFile, uuidFileName);
					// 利用包装后的io类写入
					try {
						item.write(file);
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						item.delete(); // 删除临时文件
					}
					// 数据库保存路径
					goods.setImgurl("/upload" + generateRandomDir + "/"
							+ uuidFileName);
				}
			}
			// 将goods录入数据库
			gs.save(goods);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter()
					.println(
							"<a href='javascript:void(0);' onclick='window.history.go(-1);'>继续添加</a>&nbsp;<a href='javascript:void(0);' onclick=''>查询商品列表</a>");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("文件上传失败");
		}
	}

}
