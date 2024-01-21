package com.pinting.business.coreflow.repay.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.DepFixedRepayPayResultInfo;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.business.dao.BsBgwNuccSignRelationMapper;
import com.pinting.business.dao.BsPayResultQueueMapper;
import com.pinting.business.dao.BsPaymentChannelMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnPayOrdersJnlMapper;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.dao.LnRepayMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsBgwNuccSignRelation;
import com.pinting.business.model.BsBgwNuccSignRelationExample;
import com.pinting.business.model.BsPayResultQueue;
import com.pinting.business.model.BsPaymentChannel;
import com.pinting.business.model.LnBindCard;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.model.LnPayOrdersJnl;
import com.pinting.business.model.LnRepay;
import com.pinting.business.model.LnRepayExample;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooAgreementPay_DirectAgreementPay;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.model.RiskItems;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_RepayResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_RepayResultNotice;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.loan.DafyNoticeService;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 代扣流程抽象实现类
 * Created by Gemma on 2018/6/25.
 */
public class AbstractRepayWithholdingRepay4ServiceImpl implements DepFixedService {

	private Logger logger = LoggerFactory.getLogger(AbstractRepayApplyServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    @Autowired
    private LnLoanMapper lnLoanMapper;
	@Autowired
	private LnRepayMapper  lnRepayMapper;
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	@Autowired
	private BsPaymentChannelMapper paymentChannelMapper;
	@Autowired
	private BsBgwNuccSignRelationMapper bsBgwNuccSignRelationMapper;
	@Autowired
	private LnUserMapper lnUserMapper;
	@Autowired
    private BsPayResultQueueMapper queueMapper;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
    private LnPayOrdersJnlMapper lnPayOrdersJnlMapper;
	
	@Autowired
    private PayOrdersService payOrdersService;
	@Autowired
    private BaoFooTransportService baoFooTransportService;
	@Autowired
    private DafyNoticeService dafyNoticeService;
	@Autowired
	private DepFixedRepayPaymentService depFixedRepayPaymentService;
	
    @Override
    public ResMsg execute(FlowContext flowContext) {
    	LnPayOrders lnPayOrder = (LnPayOrders) flowContext.getExtendMap().get("lnPayOrders");
    	LnBindCard lnBindCard = (LnBindCard) flowContext.getExtendMap().get("lnBindCard");
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY_SELF.getKey()+ lnPayOrder.getPartnerCode() +lnBindCard.getIdCard());
            //1.代扣还款-业务数据前置校验
            verifyBusinessBefore( flowContext, lnPayOrder, lnBindCard );
            
            String userSignNo = (String) flowContext.getExtendMap().get("userSignNo");
        	String payIP = (String) flowContext.getExtendMap().get("payIp");
        	LnRepay lnRepay = (LnRepay) flowContext.getExtendMap().get("lnRepay");
        	
            //2.代扣还款-老接口代扣/网联协议支付代扣
         	boolean isAgreementPsy = doAgreementPayRepay( flowContext, userSignNo, payIP, lnPayOrder, lnRepay );  //是否为协议支付标识
         	//2.代扣 
         	if( !isAgreementPsy ) {
         		doCutpaymentRepay(flowContext);
         	}
         	
            //3.代扣/网联支付-结果处理
         	doDkResultBiz(flowContext);
            //4.代扣还款结果处理
            doRepayResultBiz(flowContext);
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY_SELF.getKey()+ lnPayOrder.getPartnerCode() +lnBindCard.getIdCard());
            logger.info("=========代扣还款，结果处理，解锁："+RedisLockEnum.LOCK_REPAY_SELF.getKey()+ lnPayOrder.getPartnerCode() +lnBindCard.getIdCard()+"=======");
        }
        return flowContext.getRes();
    }
	/**
	 * 代扣还款处理
	 * */
    private void doDkResultBiz(FlowContext flowContext) {
    	String resCode = (String) flowContext.getExtendMap().get("resCode");
    	String resMsg  = (String) flowContext.getExtendMap().get("resMsg");
    	String pay4OnlieOrderNo = (String) flowContext.getExtendMap().get("pay4OnlieOrderNo");
    	LnPayOrders lnPayOrder = (LnPayOrders) flowContext.getExtendMap().get("lnPayOrders");
    	BsPaymentChannel channelInfo = (BsPaymentChannel) flowContext.getExtendMap().get("channelInfo");
    	
    	if ( ! ConstantUtil.BSRESCODE_SUCCESS.equals(resCode) ) {
            if ( LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode().equals( resCode ) ) {
                //更新订单表为处理中 
                payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), 
                		Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                        null, resCode, resMsg );
                //还款处理中，放到redis中
                LoanNoticeVO vo = new LoanNoticeVO();
                vo.setPayOrderNo(lnPayOrder.getOrderNo());
                vo.setChannel(lnPayOrder.getChannel());
                vo.setChannelTransType(lnPayOrder.getChannelTransType());
                vo.setTransType(lnPayOrder.getTransType());
                vo.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
                vo.setAmount(MoneyUtil.defaultRound(lnPayOrder.getAmount()).toString());
                redisUtil.rpushRedis(vo);
                //并插入到消息队列表中
                BsPayResultQueue queue = new BsPayResultQueue();
                queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
                queue.setCreateTime(new Date());
                queue.setDealNum(0);
                queue.setOrderNo(lnPayOrder.getOrderNo());
                queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
                queue.setTransType(lnPayOrder.getTransType());
                queue.setUpdateTime(new Date());
                queueMapper.insertSelective(queue);
            } else {
                //更	新订单表为处理中
                payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), 
                		Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                        null, resCode, resMsg );
            }

        } else {
        	if( channelInfo != null && channelInfo.getIsMain() != null && channelInfo.getIsMain() !=1 ) {
        		//使用的是辅助通道
        		if(StringUtil.isBlank( pay4OnlieOrderNo )) {
        			//钱包转账支付订单号为空，告警
        			specialJnlService.warn4FailNoSMS(lnPayOrder.getAmount(), "代扣商户间钱包转账失败，代扣订单号："+lnPayOrder.getOrderNo(),lnPayOrder.getOrderNo(),"【代扣商户间转账】");
        		} else {
            		//钱包转账支付订单号不为空,记钱包转账支付的订单及流水
            		LnPayOrders lnPayOrders = new LnPayOrders();
                    lnPayOrders.setCreateTime(new Date());
                    lnPayOrders.setAccountType(2);	//转账算系统,记2
                    lnPayOrders.setAmount(lnPayOrder.getAmount());
                    lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
                    lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
                    lnPayOrders.setMoneyType(0);
                    lnPayOrders.setOrderNo( pay4OnlieOrderNo );
                    lnPayOrders.setPartnerCode(lnPayOrder.getPartnerCode());
                    lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                    lnPayOrders.setSubAccountId(lnPayOrder.getSubAccountId());
                    lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
                    lnPayOrders.setUpdateTime(new Date());
                    lnPayOrders.setPaymentChannelId(channelInfo.getId());
                    lnPayOrdersMapper.insertSelective(lnPayOrders);
                    //记录订单流水表
            		LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                    lnPayOrdersJnl.setSubAccountId(lnPayOrder.getSubAccountId());
                    lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_TRANSFER.getCode());
                    lnPayOrdersJnl.setCreateTime(new Date());
                    lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
                    lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
                    lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
                    lnPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                    lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
                    lnPayOrdersJnl.setSysTime(new Date());
                    lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        		}
        	}
            //更新订单表为处理中
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrder.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, resCode, resMsg );
        }
    }
    /**
     * 还款结果处理
     * */
    private void doRepayResultBiz(FlowContext flowContext) {
    	//还款成功,还款支付结果处理
    	LnPayOrders lnPayOrder = (LnPayOrders) flowContext.getExtendMap().get("lnPayOrders");
    	String resCode = (String) flowContext.getExtendMap().get("resCode");
    	String resMsg = (String) flowContext.getExtendMap().get("resMsg");
    	
    	boolean payResultFlag = false;
    	if( ! ConstantUtil.BSRESCODE_SUCCESS.equals(resCode) ) {
    		if(  LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode().equals( resCode )  ) {
    			logger.info("订单号"+lnPayOrder.getOrderNo()+"处理中......宝付状态轮询中......");
    			return;
    		} else {
    			payResultFlag = false;
    		}
    	} else {
    		payResultFlag = true;
    	}
    	
        DepFixedRepayPayResultInfo payResult = new DepFixedRepayPayResultInfo();
        payResult.setSuccess(payResultFlag);
        payResult.setAmount(lnPayOrder.getAmount());
        payResult.setOrderNo(lnPayOrder.getOrderNo());
        payResult.setReturnCode(resCode);
        payResult.setReturnMsg(resMsg);
        depFixedRepayPaymentService.doRepayResultPayProcess(payResult);
    }
    /**
     * 业务数据前置校验
     * */
    private void verifyBusinessBefore(FlowContext flowContext, LnPayOrders lnPayOrder, LnBindCard lnBindCard) {
    	logger.info("=========代扣还款,结果处理,加锁："+RedisLockEnum.LOCK_REPAY_SELF.getKey()+ lnPayOrder.getPartnerCode() +lnBindCard.getIdCard()+"=======");
        LnRepayExample lnRepayExample = new LnRepayExample();
        lnRepayExample.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
        List<LnRepay> repayList = lnRepayMapper.selectByExample(lnRepayExample);
        // 前置校验
        if (CollectionUtils.isNotEmpty(repayList)) {
            LnPayOrders newestOrder = lnPayOrdersMapper.selectByPrimaryKey(lnPayOrder.getId());
            if(null == newestOrder){
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "无订单记录");
            }
            if(newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()) ||
                    newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode())){
                //订单已成功或处理中时
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单"+newestOrder.getOrderNo()+"正在处理中或者已经成功");
            } else if(newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()) ||
                    newestOrder.getStatus() == Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode())) {
                //订单非成功非处理中时，返回错误信息
                String errorMsg = newestOrder.getReturnMsg();
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "：" + errorMsg);
            }
            flowContext.getExtendMap().put("lnRepay", repayList.get(0));
        } else {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "无还款记录");
        }
    }

    /**
     * 老接口代扣
     * */
    private void doCutpaymentRepay(FlowContext flowContext) {
    	String merchantNo = (String) flowContext.getExtendMap().get("merchantNo");
    	Integer isMain = (Integer) flowContext.getExtendMap().get("isMain");
    	
    	LnPayOrders lnPayOrder = (LnPayOrders) flowContext.getExtendMap().get("lnPayOrders");
    	LnBindCard lnBindCard = (LnBindCard) flowContext.getExtendMap().get("lnBindCard");
		
    	//宝付代扣
        B2GReqMsg_BaoFooCutpayment_Cutpayment cutpayment = new B2GReqMsg_BaoFooCutpayment_Cutpayment();
        
        cutpayment.setMerchantNo( merchantNo );
    	cutpayment.setIsMain(isMain);
    	cutpayment.setTrans_serial_no(Util.generateUserOrderNo4Pay19(lnBindCard.getLnUserId()));
        cutpayment.setTrans_id(lnPayOrder.getOrderNo());
        //云贷宝付代扣交易金额入参需要转成分
        String partnerName = PartnerEnum.getEnumByCode(lnPayOrder.getPartnerCode()) == null ? "" :
        		PartnerEnum.getEnumByCode(lnPayOrder.getPartnerCode()).getName();
        cutpayment.setTxnAmt(MoneyUtil.multiply(lnPayOrder.getAmount(), 100).setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        cutpayment.setAcc_no(lnBindCard.getBankCard());
        cutpayment.setId_card(lnBindCard.getIdCard());
        cutpayment.setId_holder(lnBindCard.getUserName());
        cutpayment.setMobile(lnBindCard.getMobile());
        cutpayment.setPay_code(lnBindCard.getBankCode());
        cutpayment.setAdditional_info(partnerName + "用户代扣还款");
        
        B2GResMsg_BaoFooCutpayment_Cutpayment res = null;
        try {
            res = baoFooTransportService.withholding(cutpayment);
            flowContext.getExtendMap().put("pay4OnlieOrderNo", res.getPay4OnlineOrderNo());
			flowContext.getExtendMap().put("resCode", res.getResCode());
			flowContext.getExtendMap().put("resMsg", res.getResMsg());
        } catch (Exception e) {
            e.printStackTrace();
            flowContext.getExtendMap().put("resCode", LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
			flowContext.getExtendMap().put("resMsg", "通讯失败,置为处理中");
        }
    }
    /**
     * 网联协议支付代扣
     * */
    protected boolean doAgreementPayRepay( FlowContext flowContext, String userSignNo, String payIP, 
    		LnPayOrders lnPayOrder, LnRepay lnRepay) {
    	boolean isAgreementPsy = false;
    	//宝付协议支付
        B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay agreementPayReq = new B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay();
        //查询支付渠道代扣优先商户号信息
        BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
        if( channelInfo != null ) {
	    	if (!StringUtil.isEmpty(userSignNo) && !StringUtil.isEmpty(payIP)) {
	    		isAgreementPsy = true;
	    		//修改订单表payment_channel_id,是否为协议支付(isProtocolPay)
	        	payOrdersService.updatePaymentChannelId(lnPayOrder.getId(), channelInfo.getId(), Constants.BAOFOO_IS_PROTOCOLPAY_YES);
	    		//已签约，发签约代扣
	        	agreementPayReq.setIsMain(channelInfo.getIsMain());
	    		agreementPayReq.setMember_id(channelInfo.getMerchantNo());
	    		agreementPayReq.setSend_time(lnPayOrder.getCreateTime());
	    		agreementPayReq.setTrans_id(lnPayOrder.getOrderNo());
	    		//七贷不传user_id,云贷传币港湾存储的云贷user_id
        		if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(lnPayOrder.getPartnerCode())) {
					//查询云贷提供的用户编号
        			LnUser user = lnUserMapper.selectByPrimaryKey(lnPayOrder.getLnUserId());
        			agreementPayReq.setUser_id(user.getPartnerUserId());
				} else if(Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(lnPayOrder.getPartnerCode())) {
					agreementPayReq.setUser_id("");
				}
				agreementPayReq.setProtocol_no(userSignNo);
	    		//云贷宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，赞分期无需改动
	    		agreementPayReq.setTxn_amt(MoneyUtil.multiply(lnPayOrder.getAmount(), 100).longValue());
	    		B2GResMsg_BaoFooAgreementPay_DirectAgreementPay agreementPayRes = null;
	    		try {
	    			LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnRepay.getLoanId());
	    			RiskItems riskItems = getRiskItems(lnRepay.getLnUserId(),lnLoan,lnPayOrder,payIP);
	    			agreementPayReq.setRiskItems(riskItems);
	    			logger.info("协议支付入参：" + agreementPayReq.toString());
	    			agreementPayRes = baoFooTransportService.directAgreementPay(agreementPayReq);
	    			flowContext.getExtendMap().put("pay4OnlieOrderNo", agreementPayRes.getPay4OnlineOrderNo());
	    			flowContext.getExtendMap().put("resCode", agreementPayRes.getResCode());
	    			flowContext.getExtendMap().put("resMsg", agreementPayRes.getResMsg());
				} catch (Exception e) {
					 e.printStackTrace();
					 flowContext.getExtendMap().put("resCode", LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
					 flowContext.getExtendMap().put("resMsg", "通讯失败，置为处理中");
				}
			} else {
				BsBgwNuccSignRelation signRelation = getSingRelation(lnRepay.getLnUserId(),lnPayOrder.getPartnerCode(),
	        			channelInfo.getMerchantNo(),lnPayOrder.getBankCardNo(),lnPayOrder.getUserName(),
	        			lnPayOrder.getIdCard(),lnPayOrder.getMobile());
				if (signRelation != null && StringUtil.isNotBlank(signRelation.getProtocolNo())){
					isAgreementPsy = true;
	        		//修改订单表payment_channel_id，是否为协议支付（isProtocolPay）
	            	payOrdersService.updatePaymentChannelId(lnPayOrder.getId(), channelInfo.getId(), Constants.BAOFOO_IS_PROTOCOLPAY_YES);
	        		//已签约，发签约代扣
	            	agreementPayReq.setIsMain(channelInfo.getIsMain());
	        		agreementPayReq.setMember_id(channelInfo.getMerchantNo());
	        		agreementPayReq.setSend_time(lnPayOrder.getCreateTime());
	        		agreementPayReq.setTrans_id(lnPayOrder.getOrderNo());
	        		agreementPayReq.setUser_id(String.valueOf(signRelation.getUserId()));
	        		agreementPayReq.setProtocol_no(signRelation.getProtocolNo());
	        		//云贷宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，赞分期无需改动
	        		agreementPayReq.setTxn_amt(MoneyUtil.multiply(lnPayOrder.getAmount(), 100).longValue());
	        		B2GResMsg_BaoFooAgreementPay_DirectAgreementPay agreementPayRes = null;
	        		try {
	        			LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnRepay.getLoanId());
	        			RiskItems riskItems = getRiskItems(lnRepay.getLnUserId(),lnLoan,lnPayOrder,"");
	        			agreementPayReq.setRiskItems(riskItems);
	        			logger.info("协议支付入参："+agreementPayReq.toString());
	        			agreementPayRes = baoFooTransportService.directAgreementPay(agreementPayReq);
	                    flowContext.getExtendMap().put("pay4OnlieOrderNo", agreementPayRes.getPay4OnlineOrderNo());
		    			flowContext.getExtendMap().put("resCode", agreementPayRes.getResCode());
		    			flowContext.getExtendMap().put("resMsg", agreementPayRes.getResMsg());
					} catch (Exception e) {
						 e.printStackTrace();
						 flowContext.getExtendMap().put("resCode", LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
						 flowContext.getExtendMap().put("resMsg", "通讯失败，置为处理中");
					}
				} else {
	        		//修改订单表payment_channel_id,是否为协议支付(isProtocolPay)
	            	payOrdersService.updatePaymentChannelId(lnPayOrder.getId(), channelInfo.getId(), Constants.BAOFOO_IS_PROTOCOLPAY_NO);
	        	}
			} 
	    	flowContext.getExtendMap().put("merchantNo", channelInfo.getMerchantNo());
	    	flowContext.getExtendMap().put("isMain", channelInfo.getIsMain());
	    	flowContext.getExtendMap().put("channelInfo", channelInfo);
        }
        return isAgreementPsy;
    }
    
    
    /**
     * 查询BsBgwNuccSignRelation
     * @param lnUserId
     * @param userType
     * @param merchantNo
     * @param bankCardNo
     * @param userName
     * @param idCard
     * @param mobile
     * @return
     */
    private BsBgwNuccSignRelation getSingRelation(Integer lnUserId,
			String userType, String merchantNo, String bankCardNo, String userName,
			String idCard, String mobile) {
    	BsBgwNuccSignRelationExample example = new BsBgwNuccSignRelationExample();
    	example.createCriteria().andUserIdEqualTo(lnUserId).andUserTypeEqualTo(userType).andMerchanntNoEqualTo(merchantNo)
    		.andCardNoEqualTo(bankCardNo).andCardOwnerEqualTo(userName).andIdCardEqualTo(idCard).andMobileEqualTo(mobile)
    		.andStatusEqualTo(1);
    	List<BsBgwNuccSignRelation> list = bsBgwNuccSignRelationMapper.selectByExample(example);
    	if(CollectionUtils.isNotEmpty(list)){
    		return list.get(0);
    	}
		return null;
	}
    
    /**
     * 协议支付风控参数-互金消金参数
     * @param lnUserId
     * @param lnLoan
     * @param lnPayOrder
     * @return
     */
    private RiskItems getRiskItems(Integer lnUserId, LnLoan lnLoan, LnPayOrders lnPayOrder, String payIP) {
    	String dateTimeString = "yyyyMMddHHmmss";
    	RiskItems riskItems = new RiskItems();
    	LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnUserId);
    	riskItems.setUserLoginId(lnUser.getMobile());
    	riskItems.setUserMobile(lnUser.getMobile());
    	riskItems.setRegisterUserName(lnUser.getUserName());
    	riskItems.setIdentifyState("1");//是否实名认证:1是 0不是
    	riskItems.setUserIdNo(lnUser.getIdCard());
    	riskItems.setRegisterTime(DateUtil.formatDateTime(lnUser.getCreateTime(), dateTimeString));
    	riskItems.setChName(lnPayOrder.getUserName());
    	riskItems.setChIdNo(lnPayOrder.getIdCard());
    	riskItems.setChCardNo(lnPayOrder.getBankCardNo());
    	riskItems.setChMobile(lnPayOrder.getMobile());
    	if (StringUtil.isEmpty(payIP)) {
			riskItems.setChPayIp("");
		} else {
			riskItems.setChPayIp(payIP);
		}
    	riskItems.setTradeType(Constants.BAOFOO_PROTOCOLPAY_TRANS_TYPE_REPAY);
    	riskItems.setCustomerType(Constants.BAOFOO_PROTOCOLPAY_CUSTOMER_TYPE_LN);
    	riskItems.setLendingRate(lnLoan.getAgreementRate().toString());
    	//查询该笔借款的最后账单日
    	/*LnRepaySchedule repaySchedule = lnRepayScheduleMapper.lastPlanByLoanId(lnLoan.getId());
    	riskItems.setRepaymentDate(DateUtil.formatYYYYMMDD(repaySchedule.getPlanDate())+" 23:59:59");
    	
    	//查询订单表同银行卡上次协议支付成功时间
    	LnPayOrders payOrders = lnPayOrdersMapper.selectLastByCard(lnPayOrder.getBankCardNo(),lnPayOrder.getId());
    	if(payOrders == null || payOrders.getUpdateTime() == null){
    		riskItems.setLatestTradeDate("0");
    	}else{
    		riskItems.setLatestTradeDate(DateUtil.formatDateTime(payOrders.getUpdateTime(), dateTimeString));
    	}*/
    	
		return riskItems;
	}
    public void notifyPartner(final LnPayOrders lnPayOrder, final String errorMsg) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info(">>>还款通知合作方开始：[入参]" + JSON.toJSONString(lnPayOrder) + "|errorMsg=" + errorMsg + "<<<");
                B2GReqMsg_DafyLoanNotice_RepayResultNotice noticeRepay = new B2GReqMsg_DafyLoanNotice_RepayResultNotice();
                //通过订单号在还款信息列表其中一条记录中获取合作方订单号
                LnRepayExample example = new LnRepayExample();
                example.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
                List<LnRepay> repayList = lnRepayMapper.selectByExample(example);
                if(CollectionUtils.isEmpty(repayList)){
                    logger.error("通知云贷时未找到BgwOrderNo=" + lnPayOrder.getOrderNo() + "的还款信息记录");
                }
                LnRepay repay = repayList.get(0);
                noticeRepay.setOrderNo(repay.getPartnerOrderNo());
                noticeRepay.setBgwOrderNo(repay.getBgwOrderNo());
                noticeRepay.setChannelLoan(lnPayOrder.getChannel());
                List<Integer> loanIds = new ArrayList<Integer>();
                //循环判断多笔借款信息是否存在
                for(LnRepay lnRepay :repayList){
                    //根据借款编号 查询借款信息
                    LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnRepay.getLoanId());
                    if (lnLoan == null) {
                        loanIds.add(lnRepay.getLoanId());
                    }
                }
                if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_SUCCESS){
                    noticeRepay.setResultCode("SUCCESS");
                    noticeRepay.setResultMsg(LoanStatus.REPAY_STATUS_REPAIED.getDescription());
                    noticeRepay.setFinishTime(lnPayOrder.getUpdateTime());
                }else if(lnPayOrder.getStatus() == Constants.ORDER_STATUS_PAYING){
                    noticeRepay.setResultCode("PROCESS");
                    noticeRepay.setResultMsg(LoanStatus.REPAY_STATUS_PAYING.getDescription());
                }else{
                    noticeRepay.setResultCode("FAIL");
                    noticeRepay.setResultMsg(StringUtil.isEmpty(lnPayOrder.getReturnMsg())?LoanStatus.REPAY_STATUS_FAIL.getDescription():lnPayOrder.getReturnMsg());
                }
                if(CollectionUtils.isNotEmpty(loanIds)){
                    logger.error("还款通知时找不到的借款信息id为："+JSONObject.fromObject(loanIds).toString());
                }
                B2GResMsg_DafyLoanNotice_RepayResultNotice res = null;
                LnRepay repayTemp = new LnRepay();
                try {
                    res = dafyNoticeService.noticeRepay(noticeRepay);

                    if (res != null && ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                        repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_SUCCESS.getCode());
                    } else {
                        repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    repayTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                }
                LnRepayExample lnRepayExample = new LnRepayExample();
                lnRepayExample.createCriteria().andPayOrderNoEqualTo(lnPayOrder.getOrderNo());
                repayTemp.setUpdateTime(new Date());
                lnRepayMapper.updateByExampleSelective(repayTemp, lnRepayExample);
            }
        }).start();
    }
}

