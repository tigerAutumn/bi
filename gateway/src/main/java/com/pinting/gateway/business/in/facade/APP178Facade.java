package com.pinting.gateway.business.in.facade;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_OrderNotice;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateProductInfo;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateUserInfo;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_OrderNotice;
import com.pinting.gateway.hessian.message.qb178.B2GReqMsg_APP178_UpdateRepayPlan;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateProductInfo;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateRepayPlan;
import com.pinting.gateway.hessian.message.qb178.B2GResMsg_APP178_UpdateUserInfo;
import com.pinting.gateway.qb178.in.util.Qb178InMsgUtil;
import com.pinting.gateway.qb178.out.model.BaseReqModel;
import com.pinting.gateway.qb178.out.model.OrderNoticeReqModel;
import com.pinting.gateway.qb178.out.model.OrderNoticeResModel;
import com.pinting.gateway.qb178.out.model.UpdateProductInfoReqModel;
import com.pinting.gateway.qb178.out.model.UpdateProductInfoResModel;
import com.pinting.gateway.qb178.out.model.UpdateRepayPlanReqModel;
import com.pinting.gateway.qb178.out.model.UpdateRepayPlanResModel;
import com.pinting.gateway.qb178.out.model.UpdateUserInfoReqModel;
import com.pinting.gateway.qb178.out.model.UpdateUserInfoResModel;
import com.pinting.gateway.qb178.out.service.SendQb178Service;
import com.pinting.gateway.qb178.out.util.Qb178OutMsgUtil;
import com.pinting.gateway.util.Constants;

/**
 * 钱报app接口
 * 
 * @author bianyatian
 * @2017-7-29 上午11:02:11
 */
@Component("APP178")
public class APP178Facade {
	@Autowired
	private SendQb178Service sendQb178Service;
	
	/**
	 * 更新还款计划状态
	 * @param req
	 * @param res
	 */
	public void updateRepayPlan(B2GReqMsg_APP178_UpdateRepayPlan req, B2GResMsg_APP178_UpdateRepayPlan res){
		UpdateRepayPlanReqModel reqModel = new UpdateRepayPlanReqModel();
		reqModel.setUser_account(req.getUserAccount());
		reqModel.setProduct_code(req.getProductCode().toString());
		reqModel.setPeriod(req.getPeriod());
		reqModel.setJet_plan_status(req.getJetPlanStatus());
		reqModel.setReal_date(DateUtil.formatDateTime(req.getRealDate(), "yyyyMMdd"));
		reqModel.setPlan_date(DateUtil.formatDateTime(req.getPlanDate(), "yyyyMMdd"));
		reqModel = (UpdateRepayPlanReqModel)setCommReq(reqModel);
		reqModel.setCert_sign(Qb178OutMsgUtil.getSignData(reqModel));
		UpdateRepayPlanResModel resModel = sendQb178Service.updateRepayPlan(reqModel);
        if (resModel != null && Constants.Qianbao178_SUCCESS_CODE.equals(resModel.getError_no())){
	        res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        } else {
            throw  new PTMessageException(PTMessageEnum.QB178_UPDATE_REPAY_PLAN_NOTICE_ERROR,  (resModel == null ?"":resModel.getError_info()));
        }
	}
	private BaseReqModel setCommReq(BaseReqModel reqModel) {
		reqModel.setDeal_date(DateUtil.formatDateTime(new Date(), "yyyyMMdd"));
		reqModel.setDeal_time(DateUtil.formatDateTime(new Date(), "HHmmss"));
		reqModel.setExchange_code(Integer.valueOf(Qb178InMsgUtil.qb178AppInExchangeCode));
		reqModel.setChannel_code(Qb178InMsgUtil.qb178AppInChannelCode);
		reqModel.setSerial_no(String.valueOf(System.currentTimeMillis()) );
		return reqModel;
	}
	/**
	 * 订单通知
	 * @param req
	 * @param res
	 */
	public void orderNotice(B2GReqMsg_APP178_OrderNotice req, B2GResMsg_APP178_OrderNotice res){
		
		OrderNoticeReqModel reqModel = new OrderNoticeReqModel();
		reqModel.setDelegate_code(req.getDelegate_code());
		reqModel.setProduct_code(req.getProduct_code());
		reqModel.setProduct_name(req.getProduct_name());
		reqModel.setUser_account(req.getUser_account());
		reqModel.setOrder_balance(req.getOrder_balance());
		reqModel.setDelegate_type(req.getDelegate_type());
		reqModel.setDelegate_status(req.getDelegate_status());
		reqModel.setOrder_time(DateUtil.formatDateTime(req.getOrder_time(), "yyyyMMddHHmmss"));
		reqModel.setChannel(req.getSubChannel());
		reqModel = (OrderNoticeReqModel)setCommReq(reqModel);
		reqModel.setCert_sign(Qb178OutMsgUtil.getSignData(reqModel));
		OrderNoticeResModel resModel = sendQb178Service.orderNotice(reqModel);
		

        if (resModel != null && Constants.Qianbao178_SUCCESS_CODE.equals(resModel.getError_no())){
	        res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        } else {
            throw  new PTMessageException(PTMessageEnum.QB178_ORDER_NOTICE_ERROR,  (resModel == null ?"":resModel.getError_info()));
        }
	}
	
	
	/**
	 * 更新产品状态
	 * @param req
	 * @param res
	 */
	public void updateProductInfo(B2GReqMsg_APP178_UpdateProductInfo req, B2GResMsg_APP178_UpdateProductInfo res){
		
		UpdateProductInfoReqModel reqModel = new UpdateProductInfoReqModel();
		reqModel.setProduct_code(req.getProduct_code());
		reqModel.setProduct_status(req.getProduct_status());
		reqModel = (UpdateProductInfoReqModel)setCommReq(reqModel);
		reqModel.setCert_sign(Qb178OutMsgUtil.getSignData(reqModel));
		UpdateProductInfoResModel resModel = sendQb178Service.updateProductInfo(reqModel);
        if (resModel != null && Constants.Qianbao178_SUCCESS_CODE.equals(resModel.getError_no())){
	        res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        } else {
        	
            throw  new PTMessageException(PTMessageEnum.QB178_UPDATE_PRODUCT_INFO_ERROR,  (resModel == null ?"":resModel.getError_info()));
        }
	}
	
	
	
	/**
	 * 更新会员信息
	 * @param req
	 * @param res
	 */
	public void updateUserInfo(B2GReqMsg_APP178_UpdateUserInfo req, B2GResMsg_APP178_UpdateUserInfo res){
		
		UpdateUserInfoReqModel reqModel = new UpdateUserInfoReqModel();
		reqModel.setUser_account(req.getUser_account());
		reqModel.setId_kind(req.getId_kind());
		reqModel.setId_no(req.getId_no());
		reqModel.setBank_account(req.getBank_account());
		reqModel.setBank_pro_code(req.getBank_pro_code());
		
		reqModel = (UpdateUserInfoReqModel)setCommReq(reqModel);
		reqModel.setCert_sign(Qb178OutMsgUtil.getSignData(reqModel));
		UpdateUserInfoResModel resModel = sendQb178Service.updateUserInfo(reqModel);
        if ( resModel != null && Constants.Qianbao178_SUCCESS_CODE.equals(resModel.getError_no())){
	        res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        } else {
            throw  new PTMessageException(PTMessageEnum.QB178_UPDATE_USER_INFO_ERROR,  (resModel == null ?"":resModel.getError_info()));
        }
	}

}
