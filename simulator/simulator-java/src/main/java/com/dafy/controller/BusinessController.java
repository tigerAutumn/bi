/**
 * SpringMVC restful 风格
 * 接受请求总业务控制器
 * @author yanwl
 * @date 2015-11-19
 */
package com.dafy.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dafy.core.constant.DafyInConstant;
import com.dafy.core.constant.Globals;
import com.dafy.core.util.DESUtil;
import com.dafy.core.util.DateUtil;
import com.dafy.core.util.StringUtil;
import com.dafy.core.util.encrypt.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器注解 请求映射注解 controller 代表类是控制器 restcontroller 代表类是控制器， 所有的方法 都是ajax方法
 * 
 * @author yanwl
 *
 */
@Controller
public class BusinessController {
	private static final Logger log = LoggerFactory.getLogger(BusinessController.class);
	
	/**
	 * 接受请求总业务
	 * @throws Exception
	 */
	@RequestMapping(value = "/business")
	public void bussiness(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String reqData = request.getParameter("DATA").replace(" ", "+");
		String url = "";
		if(StringUtil.isEmpty(reqData)) {
			//错误返回
			url = "/errors";
		}else {
			// DES解密，获得明文
			String decryptData = new DESUtil(Globals.DAFY_OUT_DES_KEY).decryptStr(reqData);
			JSONObject json = JSON.parseObject(decryptData);
			if(json == null) {
				url = "/errors";
			}else {
				String transCode = (String)json.get("transCode");
				switch(transCode){
				case DafyInConstant.LOGIN : url = "/dafy/login";
					 break;
				case DafyInConstant.FINANCING_BUY : url = "/financing/financing_buy";
					break;
				default:
					break;
				}
			}
		}
		request.getRequestDispatcher(url).forward(request, response);
		//return new ModelAndView("redirect:/"+url);
	}
	
	/**
	 * 接受请求总业务
	 * @throws Exception
	 */
	@RequestMapping(value = "/errors")
	@ResponseBody
	public String errors(HttpServletRequest request) throws Exception {
		String responseTime = DateUtil.format(new Date());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("respCode", DafyInConstant.RETURN_CODE_REFUSE);
		map.put("respMsg", DafyInConstant.RETURN_MSG_DECODE_FAIL);
		map.put("responseTime", responseTime);
		
		StringBuffer buffer = new StringBuffer("respCode="+DafyInConstant.RETURN_CODE_REFUSE);
		buffer.append("&respMsg="+DafyInConstant.RETURN_MSG_DECODE_FAIL);
		buffer.append("&responseTime="+responseTime);
		log.debug("============hash字段明文：【" + buffer + "】============");
		String encryptHash = MD5Util.encryptMD5(buffer.toString());
		log.debug("============hash字段密文：【" + encryptHash + "】============");
		
		map.put("hash", encryptHash);
		
		String message = JSON.toJSONString(map);
		log.debug("============发送报文：【" + message + "】============");
		// 对json进行DES加密
		String ciphertext = new DESUtil(Globals.DAFY_OUT_DES_KEY).encryptStr(message);
		log.debug("============报文转换密文：【" + ciphertext + "】============");
		return ciphertext;
	}
}
