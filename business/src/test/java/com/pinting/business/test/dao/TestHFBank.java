package com.pinting.business.test.dao;

import com.pinting.business.test.TestBase;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Test;
import org.quartz.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description: 恒丰银行测试类
 */
public class TestHFBank extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestHFBank.class);
     
    @Autowired 
    private HfbankTransportService hfbankTransportService;

    /*批量开户（四要素绑卡）*/
//    @Test
//    public void batchBindCard4ElementsTest() {
//        B2GReqMsg_HFBank_BatchBindCard4Elements req = new B2GReqMsg_HFBank_BatchBindCard4Elements();
//         
//        String bindOrderNo=Util.generateOrderNo4BaoFoo(8);
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setOrder_no(bindOrderNo);
//        req.setTotal_num(1);
//        List<BatchRegistExtDetail> data = new ArrayList<>();
//
//        BatchRegistExtDetail batchRegistExtDetail = new BatchRegistExtDetail();
//        batchRegistExtDetail.setDetail_no("BINDCARD20170419001");
//        batchRegistExtDetail.setName("Gemma1");
//        batchRegistExtDetail.setId_type("1");
//        batchRegistExtDetail.setId_code("30327198806253491");
//        batchRegistExtDetail.setMobile("15168487911");
//        batchRegistExtDetail.setEmail(null);
//        batchRegistExtDetail.setSex(null);
//        batchRegistExtDetail.setCus_type(null);
//        batchRegistExtDetail.setBirthday(null);
//        batchRegistExtDetail.setOpen_branch(null);
//        batchRegistExtDetail.setCard_no("6222021203029387056");
//        batchRegistExtDetail.setCard_type("1");
//        batchRegistExtDetail.setPre_mobile("15168487911");
//        batchRegistExtDetail.setPay_code(Constants.CHANNEL_HFBANK);
//        batchRegistExtDetail.setNotify_url(null);
//        batchRegistExtDetail.setRemark(null);
//        data.add(batchRegistExtDetail);
//        req.setData(data);
//
//        B2GResMsg_HFBank_BatchBindCard4Elements res = hfbankTransportService.batchBindCard4Elements(req);
//        logger.info("恒丰银行测试批量绑卡：{}", JSONObject.fromObject(res));
//    }

    /*批量开户（实名认证）*/
//    @Test
//    public void batchBindCardRealNameAuthTest() {
//        B2GReqMsg_HFBank_BatchBindCardRealNameAuth req = new B2GReqMsg_HFBank_BatchBindCardRealNameAuth();
//        req.setTotal_num(1);
//        String bindOrderNo=Util.generateOrderNo4BaoFoo(8);
//        List<BatchRegistExt3Detail> data = new ArrayList<BatchRegistExt3Detail>();
//        BatchRegistExt3Detail batchRegistExt3Detail = new BatchRegistExt3Detail();
//        batchRegistExt3Detail.setDetail_no(bindOrderNo);
//        batchRegistExt3Detail.setName("Gemma1");
//        batchRegistExt3Detail.setId_code("30327198806253491");
//        batchRegistExt3Detail.setMobile("15168487911");
//        batchRegistExt3Detail.setEmail("");
//        batchRegistExt3Detail.setSex("0");
//        batchRegistExt3Detail.setBirthday(null);
//        batchRegistExt3Detail.setCus_type("1");
//        batchRegistExt3Detail.setOrg_name("");
//        batchRegistExt3Detail.setBusiness_license("");
//        batchRegistExt3Detail.setBank_license("");
//        batchRegistExt3Detail.setRemark("实名认证");
//        data.add(batchRegistExt3Detail);
//        req.setData(data);
//        req.setOrder_no("BATCH_AUTH_"+bindOrderNo);
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_BatchBindCardRealNameAuth res = hfbankTransportService.batchBindCardRealNameAuth(req);
//       
//        if(CollectionUtils.isNotEmpty(res.getSuccess_data())){
//            BatchRegistExt3Success batchDates = res.getSuccess_data().get(0);
//            if(StringUtil.isNotEmpty(batchDates.getPlatcust())){
//               System.out.println("aaa");
//            }
//        }
//        
//        logger.info("恒丰银行测试批量开户：{}", JSONObject.fromObject(res));
//    }

    /*短验绑卡申请*/
//    @Test
//    public void userPreBindCardTest() {
//        B2GReqMsg_HFBank_UserPreBindCard req = new B2GReqMsg_HFBank_UserPreBindCard();
//        req.setPlatcust(UUID.randomUUID().toString());
//        req.setId_type("");
//        req.setId_code("");
//        req.setName("");
//        req.setCard_no("6228480321031877111");
//        req.setCard_type("");
//        req.setPre_mobile("13575759159");
//        req.setPay_code("BAOFOO");
//        req.setRemark("");
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_UserPreBindCard res = hfbankTransportService.userPreBindCard(req);
//        logger.info("恒丰银行测试短验绑卡申请：{}", JSONObject.fromObject(res));
//    }

    /*短验绑卡确认*/
//    @Test
//    public void userBindCardTest() {
//        B2GReqMsg_HFBank_UserBindCard req = new B2GReqMsg_HFBank_UserBindCard();
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setPlatcust(UUID.randomUUID().toString());
//        req.setId_type("");
//        req.setId_code("");
//        req.setName("");
//        req.setCard_no("6228480321031877111");
//        req.setCard_type("");
//        req.setPre_mobile("13575759159");
//        req.setPay_code("BAOFOO");
//        req.setIdentifying_code("1234");
//        req.setOrigin_order_no(UUID.randomUUID().toString());
//        req.setRemark("");
//        B2GResMsg_HFBank_UserBindCard res = hfbankTransportService.userBindCard(req);
//        logger.info("恒丰银行测试短验绑卡确认：{}", JSONObject.fromObject(res));
//    }

    /*网关充值请求*/
//    @Test
//    public void userGatewayRechargeRequestTest() {
//        B2GReqMsg_HFBank_UserGatewayRechargeRequest req = new B2GReqMsg_HFBank_UserGatewayRechargeRequest();
//        String orderNo = Util.generateOrderNo4BaoFoo(8);
//        req.setOrder_no(orderNo);
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setPlatcust("2017061415321569710017");
//        req.setType("1");
//        req.setCharge_type("1");
//        req.setBankcode("ICBC");
//        req.setCard_type("1");
//        req.setCurrency_code("");
//        req.setCard_no("6228480321031877111");
//        req.setAmt(1000d);
//        req.setReturn_url(null);
//        req.setNotify_url(null);
//        req.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
//        req.setRemark("网关充值");
//        logger.info("网关充值响应：{}", JSONObject.fromObject(req));
//        B2GResMsg_HFBank_UserGatewayRechargeRequest res = hfbankTransportService.userGatewayRechargeRequest(req);
//    }

    /*快捷充值请求*/
//    @Test
//    public void UserQuickPayPreRechargeTest() {
//        B2GReqMsg_HFBank_UserQuickPayPreRecharge req = new B2GReqMsg_HFBank_UserQuickPayPreRecharge();
//        String orderNo = Util.generateOrderNo4BaoFoo(8);
//        req.setOrder_no(orderNo);
//        
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setPlatcust("2017061415321569710017");
//        req.setName("翁怡辰");
//        req.setCard_no("6228480322465194114");
//        req.setCard_type("1");
//        req.setCurrency_code("");
//        req.setId_type("1");
//        req.setId_code("330124199312030326");
//        req.setMobile("15868150199");
//        req.setEmail("");
//        req.setAmt(1000d);
//        req.setPay_code("019");
//        req.setAccount_type("");
//        req.setNotify_url(null);
//        req.setRemark("快捷充值请求");
//        B2GResMsg_HFBank_UserQuickPayPreRecharge res = hfbankTransportService.userQuickPayPreRecharge(req);
//        logger.info("快捷充值请求：{}", JSONObject.fromObject(res));
//    }

    /*快捷充值确认*/
//    @Test
//    public void userQuickPayConfirmRechargeTest() {
//        B2GReqMsg_HFBank_UserQuickPayConfirmRecharge req = new B2GReqMsg_HFBank_UserQuickPayConfirmRecharge();
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setIdentifying_code("1234");
//        String orderNo = Util.generateOrderNo4BaoFoo(8);
//        req.setOrigin_order_no(orderNo);
//        req.setPay_code("019");
//        req.setRemark("快捷充值确认");
//        B2GResMsg_HFBank_UserQuickPayConfirmRecharge res = hfbankTransportService.userQuickPayConfirmRecharge(req);
//        logger.info("快捷充值确认：{}", JSONObject.fromObject(res));
//    }

    /*借款人扣款还款（代扣）*/
//    @Test
//    public void borrowCutRepayDKTest() {
//        B2GReqMsg_HFBank_BorrowCutRepayDK req = new B2GReqMsg_HFBank_BorrowCutRepayDK();
//        String orderNo = Util.generateOrderNo4BaoFoo(8);
//        req.setOrder_no(orderNo);
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setAmt(10000d);
//        req.setRemark("借款人扣款还款");
//        List<BorrowCutRepayPlatcust> data = new ArrayList<BorrowCutRepayPlatcust>();
//        BorrowCutRepayPlatcust borrowCutRepayPlatcust = new BorrowCutRepayPlatcust();
//        borrowCutRepayPlatcust.setPlatcust("2017061415321569710017");
//        borrowCutRepayPlatcust.setAmt(10000d);
//        data.add(borrowCutRepayPlatcust);
//        borrowCutRepayPlatcust.setPlatcust("1017061415321569721217");
//        borrowCutRepayPlatcust.setAmt(10200d);
//        data.add(borrowCutRepayPlatcust);
//        req.setPlatcustList(data);
//        B2GResMsg_HFBank_BorrowCutRepayDK res = hfbankTransportService.borrowCutRepayDK(req);
//        logger.info("借款人扣款还款（代扣）：{}", JSONObject.fromObject(res));
//    }

    /*批量提现*/
//    @Test
//    public void userBatchWithdrawTest() {
//        B2GReqMsg_HFBank_UserBatchWithdraw req = new B2GReqMsg_HFBank_UserBatchWithdraw();
//        String orderNo = Util.generateOrderNo4BaoFoo(8);
//        req.setOrder_no(orderNo);
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        List<BatchWithdrawExtData> data = new ArrayList<BatchWithdrawExtData>();
//        BatchWithdrawExtData batchWithdrawExtData = new BatchWithdrawExtData();
//        batchWithdrawExtData.setDetail_no("TX000001");
//        batchWithdrawExtData.setPlatcust("2017052821310980210002");
//        batchWithdrawExtData.setAmt(100d);
//        batchWithdrawExtData.setIs_advance("1");
//        batchWithdrawExtData.setPay_code("099");
//        batchWithdrawExtData.setFee_amt(0d);
//        batchWithdrawExtData.setWithdraw_type("1");
//        batchWithdrawExtData.setRemark("借款用户代付提现");
//        batchWithdrawExtData.setBank_code("309391000011");
//        batchWithdrawExtData.setTran_type("payReal");
//        batchWithdrawExtData.setBank_name("兴业银行");
//        data.add(batchWithdrawExtData);
//        req.setData(data);
//        req.setClient_property("0");
//        req.setTotal_num(1);
//        B2GResMsg_HFBank_UserBatchWithdraw res = hfbankTransportService.userBatchWithdraw(req);
//        logger.info("批量提现:{}", JSONObject.fromObject(res));
//    }

    /*订单状态查询*/
//    @Test
//    public void queryOrderTest() {
//        B2GReqMsg_HFBank_QueryOrder req = new B2GReqMsg_HFBank_QueryOrder();
//        String orderNo = Util.generateOrderNo4BaoFoo(8);
//        req.setOrder_no(orderNo);
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        String orderNo2 = Util.generateOrderNo4BaoFoo(8);
//        req.setQuery_order_no(orderNo2);				//查询的订单号（针对批量接口，对应detail_no）
//        B2GResMsg_HFBank_QueryOrder res = hfbankTransportService.queryOrder(req);
//        logger.info("订单状态查询:{}", JSONObject.fromObject(res));
//    }

    /*账户余额明细查询*/
//    @Test
//    public void queryAccountLeftAmountInfoTest() {
//        B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
//        req.setAccount("01");//平台送01
//        req.setAcct_type("3");
//        req.setFund_type("");
//        B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
//        logger.info("账户余额明细查询：{}", JSONObject.fromObject(res));
//    }

    /*标的账户余额查询*/
//    @Test
//    public void queryProductBalanceTest() {
//        B2GReqMsg_HFBank_QueryProductBalance req = new B2GReqMsg_HFBank_QueryProductBalance();
//        String orderNo = Util.generateOrderNo4BaoFoo(8);
//        req.setOrder_no(orderNo);
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setProd_id("1180");//产品编号
//        req.setType("01");//现金01   在途02
//
//        B2GResMsg_HFBank_QueryProductBalance res = hfbankTransportService.queryProductBalance(req);
//        logger.info("标的账户余额查询：{}", JSONObject.fromObject(res));
//    }


    /*标的发布test*/
    @Test
    public void publishTest() {
        B2GReqMsg_HFBank_Publish req = new B2GReqMsg_HFBank_Publish();
        String orderNo = Util.generateOrderNo4BaoFoo(8);
        req.setOrder_no(orderNo);
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setProd_id("BGW_CS_PRD_10");
        req.setProd_name("BGW测试环境10号产品");
        req.setProd_type("0");
        req.setTotal_limit(5000d);
        req.setValue_type("3");	// 0满额起息1成立起息2投标起息 3 成立审核后起息 
        req.setCreate_type("1");// 1-成立日成立 
        req.setSell_date(null);
        req.setValue_date(null);// 起息日如起息方式为成立起息,为必选项 (YYYY-MM-DD HH:mm:ss) 
        req.setExpire_date(null);
        req.setCycle(3);		// 
        req.setCycle_unit("3");	// 周期单位  1日 2周 3月 4季 5年 
        req.setIst_year(8.9d);	// 12%	送0.089 
        req.setRepay_type("0");	// 还款方式  0-一次性还款  1-分期还款  
        
        List<PublishFinancingInfo> list = new ArrayList<PublishFinancingInfo>();
        PublishFinancingInfo publishFinancingInfo = new PublishFinancingInfo();
        publishFinancingInfo.setCust_no("2001992719cs1");
        
        publishFinancingInfo.setReg_date(new Date());	//DateUtil.formatDateTime(obj.getReg_time(), "yyyy-MM-dd")
        publishFinancingInfo.setReg_time(new Date());	//DateUtil.formatDateTime(obj.getReg_time(), "HH:mm:ss")
        publishFinancingInfo.setFinanc_int("0.10");
        publishFinancingInfo.setFee_int(0d);
        publishFinancingInfo.setFinanc_purpose("lvyouxiaofei");
        publishFinancingInfo.setUse_date(new Date());
        publishFinancingInfo.setOpen_branch("icbc");
        publishFinancingInfo.setWithdraw_account("6228480321031877111");
        publishFinancingInfo.setAccount_type("1");
        publishFinancingInfo.setPayee_name("yangende");
        publishFinancingInfo.setFinanc_amt(50000d);
        list.add(publishFinancingInfo);
        req.setFinancing_info_list(list);
        
        req.setRisk_lvl(null);
        req.setProd_info(null);
        req.setBuyer_num_limit(null);	//可不填
        req.setChargeOff_auto("1");		//出账标示： 0、成标后自动出账至借款人融资账户  1、调用成标出账接口，出账至标的对应收款账户
        req.setOverLimit("1");			//超额限制 0-不限制  1-限制
        req.setOver_total_limit(1000d);
        
        req.setRemark("标的发布");
        List<CompensationInfo> compInfoList = new ArrayList<CompensationInfo>();
        CompensationInfo compensationInfo =new CompensationInfo();
        compensationInfo.setPlatcust("111992");	//代偿账户编号 
        compensationInfo.setType("0");			//0-代偿还款 1-委托还款 
        compInfoList.add(compensationInfo);
        req.setCompensation_info_list(compInfoList);
        
        
        B2GResMsg_HFBank_Publish res = hfbankTransportService.publish(req);
        logger.info("恒丰银行测试标的发布：{}", JSONObject.fromObject(res));
    }

    /*
     * 标的成废 已完成(标的成立前保证标的账户有钱)
     * */
//    @Test
//    public void establishOrAbandonTest() {
//        B2GReqMsg_HFBank_EstablishOrAbandon req = new B2GReqMsg_HFBank_EstablishOrAbandon();
////        req.setOrder_no(UUID.randomUUID().toString());
//        req.setOrder_no("BGW_20170419_003");
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        
//        req.setProd_id("BGW_CS_PRD_5");
//        req.setFlag("2");	//2 成标 3废标
//        //funddata 
//        List<EstablishOrAbandonReqFundData> reqFundData = new ArrayList<EstablishOrAbandonReqFundData>();
//        EstablishOrAbandonReqFundData establishOrAbandonReqFundData = new EstablishOrAbandonReqFundData();
//        establishOrAbandonReqFundData.setPayout_plat_type("01");
//        establishOrAbandonReqFundData.setPayout_amt("12.12");
//        reqFundData.add(establishOrAbandonReqFundData);
//        req.setFunddata(reqFundData);
//        
//        //repay_plan_list 
//        List<EstablishOrAbandonReqRepayPlan> repayList = new ArrayList<EstablishOrAbandonReqRepayPlan>();
//        EstablishOrAbandonReqRepayPlan establishOrAbandonReqRepayPlan = new EstablishOrAbandonReqRepayPlan();
//        establishOrAbandonReqRepayPlan.setRepay_amt("1000.00");
//        establishOrAbandonReqRepayPlan.setRepay_fee("0");
//        establishOrAbandonReqRepayPlan.setRepay_num("1");
//        establishOrAbandonReqRepayPlan.setRepay_date(new Date());
//        repayList.add(establishOrAbandonReqRepayPlan);
//        req.setRepay_plan_list(repayList);
//        req.setRemark("标的成废测试");
//        
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
//        batchExtRepayReqData.setRepay_amt(150d);
//        batchExtRepayReqData.setReal_repay_date(new Date());
//        batchExtRepayReqData.setReal_repay_amt(150d);
//        batchExtRepayReqData.setPlatcust(UUID.randomUUID().toString());
//        batchExtRepayReqData.setTrans_amt(150d);
//        batchExtRepayReqData.setFee_amt(10d);
//        batchExtRepayReqData.setRemarkprivate("");
//        detailList.add(batchExtRepayReqData);
//        req.setData(detailList);
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setTotal_num(1);
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
//        req.setCause_type("1");
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_PlatformTransfer res = hfbankTransportService.platformTransfer(req);
//        logger.info("恒丰银行测试平台转账个人：{}", JSONObject.fromObject(res));
//    }

    /*平台不同账户转账*/
//    @Test
//    public void platformAccountConverseTest() {
//        B2GReqMsg_HFBank_PlatformAccountConverse req = new B2GReqMsg_HFBank_PlatformAccountConverse();
//        req.setSource_account("2017042815495507410001");
//        req.setAmt(5000d);
//        req.setDest_account("2017042817271066210003");
//        req.setRemark("");
//        req.setOrder_no(UUID.randomUUID().toString());
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        B2GResMsg_HFBank_PlatformAccountConverse res = hfbankTransportService.platformAccountConverse(req);
//        logger.info("恒丰银行测试平台不同账户转账：{}", JSONObject.fromObject(res));
//    }
    /*平台提现*/
//	@Test
//    public void platWithDrawTest() {
//        B2GReqMsg_HFBank_PlatWithDraw req = new B2GReqMsg_HFBank_PlatWithDraw();
//        req.setOrder_no("PT_TX_20170419_002");
//        req.setPartner_trans_date(new Date());
//        req.setPartner_trans_time(new Date());
//        req.setAmount(100d);
//        req.setNotify_url("http://183.129.222.138:8083/gateway/remote/hfbank/business");
//        req.setRemark("平台提现测试");
//        B2GResMsg_HFBank_PlatWithDraw res = hfbankTransportService.platWithDraw(req);
//        logger.info("恒丰银行测试平台提现：{}", JSONObject.fromObject(res));
//    }
    
//	@Test
//	public void queryAccountLeftAmountInfoTest() {
//	      B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
//	      String bindOrderNo = Util.generateOrderNo4BaoFoo(8);
//	      req.setAccount("6222021203029387056");//平台送01
//	      //req.setAcct_type("1");
//	      req.setAcct_type("12");
//	      req.setFund_type("01");
//	      B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
//	      logger.info("恒丰银行测试账户余额明细查询：{}", JSONObject.fromObject(res));
//	  }

//    @Test
//    public void fundDataCheckTest() {
//    	B2GReqMsg_HFBank_FundDataCheck req = new B2GReqMsg_HFBank_FundDataCheck();
//    	req.setOrder_no("CHECK_ORDER_20170514001");
//    	req.setPartner_trans_date(new Date());
//    	req.setPartner_trans_time(new Date());
//    	req.setPaycheck_date(DateUtil.parseDate("2017-04-29"));
//    	req.setPrecheck_flag("0");
//    	B2GResMsg_HFBank_FundDataCheck res = hfbankTransportService.fundDataCheck(req);
//    	logger.info("恒丰银行测试对账文件资金进出数据：{}", JSONObject.fromObject(res));
//    }
    
    /* 资金变动明细查询 */
//    @Test
//    public void queryAccountInfoTest() {
//    	Date date = new Date();
//    	SimpleDateFormat dFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	String startTime = dFmt.format(date);
//    	java.util.Calendar cal = java.util.Calendar.getInstance();
//    	cal.setTime(date);
//    	cal.add(java.util.Calendar.DAY_OF_YEAR, -100);
//    	String endTime = dFmt.format(cal.getTime());
//    	Date end_Date =  new Date();
//		try {
//			end_Date = dFmt.parse(endTime);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//    	
//    	B2GReqMsg_HFBank_GetFundList req = new B2GReqMsg_HFBank_GetFundList();
//      	req.setOrder_no("GETFUNDLIST_20170526001");
//      	req.setPlatcust("2017042019592728111280");
//      	req.setTrans_name("支出");
//      	req.setPagesize("100");
//      	req.setPagenum("1");
//      	req.setStart_date(end_Date);
//      	req.setEnd_date(date);
//      	B2GResMsg_HFBank_GetFundList res = hfbankTransportService.getFundList(req);
//      	logger.info("恒丰银行测试资金变动明细查询 ：{}", JSONObject.fromObject(res));
//    }
    /*批量换卡*/
//  @Test
//  public void batchChangeCardTest() {
//      B2GReqMsg_HFBank_BatchChangeCard req = new B2GReqMsg_HFBank_BatchChangeCard();
//      int n = 5;
//      req.setTotal_num(n);
//      req.setOrder_no("BATCHUPDATE_20140419_004");
//      req.setPartner_trans_date(new Date());
//      req.setPartner_trans_time(new Date());
//      
//      List<BatchUpdateCardExtDetail> dataList = new ArrayList<BatchUpdateCardExtDetail>();
//      for( int i = 1; i <= n; i++) {
//      	BatchUpdateCardExtDetail batchUpdateCardExtDetail = new BatchUpdateCardExtDetail();
//          batchUpdateCardExtDetail.setDetail_no("DETAIL_400"+i);
//          batchUpdateCardExtDetail.setPlatcust("2017041813372262011104");
//          batchUpdateCardExtDetail.setCard_no("6222021203029387056");
//          batchUpdateCardExtDetail.setMobile("15168487916");
//          batchUpdateCardExtDetail.setCard_type("1");
//          batchUpdateCardExtDetail.setCard_no_old("6222021203029387051");
//          batchUpdateCardExtDetail.setCard_type_old("1");
//          batchUpdateCardExtDetail.setName("Gemma");
//          batchUpdateCardExtDetail.setPay_code(Constants.CHANNEL_BAOFOO);
//          batchUpdateCardExtDetail.setRemark("测试批量更新任务"+i);
//          dataList.add(batchUpdateCardExtDetail);
//          req.setData(dataList);
//      }
//     
//      
//      B2GResMsg_HFBank_BatchChangeCard res = hfbankTransportService.batchChangeCard(req);
//      logger.info("恒丰银行测试批量换卡：{}", JSONObject.fromObject(res));
//  }
    /* 4.1.3绑卡确认 */
//    @Test
//    public void userBindCardTest() {
//    	B2GReqMsg_HFBank_UserAddCard req = new B2GReqMsg_HFBank_UserAddCard();
//    	String orderNo = Util.generateOrderNo4BaoFoo(8);
//    	
//    	req.setOrder_no(orderNo);
//    	req.setPartner_trans_date(new Date());
//    	req.setPartner_trans_time(new Date());
//    	req.setType("1");//绑卡类型：1个人客户 2 对公客户
//    	req.setPlatcust("2017041512511533611032");
//    	req.setId_type("1");	//1:身份证
//    	req.setId_code("330327198806253496");
//    	req.setName("杨恩德");	
//    	req.setCard_no("6222021203029387056");
//    	req.setCard_type("1");	//1:借记卡,2:信用卡
//    	req.setPre_mobile("15068487916");
//    	
//    	req.setOrg_no("");
//    	req.setAcct_no("");
//    	req.setAcct_name("");
//    	req.setOpen_branch("");
//    	
//      	req.setPay_code("019");
//      	req.setRemark("个人绑卡");
//      	B2GResMsg_HFBank_UserAddCard res = hfbankTransportService.userAddCard(req);
//      	logger.info("恒丰银行测试绑卡确认：{}", JSONObject.fromObject(res));
//  }
}