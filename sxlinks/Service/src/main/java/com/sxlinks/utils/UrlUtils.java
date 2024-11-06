package com.sxlinks.utils;

import org.apache.commons.lang3.RandomStringUtils;


import java.net.URL;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtils {


	/**
	 * 相对地址转绝对地址
	 * 
	 * @param absolutePath 当前绝对地址
	 * @param relativePath 相对地址
	 * @return 绝对地址
	 */
	public static String relative2Absolute(String absolutePath, String relativePath) {
		if(relativePath == null) {
			return null;
		}
		//relativePath = relativePath.toLowerCase();
		if(relativePath.startsWith("http")) {
			return relativePath;
		}
		try {
			// 以下方法对相对路径进行转换
			URL absoluteUrl = new URL(absolutePath);
			URL parseUrl = new URL(absoluteUrl, relativePath);
			return parseUrl.toString();
		} catch(Exception ex) {
			return relativePath;
		}
	}

	/**
	 * 从url中匹配虚拟目录（除去协议、域名、端口号和参数部分）
	 * @param url
	 * @param containProjectName
	 *            是否去掉项目名
	 * @return
	 */
	public static String getURI(String url, boolean containProjectName) {
		String regx = "(?:https?://)(?:(?:\\w+\\.){2,3}|[a-zA-Z0-9]+)(?:\\w+)(?::[0-9]+)?" + (containProjectName ? "(?:/\\w+/)" : "")
				+ "([^?]*)";
		Pattern p = Pattern.compile(regx);
		Matcher matcher = p.matcher(url);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	/**
	 * 匹配域名
	 * 如url为http://192.168.10.36:2829/appweb/script/plugin/ueditor/jsp/upload1/20170901/41281504250668218.jpg
	 * 返回192.168.10.36
	 *
	 * @param url
	 * @return
	 */
	public static String getHost(String url) {
		Pattern p = Pattern.compile("(?:https?://)((\\w+\\.){2,3}\\w+|[a-zA-z0-9]+)(?::[0-9]+)?");
		Matcher matcher = p.matcher(url);
		if (matcher.find()) {
			return matcher.group(1);
		}
		return null;
	}

	public static void main(String[] args) {
		String path = "http://localhost:2829/script/plugin/ueditor/jsp/upload1/20170918/95421505699068820.jpg?t=3";
//		System.out.println(getURI(path,false));///script/plugin/ueditor/jsp/upload1/20170918/95421505699068820.jpg
//		System.out.println(getHost(path));//localhost
//

		path="http://www.baidu.com:2829/app/script/plugin/ueditor/jsp/upload1/20170918/95421505699068820.jpg?t=3";
		System.out.println(getURI(path,true));///script/plugin/ueditor/jsp/upload1/20170918/95421505699068820.jpg
		System.out.println(getHost(path));//www.baidu.com

		String rand= RandomStringUtils.randomAlphabetic(6)+System.nanoTime();
		System.out.println("rand="+rand);
		String reg = "/((?!0000)[0-9]{4}-((0[1-9]|1[0-2])-(0[1-9]|1[0-9]|2[0-8])|(0[13-9]|1[0-2])-(29|30)|(0[13578]|1[02])-31)|([0-9]{2}(0[48]|[2468][048]|[13579][26])|(0[48]|[2468][048]|[13579][26])00)-02-29)/";
		reg="\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}(20|21|22|23|[0-1]\\d):[0-5]\\d:[0-5]\\d$/";



		Pattern pattern = Pattern.compile(reg);
		Pattern pattern1 = Pattern.compile("\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}");

		String test1 = "1996-11-15 23:55:31";
		String test2 = "127.0.0.1";
		Matcher matcher1 = pattern1.matcher(test1);
		System.out.println(matcher1.matches());//返回true
		//matcher = pattern1.matcher(test2);
		System.out.println(matcher1.matches());//返回false

	}



}
