package com.pinting.gateway.in.facade;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.dto.OrderResultInfo;
import com.pinting.business.service.common.OrderResultDispatchService;
import com.pinting.gateway.hessian.message.baofoo.G2BReqMsg_BaoFooPay_NewCounterResultNotice;
import com.pinting.gateway.hessian.message.baofoo.G2BResMsg_BaoFooPay_NewCounterResultNotice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by 剑钊 on 2016/8/3.
 */
@Component("BaoFooPay")
public class BaoFooPayFacade {

	@Autowired
	private OrderResultDispatchService orderResultDispatchService;
	
	private Logger log = LoggerFactory.getLogger(BaoFooPayFacade.class);

    public void newCounterResultNotice(G2BReqMsg_BaoFooPay_NewCounterResultNotice req,
                                       G2BResMsg_BaoFooPay_NewCounterResultNotice res){
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");  
		try {
			log.info("====================网银通知处理：succTime="+req.getSuccTime());
			Date date = sdf.parse(req.getSuccTime());
			log.info("====================网银通知处理：finishTime="+date);
	        if ("1".equals(req.getResult())) {
	        	OrderResultInfo orderResultInfo = new OrderResultInfo();
	        	orderResultInfo.setSuccess(true);
	        	orderResultInfo.setOrderNo(req.getTransId());//订单号
	        	orderResultInfo.setPayOrderNo(null);//支付订单号
	        	orderResultInfo.setResCode(req.getResult());//返回码
	        	orderResultInfo.setReturnMsg(req.getResultDesc());//返回信息
	        	orderResultInfo.setFinishTime(date);//订单完成时间
	        	orderResultDispatchService.dispatch(orderResultInfo);
			}else {
	        	OrderResultInfo orderResultInfo = new OrderResultInfo();
	        	orderResultInfo.setSuccess(false);//状态
	        	orderResultInfo.setOrderNo(req.getTransId());//订单号
	        	orderResultInfo.setPayOrderNo(null);//支付订单号
	        	orderResultInfo.setResCode(req.getResult());//返回码
	        	orderResultInfo.setReturnMsg(req.getResultDesc());//返回信息
	        	orderResultInfo.setFinishTime(date);//订单完成时间
	        	orderResultDispatchService.dispatch(orderResultInfo);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			log.info("====================网银通知处理异常："+e);
			throw new PTMessageException(PTMessageEnum.BAOFOO_BUY_EBANK_ERROR);
		}  
    	
    }
    
}
