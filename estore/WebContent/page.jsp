<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
	.page, .page div{overflow:hidden;width:100%;margin-top:5px;}
	.page {text-align:right;margin:0;padding:0;font-size:12px;}
   	.page button{margin:0;cursor:pointer;}
   	.page button.curr{color:black;border-color:red;background-color:transparent; font-weight:bold;cursor:auto;}
   	.page span button {color:blue;}
   	.page select {margin:0;padding:0;width:39px;}
   	.page input {margin:0;padding:0;padding-left:2px;width:27px;}
</style>
<hr/>
<!-- 分页开始 -->
<div class="page">
	<script type="text/javascript">
		function gogogo(pageNum) {
			location = "${path}/goodsServlet?method=queryPage&currPage=" 
			+ pageNum + "&pageSize=" + pageSize.value;
		}
	</script>
	<c:if test="${bean.currPage > 1 }">
		<button onclick="gogogo(1)">首页</button>
		<button onclick="gogogo(${bean.currPage-1})">上一页</button>
	</c:if>
	<span>
		<c:forEach begin="${bean.startPage }" end="${bean.endPage }" var="i">
			<button class="${bean.currPage == i ? 'curr' : ''}" onclick="gogogo(${i});">${i}</button>
		</c:forEach>
	</span>
	
	<c:if test="${bean.currPage < bean.totalPage }">
		<button onclick="gogogo(${bean.currPage+1})">下一页</button>
		<button onclick="gogogo(${bean.totalPage})">尾页</button>
	</c:if>
	<div>
		共 ${bean.totalCount } 条记录，
		每页 <select id="pageSize">
			<option value="5">5</option>
			<option value="10" ${bean.pageSize == 10 ? 'selected' : '' }>10</option>
			<option value="20" ${bean.pageSize == 20 ? 'selected' : '' }>20</option>
		</select> 条，
		当前页 ${bean.currPage } / ${bean.totalPage }，
		跳转到第 <input id="currPage" value="${bean.currPage }" style="width:35px;" /> 页
		<button onclick="gogogo(currPage.value)">GO</button>
	</div>
</div>
<!-- 分页结束 -->