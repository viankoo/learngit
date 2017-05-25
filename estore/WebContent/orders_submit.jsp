<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="Generator" content="ECSHOP v2.7.3" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<title>提交订单</title>
<%@include file="inc/common_head.jsp"%>

</head>
<body>
	<%@include file="inc/header.jsp"%>
	<div class="block clearfix"><div class="AreaR">
	<div class="block box"><div class="blank"></div>
		<div id="ur_here">
			当前位置: <a href="index.jsp">首页</a><code>&gt;</code>购物流程
		</div>
	</div><div class="blank"></div><div class="box"><div class="box_1">
	<div class="userCenterBox boxCenterList clearfix" style="_height:1%;">
	<form id="orderform" action="${path }/orderServlet?method=preparedOrder" method="post">
		<!---------收货人信息开始---------->
		<h5><span>收货人信息</span></h5>
		<table width="100%" align="center" border="0" cellpadding="5"
			cellspacing="1" bgcolor="#dddddd">
			<tr>
				<td bgcolor="#ffffff" align="right" width="120px">区域信息：</td>
				<td bgcolor="#ffffff">
					<!-- 省 -->
					<select id="province" name="province" onchange="load(city,value)">
						<option value="none">-- 请选择省 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<!-- 市 -->
					<select id="city" name="city" onchange="load(district,value)">
						<option value="none" >-- 请选择市 --</option>
					</select>&nbsp;&nbsp;&nbsp;
					<!-- 县(区) -->
					<select id="district" name="district">
						<option value="none">-- 请选择县(区) --</option>
					</select>
					<input type="hidden" id="hidden_add" name="add">
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">详细地址：</td>
				<td bgcolor="#ffffff">
					<input style="width:347px;" id="detailAddress" name="address"/>
				</td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">邮政编码：</td>
				<td bgcolor="#ffffff"><input id="detailAddress"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">收货人姓名：</td>
				<td bgcolor="#ffffff"><input id="detailAddress" name="acceptperson"/></td>
			</tr>
			<tr>
				<td bgcolor="#ffffff" align="right">联系电话：</td>
				<td bgcolor="#ffffff"><input id="detailAddress" name="telephone"/></td>
			</tr>
		</table>
		<!---------收货人信息结束---------->
		
		<!----------商品列表开始----------->
		<div class="blank"></div>
		<h5><span>商品列表</span></h5>
		<table width="100%" align="center" border="0" cellpadding="5"
							cellspacing="1" bgcolor="#dddddd">
							<tr>
								<th bgcolor="#ffffff">商品名称</th>
								<th bgcolor="#ffffff">市场价</th>
								<th bgcolor="#ffffff">本店价</th>
								<th bgcolor="#ffffff">购买数量</th>
								<th bgcolor="#ffffff">小计</th>
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
											<span   style="text-align:center;" >${goods.buynum }</span>
										</td>
										<td id="${goods.id }xiaoji" align="center" bgcolor="#ffffff">${goods.estoreprice*goods.buynum }元</td>
									</tr>
									<c:set value="${marketsum+goods.marketprice*goods.buynum }" var="marketsum"></c:set>
									<c:set value="${estoresum+goods.estoreprice*goods.buynum }" var="estoresum"></c:set>
								</c:forEach>
							</c:if>
							<tr>
								<td colspan="6" style="text-align:right;padding-right:10px;font-size:25px;">
								<input type="hidden" name="totalprice" value="${estoresum }">
									购物金额小计&nbsp;<font id="paymoney" color="red">${estoresum }</font>元
									<input value="提交订单" type="button" class="btn" onclick="submitform();" />
								</td>
							</tr>
						</table>
		<!----------商品列表结束----------->
	</form>
	</div></div></div></div></div>
	<%@include file="inc/footer.jsp"%>
</body>
<script type="text/javascript">
	function load(target,pid){
		target.length=1;
		district.length=1;
		if(pid=="none") return;
		ajax({
			url:"${path}/loadPCDServlet?method=loadPcd&pid="+pid,
			method:"GET",
			callback:function(data){
				var arr = JSON.parse(data);
				for (var i = 0; i < arr.length; i++) {
					var opt = document.createElement("option");
					opt.value=arr[i].id;
					opt.innerHTML=arr[i].name;
					target.appendChild(opt);
				}
			}
		});
	}
	load(province,0);
	
	
	function submitform() {
		document.getElementById("hidden_add").value= province.selectedOptions[0].innerText+city.selectedOptions[0].innerText+district.selectedOptions[0].innerText;
		document.getElementById("orderform").submit();
	}
</script>

</html>