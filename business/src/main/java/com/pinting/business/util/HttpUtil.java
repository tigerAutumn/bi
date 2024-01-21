package com.pinting.business.util;




import java.io.IOException;
import java.util.Map;

/**
 * HTTP操作工具
 * 
 * @author Hanley
 * 
 */
public class HttpUtil {
	
	private static HttpHelper httpHelper;
	
	static {
		httpHelper = new HttpHelper(3);
	}

	
	/**
	 * 将MAP转换成GET提交URL形式
	 * 
	 * @param params
	 * @return
	 */
	public static String mapParamsToUrl(Map<String, String> params) {
		StringBuilder sb = new StringBuilder();

		for (String s : params.keySet()) {
			sb.append(s).append("=").append(params.get(s)).append("&");
		}

		if (params.size() > 1)
			sb.delete(sb.length() - 1, sb.length()); // 去掉最后一个字符

		return sb.toString();
	}
	

	public static String doPost(String uri, String param, String charset) throws IOException {
		return httpHelper.doPost(uri, param, charset);
	}
	
	public static String doGet(String uri, String params) throws IOException {
		return httpHelper.doGetAsString(uri, params);
	}

}
