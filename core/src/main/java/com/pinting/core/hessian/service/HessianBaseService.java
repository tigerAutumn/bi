package com.pinting.core.hessian.service;

import com.pinting.core.exception.PTException;
import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.util.ConstantUtil;

public class HessianBaseService {
	/**
	 * 交易成功 默认公共信息返回
	 * @param reqMsg
	 * @param resMsg
	 */
	public void successRes(ReqMsg reqMsg, ResMsg resMsg){
		resMsg.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		resMsg.setResMsg(ConstantUtil.DEFAULT_SUCESSMSG);
		resMsg.setVersion(reqMsg.getVersion());
		resMsg.setMsgID(reqMsg.getMsgID());
	}
	/**
	 * 交易失败 默认 公共信息返回
	 * @param reqMsg
	 * @param resMsg
	 */
	public void errorRes(ReqMsg reqMsg, ResMsg resMsg){
		if(resMsg == null){
			resMsg = new ResMsg();
		}
		resMsg.setResCode(ConstantUtil.BSRESCODE_FAIL);
		resMsg.setResMsg(ConstantUtil.DEFAULT_ERRMSG);
		resMsg.setVersion(reqMsg.getVersion());
		resMsg.setMsgID(reqMsg.getMsgID());
	}
	/**
	 * 交易失败 异常信息 公共信息返回
	 * @param reqMsg
	 * @param resMsg
	 * @param e
	 */
	public void errorRes(ReqMsg reqMsg, ResMsg resMsg, Exception e){
		if(e instanceof PTException){
			resMsg.setResCode(((PTException) e).getErrCode());
			resMsg.setResMsg(((PTException) e).getErrMessage());
		}else{
			resMsg.setResCode(ConstantUtil.BSRESCODE_FAIL);
			if(e == null || "".equals(e.getMessage())){
				resMsg.setResMsg(ConstantUtil.DEFAULT_ERRMSG);
			}else{
				resMsg.setResMsg(e.getMessage());
			}
		}
		resMsg.setVersion(reqMsg.getVersion());
		resMsg.setMsgID(reqMsg.getMsgID());
	}
	/**
	 * 交易失败  自定义message 公共信息返回
	 * @param reqMsg
	 * @param resMsg
	 * @param message
	 */
	public void errorRes(ReqMsg reqMsg, ResMsg resMsg, String message){
		resMsg.setResCode(ConstantUtil.BSRESCODE_FAIL);
		if(message == null || "".equals(message)){
			resMsg.setResMsg(ConstantUtil.DEFAULT_ERRMSG);
		}else{
			resMsg.setResMsg(message);
		}
		resMsg.setVersion(reqMsg.getVersion());
		resMsg.setMsgID(reqMsg.getMsgID());
	}
}
