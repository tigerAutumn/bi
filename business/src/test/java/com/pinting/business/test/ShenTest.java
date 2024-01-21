package com.pinting.business.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.itextpdf.text.DocumentException;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.ProtocolSealVO;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.accounting.loan.service.SysDailyBizService;
import com.pinting.business.dao.BsActivityAwardMapper;
import com.pinting.business.dao.BsActivityLuckyDrawMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsPaymentChannelMapper;
import com.pinting.business.dao.LnCompensateAgreementAddressMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsActivityAward;
import com.pinting.business.model.BsActivityAwardExample;
import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.BsPaymentChannel;
import com.pinting.business.model.BsPaymentChannelExample;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.LnCompensate;
import com.pinting.business.model.LnCompensateAgreementAddress;
import com.pinting.business.model.LnCompensateAgreementAddressExample;
import com.pinting.business.model.LnCompensateDetail;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.service.loan.LateRepayAgreementService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.protocol.LoanAgreementSignSealService;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.HFBankDepSiteService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.business.util.LuckyDrawUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;

/**
 *
 * @author SHENGUOPING
 * @date  2018年1月15日 上午9:24:02
 */
public class ShenTest extends TestBase {

	private Logger log = LoggerFactory.getLogger(ShenTest.class);

	@Autowired
	private BsActivityLuckyDrawMapper bsActivityLuckyDrawMapper;
	@Autowired
	private BsActivityAwardMapper bsActivityAwardMapper;
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	@Autowired
	private LateRepayAgreementService lateRepayAgreementService;
	@Autowired
	private BsSysConfigService sysConfigService;
    @Autowired
    private LnCompensateAgreementAddressMapper lnCompensateAgreementAddressMapper;

	@Autowired
	private HFBankDepSiteService hfBankDepSiteService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private LoanAgreementSignSealService loanAgreementSignSealService;
	@Autowired
	private SysDailyBizService sysDailyBizService;
	@Autowired
	private BsPaymentChannelMapper bsPaymentChannelMapper;
	@Autowired
	private BsPayOrdersMapper bsPayOrdersMapper;
	@Autowired
    private BsPaymentChannelMapper paymentChannelMapper;
	
	/*@Test
	public void test() {
		LnPayOrders lnPayOrders = lnPayOrdersMapper.selectByOrderNoAndChannel("9ba0d8a33bc150953783", "BAOFOO");
		
		System.out.println("lnPayOrders amount:" + lnPayOrders.getAmount());
	}*/
	
	
	
	//@Test
	public void testLuckAward() {
		int userId = 1000246;
		String awardNote = "";
		BsActivityLuckyDraw record = null;
		for (int i = 0; i < 1000; i++) {			
			//awardNote = getGuaguaLuckyAward(500d);
			//awardNote = getGuaguaLuckyAward(200d);
			//awardNote = getGuaguaLuckyAward(30000d);
			//awardNote = getGuaguaLuckyAward(60000d);
			//awardNote = getGuaguaLuckyAward(150000d);
			//awardNote = getGuaguaLuckyAward(210000d);
			awardNote = getGuaguaLuckyAward(5000d);
			record = new BsActivityLuckyDraw();
			BsActivityAwardExample example = new BsActivityAwardExample();
			example.createCriteria().andActivityIdEqualTo(Constants.ACTIVITY_ID_2017_SCRATCH_CARD).andContentEqualTo(awardNote);
			List<BsActivityAward> list = bsActivityAwardMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				log.info("userScratch method, awardId:{}", list.get(0).getId());
				record.setAwardId(list.get(0).getId());
				record.setNote(awardNote);
				record.setIsWin("Y");     //是否中奖-Y
			} else {
				record.setAwardId(null);
				record.setNote("未中奖");
				record.setIsWin("N");     //是否中奖-N
			}
			record.setUserId(userId);
			record.setActivityId(Constants.ACTIVITY_ID_2017_SCRATCH_CARD);
			record.setIsBackDraw("N");//是否后台抽奖-N
			record.setIsUserDraw("Y");//用户是否抽奖-Y
			record.setUserDrawTime(new Date());
			record.setIsConfirm("N"); //是否兑奖-N
			record.setIsAutoConfirm("N"); //是否自动兑奖-N
			record.setCreateTime(new Date());
			record.setUpdateTime(new Date());
			bsActivityLuckyDrawMapper.insertSelective(record);
		}				
	}
	
	private String getGuaguaLuckyAward(Double investAmount) {
		int luckyNum = LuckyDrawUtil.luckyNumber(); //随机获得的幸运数字[1,10000]
		log.info("getLuckyAward method, luckyNum:{}", luckyNum);
		String awardNote = "";
		if (investAmount.compareTo(1000d) < 0) {
			if(luckyNum <= 3500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_TENTH;
			} else if (luckyNum <= 6500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_NINTH;
			} else if (luckyNum <= 9000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_EIGHTH;
			} else if (luckyNum <= 9500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SEVENTH;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH;
			}
		} else if (investAmount.compareTo(10000d) < 0) {
			if(luckyNum <= 2000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_TENTH;
			} else if (luckyNum <= 4000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_NINTH;
			} else if (luckyNum <= 6500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SEVENTH;
			} else if (luckyNum <= 9000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH;
			} else if (luckyNum <= 9500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIFTH;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			}
		} else if (investAmount.compareTo(50000d) < 0) {
			if(luckyNum <= 1000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_NINTH;
			} else if (luckyNum <= 2500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH;
			} else if (luckyNum <= 6000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIFTH;
			} else if (luckyNum <= 9500){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;
			}
		} else if (investAmount.compareTo(100000d) < 0) {
			if(luckyNum <= 1000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_NINTH;
			} else if (luckyNum <= 1500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SIXTH;
			} else if (luckyNum <= 2000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIFTH;
			} else if (luckyNum <= 4000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			} else if (luckyNum <= 7000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SECOND;
			}
		} else if (investAmount.compareTo(200000d) < 0) {
			if(luckyNum <= 1000){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_NINTH;
			} else if (luckyNum <= 2500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;		
			} else if (luckyNum <= 5500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;
			} else if (luckyNum <= 9000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SECOND;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIRST;
			}
		} else {
			if(luckyNum <= 500){
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FORTH;
			} else if (luckyNum <= 3000) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_THIRD;			
			} else if (luckyNum <= 6500) {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_SECOND;
			} else {
				awardNote = Constants.ACTIVITY_SCRATCH_CARD_AWARDS_FIRST;
			}
		}
		log.info("getLuckyAward method, awardNote:{}", awardNote);
		return awardNote;
	}

	//@Test
	public void testCompensateAgreement() {
		LnCompensate lnCompensate = new LnCompensate();
		lnCompensate.setOrderNo("2018012300000001-1-4");
		lnCompensate.setPartnerCode("YUN_DAI_SELF");
		List<LnCompensateDetail> agreementList = new ArrayList<>();
		LnCompensateDetail lnCompensateDetail = new LnCompensateDetail();
		lnCompensateDetail.setCompensateId(9060);
		lnCompensateDetail.setPartnerUserId("YUN_LOAN_2018012301");
		lnCompensateDetail.setPartnerLoanId("2018020100000209");
		lnCompensateDetail.setPartnerRepayId("2018012300000002-3");
		lnCompensateDetail.setRepaySerial(1);
		lnCompensateDetail.setRepayType("OVERDUE");
		lnCompensateDetail.setTotal(3090d);
		lnCompensateDetail.setPrincipal(3000d);
		lnCompensateDetail.setInterest(90d);
		lnCompensateDetail.setStatus("SUCC");
		agreementList.add(lnCompensateDetail);
		// 代偿签章协议生成走定时任务：LateAgreementGenerateTask
		// 代偿签章协议通知走定时任务：LateAgreementNotifyTask
	}
	
	//@Test
	public void testCompensateAgreementGen() {
		BsSysConfig sysConfig = sysConfigService.findKey(Constants.LOAN_AGREEMENT_CHANGE_THREE_PART_TIME); 
    	if (sysConfig == null) {
    		throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
    	}
    	String agreementChangeTime = sysConfig.getConfValue();
    	// 对应代偿协议在四方改三方之后生成，合规化要求
    	LnCompensateAgreementAddressExample lnCompensateAgreementAddressExample = new LnCompensateAgreementAddressExample();
    	lnCompensateAgreementAddressExample.createCriteria().andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode()).
    	andPartnerLoanIdEqualTo("00010132111sdfsdfghj").andCreateTimeGreaterThan(DateUtil.parseDate(agreementChangeTime));
    	List<LnCompensateAgreementAddress> list = lnCompensateAgreementAddressMapper.selectByExample(lnCompensateAgreementAddressExample);    	
    	System.out.println("testCompensateAgreementGen size:" + list.size());
	}
	
	// http://localhost:9084/site/micro2.0/agreement/loanAgreement7PdfAnyRepay?partnerLoanId=partnerLoanId167898
	//@Test
	public void test() {
		String agreementNo = "partnerLoanId167898";
		LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(167898);
		List<LnLoanRelation> loanRelations = new ArrayList<>();
		loanAgreementSignSealService.protocolSeal(PartnerEnum.SEVEN_DAI_SELF, new ProtocolSealVO(agreementNo, lnLoan, loanRelations));
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	//@Test
	public void pdf(){
        String buyHtml = "";
        String buyPdfPath = "";
        String  dateString = DateUtil.formatDateTime(new Date(), "yyyyMM");
        buyPdfPath = GlobEnvUtil.get("self.loan.7dai.seal.path") +"/quartetAgree_"+dateString+"/"+"xieyibianhao001"+".pdf";
        buyHtml = GlobEnvUtil.get("cfca.seal.loanAgreement7.pdfSrcHtml") + "?partnerLoanId=longmaotestloanid20180305001";
        try {
			ITextPdfUtil.createHtm2Pdf(buyHtml, buyPdfPath, "借款协议", "借款协议", "xieyibianhao001");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
        System.out.println(buyPdfPath);
	}
	
	//@Test
	public void generateMainCheckGacha() {
		BsPaymentChannelExample bsPaymentChannelExample = new BsPaymentChannelExample();
		bsPaymentChannelExample.createCriteria().andMerchantNoEqualTo("1198035");
		/*bsPaymentChannel.setMerchantNo("1017304");
		List<BsPaymentChannel> list = bsPaymentChannelMapper.selectByExample(bsPaymentChannelExample);
		sysDailyBizService.checkBaoFooDailyAccountingAssist(list.get(0));*/
	}
	
	//@Test
	public void generateAssistCheckGacha() {
		BsPaymentChannel bsPaymentChannel = new BsPaymentChannel();
		bsPaymentChannel.setMerchantNo("1198035");
		sysDailyBizService.downloadCheckAccountFileAssist(bsPaymentChannel);
	}
	
	@Test
	public void testLnPayOrdersIndex() {
		LnPayOrders lnPayOrders = null;
		Integer lnUserId = 1741116;
		log.info("=========【测试LnPayOrders索引】开始======================");
        long start = System.currentTimeMillis();
        int size = 10000;
		for (int i = 0; i < size; i++) {	
			lnPayOrders = new LnPayOrders();
			lnPayOrders.setLnUserId(lnUserId);
			lnPayOrders.setCreateTime(new Date());
		    lnPayOrders.setAccountType(1);
		    lnPayOrders.setAmount(100d);
		    lnPayOrders.setBankCardNo("6221000246246");
		  
	        lnPayOrders.setBankId(1000246246);
	        lnPayOrders.setBankName("工商银行");
	        lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
	        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode());
	        lnPayOrders.setIdCard("33050119901000246246");
	        lnPayOrders.setMobile("18658806666");
	        lnPayOrders.setMoneyType(0);
	        lnPayOrders.setOrderNo("622100024624600"+i);
	        lnPayOrders.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
	        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
	        lnPayOrders.setSubAccountId(3660);
	        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_REPAY.getCode());
	        lnPayOrders.setUpdateTime(new Date());
	        lnPayOrders.setUserName("吴彦祖");
		        //查询支付渠道代扣优先商户号信息
		    	BsPaymentChannel channelInfo = paymentChannelMapper.selectFisrtDK();
		    	if(channelInfo != null){
		    		//修改订单表payment_channel_id
		    		lnPayOrders.setPaymentChannelId(channelInfo.getId());
		    	}
		    	
			lnPayOrdersMapper.insertSelective(lnPayOrders);
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		log.info("=========【测试LnPayOrders索引】结束======================");
		log.info("=========【LnPayOrders插入{}条数据】用时:{}ms", size, (end - start));
	}
	
	
}
