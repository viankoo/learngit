<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	var txt = '{ "employees" : [' +
	'{ "firstName":"Bill" , "lastName":"Gates" },' +
	'{ "firstName":"George" , "lastName":"Bush" },' +
	'{ "firstName":"Thomas" , "lastName":"Carter" } ]}';
	// 将上述字符串 转换成 JS对象 
// 方法1 采用 eval
	var obj = eval("("  + txt  +  ")"); // 不推荐使用
	alert(obj);
// 方法2 采用JSON.parse对象方法
	var obj2 = JSON.parse(txt);
	alert(obj2);
// 查看转换之后的数据结构
	var jiegou = JSON.stringify(obj2);
	alert(jiegou);
	
	var ob="{'name':'lisi'}";
	var oo = JSON.parse(ob);
	alert(oo);
</script>

<script type="text/javascript">

   //  第一步 获取ajax核心对象XMLHttpRequest
	function getXHR() {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			// code for IE7+, Firefox, Chrome, Opera, Safari
			xmlhttp = new XMLHttpRequest();
		} else {
			// code for IE6, IE5
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		return xmlhttp;
	}

	function checkEmail(email) {
        var msg = document.getElementById("email_sp");// 获取span对象
		if (email == "") {
			msg.color = "red";
			msg.innerHTML = "请输入邮箱账号";
			return;
		}
		// 2. 获取核心对象
		var XHR = getXHR();
		
		// 3. 监听服务器返回的状态,定义回调函数,该函数用于获取服务器响应的数据,该函数一开始是不执行的,只有服务器响应时才会执行.
		XHR.onreadystatechange = function() {
			if (XHR.readyState == 4 && XHR.status == 200) {
				// 这个时候，表示响应已经就绪，才可以获取到服务器返回的数据
				// 4. 获取服务器返回的数据，做不同的处理
				var result = XHR.responseText; // 返回结果全部是字符串
				// do something...
				//假设0是不存在，其他情况为存在
				if (result == "0") {
					msg.color = "green";
					msg.innerHTML = "账号可用√";
				} else {
					msg.color = "red";
					msg.innerHTML = "账号存在!";
				}
			}
		};
		
		// 3. 发送异步请求 open方法三个参数 1 请求方式,2 请求服务器动态资源地址 3 是否异步 默认true 不写也可以 异步请求  false:同步请求
		XHR.open("POST", "demo.jsp", true);
		// POST请求一定要加请求头,数据以请求体发送给服务器   GET请求因为数据是以Url形式传递给服务器故而不需要下面代码
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		XHR.send("email="+email);// post请求提交数据
	}
</script>
</head>
<body>
邮箱账号：<input type=”text” name="email" onblur="checkEmail(value);"/>
	<font id="email_sp"></font><br>
<!--  value等同this.value  -->
用户密码:  <input type=”password” name=”password”>
</body>
</html>
