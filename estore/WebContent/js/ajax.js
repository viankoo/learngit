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

/**
 * ajax的封装
 * 传递参数，使用对象：
 * {
 * 	url: 'xxServlet', // 请求路径
 * 	method: 'get|post', // 请求方式
 * 	async: false|true, // 同步还是异步，默认true
 * 	callback: function() {},
 * 	params: "a=1&b=2",
 * 	cache: false|true // 是否使用缓存
 * }
 */
function ajax(options) {
	// 1. 获取ajax的核心对象XMLHTTPRequest
	var XHR = getXHR();
	
	// 2. 监听服务器返回状态
	XHR.onreadystatechange = function() {
		if (XHR.readyState == 4 && XHR.status == 200) {
			// 获取服务器返回的数据
			var jsonStr = XHR.responseText;
			if ((typeof options.callback) == "function") {
				options.callback(jsonStr);
			}
		}
	};
	// 将请求方式转换为字符串
	options.method += "";
	options.method = options.method.toUpperCase();
	if (options.method != "POST") {
		options.method = "GET";
	}
	
	// 默认true: options.async !== false
	// 默认false: options.async === true
	
	// 根据get或post处理参数和url
	if ("GET" == options.method) {
		// url: a?name=1
		// params: bb=1&ccc=2
		if (options.url.indexOf("?") > -1) {
			options.url = options.url + "&" + options.params;
		} else {
			options.url = options.url + "?" + options.params;
		}
		options.params = null;
		
		// 判断是否需要使用缓存
		if (options.cache === false) {
			var r = new Date().getTime();
			if (options.url.indexOf("?") > -1) {
				options.url += "&_t=" + r;
			} else {
				options.url += "?_t=" + r;
			}
		}
	}
	
	// 3. 发送请求
	XHR.open(options.method, options.url, options.async !== false);
	
	if (options.method == "POST") {
		XHR.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	}
	
	XHR.send(options.params);
}