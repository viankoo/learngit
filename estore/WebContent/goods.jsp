<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>选购中心</title>
<%@include file="inc/common_head.jsp"%>
<%@include file="inc/header.jsp"%>
</head>
<body>
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
						<span>商品列表</span>
					</h3>
					<!-- 商品列表开始 -->
					<div class="clearfix goodsBox" style="border:none; ">
						<c:if test="${empty bean.data }">
						      <font color="blue">您还没添加任何商品,请先添加..</font>
						</c:if>
						<!-- 第1个商品 -->
						<c:forEach var="goods" items="${bean.data }">
							<div class="goodsItem" style="padding: 10px 4px 15px 1px;">
								<a href="${path}/goodsServlet?method=findGoodsById&id=${goods.id}">
									<img src="${path }${goods.imgurl}"
									alt="${goods.name }" class="goodsimg" />
								</a><br />
								<p style=" height:20px; overflow:hidden;">
									<a href="${path}/goodsServlet?method=findGoodsById&id=${goods.id}" title="">${goods.name }</a>
								</p>
								市场价：<font class="market">${goods.marketprice }元</font><br /> 本店价：<font class="f1">${goods.estoreprice }元
								</font>
							</div>
						</c:forEach>
						<div align="center">
							<%@ include file="page.jsp" %>
						</div>
					</div>
					<!-- 商品列表结束 -->
					<div>
						<c:if test="${not empty historygoods }">
							<font>您的足迹：</font>
							<c:forEach var="hg" items="${historygoods }" >
								<div class="goodsItem" style="padding: 10px 4px 15px 1px;">
									<a href="${path}/goodsServlet?method=findGoodsById&id=${hg.id}">
										<img src="${path }${hg.imgurl}"
										alt="${hg.name }" class="goodsimg" />
									</a><br />
									<p style=" height:20px; overflow:hidden;">
										<a href="${path}/goodsServlet?method=findGoodsById&id=${hg.id}" title="">${hg.name }</a>
									</p>
								</div>
							</c:forEach>
						</c:if>
					</div>
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