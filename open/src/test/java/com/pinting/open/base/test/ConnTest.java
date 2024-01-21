package com.pinting.open.base.test;

import com.google.gson.Gson;
import com.pinting.open.base.client.AppOpenClient;
import com.pinting.open.base.client.OpenClient;
import com.pinting.open.pojo.request.asset.*;
import com.pinting.open.pojo.request.index.AppStartRequest;
import com.pinting.open.pojo.request.index.LoginRequest;
import com.pinting.open.pojo.request.more.NewsListInfoRequest;
import com.pinting.open.pojo.request.product.CheckBeforeBuyRequest;
import com.pinting.open.pojo.request.product.ProductListIndexRequest;
import com.pinting.open.pojo.request.product.ProductListReturnTypeRequest;
import com.pinting.open.pojo.request.product.ProductSingleRequest;
import com.pinting.open.pojo.response.asset.*;
import com.pinting.open.pojo.response.index.AppStartResponse;
import com.pinting.open.pojo.response.index.LoginResponse;
import com.pinting.open.pojo.response.more.NewsListInfoResponse;
import com.pinting.open.pojo.response.product.CheckBeforeBuyResponse;
import com.pinting.open.pojo.response.product.ProductListIndexResponse;
import com.pinting.open.pojo.response.product.ProductListReturnTypeResponse;

import com.pinting.open.pojo.response.product.ProductSingleResponse;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConnTest {

    //回款计划列表
//    @Test
//    public void test() {
//        OpenClient client = new AppOpenClient("test");
//        RepayScheduleListRequest request = new RepayScheduleListRequest();
//        request.setUserId(1001605);
//        request.setAppVersion("6.6.6");
//        request.setVersion("v_1.0.0");
//        RepayScheduleListResponse response = (RepayScheduleListResponse)  client.execute(request);
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(response));
//    }


    //回款计划明细
//    @Test
//    public void test() {
//        OpenClient client = new AppOpenClient("test");
//        RepayScheduleDetailsRequest request = new RepayScheduleDetailsRequest();
//        request.setUserId(1001605);
//        request.setAppVersion("6.6.6");
//        request.setVersion("v_1.0.0");
//        request.setPlanDate("2017-04");
//        RepayScheduleDetailsResponse response = (RepayScheduleDetailsResponse)  client.execute(request);
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(response));
//    }

/*    @Test
    public void test() {
        OpenClient client = new AppOpenClient("test");
        RedPacketListRequest request = new RedPacketListRequest();
        request.setNumPerPage(10);
        request.setUserId(1000562);
        request.setPageNum(1);
        request.setStatus("OVER");
        request.setAppVersion("6.6.6");
        request.setVersion("v_1.0.0");
        RedPacketListResponse response = (RedPacketListResponse)  client.execute(request);
        Gson gson = new Gson();
        System.out.println(gson.toJson(response));
    }*/

//    @Test
//    public void test() {
//        OpenClient client = new AppOpenClient("test");
//        TradingDetailRequest request = new TradingDetailRequest();
//        request.setUserId(1024992);
//        request.setAppVersion("5.5.5");
//        request.setVersion("v_1.0.0");
//        request.setPageIndex(6);
//        TradingDetailResponse response = (TradingDetailResponse) client.execute(request);
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(response));

//        PaymentStageDetailsRequest paymentStageDetailsRequest = new PaymentStageDetailsRequest();
//        paymentStageDetailsRequest.setTime("2017-01-24 00:10:04");
//        paymentStageDetailsRequest.setUserId(1024992);
//        paymentStageDetailsRequest.setVersion("v_1.0.0");
//        paymentStageDetailsRequest.setAppVersion("5.5.5");
//        PaymentStageDetailsResponse response = (PaymentStageDetailsResponse) client.execute(paymentStageDetailsRequest);
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(response));
//    }

/*
	@Test
	public void test() {
//		OpenClient client = new AppOpenClient("test");
//		LoginRequest request = new LoginRequest();
//		request.setMobile("15158191349");
//		request.setPassword("123456");
//		LoginResponse response = (LoginResponse) client.execute(request);
//		System.out.println(response.getException());
		
		OpenClient client = new AppOpenClient("test");
		RegisterRequest request = new RegisterRequest();
		request.setMobile("13516708921");
		request.setMobileCode("3030");
		request.setPassword("123456");
		RegisterResponse response = (RegisterResponse) client.execute(request);
		System.out.println(response.getException());
	}
	
	@Test
	public void sendTest() {
		
		OpenClient client = new AppOpenClient("test");
		SendCodeRequest request = new SendCodeRequest();
		request.setMobile("13516708921");
		SendCodeResponse response = (SendCodeResponse) client.execute(request);
		System.out.println(response.getException());
		
	}
	

	*/
	//我的余额
//	@Test
//	public void balanceTest() {
//		OpenClient client = new AppOpenClient("test");
//		BalanceRequest request = new BalanceRequest();
//		request.setUserId(21);
//		request.setVersion("v_1.0.0");
//        request.setAppVersion("5.5.5");
//		BalanceResponse response = (BalanceResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}

//	@Test
//	public void test() {
////		OpenClient client = new AppOpenClient("test");
////		LoginRequest request = new LoginRequest();
////		request.setMobile("13516708921");
////		request.setPassword("123123");
////		LoginResponse response = (LoginResponse) client.execute(request);
////		System.out.println(response.getException());
//
//		OpenClient client = new AppOpenClient("test");
//		SendCodeRequest request = new SendCodeRequest();
//		request.setMobile("13516708921");
//		request.setVersion("v_1.0.0");
//		SendCodeResponse response = (SendCodeResponse) client.execute(request);
//
////		OpenClient client = new AppOpenClient("test");
////		IndexRequest request = new IndexRequest();
////		request.setUserType("PROMOT");
////		IndexResponse response = (IndexResponse) client.execute(request);
////		System.out.println(response.getMinRete());
////		System.out.println(response.getMaxRate());
//	}
	
	
/*	@Test
	public void preBindCardTest() {
		OpenClient client = new AppOpenClient("test");
		BankCardBindPreRequest request = new BankCardBindPreRequest();
		request.setUserId(1000561);
		request.setUserName("史玉隆");
		request.setIdCard("520203199012161817");
		request.setMobile("15868157902");
		request.setCardNo("6228480322397174614");
		request.setBankId(3);
		request.setTerminalType("1");
		request.setVersion("v_1.0.0");
		BankCardBindPreResponse response = (BankCardBindPreResponse) client.execute(request);
		System.out.println(response.getException());
	}
*/
/*	@Test
	public void bindCardTest() {
		OpenClient client = new AppOpenClient("test");
		BankCardBindRequest request = new BankCardBindRequest();
		request.setUserId(1000561);
		request.setSmsCode("1021");
		request.setOrderNo("123456");
		request.setVersion("v_1.0.0");
		BankCardBindResponse response = (BankCardBindResponse) client.execute(request);
		System.out.println(response.getException());
	}*/
	
	
/*	@Test
	public void balanceTest() {
		OpenClient client = new AppOpenClient("test");
		BalanceRequest request = new BalanceRequest();
		request.setUserId(1000561);
		BalanceResponse response = (BalanceResponse) client.execute(request);
		JsonArray jsonArray = new JsonArray();
		Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
	}*/
	
	/*@Test
	public void productTest() {
		OpenClient client = new AppOpenClient("test");
		ProductSingleRequest request = new ProductSingleRequest();
		request.setId(317);
		request.setVersion("v_1.0.0");
		request.setAppVersion("1.0");
		ProductSingleResponse response = (ProductSingleResponse) client.execute(request);
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
	}*/

/*	@Test
	public void productTest() {
		OpenClient client = new AppOpenClient("test");
		SafeCenterRequest request = new SafeCenterRequest();
		request.setUserId(1000561);
		request.setVersion("v_1.0.1");
		SafeCenterResponse response = (SafeCenterResponse) client.execute(request);
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
	}*/
	
	
	/*@Test
	public void productListIndexTest() {
		OpenClient client = new AppOpenClient("test");
		ProductListIndexRequest request = new ProductListIndexRequest();
		request.setVersion("v_1.0.1");
		request.setAppVersion("6.6.6");
		ProductListIndexResponse response = (ProductListIndexResponse) client.execute(request);
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
	}*/
	
//	@Test
//	public void productListReturnTypeTest() {
//		OpenClient client = new AppOpenClient("test");
//		ProductListReturnTypeRequest request = new ProductListReturnTypeRequest();
//		request.setVersion("v_1.0.2");
//		request.setAppVersion("6.0.2");
//		request.setReturnType("AVERAGE_CAPITAL_PLUS_INTEREST");
//		ProductListReturnTypeResponse response = (ProductListReturnTypeResponse) client.execute(request);
//
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}

	/*@Test
	public void newsListInfoTest() {
		OpenClient client = new AppOpenClient("test");
		NewsListInfoRequest request = new NewsListInfoRequest();
		request.setVersion("v_1.0.2");
		request.setAppVersion("v_6.0.2");
		request.setType("NOTICE");
		NewsListInfoResponse response = (NewsListInfoResponse) client.execute(request);
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
	}*/
	
/*	@Test
	public void productTest() {
		OpenClient client = new AppOpenClient("test");
		ProductSingleRequest request = new ProductSingleRequest();
		request.setId(317);
		request.setVersion("v_1.0.1");
		ProductSingleResponse response = (ProductSingleResponse) client.execute(request);
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
	}
	*/
	
/*	@Test
	public void productTest() {
		OpenClient client = new AppOpenClient("test");
		ProductListStatusFinish30Request request = new ProductListStatusFinish30Request();
		request.setReturnType("FINISH_RETURN_ALL");
		request.setPage(8);
		request.setVersion("v_1.0.0");
		ProductListStatusFinish30Response response = (ProductListStatusFinish30Response) client.execute(request);
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
	}
*/
	
	
	
/*	@Test
	public void balanceWithdrawTest() {
		OpenClient client = new AppOpenClient("test");
		BalanceWithdrawRequest request = new BalanceWithdrawRequest();
		request.setVersion("v_1.0.1");
		request.setUserId(2000035);
		request.setCardId(6054);
		request.setAmount(10.0);
		request.setPayPassword("123456");
		request.setTerminalType(4);
		
		BalanceWithdrawResponse response = (BalanceWithdrawResponse) client.execute(request);
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
	}*/
	
//	//我的资产页面	
//	@Test
//	public void myAssetTest() {
//		OpenClient client = new AppOpenClient("test");
//		MyAssetRequest request = new MyAssetRequest();
//		request.setUserId(1000561);
//		request.setAppVersion("v_2.4.0");
//		request.setVersion("v_1.0.3");
//		MyAssetResponse response = (MyAssetResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}

	
	//提现页面信息

/*	@Test
	public void withdrawalIndexTest() {
		OpenClient client = new AppOpenClient("test");
		WithdrawalIndexRequest request = new WithdrawalIndexRequest();
		request.setUserId(2000051);
		request.setVersion("v_1.0.1");
		WithdrawalIndexResponse response = (WithdrawalIndexResponse) client.execute(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
	}*/

	
	
	
//	@Test
//	public void loginTest() {
//		OpenClient client = new AppOpenClient("test");
//		LoginRequest request = new LoginRequest();
//		request.setMobile("13757121196");
//		request.setPassword("123123");
//		request.setVersion("v_1.0.0");
//		LoginResponse response = (LoginResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}
	
	
/*	@Test
	public void rechargeTest() {
		OpenClient client = new AppOpenClient("test");
		BalanceRechargeProRequest request = new BalanceRechargeProRequest();
		request.setUserId(1000561);
		request.setAmount(100.0);
		request.setBankId(3);
		request.setTerminalType(3);
		request.setVersion("v_1.0.0");
		BalanceRechargeProResponse response = (BalanceRechargeProResponse) client.execute(request);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
	}*/
	
//	@Test
//	public void redPocketTest() {
//		OpenClient client = new AppOpenClient("test");
//		MyRedPacketListRequest  request = new MyRedPacketListRequest();
//		//request.setAmount(100.00);
//		request.setUserId(2000051);
//		request.setProductId(635);
//		request.setStatus("INIT");
//		request.setVersion("v_1.0.0");
//        request.setAppVersion("5.5.5");
//		MyRedPacketListResponse  response = (MyRedPacketListResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}
	
//	@Test
//	public void checkBeforeBuyTest() {
//		OpenClient client = new AppOpenClient("test");
//		CheckBeforeBuyRequest  request = new CheckBeforeBuyRequest();
//
//		request.setUserId(1000561);
//		request.setProductId(478);
//
//		request.setVersion("v_1.0.0");
//        request.setAppVersion("5.5.5");
//        CheckBeforeBuyResponse  response = (CheckBeforeBuyResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}
	
	
//	@Test
//	public void appStartTest() {
//		OpenClient client = new AppOpenClient("test");
//		AppStartRequest request = new AppStartRequest();
//		request.setVersion("v_1.0.0");
//        request.setAppVersion("2.4.0");
//        AppStartResponse  response = (AppStartResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}
	
	
	//账户总资产
//	@Test
//	public void myAssetTest() {
//		OpenClient client = new AppOpenClient("test");
//		TotalAssetsRequest request = new TotalAssetsRequest();
//		request.setUserId(1000561);
//		request.setAppVersion("v_2.4.0");
//		request.setVersion("v_1.0.1");
//		TotalAssetsResponse response = (TotalAssetsResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}
	
//	//获取激活页面数据_存管改造
//	@Test
//	public void activatePageInfoTest() {
//		OpenClient client = new AppOpenClient("test");
//		ActivatePageInfoRequest request = new ActivatePageInfoRequest();
//		request.setUserId(1000561);
//		request.setAppVersion("v_2.4.0");
//		request.setVersion("v_1.0.0");
//		ActivatePageInfoResponse response = (ActivatePageInfoResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}
	
	
	//激活存管账户
//	@Test
//	public void activateDepAccountTest() {
//		OpenClient client = new AppOpenClient("test");
//		ActivateDepAccountRequest request = new ActivateDepAccountRequest();
//		request.setUserId(1000561);
//		request.setAppVersion("v_2.4.0");
//		request.setVersion("v_1.0.0");
//		request.setMobileCode("0653");
//		ActivateDepAccountResponse  response = (ActivateDepAccountResponse ) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}
	
	
	//账户余额_提现_页面信息_存管改造
//	@Test
//	public void activateDepAccountTest() {
//		OpenClient client = new AppOpenClient("test");
//		WithdrawalIndexRequest request = new WithdrawalIndexRequest();
//		request.setUserId(1000561);
//		request.setAppVersion("v_2.4.0");
//		request.setVersion("v_1.0.2");
//		WithdrawalIndexResponse  response = (WithdrawalIndexResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}
	
	//奖励金转出页面数据
//	@Test
//	public void myBonusWithdrawInfoTest() {
//		OpenClient client = new AppOpenClient("test");
//		MyBonusWithdrawInfoRequest request = new MyBonusWithdrawInfoRequest();
//		request.setUserId(1000561);
//		request.setAppVersion("v_2.4.0");
//		request.setVersion("v_1.0.0");
//		MyBonusWithdrawInfoResponse  response = (MyBonusWithdrawInfoResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}
	
	
//	//奖励金转出
//	@Test
//	public void myBonusWithdrawTest() {
//		OpenClient client = new AppOpenClient("test");
//		MyBonusWithdrawRequest request = new MyBonusWithdrawRequest();
//
//		request.setAppVersion("v_2.4.0");
//		request.setVersion("v_1.0.0");
//		request.setUserId(1000561);
//		request.setBonusAmount(20.0);
//		request.setCardId(6054);
//		request.setPayPassword("123456");
//		request.setTerminalType(4);
//
//		MyBonusWithdrawResponse  response = (MyBonusWithdrawResponse) client.execute(request);
//		Gson gson = new Gson();
//		String jsonStr = gson.toJson(response);
//		System.out.println(jsonStr);
//	}

    /*@Test
    public void myLoadMatchTest() {
        OpenClient client = new AppOpenClient("test");
        MyLoadMatchRequest request = new MyLoadMatchRequest();
        request.setVersion("v_2.4.0");
        request.setAppVersion("v_1.0.1");
        request.setPageIndex(1);
        request.setUserId(2000008);
        request.setProductId(3091);
        request.setSubAccountId(185744);

        MyLoadMatchResponse response = (MyLoadMatchResponse) client.execute(request);
        Gson gson = new Gson();
		String jsonStr = gson.toJson(response);
		System.out.println(jsonStr);
    }*/

    // 我的投资_明细(1支持标的)
   /* @Test
    public void myInvestmentTest() {
        OpenClient client = new AppOpenClient("test");
        MyInvestmentRequest investmentRequest = new MyInvestmentRequest();
        investmentRequest.setUserId(10001605);
        investmentRequest.setVersion("v_1.0.1"); //接口版本号
        investmentRequest.setAppVersion("v_2.4.0");
        investmentRequest.setPageIndex(0);
        MyInvestmentResponse response = (MyInvestmentResponse) client.execute(investmentRequest);
        Gson gson = new Gson();
        String jsonStr = gson.toJson(response);
        System.out.println(jsonStr);
    }*/


//    @Test
//    public void myInvestmentTest() {
//        OpenClient client = new AppOpenClient("test");
//        MyInvestmentRequest investmentRequest = new MyInvestmentRequest();
//        investmentRequest.setUserId(10001605);
//        investmentRequest.setVersion("v_1.0.1"); //接口版本号
//        investmentRequest.setAppVersion("v_2.4.0");
//        investmentRequest.setPageIndex(0);
//        MyInvestmentResponse response = (MyInvestmentResponse) client.execute(investmentRequest);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(response);
//        System.out.println(jsonStr);
//    }


    // 首页接口 index/v_1.0.4
//    @Test
//    public void indexPageTest() {
//        OpenClient client = new AppOpenClient("test");
//        IndexRequest indexRequest = new IndexRequest();
//        indexRequest.setUserType("NORMAL");
//        indexRequest.setUserId("1001605");
//        indexRequest.setVersion("v_1.0.4"); //接口版本号
//        indexRequest.setAppVersion("v_2.4.0");
//        IndexResponse response = (IndexResponse) client.execute(indexRequest);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(response);
//        System.out.println(jsonStr);
//    }

    // 产品列表（分级）-根据回款类型查产品列表 productListReturnType/v_1.0.2
//    @Test
//    public void productListReturnTypeTest() {
//        OpenClient client = new AppOpenClient("test");
//        ProductListReturnTypeRequest productListReturnTypeRequest = new ProductListReturnTypeRequest();
//        productListReturnTypeRequest.setReturnType("FINISH_RETURN_ALL ");
//        productListReturnTypeRequest.setVersion("v_1.0.2"); //接口版本号
//        productListReturnTypeRequest.setAppVersion("v_2.4.0");
//        ProductListReturnTypeResponse response = (ProductListReturnTypeResponse) client.execute(productListReturnTypeRequest);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(response);
//        System.out.println(jsonStr);
//    }

    // 产品详情页 selectSingleProduct/v_1.0.1
//    @Test
//    public void selectSingleProductTest() {
//        OpenClient client = new AppOpenClient("test");
//        ProductSingleRequest productSingleRequest = new ProductSingleRequest();
//        productSingleRequest.setId(3386); // 3386  3393
//        productSingleRequest.setVersion("v_1.0.1"); //接口版本号
//        productSingleRequest.setAppVersion("v_2.4.0");
//        ProductSingleResponse response = (ProductSingleResponse) client.execute(productSingleRequest);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(response);
//        System.out.println(jsonStr);
//    }

    // 解绑卡申请_人脸核身验证v_1.0.0
//    @Test
//    public void unbindApplyPoliceVerifyTest() {
//
//        OpenClient client = new AppOpenClient("test");
//        UnbindApplyPoliceVerifyRequest unbindApplyPoliceVerifyRequest = new UnbindApplyPoliceVerifyRequest();
//        unbindApplyPoliceVerifyRequest.setUserId(2000045);
//        unbindApplyPoliceVerifyRequest.setBankId(14145);
//        unbindApplyPoliceVerifyRequest.setVerifyResult("success");
//        unbindApplyPoliceVerifyRequest.setScore(90);
//        unbindApplyPoliceVerifyRequest.setVersion("v_1.0.0"); //接口版本号
//        unbindApplyPoliceVerifyRequest.setAppVersion("v_2.4.0");
//        UnbindApplyPoliceVerifyResponse response = (UnbindApplyPoliceVerifyResponse) client.execute(unbindApplyPoliceVerifyRequest);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(response);
//        System.out.println(jsonStr);
//    }

    // 人脸核身验证上传检测图片v_1.0.0
//    @Test
//    public void uploadPicPoliceVerify() {
//
//        OpenClient client = new AppOpenClient("test");
//        UploadPicPoliceVerifyRequest request = new UploadPicPoliceVerifyRequest();
//        request.setUserId(2000045);
//        request.setSerialNo("15272387499122000045096954233");
//        request.setIdCardFrontPic("1");
//        request.setIdCardBackPic("2");
//        request.setLivenessPicBlink("3");
//        request.setLivenessPicDropHead("4");
//        request.setLivenessPicLeftHead("5");
//        request.setLivenessPicRaiseHead("6");
//        request.setLivenessPicRightHead("7");
//        request.setLivenessPicSay("8");
//        request.setLivenessPicShakeHead("9");
//        request.setVersion("v_1.0.0"); //接口版本号
//        request.setAppVersion("v_2.4.0");
//        UploadPicPoliceVerifyResponse response = (UploadPicPoliceVerifyResponse) client.execute(request);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(response);
//        System.out.println(jsonStr);
//    }


//	@Test
//    public void unbindCheckTest() {
//
//        OpenClient client = new AppOpenClient("test");
//        BankCardUnbindCheckRequest unbindCheckRequest = new BankCardUnbindCheckRequest();
//        unbindCheckRequest.setUserId(2000045);
//        unbindCheckRequest.setBankCardId(14145);
//        unbindCheckRequest.setVersion("v_1.0.0"); //接口版本号
//        unbindCheckRequest.setAppVersion("v_2.4.0");
//        BankCardUnbindCheckResponse response = (BankCardUnbindCheckResponse) client.execute(unbindCheckRequest);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(response);
//        System.out.println(jsonStr);
//    }


//    // 人脸核身验证上传检测图片v_1.0.0
//    @Test
//    public void uploadPicPoliceVerify() {
//
//        OpenClient client = new AppOpenClient("test");
//        TokenPoliceVerifyRequest request = new TokenPoliceVerifyRequest();
//        request.setVersion("v_1.0.0"); //接口版本号
//        request.setAppVersion("v_2.4.0");
//        TokenPoliceVerifyResponse response = (TokenPoliceVerifyResponse) client.execute(request);
//        Gson gson = new Gson();
//        String jsonStr = gson.toJson(response);
//        System.out.println(jsonStr);
//    }
}
