package com.sxlinks.utils;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Package: com.chinamobile.iot
 * Description：处理url的相关方法
 *
 * @version 1.0
 * @date: Created in 2020/1/19 10:29
 */
public class UrlUtil {
	/**
	 * 根据map生成有序的请求参数-值的字符串
	 * @param params 请求参数
	 * @return 有序的请求参数-值的字符串
	 */
	public static String getOrderedParamString(Map<String, String> params) {
		if (params == null || params.size() == 0) {
			return "";
		}
		//按参数名的字典顺序
		Map<String, String> paramMap = new TreeMap<String, String>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}
				});
		paramMap.putAll(params);

		//用=号和&号拼装参数
		StringBuilder canonicalQueryString = new StringBuilder();
		for (Map.Entry<String, String> entry : paramMap.entrySet()) {
			canonicalQueryString.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		if (canonicalQueryString.length() > 1) {
			canonicalQueryString.setLength(canonicalQueryString.length() - 1);
		}
		return canonicalQueryString.toString();
	}
}
