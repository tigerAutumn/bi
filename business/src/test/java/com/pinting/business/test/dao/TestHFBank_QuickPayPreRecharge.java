package com.pinting.business.test.dao;

import com.pinting.business.test.TestBase;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description: 恒丰银行测试类
 */
public class TestHFBank_QuickPayPreRecharge extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank_QuickPayPreRecharge.class);

    @Autowired 
    private HfbankTransportService hfbankTransportService;

    /*快捷充值请求*/
    @Test
    public void UserQuickPayPreRechargeTest() {
        B2GReqMsg_HFBank_UserQuickPayPreRecharge req = new B2GReqMsg_HFBank_UserQuickPayPreRecharge();
        
        req.setOrder_no("QUICK_CZ_20170419001");
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setPlatcust("2017041813372262011104");
        req.setName("Gemma");
        req.setCard_no("6222021203029387056");
        req.setCard_type("");
        req.setCurrency_code("");
        req.setId_type("1");
        req.setId_code("336327198806253496");
        req.setMobile("15168487916");
        req.setEmail("");
        req.setAmt(10000d);
        req.setPay_code(Constants.CHANNEL_HFBANK);
        req.setAccount_type("");	//投融资账户类型 1-投资账户  2-融资账户 
        req.setNotify_url(null);
        req.setRemark("");
        B2GResMsg_HFBank_UserQuickPayPreRecharge res = hfbankTransportService.userQuickPayPreRecharge(req);
        logger.info("恒丰银行测试快捷充值请求：{}", JSONObject.fromObject(res));
    }

    /*快捷充值确认*/
//    @Test
//    public void userQuickPayConfirmRechargeTest() {
//        B2GReqMsg_HFBank_UserQuickPayConfirmRecharge req = new B2GReqMsg_HFBank_UserQuickPayConfirmRecharge();
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setIdentifying_code("1234");
//        req.setOrigin_order_no(UUID.randomUUID().toString());
//        req.setPay_code("BAOFOO");
//        req.setRemark("");
//        B2GResMsg_HFBank_UserQuickPayConfirmRecharge res = hfbankTransportService.userQuickPayConfirmRecharge(req);
//        logger.info("恒丰银行测试快捷充值确认：{}", JSONObject.fromObject(res));
//    }

    /*借款人扣款还款（代扣）*/
//    @Test
//    public void borrowCutRepayDKTest() {
//        B2GReqMsg_HFBank_BorrowCutRepayDK req = new B2GReqMsg_HFBank_BorrowCutRepayDK();
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setAmt(10000d);
//        req.setNotify_url(UUID.randomUUID().toString());
//        req.setRemark("");
//        List<BorrowCutRepayPlatcust> data = new ArrayList<BorrowCutRepayPlatcust>();
//        BorrowCutRepayPlatcust borrowCutRepayPlatcust = new BorrowCutRepayPlatcust();
//        borrowCutRepayPlatcust.setPlatcust("");
//        borrowCutRepayPlatcust.setAmt(10000d);
//        data.add(borrowCutRepayPlatcust);
//        req.setPlatcustList(data);
//        B2GResMsg_HFBank_BorrowCutRepayDK res = hfbankTransportService.borrowCutRepayDK(req);
//        logger.info("恒丰银行测试借款人扣款还款（代扣）：{}", JSONObject.fromObject(res));
//    }

    /*批量提现*/
//    @Test
//    public void userBatchWithdrawTest() {
//        B2GReqMsg_HFBank_UserBatchWithdraw req = new B2GReqMsg_HFBank_UserBatchWithdraw();
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        List<BatchWithdrawExtData> data = new ArrayList<BatchWithdrawExtData>();
//        BatchWithdrawExtData batchWithdrawExtData = new BatchWithdrawExtData();
//        batchWithdrawExtData.setDetail_no(UUID.randomUUID().toString());
//        batchWithdrawExtData.setPlatcust(UUID.randomUUID().toString());
//        batchWithdrawExtData.setAmt(10000d);
//        batchWithdrawExtData.setIs_advance("");
//        batchWithdrawExtData.setPay_code("BAOFOO");
//        batchWithdrawExtData.setFee_amt(100d);
//        batchWithdrawExtData.setType("");
//        batchWithdrawExtData.setWithdraw_type("");
//        batchWithdrawExtData.setBank_id("");
//        batchWithdrawExtData.setNotify_url("");
//        batchWithdrawExtData.setRemark("");
//        data.add(batchWithdrawExtData);
//        req.setData(data);
//        req.setClient_property("1");
//        req.setCity_code("");
//
//        B2GResMsg_HFBank_UserBatchWithdraw res = hfbankTransportService.userBatchWithdraw(req);
//        logger.info("恒丰银行测试批量提现：{}", JSONObject.fromObject(res));
//    }

    /*订单状态查询*/
//    @Test
//    public void queryOrderTest() {
//        B2GReqMsg_HFBank_QueryOrder req = new B2GReqMsg_HFBank_QueryOrder();
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setQuery_order_no(UUID.randomUUID().toString());
//        B2GResMsg_HFBank_QueryOrder res = hfbankTransportService.queryOrder(req);
//        logger.info("恒丰银行测试订单状态查询：{}", JSONObject.fromObject(res));
//    }

    /*资金余额查询*/
//    @Test
//    public void queryAccountInfoTest() {
//        B2GReqMsg_HFBank_QueryAccountInfo req = new B2GReqMsg_HFBank_QueryAccountInfo();
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setAccount(UUID.randomUUID().toString());
//        B2GResMsg_HFBank_QueryAccountInfo res = hfbankTransportService.queryAccountInfo(req);
//        logger.info("恒丰银行测试资金余额查询：{}", JSONObject.fromObject(res));
//    }

    /*账户余额明细查询*/
//    @Test
//    public void queryAccountLeftAmountInfoTest() {
//        B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
//        String bindOrderNo=Util.generateOrderNo4BaoFoo(8);
//        req.setAccount("01");
////        req.setAcct_type("1");
////        req.setFund_type("01");
//        B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
//        logger.info("恒丰银行测试账户余额明细查询：{}", JSONObject.fromObject(res));
//    }

    /*标的账户余额查询*/
//    @Test
//    public void queryProductBalanceTest() {
//        B2GReqMsg_HFBank_QueryProductBalance req = new B2GReqMsg_HFBank_QueryProductBalance();
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setProd_id(UUID.randomUUID().toString());
//        req.setType("");
//
//        B2GResMsg_HFBank_QueryProductBalance res = hfbankTransportService.queryProductBalance(req);
//        logger.info("恒丰银行测试标的账户余额查询：{}", JSONObject.fromObject(res));
//    }



    /*标的发布test*/
//    @Test
//    public void publishTest() {
//    	B2GReqMsg_HFBank_Publish req = new B2GReqMsg_HFBank_Publish();
//    	req.setOrder_no(UUID.randomUUID().toString());
//	    req.setOrder_no("YED_LIANTIAO_CS_1");
//	    req.setPartner_trans_date(new Date());
//	    req.setPartner_trans_time(new Date());
//	    req.setProd_id("BGW_CS_PRD_1");
//	    req.setProd_name("BGW测试环境1号产品");
//	    req.setProd_type("0");
//	    req.setTotal_limit(1000.12d);
//	    req.setValue_type("3");		//0满额起息1成立起息2投标起息 3 成立审核后起息
//	    req.setCreate_type("1");	//1-成立日成立
//	    req.setSell_date(null);
//	    req.setValue_date(null);	//起息日如起息方式为成立起息，为必选项 (YYYY-MM-DD HH:mm:ss)
//	    req.setExpire_date(null);
//	    req.setCycle(3);	//
//	    req.setCycle_unit("3");	//周期单位  1日 2周 3月 4季 5年
//	    req.setIst_year(0.12d);	//12%
//	    req.setRepay_type("0");	//还款方式  0-一次性还款  1-分期还款 
//	    
//	    List<PublishFinancingInfo> list = new ArrayList<PublishFinancingInfo>();
//	    PublishFinancingInfo publishFinancingInfo = new PublishFinancingInfo();
//	    publishFinancingInfo.setCust_no("2001992719cs1");
//	    
//	    publishFinancingInfo.setReg_date(new Date());	//DateUtil.formatDateTime(obj.getReg_time(), "yyyy-MM-dd")
//	    publishFinancingInfo.setReg_time(new Date());	//DateUtil.formatDateTime(obj.getReg_time(), "HH:mm:ss")
//	    publishFinancingInfo.setFinanc_int("0.10");
//	    publishFinancingInfo.setFinanc_purpose("lvyouxiaofei");
//	    publishFinancingInfo.setUse_date(new Date());
//	    publishFinancingInfo.setOpen_branch("icbc");
//	    publishFinancingInfo.setWithdraw_account("6228480321031877111");
//	    publishFinancingInfo.setAccount_type("1");
//	    publishFinancingInfo.setPayee_name("yangende");
//	    publishFinancingInfo.setFinanc_amt(1000.11d);
//	    list.add(publishFinancingInfo);
//	    req.setFinancing_info_list(list);
//	    
//	    req.setRisk_lvl(null);
//	    req.setProd_info(null);
//	    req.setBuyer_num_limit(100);	//可不填
//	    req.setChargeOff_auto("1");		//出账标示： 0、成标后自动出账至借款人融资账户  1、调用成标出账接口，出账至标的对应收款账户
//	    req.setOverLimit("1");			//超额限制 0-不限制  1-限制
//	    req.setOver_total_limit(1000.12d);
//	    req.setRemark("受托报备测试");
//	    List<CompensationInfo> compInfoList = new ArrayList<CompensationInfo>();
//	    CompensationInfo compensationInfo =new CompensationInfo();
//	    compensationInfo.setPlatcust("111992");	//代偿账户编号 
//	    compensationInfo.setType("0");			//0-代偿还款 1-委托还款 
//	    compInfoList.add(compensationInfo);
//	    req.setCompensation_info_list(compInfoList);
//	    
//	    
//	    B2GResMsg_HFBank_Publish res = hfbankTransportService.publish(req);
//	    logger.info("恒丰银行测试标的发布：{}", JSONObject.fromObject(res));
//    }

    /*标的成废*/
//    @Test
//    public void establishOrAbandonTest() {
//        B2GReqMsg_HFBank_EstablishOrAbandon req = new B2GReqMsg_HFBank_EstablishOrAbandon();
//        req.setProd_id("1218");
//        req.setFlag("3");
//        List<EstablishOrAbandonReqFundData> list = new ArrayList<EstablishOrAbandonReqFundData>();
//        EstablishOrAbandonReqFundData establishOrAbandonReqFundData = new EstablishOrAbandonReqFundData();
//        establishOrAbandonReqFundData.setPayout_plat_type("01");
//        establishOrAbandonReqFundData.setPayout_amt("100");
//        list.add(establishOrAbandonReqFundData);
//        req.setFunddata(list);
//
//        List<EstablishOrAbandonReqRepayPlan> repayList = new ArrayList<EstablishOrAbandonReqRepayPlan>();
//        EstablishOrAbandonReqRepayPlan establishOrAbandonReqRepayPlan = new EstablishOrAbandonReqRepayPlan();
//        establishOrAbandonReqRepayPlan.setRepay_amt("1000");
//        establishOrAbandonReqRepayPlan.setRepay_fee("0");
//        establishOrAbandonReqRepayPlan.setRepay_num("1");
//        establishOrAbandonReqRepayPlan.setRepay_date(new Date());
//        repayList.add(establishOrAbandonReqRepayPlan);
//        req.setRepay_plan_list(repayList);
//
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_EstablishOrAbandon res = hfbankTransportService.establishOrAbandon(req);
//        logger.info("恒丰银行测试标的成废：{}", JSONObject.fromObject(res));
//    }

    /*批量投标*/
//    @Test
//    public void batchExtBuyTest() {
//        B2GReqMsg_HFBank_BatchExtBuy req = new B2GReqMsg_HFBank_BatchExtBuy();
//        req.setTotal_num(1);
//        req.setProd_id("1218");
//        List<BatchExtBuyReqData> list = new ArrayList<BatchExtBuyReqData>();
//        BatchExtBuyReqData batchExtBuyReqData = new BatchExtBuyReqData();
//        batchExtBuyReqData.setDetail_no(UUID.randomUUID().toString());
//        batchExtBuyReqData.setPlatcust(UUID.randomUUID().toString());
//        batchExtBuyReqData.setTrans_amt(10000d);
//        batchExtBuyReqData.setExperience_amt(5000d);
//        batchExtBuyReqData.setCoupon_amt(500d);
//        batchExtBuyReqData.setSelf_amt(4500d);
//        batchExtBuyReqData.setIn_interest(null);
//        batchExtBuyReqData.setSubject_priority("0");
//
//        List<BatchExtBuyReqCommission> commissionList = new ArrayList<BatchExtBuyReqCommission>();
//        BatchExtBuyReqCommission batchExtBuyReqCommission = new BatchExtBuyReqCommission();
//        batchExtBuyReqCommission.setPayout_plat_type("01");
//        batchExtBuyReqCommission.setPayout_amt("");
//        commissionList.add(batchExtBuyReqCommission);
//        batchExtBuyReqData.setCommission(commissionList);
//
//        batchExtBuyReqData.setRemark("");
//        list.add(batchExtBuyReqData);
//        req.setData(list);
//
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_BatchExtBuy res = hfbankTransportService.batchExtBuy(req);
//        logger.info("恒丰银行测试批量投标：{}", JSONObject.fromObject(res));
//    }

    /*标的出账*/
//    @Test
//    public void chargeOffTest() {
//        B2GReqMsg_HFBank_ChargeOff req = new B2GReqMsg_HFBank_ChargeOff();
//        req.setProd_id("1218");
//        List<ChargeOffReqDetail> list = new ArrayList<ChargeOffReqDetail>();
//        ChargeOffReqDetail chargeOffReqDetail = new ChargeOffReqDetail();
//        chargeOffReqDetail.setIs_advance("1");
//        chargeOffReqDetail.setPlatcust(UUID.randomUUID().toString());
//        chargeOffReqDetail.setOut_amt("5000");
//        chargeOffReqDetail.setOpen_branch("农业银行");
//        chargeOffReqDetail.setWithdraw_account("6228480321031877111");
//        chargeOffReqDetail.setPayee_name("舒煌辉");
//        chargeOffReqDetail.setBank_id("");
//        chargeOffReqDetail.setClient_property("0");
//        chargeOffReqDetail.setCity_code("");
//        list.add(chargeOffReqDetail);
//        req.setCharge_off_list(list);
//
//        req.setNotify_url(null);
//        req.setPay_code("BAOFOO");
//        req.setRemark("");
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_ChargeOff res = hfbankTransportService.chargeOff(req);
//        logger.info("恒丰银行测试标的出账：{}", JSONObject.fromObject(res));
//    }


    /*标的出账信息修改*/
//    @Test
//    public void modifyPayOutAcctTest() {
//        B2GReqMsg_HFBank_ModifyPayOutAcct req = new B2GReqMsg_HFBank_ModifyPayOutAcct();
//        req.setProd_id("1218");
//        req.setOpen_branch("");
//        req.setWithdraw_account("");
//        req.setAccount_type("");
//        req.setPayee_name("");
//        req.setRemark("");
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_ModifyPayOutAcct res = hfbankTransportService.modifyPayOutAcct(req);
//        logger.info("恒丰银行测试标的出账信息修改：{}", JSONObject.fromObject(res));
//    }

    /*标的转让*/
//    @Test
//    public void transferDebtTest() {
//        B2GReqMsg_HFBank_TransferDebt req = new B2GReqMsg_HFBank_TransferDebt();
//        req.setPlatcust(UUID.randomUUID().toString());
//        req.setTrans_share(5000d);
//        req.setProd_id("1218");
//        req.setTrans_amt(5500d);
//        req.setDeal_amount(5500d);
//        req.setCoupon_amt(0d);
//        req.setDeal_platcustprivate(UUID.randomUUID().toString());
//
//        List<TransferDebtReqCommission> detailList = new ArrayList<TransferDebtReqCommission>();
//        TransferDebtReqCommission transferDebtReqCommission = new TransferDebtReqCommission();
//        transferDebtReqCommission.setPayout_plat_type("01");
//        transferDebtReqCommission.setPayout_amt("10");
//        req.setCommission(detailList);
//
//        List<TransferDebtReqCommissionExt> transferDebtReqCommissionExtList = new ArrayList<TransferDebtReqCommissionExt>();
//        TransferDebtReqCommissionExt transferDebtReqCommissionExt = new TransferDebtReqCommissionExt();
//        transferDebtReqCommissionExt.setPayout_plat_type("01");
//        transferDebtReqCommissionExt.setPayout_amt("10");
//        transferDebtReqCommissionExtList.add(transferDebtReqCommissionExt);
//        req.setCommission_ext(transferDebtReqCommissionExtList);
//
//        req.setPublish_date(new Date());
//        req.setTrans_date(new Date());
//        req.setTransfer_income(490d);
//        req.setIncome_acct(UUID.randomUUID().toString());
//        req.setRelated_prod_ids("");
//        req.setRemark("");
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_TransferDebt res = hfbankTransportService.transferDebt(req);
//        logger.info("恒丰银行测试标的转让：{}", JSONObject.fromObject(res));
//    }

    /*借款人批量还款*/
//    @Test
//    public void batchExtRepayTest() {
//        B2GReqMsg_HFBank_BatchExtRepay req = new B2GReqMsg_HFBank_BatchExtRepay();
//        List<BatchExtRepayReqData> detailList = new ArrayList<BatchExtRepayReqData>();
//        BatchExtRepayReqData batchExtRepayReqData = new BatchExtRepayReqData();
//        batchExtRepayReqData.setDetail_no(UUID.randomUUID().toString());
//        batchExtRepayReqData.setProd_id("1218");
//        batchExtRepayReqData.setRepay_num("1");
//        batchExtRepayReqData.setRepay_date(new Date());
//        batchExtRepayReqData.setRepay_amt("1500");
//        batchExtRepayReqData.setReal_repay_date(new Date());
//        batchExtRepayReqData.setReal_repay_amt("1500");
//        batchExtRepayReqData.setPlatcust(UUID.randomUUID().toString());
//        batchExtRepayReqData.setTrans_amt("1500");
//        batchExtRepayReqData.setFee_amt("");
//        batchExtRepayReqData.setRemarkprivate("");
//        detailList.add(batchExtRepayReqData);
//        req.setData(detailList);
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_BatchExtRepay res = hfbankTransportService.batchExtRepay(req);
//        logger.info("恒丰银行测试借款人批量还款：{}", JSONObject.fromObject(res));
//    }

    /*标的还款*/
//    @Test
//    public void prodRepayTest() {
//        B2GReqMsg_HFBank_ProdRepay req = new B2GReqMsg_HFBank_ProdRepay();
//        req.setProd_id("1218");
//        req.setRepay_num(1);
//        req.setIs_payoff("0");
//        req.setTrans_amt(10000d);
//
//        ProdRepayReqFundData prodRepayReqFundData = new ProdRepayReqFundData();
//        List<ProdRepayReqFundDataCustRepay> custRepayList = new ArrayList<ProdRepayReqFundDataCustRepay>();
//        ProdRepayReqFundDataCustRepay prodRepayReqFundDataCustRepay = new ProdRepayReqFundDataCustRepay();
//        prodRepayReqFundDataCustRepay.setReal_repay_amt("1000");
//        prodRepayReqFundDataCustRepay.setReal_repay_amount("900");
//        prodRepayReqFundDataCustRepay.setExperience_amt("");
//        prodRepayReqFundDataCustRepay.setRates_amt("");
//        prodRepayReqFundDataCustRepay.setReal_repay_val("100");
//        prodRepayReqFundDataCustRepay.setRepay_fee("");
//        prodRepayReqFundDataCustRepay.setCust_no(UUID.randomUUID().toString());
//        prodRepayReqFundDataCustRepay.setRepay_num("1");
//        prodRepayReqFundDataCustRepay.setRepay_date(DateUtil.formatYYYYMMDD(new Date()));
//        prodRepayReqFundDataCustRepay.setReal_repay_date(DateUtil.formatYYYYMMDD(new Date()));
//        custRepayList.add(prodRepayReqFundDataCustRepay);
//        prodRepayReqFundData.setCustRepayList(custRepayList);
//        req.setFunddata(prodRepayReqFundData);
//
//        req.setRemark("");
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_ProdRepay res = hfbankTransportService.prodRepay(req);
//        logger.info("恒丰银行测试标的还款：{}", JSONObject.fromObject(res));
//    }

    /*平台转账个人*/
//    @Test
//    public void platformTransferTest() {
//        B2GReqMsg_HFBank_PlatformTransfer req = new B2GReqMsg_HFBank_PlatformTransfer();
//        req.setPlat_account("01");
//        req.setAmount("3000");
//        req.setPlatcust(UUID.randomUUID().toString());
//        req.setRemark(null);
//        req.setOrder_no("ZZ20170110");
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_PlatformTransfer res = hfbankTransportService.platformTransfer(req);
//        logger.info("恒丰银行测试平台转账个人：{}", JSONObject.fromObject(res));
//    }

    /*平台不同账户转账*/
//    @Test
//    public void platformAccountConverseTest() {
//        B2GReqMsg_HFBank_PlatformAccountConverse req = new B2GReqMsg_HFBank_PlatformAccountConverse();
//        req.setSource_account("1");
//        req.setAmt(5000d);
//        req.setDest_account("1");
//        req.setRemark("");
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_PlatformAccountConverse res = hfbankTransportService.platformAccountConverse(req);
//        logger.info("恒丰银行测试平台不同账户转账：{}", JSONObject.fromObject(res));
//    }

}
