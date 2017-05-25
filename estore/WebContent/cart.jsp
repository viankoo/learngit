<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的购物车</title>
<%@include file="inc/common_head.jsp"%>
<script type="text/javascript">
	function delectgoods(gid) {
		var b = window.confirm("您确定要删除该商品吗？");
		if (b) {
			location = "${path}/cartServlet?method=del&gid="+gid;
		}
	}
	function updateNum(gid,buynum) {
		  //  购买数量获取
 	   var num = buynum;
 	     //  是否合法    不能 小数  负数  字符  最大数量不超过100
 	     //  用户不输入 
 	      if(num==""){
 	    	  alert("请输入数值");
 	    	  return ;
 	      }
 	      //  全局函数   parseInt/float 
 	     var n =  parseInt(num);// 
 	      if(num!=n){
 	    	  // 字符/小数
 	    	  alert("请输入数值类型");
 	    	  return ;
 	      }
 	      if(n<=0){
 	    	  // 负数和0
 	    	  alert("请输入正整数");
 	    	  return ;
 	      }
 	      if(n>30){
 	    	  // 超过购买限制
 	    	  document.getElementById(gid+"buynum").value=29;
 	      }
		
		//ajax 发送buynum gid uid   返回“总计钱数@节省钱数@商品小计”
		ajax({
			url:"${path}/cartServlet?method=changBuynum&buynum="+buynum+"&gid="+gid,
			method:"GET",
			callback:function(data){
				paymoney.innerHTML = data.split("@")[0];
				savemoney.innerHTML = data.split("@")[1];
				document.getElementById(gid+"xiaoji").innerHTML  = data.split("@")[2];
			}
		});
	}
	
	function clearCart() {
		var b = window.confirm("您确定要清空购物车吗？");
		if (b) {
			location.href="${path}/cartServlet?method=clearCart";
		}
	}
</script>
</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block table">
		<div class="AreaR">
			<div class="block box">
				<div class="blank"></div>
				<div id="ur_here">
					当前位置: <a href="index.jsp">首页</a><code>&gt;</code>我的购物车
				</div>
			</div>
			<div class="blank"></div>
			<div class="box">
				<div class="box_1">
					<div class="userCenterBox boxCenterList clearfix"
						style="_height:1%;">
						<h5><span>我的购物车</span></h5><a href="javascript:;" onclick="clearCart();">清空购物车</a>
						<table width="100%" align="center" border="0" cellpadding="5"
							cellspacing="1" bgcolor="#dddddd">
							<tr>
								<th bgcolor="#ffffff">商品名称</th>
								<th bgcolor="#ffffff">市场价</th>
								<th bgcolor="#ffffff">本店价</th>
								<th bgcolor="#ffffff">购买数量</th>
								<th bgcolor="#ffffff">小计</th>
								<th bgcolor="#ffffff" width="160px">操作</th>
							</tr>
							<c:if test="${empty cList }">
								买点东西吧，主人^ ^
							</c:if>
							<c:if test="${not empty cList }">
								<c:set value="0" var="marketsum"></c:set>
								<c:set value="0" var="estoresum"></c:set>
								<c:forEach var="goods" items="${cList }">
									<tr>
										<td bgcolor="#ffffff" align="center" style="width:300px;">
											<!-- 商品图片 -->
											<a href="javascript:;" target="_blank">
												<img style="width:80px; height:80px;"
												src="${path }${goods.imgurl}"
												border="0" title="${goods.name }" />
											</a><br />
											<!-- 商品名称 -->
											<a href="javascript:;" target="_blank" class="f6">${goods.name }</a>
										</td>
										<td align="center" bgcolor="#ffffff">${goods.marketprice }元</td>
										<td align="center" bgcolor="#ffffff">${goods.estoreprice }元</td>
										<td align="center" bgcolor="#ffffff">
											<input id="${goods.id }buynum"value="${goods.buynum }" onblur="updateNum(${goods.id},value);" size="4" class="inputBg" style="text-align:center;" />
										</td>
										<td id="${goods.id }xiaoji" align="center" bgcolor="#ffffff">${goods.estoreprice*goods.buynum }元</td>
										<td align="center" bgcolor="#ffffff">
											<a href="javascript:;" class="f6" onclick="delectgoods(${goods.id});">删除</a>
										</td>
									</tr>
									<c:set value="${marketsum+goods.marketprice*goods.buynum }" var="marketsum"></c:set>
									<c:set value="${estoresum+goods.estoreprice*goods.buynum }" var="estoresum"></c:set>
								</c:forEach>
							</c:if>
							<tr>
								<td colspan="6" style="text-align:right;padding-right:10px;font-size:25px;">
									购物金额小计&nbsp;<font id="paymoney" color="red">${estoresum }</font>元，
									共为您节省了&nbsp;<font id="savemoney" color="red">${marketsum-estoresum }</font>元
									<a href="${path}/orderServlet?method=orderlist" onclick="pay()"><input value="去结算" type="button" class="btn" /></a>
								</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="blank"></div>
		<div class="blank5"></div>
	</div>
	<%@include file="inc/footer.jsp"%>
</body>
</html>
