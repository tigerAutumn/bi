package com.pinting.business.test;

import org.junit.Test;

/**
 * @Project: gateway
 * @Title: TestDafy.java
 * @author Zhou Changzai
 * @date 2015-2-12 下午3:31:50
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class
TestDafy extends TestBase {
	//@Autowired
	//private SysBatchBuyProductService sysBatchBuyProductService;


	@Test
	// 登录
	public void testLogin2Dafy() {
		//TODO 19付钱包转账
//		Date now=new Date();
//		//发起19付钱包转账
//		AcctTransReq acctTransReq = new AcctTransReq();
//		acctTransReq.setAccountFrom("wangyy@19pay.com.cn");
////		acctTransReq.setAccountFrom("zhangqing@dafy.com");
//		acctTransReq.setAccountTo("caitx@19pay.com.cn");
////		acctTransReq.setAccountTo("caiwuzhichi2@7daichina.com");
//		acctTransReq.setNotifyUrl("http://121.43.110.214:8084/remote/pay19/acctTrans/notice");
//		acctTransReq.setOrderAmount(new DecimalFormat("0.00").format(2000000d));
//		acctTransReq.setOrderDate(DateUtil.formatDateTime(now,
//				"yyyyMMddHHmmss"));
//		acctTransReq.setOrderId(generateSysOrderNo("TS"));
//		System.out.println(acctTransReq.getOrderId());
//		acctTransReq.setRemarks("正式环境转账测试");
//		acctTransReq.setToAcctName("蔡天鲜");
////		acctTransReq.setToAcctName("深圳市前海龙汇通互联网金融服务有限公司");
//		acctTransReq.setToAcctType("ENTPRISE");
//		acctTransReq.setTradeDesc("正式环境转账测试");
//		acctTransReq.setTradeType("TRANSFER");
//
//		AcctTransResp resp =pay19Service.acctTrans(acctTransReq);
//		System.out.println(resp.getMpOrderId());
//		System.out.println(resp.getOrderId());
//		System.out.println(resp.getReqStatus());
//		System.out.println(resp.getTradeResult());


//		QueryRecvAcctTransReq transReq = new QueryRecvAcctTransReq();
//		transReq.setMerchantId("190044");
//		transReq.setOriOutMxId("190044");
//		transReq.setOriOutOrderDate(DateUtil.formatDateTime(new Date(), "yyyyMMdd"));
//		transReq.setOriOutOrderId(generateSysOrderNo("TS"));
//		transReq.setTradeType("TRANSFER");
//		transReq.setTs(DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
//
//		QueryRecvAcctTransResp response = pay19Service.queryRecvAcctTrans(transReq);
//		System.out.print(response.getOrderAmount());

//		购买7贷产品
//		SysBatchBuyProductReqModel req = new SysBatchBuyProductReqModel();
//		req.setCustomerId("bgw_dafy_customer_id");
//		req.setPayPlatform("19PAY");
//		req.setMerchantId(resp.getMerchantId());
//		req.setPayOrderNo(resp.getOrderId());
//		req.setPayReqTime(DateUtil.formatDateTime(now,"yyyy-MM-dd HH:mm:ss"));
//		req.setPayFinishTime(DateUtil.formatDateTime(now,"yyyy-MM-dd HH:mm:ss"));
//		req.setAmount(2000000d);
//		List<Products> products = new ArrayList<Products>();
//		Products p1 = new Products();
//		p1.setProductAmount(500000.0d);
//		p1.setProductCode("2030");
//		p1.setProductOrderNo("201607130001");
//		Products p2 = new Products();
//		p2.setProductAmount(700000.0d);
//		p2.setProductCode("2090");
//		p2.setProductOrderNo("201607130002");
//		Products p3 = new Products();
//		p3.setProductAmount(200000.0d);
//		p3.setProductCode("2365");
//		p3.setProductOrderNo("201607130003");
//		Products p4 = new Products();
//		p4.setProductAmount(600000.0d);
//		p4.setProductCode("2180");
//		p4.setProductOrderNo("201607130004");
//		products.add(p1);
//		products.add(p2);
//		products.add(p3);
//		products.add(p4);
//		req.setProducts(products);
//		SysBatchBuyProductResModel res = service.sysBatchBuyProduct(req);
//		System.out.println(res.getRespCode());

//		//查询个人债券匹配
//		QueryRepayJnlReqModel repayJnlReqModel=new QueryRepayJnlReqModel();
//		repayJnlReqModel.setBorrowNo("16053146811877820106");
//		repayJnlReqModel.setCustomerId("bgw_dafy_customer_id");
//		QueryRepayJnlResModel model=service.queryRepayJnl(repayJnlReqModel);
//		System.out.println(model.getResponseTime());

//		sysBatchBuyProductService.sysAcctTrans4BatchBuyAll(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI);
		//sysBatchBuyProductService.sysAcctTrans4BatchBuyAll(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
	}

	// @Test//到达飞注册
	// public void testRegister2Dafy(){
	// B2GReqMsg_Customer_Register req = new B2GReqMsg_Customer_Register();
	// B2GResMsg_Customer_Register res = new B2GResMsg_Customer_Register();
	//
	// req.setIdCard("330226198808122211");
	// req.setName("张三");
	// req.setMobile("13777123456");
	//
	// customerFacade.register(req, res);
	//
	// System.out.println("调用达飞注册接口, 返回customerId=" + res.getCustomerId());
	// }

	/*
	 * @Test public void testDafyLogin(){ String url =
	 * "http://115.236.48.172/remote/dafy/business"; LoginReqModel req = new
	 * LoginReqModel(); req.setClientKey("abc"); req.setClientSecret("xyz");
	 * req.setTransCode("login");
	 * 
	 * String encryptStr = DafyOutMsgUtil.packageMsg(req); HashMap<String,
	 * String> map = new HashMap<String, String>(); map.put("DATA", encryptStr);
	 * String retJson = HttpClientUtil.sendRequest(url, map);
	 * System.out.println("返回的数据密文：" + retJson);
	 * 
	 * LoginResModel res = (LoginResModel)DafyOutMsgUtil.parseMsg(retJson,
	 * "login"); System.out.println("返回结果" + res.getRespMsg()); }
	 */

//	@Autowired
//	SendDafyService dafySendService;

	@Test
	/**
	 * 对账测试
	 */
	public void testDafyCheckAccount() {
		/*
		 * B2GReqMsg_Account_CheckAccount req = new
		 * B2GReqMsg_Account_CheckAccount();
		 * req.setQueryDate(DateUtil.parseDate(
		 * DateUtil.format(DateUtil.addDays(new Date(), 0))));
		 * 
		 * // 组装报文 CheckAccountReqModel reqModel = new CheckAccountReqModel();
		 * reqModel.setQueryDate(req.getQueryDate()); // 发送给达飞
		 * CheckAccountResModel dafyRes =
		 * dafySendService.checkAccount(reqModel); // 返回信息
		 * List<InvestmentAmounts> list = dafyRes.getInvestmentAmounts(); for
		 * (InvestmentAmounts investmentAmounts : list) {
		 * System.out.println(investmentAmounts
		 * .getProductCode()+investmentAmounts
		 * .getProductName()+investmentAmounts.getAmount()); }
		 */
	}

//	@Autowired
//	private HessianService gatewayDafyService;

	@Test
	public void testBusinessBuy() {
		
		 /*G2BReqMsg_DafyPayment_BuyProductResult req = new G2BReqMsg_DafyPayment_BuyProductResult(); 
		 req.setAmount(1);
		 req.setOrderNo("150317000000853"); 
		 req.setResult(1);
		 req.setBankName("工商银行YZ"); 
		 req.setMoneyType(0);
		 req.setSuccessTime(DateUtil.parseDateTime("2015-03-18 14:41:58"));
		 G2BResMsg_DafyPayment_BuyProductResult res =
		 (G2BResMsg_DafyPayment_BuyProductResult) gatewayDafyService.handleMsg(req);
		 */
	}

	@Test
	public void testBusinessRepay() throws ClassNotFoundException {
		/*String jsonStr = "{\"token\":\"a7e8e0c04d70369ea1f19423fe48d613\",\"transCode\":\"receiveMoneyNotice\",\"requestTime\":\"2015-04-03 14:11:49\",\"data\":[{\"cardNo\":\"3123123123132\",\"bankNo\":\"324273492834923\",\"orderNo\":\"150317000000858\",\"status\":0,\"successTime\":\"2015-04-03 11:39:27\",\"name\":\"请原谅\",\"amountLx\":0.11,\"amountBj\":100,\"productId\":3}],\"hash\":\"53e598124f4864c741937a72a8685e78\"}";
		BaseReqModel model = null;
		// String 转 json
		// 获得 报文bean类型名称
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,
				new JsonValueProcessorImpl());
		JSONObject jsonObject = JSONObject.fromObject(jsonStr, config);
		String transCode = jsonObject.getString("transCode");
		StringBuffer modelClassName = new StringBuffer(
				Constants.DAFY2GATEWAY_MESSAGE_MODEL_PRE)
				.append(transCode.substring(0, 1).toUpperCase())
				.append(transCode.substring(1)).append("ReqModel");
		// json 2 bean
		Class modelClass = Class.forName(modelClassName.toString());
		// 判断是否需要嵌套转换
		String listName = null;
		String listClassName = null;
		Field[] fields = modelClass.getDeclaredFields();
		for (Field field : fields) {
			String fieldClassName = field.getType().getName();
			if (List.class.getName().equals(fieldClassName)) {
				listName = field.getName();// 获取列表数据变量名
				Type type = field.getGenericType();// 获取List列表变量类型
				if (type instanceof ParameterizedType) {
					ParameterizedType pt = (ParameterizedType) type;
					Class<?> c = (Class<?>) pt.getActualTypeArguments()[0];
					listClassName = c.getName();
				}
				break;
			}
		}
		if (listName != null) {// 如果有信息列表（嵌套转换）
			model = (BaseReqModel) JsonLibUtil.json2Bean(jsonStr,
					Class.forName(modelClassName.toString()), listName,
					Class.forName(listClassName));
		} else {
			model = (BaseReqModel) JsonLibUtil.json2Bean(jsonStr,
					Class.forName(modelClassName.toString()));
		}*/
		
		
		/*BaseReqModel model = DafyInMsgUtil.parseMsg("olTfcG+7yJ2vpUigC3KJ1QaB2+k1XMXE0E4pvS1RavfcL0i3EqcJlTp/tF7OyWenr63YR3P8JW32BD8o2Z68849wDKll3OfWFR539PJnJ3dyb41DKdaRIA+Iw6acmEH4rsdP2z03ENo86BcPDHQboCGXN2aYoxsXKyaQjChAnDlLugKqBR4Z6jI5LeV6d6IpDuwATYjspDciMieS9AsDH1gTypPctAW7R5pSMLOZjJvsDu5OFkQOjTIg6GBiKI0Clm4R8CL0Dp9tnLGw5RaWHkeTwP/n1jcEaaDBVaouwDmORoGiUt6qzyz2OShDBF8kJW5PmJL+m/GeeIsiUIDXMld/nQwNCmgcysjrf1yWQF/lQWfAgxtS5mtcTxDripYtHMSNlS1emYOVFbJK1pkDAUaWP5Sj1xbv/QWT5L05TY9GwzWaduq1kbOjZzENOO1PhU0SEgS6vPqnbr1ImKvPuVdNg9BLDdCHHmnCq8MQyVn+krtRX7SNiJeQYdxEkDv1wS7TTsIDYALv66KPOuzDasK0VMUgh6ULN51DgyajYkAf18QrGWvuC/GBuV4wEiIYUi7mRpXq+bS27OHZk3SSg1TisN1JT3d8D142OC9CaKv79TSzvxEBhnEoCmbf1BHfnslC9bMn219Hpykxz88huKol0geVrWomOZdNs4NREgX21hfuliZdFTXddKqCH9Xl0mcGuPoNqvAQWqdM8nUV1LbgeaTCgNQTgJpbQXr3aWgc0yuHtr9q+kl8XGoIzxfHhbdNREcLtkxwWbKbo4+B0c+h7CMkkDWCyjrh3+mPYGeFDEEdLB5InpxNUbrobEGN20jTNSoB9ec=");
		
		ReceiveMoneyNoticeReqModel reqModel = (ReceiveMoneyNoticeReqModel)model;
		G2BReqMsg_Customer_ReceiveMoney req = new G2BReqMsg_Customer_ReceiveMoney();
		req.setDataList(reqModel.getData());
		G2BResMsg_Customer_ReceiveMoney res = (G2BResMsg_Customer_ReceiveMoney) gatewayDafyService.handleMsg(req);
		System.out.println("...........");*/
	}
	
	@Test
	public void testRepayJnl() {
		/*QueryRepayJnlReqModel req = new QueryRepayJnlReqModel();
		req.setBorrowNo("12300005058");
		req.setCustomerId("1232693");
		QueryRepayJnlResModel res = dafySendService.queryRepayJnl(req);
		
		System.out.println(res);*/
		
	}

}
