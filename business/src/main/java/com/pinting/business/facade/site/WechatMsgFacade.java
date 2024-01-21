package com.pinting.business.facade.site;

import com.pinting.business.service.manage.BsRedPackatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_WechatMsg_sendMesg;
import com.pinting.business.hessian.site.message.ResMsg_WechatMsg_sendMesg;
import com.pinting.business.service.site.SendWechatService;

import java.util.ArrayList;
import java.util.List;

@Component("WechatMsg")
public class WechatMsgFacade {

	@Autowired
	private SendWechatService sendWechatService;
	@Autowired
	private BsRedPackatInfoService bsRedPackatInfoService;

	public void sendMesg(ReqMsg_WechatMsg_sendMesg req,ResMsg_WechatMsg_sendMesg res){
		//这里把openid当做 userId传入
		//String return1 = sendWechatService.paymentMgs2Balance(Integer.parseInt(req.getOpenId()),"","1020","1000","20");
		String bankCardNo = "1234567890";
		bankCardNo = bankCardNo.substring(bankCardNo.length()-4,bankCardNo.length());
		/*String return8 = sendWechatService.paymentMgs2Card(Integer.parseInt(req.getOpenId()),"","1020","1000","20",bankCardNo);
		String return2 = sendWechatService.buyProductMgs(Integer.parseInt(req.getOpenId()), "", "币港湾定期投资", "9.0%", "50000", "30", "suc",null);
		String return6 = sendWechatService.buyProductMgs(Integer.parseInt(req.getOpenId()), "", "币港湾定期投资", "9.0%", "50000", "30", "fall","");
		String return3 = sendWechatService.topUpMgs(Integer.parseInt(req.getOpenId()),"", "15990047160", "3000", "suc", null);
		String return4 = sendWechatService.topUpMgs(Integer.parseInt(req.getOpenId()),"", "15990047160", "3000", "fall", "银行卡余额不足");
		String return5 = sendWechatService.withdrawMgs(Integer.parseInt(req.getOpenId()),"", "0602", "3005", "suc",null);
		String return7 = sendWechatService.withdrawMgs(Integer.parseInt(req.getOpenId()),"", "0602", "3005", "fall","");
		*/
		sendWechatService.packetSend(Integer.parseInt(req.getOpenId()), "100", "女神节大礼包");


		List<Integer> userIds = new ArrayList<>();
		userIds.add(Integer.parseInt(req.getOpenId()));
		bsRedPackatInfoService.sendRedPacketMessage(3, null, userIds, "红包测试", "1");
		/*res.setResMsg(return1);
		res.setResMsg(return8);
		System.out.println(return1+return8);*/
	}
}
