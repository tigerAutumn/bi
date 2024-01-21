package com.pinting.business.util;




import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * 接口签名工具
 */
public class SignUtil {
	
	/**
	 * 将所有参数值按升序排序
	 * 
	 * @param params
	 * @return
	 */
	public static String sortParamsToSign(Map<String, String> params) {

		// !按参数名字典排序
		List<String> valList = Arrays.asList(params.keySet().toArray(new String[params.size()]));
		Collections.sort(valList);

		StringBuilder sb = new StringBuilder();

		for (String k : valList) {
			// !跳过 不被签名参数
			if (k.equals("sign")) {
				continue;
			}
			sb.append(k).append("=").append(params.get(k)).append("&");
		}
		if (params.size() > 1)
			sb.delete(sb.length() - 1, sb.length()); // 去掉最后一个字符
		return sb.toString();
	}
	
}
