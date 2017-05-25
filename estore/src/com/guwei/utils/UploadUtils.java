package com.guwei.utils;

import java.util.Random;
import java.util.UUID;

public class UploadUtils {
	/**
	 * 截取真实文件名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String subFileName(String fileName) {
		// 查找最后一个 \出现位置
		int index = fileName.lastIndexOf("\\");
		if (index == -1) {
			// 没有找到 火狐浏览器
			return fileName;
		}
		// IE 访问的文件名称
		return fileName.substring(index + 1);
	}

	// 获得随机UUID文件名
	public static String generateRandonFileName(String fileName) {
		// 获得扩展名
		// String ext = fileName.substring(fileName.lastIndexOf("."));
		return UUID.randomUUID().toString() + "_" + fileName;
	}

	// 获得hashcode生成二级目录
	public static String generateRandomDir(String uuidFileName) {
		int hashCode = uuidFileName.hashCode();
		// 一级目录
		int d1 = hashCode & 0xf;
		// 二级目录
		int d2 = (hashCode >> 4) & 0xf;
		return "/" + d1 + "/" + d2; // /1/2 /3/4
	}

	/**
	 * @param level
	 *            生成文件夹的层级（深度）
	 * @param dirsCount
	 *            每个文件夹中最多有多少个文件夹
	 * @return
	 */
	public static String randomDirs(int level, int dirsCount) {
		Random r = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < level; i++) {
			sb.append("/" + r.nextInt(dirsCount));
		}
		// System.out.println(sb.toString());
		return sb.toString();
	}

}
