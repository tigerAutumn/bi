package com.pinting.gateway.in.facade;

import com.alibaba.fastjson.JSON;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.gateway.hessian.message.qb178.*;
import com.pinting.gateway.hessian.message.qb178.G2BReqMsg_Qb178_QueryProductList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.gateway.in.service.Qb178Service;

@Component("Qb178")
public class Qb178Facade {
	private Logger logger = LoggerFactory.getLogger(Qb178Facade.class);
	
	@Autowired
	private Qb178Service qb178Service;

    /**
     * 分页查询产品列表
     * @param req
     * @param res
     */
    public void queryProductList(G2BReqMsg_Qb178_QueryProductList req,G2BResMsg_Qb178_QueryProductList res) {
    	logger.info("钱报178产品列表查询入参：" + JSON.toJSONString(req));
        if(req.getPage_size() == null || req.getPage_size() <= 0 || req.getPage_size()> 200 || req.getPage_num() == null || req.getPage_num() <= 0){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "分页参数错误");
        }
        G2BResMsg_Qb178_QueryProductList resTemp = qb178Service.queryProductDetails(req);
        if(resTemp != null){
            res.setCurrent_page(resTemp.getCurrent_page());
            res.setTotal_num(resTemp.getTotal_num());
            res.setData(resTemp.getData());
        }
    }
    
    public void queryOrderList(G2BReqMsg_Qb178_QueryOrderList req,G2BResMsg_Qb178_QueryOrderList res) {
    	logger.info("Business平台已收到订单列表查询通知：" + req);
    	if(req.getPage_size() == null || req.getPage_size() <= 0 ||  req.getPage_size()> 200 || req.getPage_num() == null || req.getPage_num() <= 0){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "分页参数错误");
        }
    	G2BResMsg_Qb178_QueryOrderList resTemp = qb178Service.queryOrderDetails(req);
    	if(resTemp != null){
	    	res.setCurrent_page(resTemp.getCurrent_page());
	    	res.setTotal_num(resTemp.getTotal_num());
	    	res.setData(resTemp.getData());
    	}
    }

    public void queryUserDetails(G2BReqMsg_Qb178_QueryUserDetails req, G2BResMsg_Qb178_QueryUserDetails res) {
        logger.info("钱报178会员详情查询入参：" + JSON.toJSONString(req));
        if(req.getPage_size() == null || req.getPage_size() <= 0 || req.getPage_size()> 200 || req.getPage_num() == null || req.getPage_num() <= 0){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "分页参数错误");
        }
        G2BResMsg_Qb178_QueryUserDetails resTemp = qb178Service.queryUserDetails(req);
        if(resTemp != null) {
            res.setCurrent_page(resTemp.getCurrent_page());
            res.setTotal_num(resTemp.getTotal_num());
            res.setData(resTemp.getData());
        }
    }
    
    public void queryPositionBalance(G2BReqMsg_Qb178_QueryPositionBalance req,
			G2BResMsg_Qb178_QueryPositionBalance res) throws Exception {
    	logger.info("钱报178查询会员持仓余额入参：" + JSON.toJSONString(req));
        if(req.getPage_size() == null || req.getPage_size() <= 0 || req.getPage_size()> 200 || req.getPage_num() == null || req.getPage_num() <= 0){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "分页参数错误");
        }
        qb178Service.queryPositionBalance(req, res);
    }
    public void queryBalance(G2BReqMsg_Qb178_QueryBalance req, G2BResMsg_Qb178_QueryBalance res) throws Exception {
        logger.info("钱报178会员查询会员资金余额入参：" + JSON.toJSONString(req));
        if(req.getPage_size() == null || req.getPage_size() <= 0 || req.getPage_size()> 200 || req.getPage_num() == null || req.getPage_num() <= 0){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "分页参数错误");
        }
        G2BResMsg_Qb178_QueryBalance resTemp = qb178Service.queryBalance(req);
        if(resTemp != null) {
            res.setCurrent_page(resTemp.getCurrent_page());
            res.setTotal_num(resTemp.getTotal_num());
            res.setData(resTemp.getData());
        }
    }

	public void queryRepayPlan(G2BReqMsg_Qb178_QueryRepayPlan req,
                               G2BResMsg_Qb178_QueryRepayPlan res)throws Exception {
        logger.info("钱报178查询还款计划入参：" + JSON.toJSONString(req));
        if(req.getPage_size() == null || req.getPage_size() <= 0 || req.getPage_size()> 200 || req.getPage_num() == null || req.getPage_num() <= 0){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "分页参数错误");
        }
        qb178Service.queryRepayPlan(req, res);
    }

    /**
     * 分页查询会员资金流水
     * @param req
     * @param res
     */
    public void queryBalanceJnl(G2BReqMsg_Qb178_QueryBalanceJnl req,G2BResMsg_Qb178_QueryBalanceJnl res){
        logger.info("钱报178会员资金流水查询入参：" + JSON.toJSONString(req));
        if(req.getPage_size() == null || req.getPage_size() <= 0 || req.getPage_size()> 200
                || req.getPage_num() == null || req.getPage_num() <= 0){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "参数错误");
        }
        G2BResMsg_Qb178_QueryBalanceJnl resTemp = qb178Service.queryUserBalanceJnl(req);
        if(resTemp != null){
            res.setCurrent_page(resTemp.getCurrent_page());
            res.setTotal_num(resTemp.getTotal_num());
            res.setData(resTemp.getData());
        }
    }
}
