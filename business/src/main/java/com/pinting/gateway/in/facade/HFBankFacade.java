package com.pinting.gateway.in.facade;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.finance.model.SysRechargeResultInfo;
import com.pinting.business.accounting.finance.model.SysWithdrawResultInfo;
import com.pinting.business.accounting.finance.service.DepUserBalanceWithdrawService;
import com.pinting.business.accounting.finance.service.DepUserTopUpService;
import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.enums.HFAccountTypeEnum;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.RepayResultInfo;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.accounting.loan.service.DepOfflineRepayService;
import com.pinting.business.accounting.loan.service.LoanPaymentService;
import com.pinting.business.accounting.loan.service.SysDailyBizService;
import com.pinting.business.dao.BsLiquidationRecordMapper;
import com.pinting.business.dao.BsRedPacketInfoMapper;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.OrderResultInfo;
import com.pinting.business.model.vo.HFBalanceDetailVO;
import com.pinting.business.service.common.OrderBusinessService;
import com.pinting.business.service.manage.BsHfBankService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("HFBank")
public class HFBankFacade {
	private Logger log = LoggerFactory.getLogger(HFBankFacade.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private DepUserTopUpService depUserTopUpService;
    @Autowired
    private DepUserBalanceWithdrawService depUserBalanceWithdrawService;
    @Autowired
    private DepFixedLoanPaymentService depFixedLoanPaymentService;
    @Autowired
    private LoanPaymentService loanPaymentService;
    @Autowired
    private DepOfflineRepayService depOfflineRepayService;
    @Autowired
    private LnPayOrdersMapper payOrdersMapper;
    @Autowired
    private BsHfBankService bsHfBankService;
    @Autowired
    private OrderBusinessService orderBusinessService;
    @Autowired
    private SysDailyBizService sysDailyBizService;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private BsRedPacketInfoMapper bsRedPacketInfoMapper;
    @Autowired
    private BsLiquidationRecordMapper bsLiquidationRecordMapper;
    @Autowired
    private SpecialJnlService specialJnlService;

    public void outOfAccount(G2BReqMsg_HFBank_OutOfAccount req,G2BResMsg_HFBank_OutOfAccount res) throws Exception {
    	log.info("Business平台已收到成标出账通知：" + req);
    	//获取用户的赞分期、云贷标识
    	LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
    	lnPayOrdersExample.createCriteria().andOrderNoEqualTo(req.getOrder_no()).andBankCardNoEqualTo(req.getWithdraw_account());
    	List<LnPayOrders> lnPayOrders = payOrdersMapper.selectByExample(lnPayOrdersExample);
    	if(CollectionUtils.isEmpty(lnPayOrders)){
    	    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",未找到order_no=" + req.getOrder_no() + ",bank_card_no=" + req.getWithdraw_account() + "对应的标的出账订单信息");
        }
    	if(Constants.PROPERTY_SYMBOL_ZAN.equals(lnPayOrders.get(0).getPartnerCode())) {//赞分期
    		log.info("赞分期标的出账订单号=["+req.getOrder_no()+"]通知更新");
    		loanPaymentService.outOfAccountResultAcceptZan(req, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF);
    	} else {//云贷/7贷/赞时贷==
            PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(lnPayOrders.get(0).getPartnerCode());
            if(partnerEnum == null) {
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "未找到合作方编码");
            }
    		log.info(partnerEnum.getName() + "标的出账订单号=[" + req.getOrder_no() + "]通知更新");
    		depFixedLoanPaymentService.outOfAccountResultAccept(req, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF, partnerEnum);
    	}
    }

    public void gatewayRechargeNotice(G2BReqMsg_HFBank_GatewayRechargeNotice req, G2BResMsg_HFBank_GatewayRechargeNotice res) throws Exception {
        log.info("Business平台已收到充值订单号为"+req.getOrder_no()+"的通知：{}", JSONObject.fromObject(req));
        OrderResultInfo orderResultInfo = new OrderResultInfo();
        orderResultInfo.setOrderNo(req.getOrder_no());
        orderResultInfo.setAmount(req.getPay_amt());
        orderResultInfo.setPayOrderNo(req.getPay_order_no());
        orderResultInfo.setResMsg(req.getError_info());
        orderResultInfo.setResCode(req.getError_no());
        orderResultInfo.setFinishTime(DateUtil.parse(req.getPay_finish_date() + req.getPay_finish_time(), "yyyyMMddHHmmss"));
        orderResultInfo.setSuccess(true);
        if("2".equals(req.getOrder_status())) {
            // 处理成功
            orderResultInfo.setSuccess(true);
            orderBusinessService.financeHFTopUp(orderResultInfo);
        } else if("3".equals(req.getOrder_status())) {
            // 处理失败
            orderResultInfo.setSuccess(false);
            orderBusinessService.financeHFTopUp(orderResultInfo);
        }
    }

    public void userBatchWithdrawNotice(G2BReqMsg_HFBank_UserBatchWithdrawNotice req,G2BResMsg_HFBank_UserBatchWithdrawNotice res) throws Exception {
        log.info("Business平台已收到提现"+req.getOrder_no()+"的通知：{}", JSONObject.fromObject(req));
        OrderResultInfo result = new OrderResultInfo();
        result.setAmount(req.getPay_amt());
        result.setOrderNo(req.getOrder_no());
        result.setPayOrderNo(req.getPay_order_no());
        result.setReturnMsg(req.getError_info());
        result.setReturnCode(req.getError_no());
        result.setHfUserId(StringUtil.isBlank(req.getPlatcust()) ? Constants.HF_NO_PLATCUST: req.getPlatcust());
        result.setFinishTime(DateUtil.parse(req.getPay_finish_date() + req.getPay_finish_time(), "yyyyMMddHHmmss"));
        result.setPayPath(req.getPay_path());
        if("1".equals(req.getOrder_status())) {
            // 处理成功
            result.setSuccess(true);
            orderBusinessService.financeHFWithdraw(result);
        } else if("2".equals(req.getOrder_status())) {
            // 处理失败
            result.setSuccess(false);
            orderBusinessService.financeHFWithdraw(result);
        }
    }

    public void borrowCutRepayNotice(G2BReqMsg_HFBank_BorrowCutRepayNotice req,G2BResMsg_HFBank_BorrowCutRepayNotice res) throws Exception {
        log.info("Business平台已收到恒丰行内代扣还款订单号"+req.getOrder_no()+"的通知：" + req);
        RepayResultInfo repayResultInfo = new RepayResultInfo();
        repayResultInfo.setErrorCode(null);
        repayResultInfo.setErrorMsg(null);
        repayResultInfo.setOrderFinTime(new Date());
        repayResultInfo.setAmount(req.getAmt());
        repayResultInfo.setOrderId(req.getOrder_no());
        if(Constants.HF_OUT_OF_ACCOUNT_SUCCESS.equals(req.getCode())) {
            // 处理成功
            repayResultInfo.setStatus(OrderStatus.SUCCESS.getCode());
        } else if(Constants.HF_OUT_OF_ACCOUNT_FAIL.equals(req.getCode())) {
            // 处理失败
            repayResultInfo.setStatus(OrderStatus.FAIL.getCode());
        }
        depOfflineRepayService.notifyCutRepay2BorrowerResult(repayResultInfo);
    }
    
    public void platTopUpNotice(G2BReqMsg_HFBank_PlatTopUpNotice req, G2BResMsg_HFBank_PlatTopUpNotice res) throws Exception {
        log.info("Business平台已收到平台充值订单号"+req.getOrder_no()+"的通知：{}", JSONObject.fromObject(req));
        SysRechargeResultInfo sysRechargeResultInfo = new SysRechargeResultInfo();
        sysRechargeResultInfo.setOrder_no(req.getOrder_no());
        sysRechargeResultInfo.setAmount(req.getAmt());
        sysRechargeResultInfo.setCode(req.getCode());
        sysRechargeResultInfo.setPlat_no(req.getPlat_no());
        if("1".equals(req.getCode())) {
            // 处理成功
        	sysRechargeResultInfo.setStatus(OrderStatus.SUCCESS.getCode());
            bsHfBankService.sysTopUpNotify(sysRechargeResultInfo);
        } else if("2".equals(req.getCode())) {
            // 处理失败
        	sysRechargeResultInfo.setStatus(OrderStatus.FAIL.getCode());
            bsHfBankService.sysTopUpNotify(sysRechargeResultInfo);
        }
    }
    
    public void platWithdrawNotice(G2BReqMsg_HFBank_PlatWithdrawNotice req, G2BResMsg_HFBank_PlatWithdrawNotice res) throws Exception {
        log.info("Business平台已收到平台提现订单号"+req.getOrder_no()+"的通知：{}", JSONObject.fromObject(req));
        SysWithdrawResultInfo sysRechargeResultInfo = new SysWithdrawResultInfo();
        sysRechargeResultInfo.setOrder_no(req.getOrder_no());
        sysRechargeResultInfo.setAmount(req.getAmt());
        sysRechargeResultInfo.setCode(req.getCode());
        sysRechargeResultInfo.setPlat_no(req.getPlat_no());
        if("1".equals(req.getCode())) {
            // 处理成功
        	sysRechargeResultInfo.setStatus(OrderStatus.SUCCESS.getCode());
            bsHfBankService.sysWithdrawNotify(sysRechargeResultInfo);
        } else if("2".equals(req.getCode())) {
            // 处理失败
        	sysRechargeResultInfo.setStatus(OrderStatus.FAIL.getCode());
        	bsHfBankService.sysWithdrawNotify(sysRechargeResultInfo);
        }
    }
    
    
    public void liquidationNotice(G2BReqMsg_HFBank_LiquidationNotice req, G2BResMsg_HFBank_LiquidationNotice res) throws Exception {
        log.info("Business平台已收到清算成功的通知：{}", JSONObject.fromObject(req));
        /*清算标识：1:完成*/
        if(Constants.LIQUIDATION_FLAG_YES.equals(req.getLiquidation_flag())){
            //置清算时间（清算标识）
        	jsClientDaoSupport.setString(DateUtil.formatYYYYMMDD(new Date()), Constants.LIQUIDATION_FLAG);

        	//是否已进行过当日清算，没有清算记录则查询数据并新增，否则直接取清算表数据
        	Date now = new Date();
            BsLiquidationRecord record = new BsLiquidationRecord();
            BsLiquidationRecordExample liquiExample = new BsLiquidationRecordExample();
            liquiExample.createCriteria().andStatusEqualTo(Constants.HF_LIQUIDATE_SUC)
                    .andLiquidationTimeBetween(DateUtil.getDateBegin(now),now);
            List<BsLiquidationRecord> liquidationRecords = bsLiquidationRecordMapper.selectByExample(liquiExample);
            if(CollectionUtils.isEmpty(liquidationRecords)){
                //获取当前恒丰系统中自有子账余额A
                HFBalanceDetailVO vo = null;
                B2GReqMsg_HFBank_QueryAccountLeftAmountInfo leftAmountReq = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
                leftAmountReq.setAcct_type(HFAccountTypeEnum.HF_ACC_TYPE_JSH.getHfcode());
                leftAmountReq.setAccount(Constants.HF_REQ_PLATFORM);//账户编号-01-平台
                B2GResMsg_HFBank_QueryAccountLeftAmountInfo leftAmountRes = hfbankTransportService.queryAccountLeftAmountInfo(leftAmountReq);
                Double currentJSH = 0d;
                if (ConstantUtil.BSRESCODE_SUCCESS.equals(leftAmountRes.getResCode()) && StringUtil.isNotBlank(leftAmountRes.getData())) {
                    vo = JSON.parseObject(leftAmountRes.getData(),HFBalanceDetailVO.class);
                }else {
                    throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_ERROR,",恒丰账户余额查询:" + HFAccountTypeEnum.HF_ACC_TYPE_JSH.getDescription()+"失败,"+leftAmountRes.getResMsg());
                }
                if(vo != null){
                    if(StringUtil.isNotEmpty(vo.getBalance()))
                        currentJSH = MoneyUtil.add(vo.getBalance(),StringUtil.isNotEmpty(vo.getFrozen_amount())? vo.getFrozen_amount() : "0").doubleValue();
                    else
                        throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH,",恒丰账户余额查询:" + HFAccountTypeEnum.HF_ACC_TYPE_JSH.getDescription()+"余额为0,"+leftAmountRes.getResMsg());
                }else{
                    //告警
                    specialJnlService.warn4Fail(null, "【清算回调记录清算】恒丰账户余额查询，失败：恒丰系统自有子账户",
                            "", "恒丰系统自有子账户查询失败",false);
                }

                //日终到清算完成这段时间使用的红包金额
                Double redTotal = 0d;
                try{
                    redTotal = bsRedPacketInfoMapper.getUsedSumAmount(DateUtil.getDateBegin(now),now,Constants.RED_PACKET_STATUS_USED);
                }catch (Exception e){
                    log.error("=================统计【日终到清算完成这段时间使用的红包金额】异常=====================", e);
                }

                //新增一条清算记录
                record.setLiquidationTime(new Date());
                record.setStatus(Constants.HF_LIQUIDATE_SUC);
                record.setHfJshBalance(currentJSH);
                record.setUsedRedAmount(redTotal);
                record.setCreateTime(new Date());
                record.setUpdateTime(new Date());
                bsLiquidationRecordMapper.insert(record);

                //清算完成时进行垫付金调账
                sysDailyBizService.advanceAutoAdjustAcc(record);
            }else{
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",重复清算");
            }
        }
        
    }
    
}
