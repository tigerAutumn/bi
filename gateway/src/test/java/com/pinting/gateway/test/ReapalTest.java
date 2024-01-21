package com.pinting.gateway.test;

import com.pinting.gateway.reapal.out.config.ReapalConfig;
import com.pinting.gateway.reapal.out.model.req.QueryOrderResultReq;
import com.pinting.gateway.reapal.out.model.resp.QueryOrderResultResp;
import com.pinting.gateway.reapal.out.service.QuickPayServiceClient;
import com.pinting.gateway.reapal.out.service.impl.QuickPayServiceClientImpl;

public class ReapalTest {

	public static void main(String[] args) {
		//储蓄卡签约接口
//		MemoryCardSignReq req = new MemoryCardSignReq();
//		req.setMerchant_id(ReapalConfig.merchant_id);
////		req.setCard_no("6217856200024603701");
////		req.setOwner("张庆");
////		req.setCert_no("42100219790105051X");
////		req.setPhone("13588449807");
//		req.setCard_no("6217710801576471");
//		req.setOwner("朱媛");
//		req.setCert_no("330781198910221128");
//		req.setPhone("15158191349");
//		req.setCert_type("01");
//		req.setOrder_no(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//		req.setTranstime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//		req.setCurrency("156");
//		req.setTitle("储蓄卡签约测试");
//		req.setBody("储蓄卡签约测试");
//		req.setMember_id("1111111111");
//		req.setTerminal_type("web");
//		req.setTerminal_info("null_B4-6D-83-C3-8F-C0_null");
//		req.setMember_ip("183.129.222.138");
//		req.setSeller_email(ReapalConfig.seller_email);
//		req.setToken_id(ReapalUtil.getUUID());
//		req.setTotal_fee(10000);
//		req.setNotify_url(ReapalConfig.notify_url);
//		QuickPayServiceClient service = new QuickPayServiceClientImpl();
//		try {
//			MemoryCardSignResp resp = service.memoryCardSignPreOrder(req);
//			System.out.println(resp.getOrder_no());
//			System.out.println(resp.getResult_code()+":"+resp.getResult_msg()+":"+resp.getBind_id());
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
		//绑定签约接口
//		BindCardSignReq req = new BindCardSignReq();
//		req.setMerchant_id(ReapalConfig.merchant_id);
//		req.setMember_id("1111111111");
//		req.setBind_id("5369");
//		req.setOrder_no(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
//		req.setTranstime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//		req.setCurrency("156");
//		req.setTotal_fee(1);
//		req.setTitle("绑定签约测试");
//		req.setBody("绑定签约测试");
//		req.setTerminal_type("web");
//		req.setTerminal_info("null_B4-6D-83-C3-8F-C0_null");
//		req.setMember_ip("83.129.222.138");
//		req.setSeller_email(ReapalConfig.seller_email);
//		req.setToken_id(ReapalUtil.getUUID());
//		req.setNotify_url(ReapalConfig.notify_url);
//		QuickPayServiceClient service = new QuickPayServiceClientImpl();
//		try {
//			BindCardSignResp resp = service.bindCardSignPreOrder(req);
//			System.out.println(resp.getResult_code()+":"+resp.getResult_msg());
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
		//确认支付接口
//		SubmitPayReq req = new SubmitPayReq();
//		req.setMerchant_id(ReapalConfig.merchant_id);
//		req.setOrder_no("20160109111726");
//		req.setCheck_code("817458");
//		QuickPayServiceClient service = new QuickPayServiceClientImpl();
//		try {
//			SubmitPayResp resp = service.submitPay(req);
//			System.out.println(resp.getResult_code()+":"+resp.getResult_msg()+":"+resp.getBind_id());
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
		//解绑卡接口
//		CancelCardReq req = new CancelCardReq();
//		req.setMerchant_id(ReapalConfig.merchant_id);
//		req.setMember_id("1111111111");
//		req.setBind_id("5369");
//		QuickPayServiceClient service = new QuickPayServiceClientImpl();
//		try {
//			CancelCardResp resp = service.cancelCard(req);
//			System.out.println(resp.getResult_code()+":"+resp.getResult_msg()+":"+resp.getBind_id());
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
		
		//支付结果查询接口
		QueryOrderResultReq req = new QueryOrderResultReq();
		req.setMerchant_id(ReapalConfig.merchant_id);
		req.setOrder_no("201601211825418246086861642");
		QuickPayServiceClient service = new QuickPayServiceClientImpl();
		try {
			QueryOrderResultResp resp = service.queryOrderRusult(req);
			System.out.println(resp.getResult_code()+":"+resp.getResult_msg()+":"+resp.getStatus());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
		//重发短信验证码
//		ResendCodeReq req = new ResendCodeReq();
//		req.setMerchant_id(ReapalConfig.merchant_id);
//		req.setOrder_no("20160121172458");
//		QuickPayServiceClient service = new QuickPayServiceClientImpl();
//		try {
//			ResendCodeResp resp = service.resendCode(req);
//			System.out.println(resp.getResult_code()+":"+resp.getResult_msg());
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
	}

}
