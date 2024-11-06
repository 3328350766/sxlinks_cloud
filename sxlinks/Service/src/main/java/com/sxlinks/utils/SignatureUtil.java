package com.sxlinks.utils;

import com.sun.istack.internal.NotNull;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Map;

/**
 * Package: com.chinamobile.iot
 * Description：构造请求签名的工具类
 *
 * @version 1.0
 * @date: Created in 2020/1/19 10:15
 */
public class SignatureUtil {
	private final static String CHARSET_UTF8 = "utf8";
	private final static String UTF8_CAPITAL = "UTF-8";
	private final static String AND_SYMBOL = "&";
	private final static String HMAC = "HmacSHA1";

	private static final Base64.Encoder encoder = Base64.getEncoder();

	/**
	 * @param httpMethod 请求方法：GET/POST/DELETE/PUT等等
	 * @param queryParam 请求参数，对应url中?后面的内容，key为参数名，value为参数值（signature自身除外）
	 * @param body 请求体，如果没有，则传null
	 * @return 该请求的对应的signature值
	 */
	public static String doSignature(String accessKeySecret, String httpMethod, Map<String, String> queryParam, String body)
			throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
		//将map进行排序并拼装为字符串
		String orderedParamString = UrlUtil.getOrderedParamString(queryParam);
		//追加body内容
		String orderedParamAndBody = body == null ? orderedParamString : orderedParamString + body;
		//拼装http method
		StringBuilder wholeRequestContent = new StringBuilder();
		wholeRequestContent.append(httpMethod).append(AND_SYMBOL)
				.append(encode("/")).append(AND_SYMBOL).append(encode(orderedParamAndBody));
		//计算HMAC值，并进行base64编码
		return encoder.encodeToString(hmacSHA1Signature(accessKeySecret, wholeRequestContent.toString()));
	}

	/**
	 * 使用UTF-8字符集按照 RFC3986 规则编码整个字符串
	 * @param value 需要编码的参数
	 * @return
	 */
	private static String encode(String value) throws UnsupportedEncodingException {
		return value != null ? URLEncoder.encode(value, UTF8_CAPITAL).replace("+", "%20")
				.replace("*", "%2A").replace("%7E", "~") : "";
	}

	/**
	 * 计算HMAC值
	 * @param baseString 完整的请求内容
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] hmacSHA1Signature(String accessKeySecret, String baseString)
			throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException {
		if (baseString == null || baseString.length() == 0) {
			return null;
		}
		Mac mac = Mac.getInstance(HMAC);
		SecretKeySpec keySpec = new SecretKeySpec(accessKeySecret.getBytes(CHARSET_UTF8), UTF8_CAPITAL);
		mac.init(keySpec);
		return mac.doFinal(baseString.getBytes(CHARSET_UTF8));
	}
}
