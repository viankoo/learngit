<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选购中心</title>
<%@include file="inc/common_head.jsp"%>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block box">
		<div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href="index.jsp">首页</a>
			<code>&gt;</code>
			商品列表
		</div>
	</div>
	<div class="blank"></div>
	<div class="block clearfix">
		<div class="AreaR">
			<div class="box">
				<div class="box_1">
					<h3>
						<span>商品添加</span>
					</h3>
			<form action="${path }/goodsServlet?method=save" method="post" name="formGoods" enctype="multipart/form-data">
					<table width="100%" border="0" align="left" cellpadding="5"
						cellspacing="3">
						<tr>
							<td width="25%" align="right">商品名称</td>
							<td width="65%"><input name="name" type="text"
								id="name" onblur="is_goods(this.value);"
								class="inputBg" /> <span id="name_notice"
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">商品商城价格</td>
							<td><input name="estoreprice" type="text"
								class="inputBg" /> <span
								style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">商品市场价格</td>
							<td><input name="marketprice" type="text"  class="inputBg" />
								<span style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">商品类别</td>
							<td>
								<select name="category">
									  <option value="化妆品">化妆品</option>
									  <option value="书籍">书籍</option>
									  <option value="鞋包配饰">鞋包配饰</option>
									  <option value="服装内衣">服装内衣</option>
									  <option value="团购商品">团购商品</option>
							     </select>
							</td>
						</tr>
						<tr>
							<td align="right">商品数量</td>
							<td><input name="num" type="text" class="inputBg" />
								<span style="color:#FF0000"> *</span></td>
						</tr>
						<tr>
							<td align="right">商品图片</td>
							<td><input name="imgurl" type="file"/>
							</td>
						</tr>
						<tr>
							<td align="right">商品描述</td>
							<td>
							<textarea cols="22" rows="10" name="description"></textarea>
						  </td>
						</tr>
							<tr>
							<td align="right">
								<input type="submit" value="添加商品">
							</td>
						</tr>
					</table>
				</form>
				</div>
			</div>
		</div>
	</div>
	<%@include file="inc/footer.jsp"%>
	<script type="text/javascript">
		window.onload = function() {
			fixpng();
		}
	</script>
</body>
</html>