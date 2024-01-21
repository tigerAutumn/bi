package com.pinting.gateway.reapal.out.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.reapal.out.config.ReapalConfig;
import com.pinting.gateway.reapal.out.enums.ReapalDsfUrl;
import com.pinting.gateway.reapal.out.enums.ReapalUrl;
import com.pinting.gateway.reapal.out.model.req.ReapalBaseOutReq;

/* *
 *类名：ReapalSubmit
 *功能：融宝各接口请求提交类
 *详细：构造融宝各接口发送请求数据，获取远程HTTP数据
 *版本：3.1.2
 *日期：2015-08-15
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究融宝接口使用，只是提供一个参考。
 */

public class ReapalSubmit {
	private static Logger logger = LoggerFactory.getLogger(ReapalSubmit.class);
	
	/**
	 * 快捷发送请求
	 * 
	 * @return 返回结果
	 */
	public static String buildSubmit(ReapalBaseOutReq req, ReapalUrl url) throws Exception {
		Map<String, String> para = ReapalMessageUtil.transBeanMap(req);
		// 生成签名
		String mysign = Md5Utils.BuildMysign(para, ReapalConfig.key);
		para.put("sign_type", ReapalConfig.sign_type);
		para.put("sign", mysign);
		// 将map转化为json串
		String json = JSON.toJSONString(para);
		// 加密数据
		Map<String, String> maps = Decipher.encryptData(json);
		maps.put("merchant_id", ReapalConfig.merchant_id);
		// 发送请求 并接收
		logger.info("Reapal交易名称：【" + url.getDescription() + "：" + ReapalConfig.rongpay_api + url.getCode() + "】");
		String post = HttpClientUtil.post(ReapalConfig.rongpay_api + url.getCode(), maps);
		logger.info("Reapal响应报文：【" + post + "】");
		return post;
	}
	
	/**
	 * 代付发送请求
	 * 
	 * @return 返回xml字符串结果，程序异常则返回null
	 */
	public static String buildDsfSubmit(ReapalBaseOutReq req, ReapalDsfUrl url){
		String resp = null;
		try {
			Map<String, String> para = ReapalMessageUtil.transBeanMap(req);
			// 生成签名
			Map<String, String> sParaNew = DsfFunction.ParaFilter(para); //除去数组中的空值和签名参数
			String mysign = DsfFunction.BuildMysign(sParaNew, ReapalConfig.key);//生成签名结果
			para.put("signType", ReapalConfig.signType);
			para.put("sign", mysign);
			// 获取代付明细数据进行加密
			String batchContent = para.get("batchContent");
			if(!StringUtil.isEmpty(batchContent)){
				String batchContentJm = DsfFunction.jm(batchContent,ReapalConfig.agentPayCertPath);
				para.put("batchContent", batchContentJm);
			}
			// 发送请求 并接收
			logger.info("Reapal交易名称：【" + url.getDescription() + "：" + ReapalConfig.agentPayGate + url.getCode() + "】");
			
			resp = HttpClientUtil.post(ReapalConfig.agentPayGate + url.getCode(), para);
			
			logger.info("Reapal响应报文：" + resp);
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		return resp;
	}
}
