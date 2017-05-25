/**  
 * All rights Reserved, Designed By www.tydic.com
 * @Title:  ProxyUtils.java   
 * @Package com.guwei.utils   
 * @Description:    TODO(用一句话描述该文件做什么)   
 * @author: gw  
 * @date:   2017年5月16日 下午4:24:50   
 * @version V1.0 
 * @Copyright: 2017 www.tydic.com Inc. All rights reserved.
 * 注意：本内容仅限于上海黑马内部传阅，禁止外泄以及用于其他的商业目
 */
package com.guwei.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * @ClassName: ProxyUtils
 * @Description:TODO(生成代理类的泛型模板)
 * @author: gw
 * @date: 2017年5月16日 下午4:24:50
 * @param:
 * @Copyright: 2017 www.tydic.com Inc. All rights reserved.
 *             注意：本内容仅限于上海黑马内部传阅，禁止外泄以及用于其他的商业目
 */
public class ProxyUtils<T> {
	private T target;

	public ProxyUtils(T target) { // 构造方法传递目标对象
		this.target = target;
	}

	@SuppressWarnings("unchecked")
	public T getProxy() {
		return (T) Proxy.newProxyInstance(ProxyUtils.class.getClassLoader(),
				target.getClass().getInterfaces(), new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method,
							Object[] objs) throws Throwable {
						// 哪些方法需要添加事务管理 定义规范 目标类业务方法以: save开头 或者 delete 方法开头
						// 添加事务管理
						String save = ResourceBundle.getBundle("transcation")
								.getString("key1");
						String delete = ResourceBundle.getBundle("transcation")
								.getString("key3");
						Logger logger = Logger.getLogger(Proxy.class);
						if (method.getName().startsWith(save)
								|| method.getName().startsWith(delete)) {
							try {
								TranscationManager.start();
								logger.info("info:开启事物");
								return method.invoke(target, objs);
							} catch (Exception e) {
								e.printStackTrace();
								TranscationManager.rollback();
								logger.info("info:回滚事物");
								return false;
							} finally {
								TranscationManager.commit();
								logger.info("info:关闭事物");
							}

						}
						return method.invoke(target, objs);
					}
				});
	}
}
