package com.pinting.gateway.bird.in.interceptor;

import com.alibaba.fastjson.JSON;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.bird.in.controller.BirdController;
import com.pinting.gateway.bird.in.model.BaseResModel;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.util.BeanUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 异常处理
 * @author liujz
 *
 */
public class ExceptionHandler implements HandlerExceptionResolver {

	private Logger log = LoggerFactory.getLogger(ExceptionHandler.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		BaseResModel resModel=new BaseResModel();

		if(ex instanceof PTMessageException){
			resModel.setErrorCode(((PTMessageException) ex).getErrCode());
			resModel.setErrorMsg(((PTMessageException) ex).getErrMessage());
			//日志
			log.info(JSON.toJSONString(resModel));
		}else {
			ex.printStackTrace();
			resModel.setErrorCode(PTMessageEnum.SYSTEM_ERROR.getCode());
			resModel.setErrorMsg(PTMessageEnum.SYSTEM_ERROR.getMessage());
		}
		resModel.setResponseTime(DateUtil.formatDateTime(new Date(),"yyyy-MM-dd HH:mm:ss"));
		
		return new ModelAndView(new MappingJackson2JsonView(), BeanUtils.transBeanMap(resModel));
	}

}
