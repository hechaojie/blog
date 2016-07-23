package com.blog.front.util;

import java.util.Random;

/**
 * @功能描述 数字工具类
 * @Version		V1.0
 * @Date		2016-1-13 下午3:39:15
 * @author hechaojie
 */
public class NumberUtil {

	/**
	 * 随机生成几位数
	 */
	public static String getRandomNumber(int n) {
		int baseNum = 1;
		for (int i = 0; i < n; i++) {
			baseNum *= 10;
		}
		Random rad = new Random();
		String orders = String.valueOf(rad.nextInt(baseNum));
		int len = orders.length();
		for (int i = 0; i < n - len; i++) {
			orders = "0"+orders;
		}
		return orders;
	}
}
