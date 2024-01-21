package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.facade.manage.enums.RealNameRespCode;
import com.pinting.business.hessian.manage.message.ReqMsg_BsPayOrders_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsPayOrders_RealNameAuth;
import com.pinting.business.hessian.manage.message.ReqMsg_BsPayOrders_UserOrdersList;
import com.pinting.business.hessian.manage.message.ResMsg_BsPayOrders_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsPayOrders_RealNameAuth;
import com.pinting.business.hessian.manage.message.ResMsg_BsPayOrders_UserOrdersList;
import com.pinting.business.model.vo.BsPayOrdersVO;
import com.pinting.business.service.manage.BsPayOdersService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Util;
import com.pinting.core.exception.PTException;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_RealName_RealNameAuth;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_RealName_RealNameAuth;
import com.pinting.gateway.hessian.message.pay19.enums.AcctAttr;
import com.pinting.gateway.hessian.message.pay19.enums.CardType;
import com.pinting.gateway.hessian.message.pay19.enums.IdType;
import com.pinting.gateway.hessian.message.pay19.enums.RealNameTradeStatus;
import com.pinting.gateway.out.service.Pay19TransportService;

@Component("BsPayOrders")
public class BsPayOrdersFacade {
	private Logger log = LoggerFactory.getLogger(BsPayOrdersFacade.class);
	@Autowired
	private BsPayOdersService bsPayOdersService;
	@Autowired
	private Pay19TransportService pay19TransportService;
	
	public void listQuery(ReqMsg_BsPayOrders_ListQuery req,ResMsg_BsPayOrders_ListQuery res){
		BsPayOrdersVO bsPayOrdersVO = new BsPayOrdersVO();
		bsPayOrdersVO.setMobile(req.getMobile());
		bsPayOrdersVO.setIdCard(req.getIdCard());
		bsPayOrdersVO.setUserMobile(req.getUserMobile());
		bsPayOrdersVO.setPageNum(Integer.parseInt(req.getPageNum()));
		bsPayOrdersVO.setNumPerPage(Integer.parseInt(req.getNumPerPage()));
		int totalRows = bsPayOdersService.countPayOrders(bsPayOrdersVO);
		if (totalRows > 0) {
			List<BsPayOrdersVO> list = bsPayOdersService.payOrdersPage(bsPayOrdersVO);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setList(mapList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(String.valueOf(totalRows));
	}
	
	/**
	 * 19付实名认证
	 * @param req
	 * @param res
	 */
	public void realNameAuth(ReqMsg_BsPayOrders_RealNameAuth req,ResMsg_BsPayOrders_RealNameAuth res){
		B2GReqMsg_RealName_RealNameAuth rnReq = new B2GReqMsg_RealName_RealNameAuth();
        rnReq.setMxUserId("ceshi");
        Date reqDate = new Date();
        String orderNo =  "RN"+DateUtil.formatDateTime(reqDate, "yyyyMMddHHmmss")
        		+ Util.generateAssignLengthNo(8);
        rnReq.setMxReqSno(orderNo);
        rnReq.setMxReqDate(reqDate);
        rnReq.setCardHolder(req.getUserName());
        rnReq.setIdType(IdType.IDENTITY_CARD);
        rnReq.setIdNo(req.getIdCard());
        rnReq.setMobile(req.getMobile());
        rnReq.setPcId(req.getBankCode());
        rnReq.setBankCardNo(req.getCardNo());
        rnReq.setCardType(CardType.DEBITCARD);
        rnReq.setCardAttr(AcctAttr.PRIVATE);

        B2GResMsg_RealName_RealNameAuth rnResult = null;
        try {
            //发起实名认证接口
            rnResult = pay19TransportService.realNameAuth(rnReq);
            log.info("实名认证结果："+rnResult);
        } catch (Exception e) {//请求异常情况 也需 记录情况
        	String errorMsg = StringUtil.left(e.getMessage(), 20);
        	String errorCode = ConstantUtil.BSRESCODE_FAIL;
        	if(e instanceof PTException){
        		 errorMsg = ((PTException) e).getErrMessage();
                 errorCode = ((PTException) e).getErrCode();
        	}
        	throw new PTMessageException(PTMessageEnum.REAL_NAME_AUTH_ERROR, errorCode+"|"+errorMsg);
        }

        //认证成功
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(rnResult.getResCode()) && 
        		RealNameTradeStatus.MATCH.getCode().equals(rnResult.getTradeStatus().getCode())) {
        	res.setResMsg("实名认证成功，结果:匹配");
        } else if (rnResult.getTradeStatus() != null && (RealNameTradeStatus.UNMATCH.getCode().equals(
            rnResult.getTradeStatus().getCode())
                   || RealNameTradeStatus.UNVERIFY.getCode().equals(
                       rnResult.getTradeStatus().getCode()))) {
        	if(rnResult.getRetCode() != null){
        		RealNameRespCode respCode = RealNameRespCode.find(rnResult.getRetCode());
        		 throw new PTMessageException(PTMessageEnum.REAL_NAME_AUTH_ERROR,  
        				 rnResult.getRetCode()+"|"+(respCode != null ? respCode.getDescription() : "无对应错误翻译"));
        	}else{
        		throw new PTMessageException(PTMessageEnum.REAL_NAME_AUTH_ERROR,  rnResult.getResMsg());
        	}
        	
        } else {
        	String resMsg = rnResult.getResMsg();
        	throw new PTMessageException(PTMessageEnum.REAL_NAME_AUTH_ERROR, rnResult.getRetCode() +"|"+resMsg!=null?resMsg:"无认证结果，请联系三方");
        }
	}
	
	/**
	 * 财务确认处理查看详情中查询用户订单信息
	 * Created by shh on 2016/11/3 21:57.
	 */
	public void userOrdersList(ReqMsg_BsPayOrders_UserOrdersList req, ResMsg_BsPayOrders_UserOrdersList res) {
		List<BsPayOrdersVO> list = bsPayOdersService.queryPayOrdersByUserId(req.getUserId());
		if(list != null && list.size() > 0) {
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setValueList(mapList);
		}
	}
	
}
