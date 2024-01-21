package com.pinting.gateway.xicai.out.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.gateway.xicai.out.config.Config;
import com.pinting.gateway.xicai.out.model.BindUserCallBackReq;
import com.pinting.gateway.xicai.out.model.InvestCallBackReq;

/**
 * @author admin
 *
 */
public class ParameterHelper {
	
	private static Logger logger = LoggerFactory.getLogger(ParameterHelper.class);
	
	/**
	 * @param pid p2p产品id,多个时请用“，”号分隔开
	 * @return
	 */
	public static String push_p2p(String pid){
		String param = "client_id="+Config.client_id+"&client_secret="+Config.client_secret+"&pid="+pid;
		logger.info("调用希财推送产品ID接口，发送数据：【"+param+"】");
		return param;
	}
	
	public static String auto_login(){
		return "";
	}
	
	/**
	 * 投资回调url参数
	 * */
	public static String InvestCallBack(InvestCallBackReq invest){
		String param = "commision="+invest.getCommision()+"&datetime="+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())+"&earnings="+invest.getEarnings()+"&id="+invest.getId()+"&investamount="+invest.getInvestamount()+"&phone="+invest.getPhone()+"&pid="+invest.getPid()+"&url="+invest.getUrl();
		logger.info("调用希财投资回调接口，发送数据：【"+param+"】");
		String sign;
		try {
			sign = DesUtil.encrypt(param, Config.client_secret.substring(0, 8));
			sign = java.net.URLEncoder.encode(sign, "utf8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}
		return "client_id="+Config.client_id+"&sign="+sign;
	}
	
	/**
	 * 绑定回调接口参数
	 * @param bindUserCallBackReq
	 * @return
	 */
	public static String BindUserCallBack(BindUserCallBackReq bindUserCallBackReq){
		String param = "phone="+bindUserCallBackReq.getPhone()+"&name="+bindUserCallBackReq.getName()+"&result="+bindUserCallBackReq.getResult()+"&display="+bindUserCallBackReq.getDisplay();
		logger.info("调用希财绑定回调接口，发送数据：【"+param+"】");
		String sign;
		try {
			sign = DesUtil.encrypt(param, Config.client_secret.substring(0, 8));
			sign = java.net.URLEncoder.encode(sign, "utf8");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "";
		}
		return "client_id="+Config.client_id+"&sign="+sign;
		
	}
}
