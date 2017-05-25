package com.guwei.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5Utils {

	public static String md5(String plainText) {
		byte[] secretBytes = null;
		try {
			secretBytes = MessageDigest.getInstance("md5").digest(
					plainText.getBytes());
		} catch (Exception e) {
			throw new RuntimeException("加密失败");
		}
		return new BigInteger(1, secretBytes).toString(16);
	}
}
