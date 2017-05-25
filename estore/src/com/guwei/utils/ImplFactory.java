package com.guwei.utils;

import java.util.ResourceBundle;

public class ImplFactory {
	// 根据用户传入类型<T>的Class文件，获取制定接口实现类
	public static <T> T getInstance(Class<T> c) {
		// 获得字节码文件的名称
		String interfaceName = c.getSimpleName();
		String value = ResourceBundle.getBundle("tps").getString(interfaceName);
		if (value != null) {
			try {
				return (T) Class.forName(value).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("配置文件有误，请检查配置文件");
		}
	}
}
