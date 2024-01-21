package com.pinting.gateway.business.in.hessian;


import com.pinting.core.exception.PTException;
import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.hessian.service.HessianBaseService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * 统一  网关接入  接口实现
 * @Project: business
 * @Title: GatewayDafyHessianServiceImpl.java
 * @author liujz
 * @date 2015-2-10 下午2:44:28
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ZsdNoticeGatewayHessianServiceImpl extends HessianBaseService implements HessianService , ApplicationContextAware{
	
	protected static ApplicationContext applicationContext;
	private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory(); 
	
	private Logger log = LoggerFactory.getLogger(ZsdNoticeGatewayHessianServiceImpl.class);

	/**
	 * 负责 消息请求接收及响应
	 * 负责 实现业务服务的分派
	 * @param reqMsg
	 * @return ResMsg
	 * @ 
	 */
	public ResMsg handleMsg(ReqMsg reqMsg) {
		ResMsg resMsg = null;
		try {
			String[] transInfo = reqMsg.getClass().getSimpleName().split("_");
			if(transInfo.length < 3){
				throw new PTException(PTMessageEnum.TRANSCODE_NOT_FOUND.getCode(), PTMessageEnum.TRANSCODE_NOT_FOUND.getMessage());
			}
			String facadeName = transInfo[1];
			String actionName = transInfo[2];
			//方法名,首字母小写
			String headStr = actionName.substring(0, 1);
			String methodName = headStr.toLowerCase().concat(actionName.substring(1));
	
			String transCode = reqMsg.getTransCode();
			if(StringUtil.isEmpty(transCode)){
				transCode = facadeName + "-" + actionName;
				reqMsg.setTransCode(transCode);
			}
			
			log.info("======交易["  + transCode + "]：开始执行======");
			long begin = System.currentTimeMillis();

			Object bsFacade = applicationContext.getBean(facadeName);
			if (bsFacade == null) {
				throw new Exception("can't found business service, please check your transcode");
			}
			
			String resClassName = Constants.BUSINESS_ZSD_NOITCE_MESSAGE_RESMSG_PRE + facadeName + "_" + actionName;
			resMsg = (ResMsg)Class.forName(resClassName).newInstance();//创建返回消息实例
			
			//请求报文数据验证
			reqMsgValidate(reqMsg);
			
			//查找对应方法
			for (Method method : bsFacade.getClass().getDeclaredMethods()) {
				if(method.getName().equals(methodName)
						&& method.getParameterTypes().length == 2
						&& "ReqMsg".equals(method.getParameterTypes()[0].getSuperclass().getSimpleName())
						&& "ResMsg".equals(method.getParameterTypes()[1].getSuperclass().getSimpleName())){
					try {
						method.invoke(bsFacade, reqMsg, resMsg);
					} catch (InvocationTargetException e) {
						Throwable cause = e.getTargetException();
						//若cause为自定义异常，则需返回自定义错误码和错误信息
						if(cause instanceof PTMessageException){
							throw new PTException(((PTMessageException)cause).getErrCode(), ((PTMessageException)cause).getErrMessage(), cause);
						}else{
							cause.printStackTrace();
							throw new PTException(PTMessageEnum.TRANS_ERROR.getCode(), PTMessageEnum.TRANS_ERROR.getMessage(), cause);
						}
					} catch (Exception e) {
						throw new PTException(PTMessageEnum.TRANS_ERROR.getCode(), PTMessageEnum.TRANS_ERROR.getMessage(), e);
					} 
					
					break;
				}
			}
			
			successRes(reqMsg, resMsg);
			long end = System.currentTimeMillis();
			log.info("======交易[" + transCode + "]：执行结束======");
			log.info("======响应报文[耗时："+(end-begin)+"毫秒]======");
		} catch (Exception e) {
			errorRes(reqMsg, resMsg, e);
			//e.printStackTrace();
			log.error("======交易[" + reqMsg.getTransCode() + "]发生错误： " + e +"======");  
		} 
		return resMsg;
	}
	
	private void reqMsgValidate(ReqMsg reqMsg) throws Exception{
		Validator validator = validatorFactory.getValidator(); 
		Set<ConstraintViolation<ReqMsg>> violations = validator.validate(reqMsg);
		if(violations != null && violations.size() > 0){
			String errMessage = "|";
			for (ConstraintViolation<ReqMsg> constraintViolation : violations) {  
	            errMessage += constraintViolation.getMessage() + "|";
			}
			log.error("交易【" + reqMsg.getTransCode() +"】："+ errMessage);  
			throw new PTException(PTMessageEnum.TRANSCODE_NOT_FOUND.getCode(), PTMessageEnum.TRANSCODE_NOT_FOUND.getMessage() + errMessage);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		ZsdNoticeGatewayHessianServiceImpl.applicationContext = applicationContext;
		
	}


}