package com.pinting.business.service.manage.impl;

import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.accounting.loan.service.LoanPaymentService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.service.common.DepCommonService;
import com.pinting.business.service.manage.DepTargetSendAgainService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtData;
import com.pinting.gateway.hessian.message.hfbank.model.ChargeOffReqDetail;
import com.pinting.gateway.hessian.message.hfbank.model.EstablishOrAbandonReqFundData;
import com.pinting.gateway.hessian.message.hfbank.model.EstablishOrAbandonReqRepayPlan;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DepTargetSendAgainServiceImpl implements DepTargetSendAgainService {

    private Logger logger = LoggerFactory.getLogger(DepTargetSendAgainServiceImpl.class);

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private HfbankTransportService hfbankTransportService;
	@Autowired
    private LnDepositionTargetJnlMapper depositionTargetJnlMapper;
	@Autowired
    private SpecialJnlService specialJnlService;
	@Autowired
    private DepCommonService depCommonService;
	@Autowired
    private LnDepositionTargetMapper lnDepositionTargetMapper;
	@Autowired
    private LnLoanMapper lnLoanMapper;
	@Autowired
	private LnSubAccountMapper lnSubAccountMapper;
    @Autowired
    private LnAccountJnlMapper lnAccountJnlMapper;
    @Autowired
    private DepFixedLoanPaymentService depFixedLoanPaymentService;
    @Autowired
    private LoanPaymentService loanPaymentService;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LnBindCardMapper lnBindCardMapper;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper lnPayOrdersJnlMapper;
	
	/**
	 * 标的废除重发
	 */
	@Override
	public void targetAbandon(Integer targetJnlId) {
        //根据主键查询标的流水
        LnDepositionTargetJnl targetJnl = depositionTargetJnlMapper.selectByPrimaryKey(targetJnlId);
        if(targetJnl == null || Constants.TARGET_JNL_TRANS_STATUS_SUCC.equals(targetJnl.getTransStatus())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //查询同类型成功的记录,有成功记录则不需要重新发送
        LnDepositionTargetJnlExample example = new LnDepositionTargetJnlExample();
        example.createCriteria().andProdIdEqualTo(targetJnl.getProdId())
        .andTransTypeEqualTo(targetJnl.getTransType()).andTransStatusEqualTo(Constants.TARGET_JNL_TRANS_STATUS_SUCC);
        List<LnDepositionTargetJnl> succTargetJnlList = depositionTargetJnlMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(succTargetJnlList)){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //查询标的信息
        LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByPrimaryKey(targetJnl.getProdId());
        if(depositionTarget == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //查询借款信息
        final LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(depositionTarget.getLoanId());
        if(lnLoan == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //查询资金方标记
        LnUser  lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());

        String propertyInfo = "";
        PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(lnUser.getPartnerCode());
        propertyInfo = partnerEnum.getName();

        //标的废除
        String orderNo = Util.generateOrderNo4BaoFoo(8);
        B2GReqMsg_HFBank_EstablishOrAbandon abandonReq = new B2GReqMsg_HFBank_EstablishOrAbandon();
        abandonReq.setProd_id(targetJnl.getProdId().toString());
        abandonReq.setOrder_no(orderNo);
        abandonReq.setRemark("标的废除");
        abandonReq.setFlag(Constants.ESTABLISH_OR_ABANDON_FLAG_ABANDON);
        B2GResMsg_HFBank_EstablishOrAbandon abandonRes = new B2GResMsg_HFBank_EstablishOrAbandon();
        try {
            abandonRes = hfbankTransportService.establishOrAbandon(abandonReq);
        } catch (Exception e) {
             logger.error(propertyInfo+"标的废除重发请求异常：{}", e);
             abandonRes.setResCode(ConstantUtil.BSRESCODE_FAIL);
             abandonRes.setResMsg("通讯失败，置为失败");
        }
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(abandonRes.getResCode())
                && Constants.HF_ORDER_STATUS_SUCC.equals(abandonRes.getData().getOrder_status())) {

            // 标的废除重发成功，债权回退
            depFixedLoanPaymentService.backLoanDebtFinancing(lnLoan, orderNo, depositionTarget, DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED_RESEND);

            notifyLoan2Partner(partnerEnum, lnLoan, "标的废除重发");
        }else{
            //新增一条标的成立失败流水
            depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_CANCELLED,DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED_RESEND,depositionTarget,lnLoan, orderNo);
            //告警
            specialJnlService.warn4Fail(targetJnl.getAmount(), propertyInfo+"标的废除重发失败depositionTargetId：" + targetJnl.getProdId(),
                    targetJnl.getProdId().toString(), "【"+propertyInfo+"标的废除重发】",false);

            throw new PTMessageException(PTMessageEnum.DEP_TARGET_ABANDON_ERROR);
        }
	}

	/**
     * 放款通知合作方分发
     * @param partnerEnum
     * @param lnLoan
     * @param errorMsg
     */
    private void  notifyLoan2Partner(PartnerEnum partnerEnum, LnLoan lnLoan, String errorMsg){
        switch (partnerEnum){
            case YUN_DAI_SELF :
            	depFixedLoanPaymentService.notifyLoan2YunDai(lnLoan, errorMsg, null);
                break;
            case SEVEN_DAI_SELF :
            	depFixedLoanPaymentService.noticeLoan2Seven(lnLoan, errorMsg, null);
                break;
            case ZSD :
            	depFixedLoanPaymentService.noticeLoan2Dsd(lnLoan, errorMsg);
                break;
        }
    }
    
	@Override
	public void targetEstablish(Integer targetJnlId) {
		//根据主键查询标的流水
		LnDepositionTargetJnl targetJnl = depositionTargetJnlMapper.selectByPrimaryKey(targetJnlId);
		if(targetJnl == null || Constants.TARGET_JNL_TRANS_STATUS_SUCC.equals(targetJnl.getTransStatus())){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		//查询同类型成功的记录,有成功记录则不需要重新发送
		LnDepositionTargetJnlExample targetJnlExample = new LnDepositionTargetJnlExample();
		targetJnlExample.createCriteria().andProdIdEqualTo(targetJnl.getProdId())
		.andTransTypeEqualTo(targetJnl.getTransType()).andTransStatusEqualTo(Constants.TARGET_JNL_TRANS_STATUS_SUCC);
		List<LnDepositionTargetJnl> succTargetJnlList = depositionTargetJnlMapper.selectByExample(targetJnlExample);
		if(CollectionUtils.isNotEmpty(succTargetJnlList)){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		//查询标的信息
		LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByPrimaryKey(targetJnl.getProdId());
		if(depositionTarget == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		
		//查询借款信息
		final LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(depositionTarget.getLoanId());
		if(lnLoan == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		
		//查询资金方标记
		LnUser  lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
		
        String propertyInfo = "";
        PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(lnUser.getPartnerCode());
        propertyInfo = partnerEnum.getName();
		
		//标的成立请求
        B2GReqMsg_HFBank_EstablishOrAbandon establish = new B2GReqMsg_HFBank_EstablishOrAbandon();
        establish.setProd_id(targetJnl.getProdId().toString());
        establish.setFlag(Constants.ESTABLISH_OR_ABANDON_FLAG_ABLISH);
        String estabishOrderNo = Util.generateOrderNo4BaoFoo(8);
        establish.setOrder_no(estabishOrderNo);
        List<EstablishOrAbandonReqFundData> fundDatas = new ArrayList<>();
        EstablishOrAbandonReqFundData fundData = new EstablishOrAbandonReqFundData();
        fundData.setPayout_plat_type(Constants.PAYOUT_PLAT_TYPE_FEE);
        fundData.setPayout_amt(lnLoan.getHeadFee()!= null ? lnLoan.getHeadFee().toString():"0");
        fundDatas.add(fundData);
        establish.setFunddata(fundDatas);
        List<EstablishOrAbandonReqRepayPlan> repayPlans = new ArrayList<>();
        EstablishOrAbandonReqRepayPlan repayPlan = new EstablishOrAbandonReqRepayPlan();
        
        Double repay_amt = MoneyUtil.add(lnLoan.getApproveAmount(),
        			MoneyUtil.divide(
            			MoneyUtil.multiply(90,MoneyUtil.multiply(lnLoan.getApproveAmount(), lnLoan.getAgreementRate()).doubleValue()).doubleValue()
            			,36500).doubleValue()
            		).doubleValue();
        repayPlan.setRepay_amt(MoneyUtil.defaultRound(repay_amt).toString());
        repayPlan.setRepay_fee("0");
        repayPlan.setRepay_num("1");

        repayPlan.setRepay_date(DateUtils.addDays(lnLoan.getApplyTime(),89));
        repayPlans.add(repayPlan);
        establish.setRepay_plan_list(repayPlans);
        B2GResMsg_HFBank_EstablishOrAbandon establishRes = new B2GResMsg_HFBank_EstablishOrAbandon();
        try {
        	establishRes = hfbankTransportService.establishOrAbandon(establish);
		} catch (Exception e) {
            logger.error(propertyInfo+"标的成立重发失败请求异常：{}", e);
            establishRes.setResCode(ConstantUtil.BSRESCODE_FAIL);
            establishRes.setResMsg("通讯失败，置为失败");
		}
        
        if(establishRes.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)){
            //修改标的成立为SET_UP，新增一条标的成立成功流水
            depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_SET_UP,DepTargetEnum.DEP_TARGET_OPERATE_SET_UP_RESEND,depositionTarget,lnLoan,estabishOrderNo);

            //融资人余额子账户+
            LnSubAccountExample example = new LnSubAccountExample();
            example.createCriteria().andAccountTypeEqualTo(Constants.LOAN_ACT_TYPE_DEP_JSH).andLnUserIdEqualTo(lnLoan.getLnUserId());
            List<LnSubAccount> lnSubAccounts = lnSubAccountMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(lnSubAccounts)){
                throw new PTMessageException(PTMessageEnum.SUB_ACCOUNT_NO_EXIT,",融资人余额子账户不存在");
            }
            final LnSubAccount lnSubAccount = lnSubAccounts.get(0);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    LnSubAccount lnActLock = lnSubAccountMapper.selectByPrimaryKey4Lock(lnSubAccount.getId());
                    LnSubAccount tempLnAct = new LnSubAccount();
                    tempLnAct.setId(lnActLock.getId());
                    tempLnAct.setBalance(MoneyUtil.add(lnActLock.getBalance(), lnLoan.getApproveAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempLnAct.setAvailableBalance(MoneyUtil.add(lnActLock.getAvailableBalance(), lnLoan.getApproveAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempLnAct.setCanWithdraw(MoneyUtil.add(lnActLock.getCanWithdraw(), lnLoan.getApproveAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
                    tempLnAct.setUpdateTime(new Date());
                    lnSubAccountMapper.updateByPrimaryKeySelective(tempLnAct);
                    //借款人账户记账
                    LnAccountJnl lnAccountJnl = new LnAccountJnl();
                    lnAccountJnl.setTransTime(new Date());
                    lnAccountJnl.setTransCode(Constants.LN_DEP_JSH_ADD);
                    lnAccountJnl.setTransName("借款到余额");
                    lnAccountJnl.setTransAmount(lnLoan.getApproveAmount());
                    lnAccountJnl.setSysTime(new Date());
                    lnAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                    lnAccountJnl.setUserId1(lnActLock.getLnUserId());
                    lnAccountJnl.setSubAccountId1(lnActLock.getId());
                    lnAccountJnl.setBeforeBalance1(lnActLock.getBalance());
                    lnAccountJnl.setAfterBalance1(tempLnAct.getBalance());
                    lnAccountJnl.setBeforeAvialableBalance1(lnActLock.getAvailableBalance());
                    lnAccountJnl.setAfterAvialableBalance1(tempLnAct.getAvailableBalance());
                    lnAccountJnl.setBeforeFreezeBalance1(lnActLock.getFreezeBalance());
                    lnAccountJnl.setAfterFreezeBalance1(lnActLock.getFreezeBalance());
                    lnAccountJnl.setFee(0.0);
                    lnAccountJnlMapper.insertSelective(lnAccountJnl);
                }
            });

            //标的成立成功调用标的出账
            depFixedLoanPaymentService.payProcess(lnLoan);
        }else{
            //新增一条标的成立失败流水
            depCommonService.updateTargetStatus(false,Constants.DEP_TARGET_SET_UP,DepTargetEnum.DEP_TARGET_OPERATE_SET_UP_RESEND,depositionTarget,lnLoan, estabishOrderNo);
            specialJnlService.warn4Fail(lnLoan.getApproveAmount(), propertyInfo+"标的成立重发请求失败"+establishRes.getResMsg(), lnLoan.getPayOrderNo(), "【"+propertyInfo+"标的成立重发】", false);
            throw new PTMessageException(PTMessageEnum.DEP_TARGET_ESTABLISH_ERROR);
        }
	}

    @Override
    public void targetChargeOff(Integer targetJnlId) {
        //根据主键查询标的流水
        LnDepositionTargetJnl targetJnl = depositionTargetJnlMapper.selectByPrimaryKey(targetJnlId);
        if(targetJnl == null || Constants.TARGET_JNL_TRANS_STATUS_SUCC.equals(targetJnl.getTransStatus())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //查询同类型成功的记录,有成功记录则不需要重新发送
        if (Constants.TARGET_JNL_TRANS_TYPE_PROD_CHARGE_OFF.equals(targetJnl.getTransType())) {        	
        	LnDepositionTargetJnlExample targetJnlExample = new LnDepositionTargetJnlExample();
            targetJnlExample.createCriteria().andProdIdEqualTo(targetJnl.getProdId())
                    .andTransTypeEqualTo(targetJnl.getTransType()).andTransStatusEqualTo(Constants.TARGET_JNL_TRANS_STATUS_SUCC);
            List<LnDepositionTargetJnl> succTargetJnlList = depositionTargetJnlMapper.selectByExample(targetJnlExample);
            if(CollectionUtils.isNotEmpty(succTargetJnlList)){
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), "已有成功记录，无需重新发送");
            }
        }
        //查询标的信息
        LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByPrimaryKey(targetJnl.getProdId());
        if(depositionTarget == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //查询借款信息
        final LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(depositionTarget.getLoanId());
        if(lnLoan == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //查询借款对应的订单表信息（查找资金方标记）
        LnPayOrdersExample ordersExample = new LnPayOrdersExample();
        ordersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo());
        List<LnPayOrders> orders = lnPayOrdersMapper.selectByExample(ordersExample);
        LnPayOrders order = orders.get(0);

        
        String propertyInfo = "";
        PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(order.getPartnerCode());
        propertyInfo = partnerEnum.getName();
        
        
        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());

        //新增一条ln_pay_orders记录
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        //出账金额=标的金额-手续费
        lnPayOrders.setAmount(MoneyUtil.subtract(lnLoan.getApproveAmount() == null ? 0d: lnLoan.getApproveAmount(), lnLoan.getHeadFee() == null ? 0d : lnLoan.getHeadFee()).doubleValue());
        LnBindCardExample example = new LnBindCardExample();
        example.createCriteria().andLnUserIdEqualTo(lnLoan.getLnUserId()).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
        example.setOrderByClause("update_time desc");
        List<LnBindCard> lnBindCards = lnBindCardMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(lnBindCards)){
            throw new PTMessageException(PTMessageEnum.USER_NOT_BIND_CARD);
        }
        LnBindCard lnBindCard = lnBindCards.get(0);
        lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
        BsCardBin bsCardBin = bankCardService.queryBankBin(lnBindCard.getBankCard());
        BsBank bank = bankCardService.findBankById(bsCardBin.getBankId());
        lnPayOrders.setBankId(bsCardBin.getBankId());
        lnPayOrders.setBankName(lnBindCard.getBankName());
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrders.setIdCard(lnBindCard.getIdCard());
        lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
        lnPayOrders.setMobile(lnBindCard.getMobile());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(Util.generateOrderNo4BaoFoo(8));//每次标的重新出账生成一个新订单号
        lnPayOrders.setPartnerCode(order.getPartnerCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_LOAN.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(lnBindCard.getUserName());
        lnPayOrdersMapper.insertSelective(lnPayOrders);

        //新增一条ln_pay_orders_jnl的INIT记录
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnLoan.getApproveAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        LnLoan loanTemp = new LnLoan();
        loanTemp.setId(lnLoan.getId());
        loanTemp.setUpdateTime(new Date());
        loanTemp.setPayOrderNo(lnPayOrders.getOrderNo());
        lnLoanMapper.updateByPrimaryKeySelective(loanTemp);

        //发起标的出账请求
        LnDepositionTarget lnDepositionTarget = lnDepositionTargetMapper.selectByLoanId(lnLoan.getId());
        B2GReqMsg_HFBank_ChargeOff chargeOff = new B2GReqMsg_HFBank_ChargeOff();
        chargeOff.setProd_id(lnDepositionTarget.getId().toString());
        chargeOff.setPay_code(Constants.HF_PAY_CODE_HFBANK_OUT);    //行内通道出金编码
//        chargeOff.setPlat_no();   商户平台在资金账户管理平台注册的平台编号
        //出账订单号和lnPayOrders订单号保持一致
        chargeOff.setOrder_no(lnPayOrders.getOrderNo());
        chargeOff.setPartner_trans_date(new Date());
        chargeOff.setPartner_trans_time(new Date());
        List<ChargeOffReqDetail> chargeOffDetails = new ArrayList<ChargeOffReqDetail>();
        ChargeOffReqDetail chargeOffDetail = new ChargeOffReqDetail();
        chargeOffDetail.setIs_advance(Constants.HF_WITHDRAW_IS_ADVANCE_YES); //垫付
        chargeOffDetail.setPlatcust(lnUser.getHfUserId());
        chargeOffDetail.setOut_amt(lnPayOrders.getAmount().toString());//出账金额=标的金额-手续费
        chargeOffDetail.setOpen_branch(lnBindCard.getBankCode());
        chargeOffDetail.setWithdraw_account(lnBindCard.getBankCard());
        chargeOffDetail.setPayee_name(lnBindCard.getUserName());
        chargeOffDetail.setClient_property(Constants.HF_CLIENT_PROPERTY_PERSON);//公私标示(0-个人 1-公司 )
        chargeOffDetail.setTran_type(Constants.HF_WITHDRAW_TRAN_TYPE_PAY_REAL);
        chargeOffDetail.setBank_code(bank.getUnionBankId());
        chargeOffDetail.setBank_name(lnBindCard.getBankName());
        chargeOffDetails.add(chargeOffDetail);
        chargeOff.setCharge_off_list(chargeOffDetails);
        B2GResMsg_HFBank_ChargeOff res = null;
        logger.info(propertyInfo+"标的出账重发请求订单号=["+lnPayOrders.getOrderNo()+"]产品Id=["+lnDepositionTarget.getId().toString()+"]联行行号=["+chargeOffDetail.getBank_code()+"]");
        try {
            res = hfbankTransportService.chargeOff(chargeOff);
        } catch (Exception e) {
            logger.error(propertyInfo+"标的出账重发通讯失败：{}", e);
            specialJnlService.warn4Fail(lnPayOrders.getAmount(), propertyInfo+"标的出账重发请求异常", lnPayOrders.getOrderNo(), "【"+propertyInfo+"标的出账重发】", false);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), propertyInfo+"标的出账重发通讯失败");
        }
        if(Constants.DEP_RECODE_SUCCESS.equals(res.getRecode()) && res.getData() != null &&
                Constants.HF_ORDER_STATUS_SUCC.equals(res.getData().getOrder_status())){
            //改借款状态为放款中
            loanTemp.setId(lnLoan.getId());
            loanTemp.setUpdateTime(new Date());
            loanTemp.setStatus(LoanStatus.LOAN_STATUS_PAYING.getCode());
            lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
            depCommonService.updateTargetStatus(true,Constants.DEP_TARGET_SET_UP,DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE,lnDepositionTarget,lnLoan,loanTemp.getPayOrderNo());
        }else{
            G2BReqMsg_HFBank_OutOfAccount failReq = new G2BReqMsg_HFBank_OutOfAccount();
            failReq.setOrder_no(loanTemp.getPayOrderNo());
            failReq.setOrder_status(Constants.HF_OUT_OF_ACCOUNT_FAIL);
            failReq.setError_no(res.getRecode());
            failReq.setError_info(res.getRecode() + (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
            if(partnerEnum == null) {
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "未找到合作方编码");
            }
            if(PartnerEnum.ZAN.getCode().equals(order.getPartnerCode())) {
                loanPaymentService.outOfAccountResultAcceptZan(failReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE);
            } else {
                depFixedLoanPaymentService.outOfAccountResultAccept(failReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE, partnerEnum);
            }
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), failReq.getError_info() == null ? propertyInfo+"标的出账重发请求失败" : propertyInfo + "标的出账重发请求失败" + (StringUtil.isBlank(failReq.getError_info()) ? "" : "："+ failReq.getError_info()));
        }
    }
    
    @Override
	public void targetChargeOffCallBack(Integer targetJnlId) {
    	 //根据主键查询标的流水
        LnDepositionTargetJnl targetJnl = depositionTargetJnlMapper.selectByPrimaryKey(targetJnlId);
        if(targetJnl == null || Constants.TARGET_JNL_TRANS_STATUS_SUCC.equals(targetJnl.getTransStatus())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        //查询同类型成功的记录,有成功记录则不需要重新发送
        LnDepositionTargetJnlExample targetJnlExample = new LnDepositionTargetJnlExample();
        targetJnlExample.createCriteria().andProdIdEqualTo(targetJnl.getProdId())
                .andTransTypeEqualTo(targetJnl.getTransType()).andTransStatusEqualTo(Constants.TARGET_JNL_TRANS_STATUS_SUCC);
        List<LnDepositionTargetJnl> succTargetJnlList = depositionTargetJnlMapper.selectByExample(targetJnlExample);
        if(CollectionUtils.isNotEmpty(succTargetJnlList)){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), "已有成功记录，无需重新发送");
        }
        //查询标的信息
        LnDepositionTarget depositionTarget = lnDepositionTargetMapper.selectByPrimaryKey(targetJnl.getProdId());
        if(depositionTarget == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //查询借款信息
        final LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(depositionTarget.getLoanId());
        if(lnLoan == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        //查询处理中订单，存在则不允许继续操作
        LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
        lnPayOrdersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo())
                .andStatusEqualTo(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
        List<LnPayOrders> payingOrderList = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
        if(CollectionUtils.isNotEmpty(payingOrderList)) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR.getCode(), "存在处理中订单，不允许继续操作");
        }

        //查询借款对应的订单表信息（查找资金方标记）
        LnPayOrdersExample ordersExample = new LnPayOrdersExample();
        ordersExample.createCriteria().andOrderNoEqualTo(lnLoan.getPayOrderNo());
        List<LnPayOrders> orders = lnPayOrdersMapper.selectByExample(ordersExample);
        LnPayOrders order = orders.get(0);

        
        String propertyInfo = "";
        PartnerEnum partnerEnum = PartnerEnum.getEnumByCode(order.getPartnerCode());
        propertyInfo = partnerEnum.getName();
        
        
        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());

        //新增一条ln_pay_orders记录
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        //出账金额=标的金额-手续费
        lnPayOrders.setAmount(MoneyUtil.subtract(lnLoan.getApproveAmount() == null ? 0d: lnLoan.getApproveAmount(), lnLoan.getHeadFee() == null ? 0d : lnLoan.getHeadFee()).doubleValue());
        LnBindCardExample example = new LnBindCardExample();
        example.createCriteria().andLnUserIdEqualTo(lnLoan.getLnUserId()).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
        example.setOrderByClause("update_time desc");
        List<LnBindCard> lnBindCards = lnBindCardMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(lnBindCards)){
            throw new PTMessageException(PTMessageEnum.USER_NOT_BIND_CARD);
        }
        LnBindCard lnBindCard = lnBindCards.get(0);
        lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
        BsCardBin bsCardBin = bankCardService.queryBankBin(lnBindCard.getBankCard());
        BsBank bank = bankCardService.findBankById(bsCardBin.getBankId());
        lnPayOrders.setBankId(bsCardBin.getBankId());
        lnPayOrders.setBankName(lnBindCard.getBankName());
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrders.setIdCard(lnBindCard.getIdCard());
        lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
        lnPayOrders.setMobile(lnBindCard.getMobile());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(Util.generateOrderNo4BaoFoo(8));//每次标的重新出账生成一个新订单号
        lnPayOrders.setPartnerCode(order.getPartnerCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_DF_2_BORROWER.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(lnBindCard.getUserName());
        lnPayOrders.setNote("loan_id:" + lnLoan.getId());
        lnPayOrdersMapper.insertSelective(lnPayOrders);

        //新增一条ln_pay_orders_jnl的INIT记录
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnLoan.getApproveAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        LnLoan loanTemp = new LnLoan();
        loanTemp.setId(lnLoan.getId());
        loanTemp.setUpdateTime(new Date());
        loanTemp.setPayOrderNo(lnPayOrders.getOrderNo());
        lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
        
        B2GResMsg_HFBank_UserBatchWithdraw withdrawRes = new B2GResMsg_HFBank_UserBatchWithdraw();
        B2GReqMsg_HFBank_UserBatchWithdraw withdrawReq = new B2GReqMsg_HFBank_UserBatchWithdraw();
        withdrawReq.setClient_property(Constants.HF_CLIENT_PROPERTY_PERSON);
        withdrawReq.setOrder_no("DF_WITHDRAW"+lnPayOrders.getOrderNo());
        withdrawReq.setPartner_trans_date(lnPayOrders.getCreateTime());
        withdrawReq.setPartner_trans_time(lnPayOrders.getCreateTime());
        List<BatchWithdrawExtData> data = new ArrayList<>();
        BatchWithdrawExtData withdrawExtData = new BatchWithdrawExtData();
        withdrawExtData.setDetail_no(lnPayOrders.getOrderNo());
        withdrawExtData.setPlatcust(lnUser.getHfUserId());
        withdrawExtData.setAmt(lnPayOrders.getAmount());
        withdrawExtData.setIs_advance(Constants.HF_WITHDRAW_IS_ADVANCE_NO);
        withdrawExtData.setPay_code(Constants.HF_PAY_CODE_HFBANK_OUT);//行内通道出金编码
        withdrawExtData.setFee_amt(lnLoan.getHeadFee() == null ? 0d : lnLoan.getHeadFee());
        //withdrawExtData.setType("0"); //文档2.0无此字段
        withdrawExtData.setWithdraw_type(Constants.HFBANK_WITHDRAW_TYPE_FINANCE);
        withdrawExtData.setRemark("借款用户代付提现");
        withdrawExtData.setTran_type(Constants.HF_WITHDRAW_TRAN_TYPE_PAY_REAL); // 跨行
        withdrawExtData.setBank_code(bank.getUnionBankId());
        withdrawExtData.setBank_name(lnPayOrders.getBankName());
        data.add(withdrawExtData);
        withdrawReq.setData(data);
        withdrawReq.setTotal_num(1);//批量提现接口用于会员提现
        try {
        	withdrawRes = hfbankTransportService.userBatchWithdraw(withdrawReq);
        } catch (Exception e){
            logger.error("借款代付提现申请通讯失败：{}", e.getMessage());
            withdrawRes.setResCode(ConstantUtil.BSRESCODE_FAIL);
            withdrawRes.setResMsg("借款代付提现通讯失败");
        }
        
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(withdrawRes.getResCode()) && "1".equals(withdrawRes.getSuccess_num())) {
        	//修改借款状态为放款中
            loanTemp.setId(lnLoan.getId());
            loanTemp.setUpdateTime(new Date());
            loanTemp.setStatus(LoanStatus.LOAN_STATUS_PAYING.getCode());
            lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
        } else if(Constants.GATEWAY_PAYING_RES_CODE.equals(withdrawRes.getResCode())) {
        	//通讯超时,设置为处理中.....
        	loanTemp.setId(lnLoan.getId());
            loanTemp.setUpdateTime(new Date());
            loanTemp.setStatus(LoanStatus.LOAN_STATUS_PAYING.getCode());
            lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
        } else{
        	G2BReqMsg_HFBank_OutOfAccount failReq = new G2BReqMsg_HFBank_OutOfAccount();
            failReq.setOrder_no(loanTemp.getPayOrderNo());
            failReq.setOrder_status(Constants.HF_OUT_OF_ACCOUNT_FAIL);
            String errorInfo = "";
            if(StringUtils.isNotBlank(withdrawRes.getRemsg())) {
                errorInfo = withdrawRes.getRecode() + (StringUtil.isEmpty(withdrawRes.getRemsg())?"":withdrawRes.getRemsg());
            } else {
                errorInfo = withdrawRes.getResCode() + (StringUtil.isEmpty(withdrawRes.getResMsg())?"":withdrawRes.getResMsg());
            }
            failReq.setError_info(errorInfo);
            failReq.setError_no(withdrawRes.getRecode());
            if(partnerEnum == null) {
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "未找到合作方编码");
            }
            if(PartnerEnum.ZAN.getCode().equals(order.getPartnerCode())) {
                loanPaymentService.outOfAccountResultAcceptZan(failReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF);
            } else {
                depFixedLoanPaymentService.outOfAccountResultAccept(failReq, DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF, partnerEnum);
            }
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), failReq.getError_info() == null ? propertyInfo+"标的出账回调重发请求失败" : propertyInfo+"标的出账回调重发请求失败："+ failReq.getError_info());
        }
	}

    @Override
    public Map<String, Object> queryDepositionTargetJnl(Integer targetJnlId, Integer pageNum, Integer numPerPage) {
        Integer start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        int count = depositionTargetJnlMapper.countByPage(targetJnlId);
        Map<String, Object> result = new HashMap<>();

        if(count > 0) {
            List<LnDepositionTarget> targets = depositionTargetJnlMapper.selectByPage(targetJnlId, start, numPerPage);
            result.put("list", targets);
            result.put("count", count);
        } else {
            result.put("count", count);
            result.put("list", new ArrayList<LnDepositionTarget>());
        }
        return result;
    }

    @Override
    public LnDepositionTargetJnl queryById(Integer id) {
        return depositionTargetJnlMapper.selectByPrimaryKey(id);
    }
    
    
    
    public static void main(String[] args) {
        
        String propertyInfo = "";
        PartnerEnum partnerEnum = PartnerEnum.YUN_DAI_SELF.getEnumByCode("YUN_DAI_SELF");
        propertyInfo = partnerEnum.getName();
        System.out.println(propertyInfo);
	}

}
