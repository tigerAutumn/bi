package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.model.BaoFooDKQueryRes;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsPaymentChannelMapper;
import com.pinting.business.dao.LnPayOrdersJnlMapper;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.OrderResultInfo;
import com.pinting.business.model.vo.LnChannelVO;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.service.common.OrderResultDispatchService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.*;
import com.pinting.gateway.hessian.message.baofoo.*;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_GetFundOrderInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_QueryOrder;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_GetFundOrderInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_QueryOrder;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 剑钊 on 2016/8/18.
 */
@Service
public class CheckBaoFooPayBirdOrderTask {

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private Logger log = LoggerFactory.getLogger(CheckBaoFooPayBirdOrderTask.class);

    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private OrderResultDispatchService orderResultDispatchService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper lnPayOrdersJnlMapper;
    @Autowired
    private BsPaymentChannelMapper paymentChannelMapper;


    public void execute() {

        log.info(">>>process_order start<<<");
        try {
            checkOrdersStatus();
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info(">>>process_order end<<<");
    }


    private void checkOrdersStatus() {
        Long len = jsClientDaoSupport.llen("process_order");
        log.info(">>>redis正在处理中订单数量:" + len + "<<<");
        if (len != null && len > 0){
        	String item = jsClientDaoSupport.lpop("process_order");
        	if(StringUtil.isEmpty(item)){
        		log.info("redis数据为空,等待下一次定时触发");
        		return;
        	}
            OrderResultInfo orderResultInfo = new OrderResultInfo();
            try {
            	JSONObject jsonObject = JSONObject.fromObject(item);
            	JSONObject orderDetail = jsonObject.getJSONObject("orderDetail");
                LoanNoticeVO loanNoticeVO = (LoanNoticeVO) JSONObject.toBean(orderDetail, LoanNoticeVO.class);
                //代付查询
                if (loanNoticeVO.getChannelTransType().equals(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode()) ||
                        loanNoticeVO.getChannelTransType().equals(Constants.CHANNEL_TRS_TRANSFER)) {
                    if(loanNoticeVO.getChannelTransType().equals(Constants.CHANNEL_TRS_TRANSFER)){
                        log.info(">>>钱包转账请求参数" + loanNoticeVO.getPayOrderNo() + "<<<");
                    }else{
                        log.info(">>>代付请求参数" + loanNoticeVO.getPayOrderNo() + "<<<");
                    }
                    if (loanNoticeVO.getStatus().toString().equals(LoanStatus.ORDERS_STATUS_PAYING.getCode())) {
                        if(loanNoticeVO.getChannel().equals(Constants.ORDER_CHANNEL_HFBANK)){
                            //恒丰代付
                            B2GReqMsg_HFBank_QueryOrder req = new B2GReqMsg_HFBank_QueryOrder();
                            String orderNo = Util.generateOrderNo4BaoFoo(8);
                            req.setOrder_no(orderNo);
                            req.setQuery_order_no(loanNoticeVO.getPayOrderNo());
                            req.setPartner_trans_time(new Date());
                            req.setPartner_trans_date(new Date());
                            B2GResMsg_HFBank_QueryOrder res;
                            try {
                                res = hfbankTransportService.queryOrder(req);
                            }catch (Exception e){
                                jsClientDaoSupport.rpush("process_order", item);
                                log.info(">>>恒丰代付结果查询通讯异常<<<", e);
                                return;
                            }
                            log.info(">>>恒丰返回结果" + JSONObject.fromObject(res).toString() + "<<<");
                            if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                                if (res.getData() != null && StringUtil.isNotEmpty(res.getData().getStatus())) {
                                    if (Constants.HF_QUERY_ORDER_STATUS_SUCCESS.equals(res.getData().getStatus())
                                            || Constants.HF_QUERY_ORDER_STATUS_SUCCESS21.equals(res.getData().getStatus())) {
                                        orderResultInfo.setSuccess(true);
                                        orderResultInfo.setAmount(Double.parseDouble(loanNoticeVO.getAmount()));
                                        orderResultInfo.setOrderNo(res.getData().getQuery_order_no());
                                        orderResultInfo.setFinishTime(DateUtil.parseDateTime(res.getData().getTrans_time()));
                                    } else if (Constants.HF_QUERY_ORDER_STATUS_PAYING.equals(res.getData().getStatus())) {
                                        jsClientDaoSupport.rpush("process_order", item);
                                        return;
                                    } else if (Constants.HF_QUERY_ORDER_STATUS_FAIL.equals(res.getData().getStatus())
                                            || Constants.HF_QUERY_ORDER_STATUS_FAIL12.equals(res.getData().getStatus())
                                            || Constants.HF_QUERY_ORDER_STATUS_FAIL22.equals(res.getData().getStatus())) {
                                        orderResultInfo.setSuccess(false);
                                        orderResultInfo.setOrderNo(loanNoticeVO.getPayOrderNo());
                                        orderResultInfo.setReturnMsg("交易失败");
                                    } else {
                                        jsClientDaoSupport.rpush("process_order", item);
                                        return;
                                    }
                                }
                            }else{
                                jsClientDaoSupport.rpush("process_order", item);
                                return;
                            }
                            orderResultInfo.setReturnCode(res.getResCode());
                            orderResultInfo.setReturnMsg(StringUtil.isBlank(orderResultInfo.getReturnMsg()) ? res.getResMsg() : orderResultInfo.getReturnMsg());
                        }else{
                            //宝付代付
                            B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery req = new B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery();
                            req.setTrans_no(loanNoticeVO.getPayOrderNo());
                            if(StringUtils.isNotBlank(loanNoticeVO.getClientKey())){
                                req.setPartner(loanNoticeVO.getClientKey());
                            }
                            B2GResMsg_BaoFooQuickPay_Pay4StatusQuery res;
                            try {
                                res = baoFooTransportService.syncPay4Status(req);
                            }catch (Exception e){
                                jsClientDaoSupport.rpush("process_order", item);
                                log.info(">>>宝付代付结果查询通讯异常<<<：{}", e.getMessage());
                                return;
                            }

                            log.info(">>>宝付返回结果" + JSONObject.fromObject(res).toString() + "<<<");
                            if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                                orderResultInfo.setSuccess(true);
                                orderResultInfo.setAmount(Double.parseDouble(res.getTrans_money()));
                                orderResultInfo.setOrderNo(res.getTrans_no());
                                orderResultInfo.setPayOrderNo(res.getTrans_orderid());
                            } else if (res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())) {
                                jsClientDaoSupport.rpush("process_order", item);
                                return;
                            } else if (res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_FAIL.getCode())) {
                                orderResultInfo.setSuccess(false);
                                orderResultInfo.setOrderNo(loanNoticeVO.getPayOrderNo());
                            } else{
                                jsClientDaoSupport.rpush("process_order", item);
                                return;
                            }
                            orderResultInfo.setReturnCode(res.getResCode());
                            orderResultInfo.setReturnMsg(res.getResMsg());
                            orderResultInfo.setFinishTime(DateUtil.parseDateTime(res.getTrans_endtime()));
                        }
                    } else {
                        if (loanNoticeVO.getStatus().toString().equals(LoanStatus.ORDERS_STATUS_FAIL.getCode())) {
                            orderResultInfo.setSuccess(false);
                        } else if (loanNoticeVO.getStatus().toString().equals(LoanStatus.ORDERS_STATUS_SUCCESS.getCode())) {
                            orderResultInfo.setSuccess(true);
                        } else {
                        	jsClientDaoSupport.rpush("process_order", item);
                        	return;
                        }
                        orderResultInfo.setAmount(Double.parseDouble(loanNoticeVO.getAmount()));
                        orderResultInfo.setOrderNo(loanNoticeVO.getPayOrderNo());
                        orderResultInfo.setReturnMsg(loanNoticeVO.getReturnMsg());
                        orderResultInfo.setFinishTime(loanNoticeVO.getFinishTime());
                    }
                    //调用边的分发服务
                    orderResultDispatchService.dispatch(orderResultInfo);

                }
                //认证支付查询
                else if (loanNoticeVO.getChannelTransType().equals(LoanStatus.CHANNEL_TRANS_TYPE_AUTH.getCode())) {
                	log.info(">>>认证支付请求参数" + loanNoticeVO.getPayOrderNo() + "<<<");
                    if (loanNoticeVO.getStatus().toString().equals(LoanStatus.ORDERS_STATUS_PAYING.getCode())) {
                        if(loanNoticeVO.getChannel().equals(Constants.ORDER_CHANNEL_HFBANK)) {
                            //恒丰认证支付
                            if(!Constants.TRANS_TOP_UP.equals(loanNoticeVO.getTransType())){
                                //调用4.6.8.订单状态查询
                                B2GReqMsg_HFBank_QueryOrder req = new B2GReqMsg_HFBank_QueryOrder();
                                String orderNo = Util.generateOrderNo4BaoFoo(8);
                                req.setOrder_no(orderNo);
                                req.setQuery_order_no(loanNoticeVO.getPayOrderNo());
                                req.setPartner_trans_time(new Date());
                                req.setPartner_trans_date(new Date());
                                B2GResMsg_HFBank_QueryOrder res;
                                try {
                                    res = hfbankTransportService.queryOrder(req);
                                } catch (Exception e) {
                                    jsClientDaoSupport.rpush("process_order", item);
                                    log.info(">>>恒丰认证支付结果查询通讯异常<<<", e);
                                    return;
                                }
                                log.info(">>>恒丰返回结果" + JSONObject.fromObject(res).toString() + "<<<");
                                if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                                    if (res.getData() != null && StringUtil.isNotEmpty(res.getData().getStatus())) {
                                        if (Constants.HF_QUERY_ORDER_STATUS_SUCCESS.equals(res.getData().getStatus())
                                                || Constants.HF_QUERY_ORDER_STATUS_SUCCESS21.equals(res.getData().getStatus())) {
                                            orderResultInfo.setSuccess(true);
                                            orderResultInfo.setAmount(Double.parseDouble(loanNoticeVO.getAmount()));
                                            orderResultInfo.setOrderNo(res.getData().getQuery_order_no());
                                            orderResultInfo.setFinishTime(DateUtil.parseDateTime(res.getData().getTrans_time()));
                                        } else if (Constants.HF_QUERY_ORDER_STATUS_PAYING.equals(res.getData().getStatus())) {
                                            jsClientDaoSupport.rpush("process_order", item);
                                            return;
                                        } else if (Constants.HF_QUERY_ORDER_STATUS_FAIL.equals(res.getData().getStatus())
                                                || Constants.HF_QUERY_ORDER_STATUS_FAIL12.equals(res.getData().getStatus())
                                                || Constants.HF_QUERY_ORDER_STATUS_FAIL22.equals(res.getData().getStatus())) {
                                            orderResultInfo.setSuccess(false);
                                            orderResultInfo.setOrderNo(loanNoticeVO.getPayOrderNo());
                                        } else {
                                            jsClientDaoSupport.rpush("process_order", item);
                                            return;
                                        }
                                    }
                                } else {
                                    jsClientDaoSupport.rpush("process_order", item);
                                    return;
                                }
                                orderResultInfo.setReturnCode(res.getResCode());
                                orderResultInfo.setReturnMsg(res.getResMsg());
                            }else{
                                //充值业务调用4.6.9充值订单状态查询，因4.6.8查询到的订单状态不准确
                                B2GReqMsg_HFBank_GetFundOrderInfo req = new B2GReqMsg_HFBank_GetFundOrderInfo();
                                req.setOriginal_serial_no(loanNoticeVO.getPayOrderNo());
                                req.setOccur_balance(Double.parseDouble(loanNoticeVO.getAmount()));
                                B2GResMsg_HFBank_GetFundOrderInfo res;
                                try {
                                    res = hfbankTransportService.getFundOrderInfo(req);
                                } catch (Exception e) {
                                    jsClientDaoSupport.rpush("process_order", item);
                                    log.info(">>>恒丰充值订单状态查询通讯异常<<<", e);
                                    return;
                                }
                                log.info(">>>恒丰返回结果" + JSONObject.fromObject(res).toString() + "<<<");
                                if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                                    if (res.getData() != null && StringUtil.isNotEmpty(res.getData().getStatus())) {
                                        if (Constants.HF_QUERY_ORDER_STATUS_SUCCESS.equals(res.getData().getStatus())
                                                || Constants.HF_QUERY_ORDER_STATUS_SUCCESS21.equals(res.getData().getStatus())) {
                                            orderResultInfo.setSuccess(true);
                                            orderResultInfo.setAmount(Double.parseDouble(loanNoticeVO.getAmount()));
                                            orderResultInfo.setOrderNo(res.getData().getQuery_order_no());
                                            orderResultInfo.setFinishTime(DateUtil.parseDateTime(res.getData().getTrans_time()));
                                        } else if (Constants.HF_QUERY_ORDER_STATUS_PAYING.equals(res.getData().getStatus())) {
                                            jsClientDaoSupport.rpush("process_order", item);
                                            return;
                                        } else if (Constants.HF_QUERY_ORDER_STATUS_FAIL.equals(res.getData().getStatus())
                                                || Constants.HF_QUERY_ORDER_STATUS_FAIL12.equals(res.getData().getStatus())
                                                || Constants.HF_QUERY_ORDER_STATUS_FAIL22.equals(res.getData().getStatus())) {
                                            orderResultInfo.setSuccess(false);
                                            orderResultInfo.setOrderNo(loanNoticeVO.getPayOrderNo());
                                        } else {
                                            jsClientDaoSupport.rpush("process_order", item);
                                            return;
                                        }
                                    }
                                } else {
                                    jsClientDaoSupport.rpush("process_order", item);
                                    return;
                                }
                                orderResultInfo.setReturnCode(res.getResCode());
                                orderResultInfo.setReturnMsg(res.getResMsg());
                            }
                        }else {
                            //宝付认证支付
                            B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery req = new B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery();
                            req.setOrig_trans_id(loanNoticeVO.getPayOrderNo());
                            req.setTrans_serial_no(Util.generateUserTransNo4BaoFoo());
                            B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery res;
                            try {
                                res = baoFooTransportService.syncQuickPayStauts(req);
                            }catch (Exception e){
                                jsClientDaoSupport.rpush("process_order", item);
                                log.info(">>>宝付认证支付查询通讯异常<<<", e);
                                return;
                            }

                            log.info(">>>宝付认证支付返回结果" + JSONObject.fromObject(res).toString() + "<<<");
                            if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                                orderResultInfo.setSuccess(true);
                                orderResultInfo.setAmount(MoneyUtil.divide(res.getSucc_amt(),"100").doubleValue());
                                orderResultInfo.setOrderNo(res.getTrans_id());
                                orderResultInfo.setPayOrderNo(res.getTrans_no());
                            }
                            else if(res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())){
                                log.info(">>>宝付认证支付查询处理中，入redis：process_order<<<:{}", item);
                                jsClientDaoSupport.rpush("process_order", item);
                                return;
                            }
                            else if(res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_FAIL.getCode())){
                                log.info(">>>宝付认证支付查询失败：{}", res.getResCode());
                                orderResultInfo.setSuccess(false);
                                orderResultInfo.setOrderNo(loanNoticeVO.getPayOrderNo());
                            } else {
                                log.info(">>>宝付认证支付查询状态未知：入redis：{}", item);
                                jsClientDaoSupport.rpush("process_order", item);
                                return;
                            }

                            orderResultInfo.setReturnCode(res.getResCode());
                            orderResultInfo.setReturnMsg(res.getResMsg());
                            orderResultInfo.setFinishTime(new Date());
                        }

                    } else {

                        if (loanNoticeVO.getStatus().toString().equals(LoanStatus.ORDERS_STATUS_FAIL.getCode())) {
                            orderResultInfo.setSuccess(false);
                        } else if (loanNoticeVO.getStatus().toString().equals(LoanStatus.ORDERS_STATUS_SUCCESS.getCode())) {
                            orderResultInfo.setSuccess(true);
                        } else {
                        	jsClientDaoSupport.rpush("process_order", item);
                        	return;
                        }
                        orderResultInfo.setAmount(Double.parseDouble(loanNoticeVO.getAmount()));
                        orderResultInfo.setOrderNo(loanNoticeVO.getPayOrderNo());
                        orderResultInfo.setReturnMsg(loanNoticeVO.getReturnMsg());
                        orderResultInfo.setFinishTime(loanNoticeVO.getFinishTime());
                    }

                    //调用边的分发服务
                    orderResultDispatchService.dispatch(orderResultInfo);
                }
                //代扣支付查询
                else if(loanNoticeVO.getChannelTransType().equals(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode())){
                	log.info(">>>代扣支付请求参数" + loanNoticeVO.getPayOrderNo() + "<<<");

                    if (loanNoticeVO.getStatus().toString().equals(LoanStatus.ORDERS_STATUS_PAYING.getCode())) {
                        if(loanNoticeVO.getChannel().equals(Constants.ORDER_CHANNEL_HFBANK)) {
                            //恒丰代扣支付
                            B2GReqMsg_HFBank_QueryOrder req = new B2GReqMsg_HFBank_QueryOrder();
                            String orderNo = Util.generateOrderNo4BaoFoo(8);
                            req.setOrder_no(orderNo);
                            req.setQuery_order_no(loanNoticeVO.getPayOrderNo());
                            req.setPartner_trans_time(new Date());
                            req.setPartner_trans_date(new Date());
                            B2GResMsg_HFBank_QueryOrder res;
                            try {
                                res = hfbankTransportService.queryOrder(req);
                            } catch (Exception e) {
                                jsClientDaoSupport.rpush("process_order", item);
                                log.info(">>>恒丰代扣支付结果查询通讯异常<<<", e);
                                return;
                            }
                            log.info(">>>恒丰返回结果" + JSONObject.fromObject(res).toString() + "<<<");
                            if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                                if (res.getData() != null && StringUtil.isNotEmpty(res.getData().getStatus())) {
                                    if (Constants.HF_QUERY_ORDER_STATUS_SUCCESS.equals(res.getData().getStatus())
                                            || Constants.HF_QUERY_ORDER_STATUS_SUCCESS21.equals(res.getData().getStatus())) {
                                        orderResultInfo.setSuccess(true);
                                        orderResultInfo.setAmount(Double.parseDouble(loanNoticeVO.getAmount()));
                                        orderResultInfo.setOrderNo(res.getData().getQuery_order_no());
                                        orderResultInfo.setFinishTime(DateUtil.parseDateTime(res.getData().getTrans_time()));
                                    } else if (Constants.HF_QUERY_ORDER_STATUS_PAYING.equals(res.getData().getStatus())) {
                                        jsClientDaoSupport.rpush("process_order", item);
                                        return;
                                    } else if (Constants.HF_QUERY_ORDER_STATUS_FAIL.equals(res.getData().getStatus())
                                            || Constants.HF_QUERY_ORDER_STATUS_FAIL12.equals(res.getData().getStatus())
                                            || Constants.HF_QUERY_ORDER_STATUS_FAIL22.equals(res.getData().getStatus())) {
                                        orderResultInfo.setSuccess(false);
                                        orderResultInfo.setOrderNo(loanNoticeVO.getPayOrderNo());
                                    } else {
                                        jsClientDaoSupport.rpush("process_order", item);
                                        return;
                                    }
                                }
                            } else {
                                jsClientDaoSupport.rpush("process_order", item);
                                return;
                            }
                            orderResultInfo.setReturnCode(res.getResCode());
                            orderResultInfo.setReturnMsg(res.getResMsg());
                        }else {
                        	//宝付协议支付订单查询
                        	B2GReqMsg_BaoFooAgreementPay_QueryOrder  agreementPayQueryReq = new B2GReqMsg_BaoFooAgreementPay_QueryOrder();
                            //宝付代扣支付订单查询
                            B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery queryReq = new B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery();
                            
                            BaoFooDKQueryRes dkQueryRes = new BaoFooDKQueryRes(); 
                            
                            //查询订单商户号信息(两张订单表都需要查询)，判断该订单是否已经被修改为成功、失败，管理台驻留订单有可能手动添加成功、失败订单
                            LnChannelVO merchantInfo = lnPayOrdersMapper.queryLnMerchantInfoOfOrder(loanNoticeVO.getPayOrderNo());
                            if(merchantInfo == null){
                                merchantInfo = bsPayOrdersMapper.queryBsMerchantInfoOfOrder(loanNoticeVO.getPayOrderNo());
                            }
                            if(merchantInfo != null && StringUtil.isNotBlank(merchantInfo.getMerchantNo())){
                            	queryReq.setMerchantNo(merchantInfo.getMerchantNo());
                            	agreementPayQueryReq.setMember_id(merchantInfo.getMerchantNo());
                            }
                            if(merchantInfo != null && StringUtil.isNotBlank(merchantInfo.getIsProtocolPay()) 
                            		&& Constants.BAOFOO_IS_PROTOCOLPAY_YES.equals(merchantInfo.getIsProtocolPay())){
                            	//商户信息不为空，且为协议支付
                            	B2GResMsg_BaoFooAgreementPay_QueryOrder agreementPayQueryRes = new B2GResMsg_BaoFooAgreementPay_QueryOrder();
                            	try {
                            		agreementPayQueryReq.setOrig_trans_id(loanNoticeVO.getPayOrderNo());
                            		agreementPayQueryReq.setOrig_trade_date(merchantInfo.getTransTime());
                            		log.info("宝付协议支付结果查询入参："+agreementPayQueryReq.toString());
                            		agreementPayQueryRes = baoFooTransportService.queryOrder(agreementPayQueryReq);
                            		dkQueryRes.setResCode(agreementPayQueryRes.getResCode());
                                    dkQueryRes.setResMsg(agreementPayQueryRes.getResMsg());
                                    dkQueryRes.setSucc_amt(agreementPayQueryRes.getSucc_amt() != null ? MoneyUtil.divide(agreementPayQueryRes.getSucc_amt().doubleValue(), 100, 2).doubleValue() : 0d);
                                    dkQueryRes.setTrans_id(loanNoticeVO.getPayOrderNo());
                                    dkQueryRes.setTrans_no(agreementPayQueryRes.getOrder_id());
								} catch (Exception e) {
									jsClientDaoSupport.rpush("process_order", item);
                                    log.info(">>>宝付协议支付查询通讯异常<<<", e);
                                    return;
								}
                            	log.info(">>>宝付协议支付查询返回结果" + JSONObject.fromObject(agreementPayQueryRes).toString() + "<<<");
                            }else{
                            	B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery res;
                                try {
                                	queryReq.setOrig_trans_id(loanNoticeVO.getPayOrderNo());
                                	queryReq.setTrans_serial_no(Util.generateUserTransNo4BaoFoo());
                                    res = baoFooTransportService.queryWithholdingStatus(queryReq);
                                    dkQueryRes.setResCode(res.getResCode());
                                    dkQueryRes.setResMsg(res.getResMsg());
                                    dkQueryRes.setSucc_amt(StringUtil.isNotBlank(res.getSucc_amt()) ? Double.parseDouble(res.getSucc_amt()) : 0d);
                                    dkQueryRes.setTrans_id(res.getTrans_id());
                                    dkQueryRes.setTrans_no(res.getTrans_no());
                                }catch (Exception e){
                                    jsClientDaoSupport.rpush("process_order", item);
                                    log.info(">>>宝付代扣支付查询通讯异常<<<", e);
                                    return;
                                }

                                log.info(">>>宝付代扣查询返回结果" + JSONObject.fromObject(res).toString() + "<<<");
                            }
                            

                            if (dkQueryRes.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                                if(merchantInfo != null && merchantInfo.getIsMain() != null && merchantInfo.getIsMain() == 2){
                                	List<Integer> statusList = new ArrayList<>();
                                	statusList.add(Constants.ORDER_STATUS_PAYING);
                                	statusList.add(Constants.ORDER_STATUS_SUCCESS);
                                    //代扣成功且非主通道，钱包转账到主商户号
                                    LnPayOrdersExample exampleOrder = new LnPayOrdersExample();
                                    exampleOrder.createCriteria().andOrderNoEqualTo("DKTS" + loanNoticeVO.getPayOrderNo()).andStatusIn(statusList);
                                    List<LnPayOrders> list = lnPayOrdersMapper.selectByExample(exampleOrder);
                                    if(CollectionUtils.isEmpty(list)){
                                        LnPayOrders transOrder = new LnPayOrders();
                                        transOrder.setPaymentChannelId(merchantInfo.getId());
                                        transOrder.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
                                        transOrder.setAmount(StringUtil.isNotEmpty(loanNoticeVO.getAmount()) ? Double.parseDouble(loanNoticeVO.getAmount()): 0d);
                                        transOrder.setChannel(Constants.CHANNEL_BAOFOO);
                                        transOrder.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
                                        transOrder.setCreateTime(new Date());
                                        transOrder.setMoneyType(Constants.ORDER_CURRENCY_CNY);
                                        transOrder.setOrderNo("DKTS" + loanNoticeVO.getPayOrderNo());
                                        transOrder.setStatus(Constants.ORDER_STATUS_CREATE);
                                        transOrder.setTransType(loanNoticeVO.getTransType());
                                        transOrder.setUpdateTime(new Date());
                                        transOrder.setNote("钱包转账到主商户号");
                                        if(StringUtil.isNotBlank(merchantInfo.getPartnerCode())){
                                            transOrder.setPartnerCode(merchantInfo.getPartnerCode());
                                        }
                                        //转账到主商户号
                                        tranToMainMerchant(transOrder,merchantInfo.getMerchantNo());
                                    }
                                }


                                orderResultInfo.setSuccess(true);
                                orderResultInfo.setAmount(dkQueryRes.getSucc_amt());
                                orderResultInfo.setOrderNo(dkQueryRes.getTrans_id());
                                orderResultInfo.setPayOrderNo(dkQueryRes.getTrans_no());
                            }
                            else if(dkQueryRes.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())){
                                jsClientDaoSupport.rpush("process_order", item);
                                return;
                            }
                            else if(dkQueryRes.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_FAIL.getCode())){
                                orderResultInfo.setSuccess(false);
                                orderResultInfo.setOrderNo(loanNoticeVO.getPayOrderNo());
                            } else {
                                jsClientDaoSupport.rpush("process_order", item);
                                return;
                            }

                            orderResultInfo.setReturnCode(dkQueryRes.getResCode());
                            orderResultInfo.setReturnMsg(dkQueryRes.getResMsg());
                            orderResultInfo.setFinishTime(new Date());
                        }

                    } else {

                        if (loanNoticeVO.getStatus().toString().equals(LoanStatus.ORDERS_STATUS_FAIL.getCode())) {
                            orderResultInfo.setSuccess(false);
                        } else if (loanNoticeVO.getStatus().toString().equals(LoanStatus.ORDERS_STATUS_SUCCESS.getCode())) {
                            orderResultInfo.setSuccess(true);
                        } else {
                        	jsClientDaoSupport.rpush("process_order", item);
                        	return;
                        }
                        orderResultInfo.setAmount(Double.parseDouble(loanNoticeVO.getAmount()));
                        orderResultInfo.setOrderNo(loanNoticeVO.getPayOrderNo());
                        orderResultInfo.setReturnMsg(loanNoticeVO.getReturnMsg());
                        orderResultInfo.setFinishTime(loanNoticeVO.getFinishTime());
                    }

                    //调用边的分发服务
                    orderResultDispatchService.dispatch(orderResultInfo);

                }
            } catch (Exception e) {
                log.info(">>>宝付订单[" + item + "]支付结果轮询异常<<<");
                specialJnlService.warn4FailNoSMS(null, "宝付订单[" + item + "]支付结果轮询异常", null, "【宝付订单支付结果轮询】");
                e.printStackTrace();
            }
        }

    }

    /**
     * 转账到主商户号
     * @param transOrder
     */
    private void tranToMainMerchant(LnPayOrders transOrder,String merchantNo) {

        lnPayOrdersMapper.insertSelective(transOrder);
        //订单明细表插入
        LnPayOrdersJnl orderJnl = new LnPayOrdersJnl();
        orderJnl.setCreateTime(new Date());
        orderJnl.setOrderId(transOrder.getId());
        orderJnl.setOrderNo(transOrder.getOrderNo());
        orderJnl.setSysTime(new Date());
        orderJnl.setTransAmount(transOrder.getAmount());
        orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        orderJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
        lnPayOrdersJnlMapper.insertSelective(orderJnl);

        //发起宝付钱包转账
        B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans acctTransReq = new B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans();
        acctTransReq.setTrans_money(transOrder.getAmount().toString());
        acctTransReq.setTrans_no(transOrder.getOrderNo());
        acctTransReq.setTransSummary("钱包转账到主商户号");
        acctTransReq.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_MAIN);
        acctTransReq.setMerchantNo(merchantNo);

        B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans tranRes = null;
        try {
            tranRes = baoFooTransportService.pay4OnlineTrans(acctTransReq);
        } catch (Exception e) {
            tranRes.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
            tranRes.setState(Constants.BAOFOO_ONLINE_TRANS_STATUS_PAYING);
            //告警
            specialJnlService.warn4FailNoSMS(transOrder.getAmount(), "{系统钱包转账-" + transOrder.getNote() + "}订单号["+transOrder.getOrderNo()+"]转账申请通讯异常",
                    transOrder.getOrderNo(),"系统钱包转账-" + transOrder.getNote());
        }

        LnPayOrders updateOrder = new LnPayOrders();
        updateOrder.setId(transOrder.getId());
        updateOrder.setReturnMsg(tranRes.getResMsg());

        LnPayOrdersJnl insertOrderJnl = new LnPayOrdersJnl();
        insertOrderJnl.setOrderId(transOrder.getId());
        insertOrderJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
        insertOrderJnl.setOrderNo(transOrder.getOrderNo());
        insertOrderJnl.setTransAmount(transOrder.getAmount());
        insertOrderJnl.setReturnMsg(tranRes.getResMsg());

        //转账请求成功
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(tranRes.getResCode())){
            if(Constants.BAOFOO_ONLINE_TRANS_STATUS_PAYING.equals(tranRes.getState())){
                //转账中
                updateOrder.setStatus(Constants.ORDER_STATUS_PAYING);
                updateOrder.setReturnCode(ConstantUtil.BSRESCODE_ING);
                updateOrder.setUpdateTime(new Date());
                lnPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);

                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
                insertOrderJnl.setReturnCode(ConstantUtil.BSRESCODE_ING);
                lnPayOrdersJnlMapper.insertSelective(insertOrderJnl);

                specialJnlService.warn4FailNoSMS(transOrder.getAmount(), "{系统钱包转账-" + transOrder.getNote() + "}订单号["+transOrder.getOrderNo()+"]转账申请处理中",
                        transOrder.getOrderNo(),"系统钱包转账-" + transOrder.getNote());

            }else if(Constants.BAOFOO_ONLINE_TRANS_STATUS_SUCCESS.equals(tranRes.getState())){
                //成功,更新订单表，记录订单流水表
                updateOrder.setStatus(Constants.ORDER_STATUS_SUCCESS);
                updateOrder.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                updateOrder.setReturnMsg("查询成功");
                updateOrder.setUpdateTime(new Date());
                lnPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);

                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                insertOrderJnl.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                lnPayOrdersJnlMapper.insertSelective(insertOrderJnl);
            }else {
                //失败,更新订单表，记录订单流水表
                updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                updateOrder.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
                updateOrder.setReturnMsg("查询失败");
                updateOrder.setUpdateTime(new Date());
                lnPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);

                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                insertOrderJnl.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
                lnPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                //告警
                specialJnlService.warn4FailNoSMS(transOrder.getAmount(), "{系统钱包转账-" + transOrder.getNote() + "}订单号["+transOrder.getOrderNo()+"]转账申请失败",
                        transOrder.getOrderNo(),"系统钱包转账-" + transOrder.getNote());
            }
        }else{
            //转账请求失败，更新订单表，记录订单流水表
            updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            updateOrder.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
            updateOrder.setReturnMsg("查询失败");
            updateOrder.setUpdateTime(new Date());
            lnPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);

            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            insertOrderJnl.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
            lnPayOrdersJnlMapper.insertSelective(insertOrderJnl);

            //告警
            specialJnlService.warn4FailNoSMS(transOrder.getAmount(), "{系统钱包转账-" + transOrder.getNote() + "}订单号["+transOrder.getOrderNo()+"]转账申请失败",
                    transOrder.getOrderNo(),"系统钱包转账-" + transOrder.getNote());

        }
    }

}
