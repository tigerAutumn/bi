package com.pinting.core.base;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.util.ConsMsgUtil;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;



public class BaseController {
	protected Logger log = null;
	
	public BaseController(){
		this.log = LoggerFactory.getLogger(this.getClass());
	}
	

	/**
	 * 交易成功 默认公共信息返回
	 * @param dataMap
	 */
	public void successRes(Map<String, Object> dataMap){
		dataMap.put(ConsMsgUtil.RESCODE, ConstantUtil.RESCODE_SUCCESS);
		dataMap.put(ConsMsgUtil.RESMSG, ConstantUtil.DEFAULT_SUCESSMSG);
	}
	/**
	 * business平台交易成功  公共信息返回
	 * @param dataMap
	 * @param resMsg
	 */
	public void successRes(Map<String, Object> dataMap, ResMsg resMsg){
		dataMap.put(ConsMsgUtil.RESCODE, ConstantUtil.RESCODE_SUCCESS);
		dataMap.put(ConsMsgUtil.RESMSG, ConstantUtil.DEFAULT_SUCESSMSG);
		dataMap.put(ConsMsgUtil.BSRESCODE, resMsg.getResCode());
		dataMap.put(ConsMsgUtil.BSRESMSG, resMsg.getResMsg());
		
	}
	/**
	 * 交易失败 默认 公共信息返回
	 * @param dataMap
	 */
	public void errorRes(Map<String, Object> dataMap){
		dataMap.put(ConsMsgUtil.RESCODE, ConstantUtil.RESCODE_FAIL);
		dataMap.put(ConsMsgUtil.RESMSG, ConstantUtil.DEFAULT_ERRMSG);
	}
	/**
	 * 交易失败 异常信息 公共信息返回
	 * @param dataMap
	 * @param e
	 */
	public void errorRes(Map<String, Object> dataMap, Exception e){
		dataMap.put(ConsMsgUtil.RESCODE, ConstantUtil.RESCODE_FAIL);
		if(e == null || "".equals(e.getMessage())){
			dataMap.put(ConsMsgUtil.RESMSG, ConstantUtil.DEFAULT_ERRMSG);
		}else{
			String errorMsg = e.getMessage();
			if(errorMsg.contains("Connection refused")){
				errorMsg = "网络似乎不好哦~~~";
			}else{
				errorMsg = StringUtil.left(errorMsg, 36);
			}
			
			dataMap.put(ConsMsgUtil.RESMSG, errorMsg);
		}
	}
	/**
	 * 交易失败  自定义message 公共信息返回
	 * @param dataMap
	 * @param message
	 */
	public void errorRes(Map<String, Object> dataMap, String message){
		dataMap.put(ConsMsgUtil.RESCODE, ConstantUtil.RESCODE_FAIL);
		if(message == null || "".equals(message)){
			dataMap.put(ConsMsgUtil.RESMSG, ConstantUtil.DEFAULT_ERRMSG);
		}else{
			dataMap.put(ConsMsgUtil.RESMSG, message);
		}
	}
	/**
	 * business平台交易失败  公共信息返回
	 * @param dataMap
	 * @param resMsg
	 */
	public void errorRes(Map<String, Object> dataMap, ResMsg resMsg){
		dataMap.put(ConsMsgUtil.RESCODE, ConstantUtil.RESCODE_FAIL);
		dataMap.put(ConsMsgUtil.RESMSG, resMsg.getResMsg());
		dataMap.put(ConsMsgUtil.BSRESCODE, resMsg.getResCode());
		dataMap.put(ConsMsgUtil.BSRESMSG, resMsg.getResMsg());
		
	}
	
}
