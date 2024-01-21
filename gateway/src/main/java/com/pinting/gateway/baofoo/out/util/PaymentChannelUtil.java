package com.pinting.gateway.baofoo.out.util;

import java.util.HashMap;
import java.util.Map;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.mobile.in.util.Constants;

/**
 * 支付渠道信息工具
 * @author bianyatian
 * @2017-12-7 上午10:22:02
 */
public class PaymentChannelUtil {

	public static Map<String,PaymentChannelInfo> channelInfoMap = new HashMap<String, PaymentChannelInfo>(){
		{
			//主商户号信息
			PaymentChannelInfo channelInfoMain = new PaymentChannelInfo();
			channelInfoMain.setMerchantNo(GlobEnvUtil.get("baofoo.member.id"));
			channelInfoMain.setCutPaymentterminalId(GlobEnvUtil.get("baofoo.cut.terminal.id"));
			channelInfoMain.setPfxpath(GlobEnvUtil.get("baofoo.pfx.url"));
			channelInfoMain.setPfxpwd(GlobEnvUtil.get("baofoo.pfx.pwd"));
			channelInfoMain.setCutPaymentcerPath(GlobEnvUtil.get("baofoo.cut.cer.url"));
			channelInfoMain.setCutVersion(GlobEnvUtil.get("baofoo.cut.version"));
			channelInfoMain.setPay4cerpath(GlobEnvUtil.get("baofoo.pay4.cer.url"));
			channelInfoMain.setPay4terminalId(GlobEnvUtil.get("baofoo.pay4.terminal.id"));
			
			channelInfoMain.setSinglePayTerminalId(GlobEnvUtil.get("baofoo.agreementpay.singlepay.terminalid"));//协议支付终端号
			channelInfoMain.setSinglePayDesKey(GlobEnvUtil.get("baofoo.agreementpay.singlepay.aeskey"));//协议支付数字信封desKey
			channelInfoMain.setSinglePayCerPath(GlobEnvUtil.get("baofoo.agreementpay.singlepay.cerpath"));//协议支付公钥路径
			channelInfoMain.setSinglePayPfxPath(GlobEnvUtil.get("baofoo.agreementpay.singlepay.pfxpath"));//协议支付私钥路径
			channelInfoMain.setSinglePayPfxPwd(GlobEnvUtil.get("baofoo.agreementpay.singlepay.pfxpwd"));//协议支付私钥密码
			channelInfoMain.setSinglePayVersion(GlobEnvUtil.get("baofoo.agreementpay.singlepay.version")); //协议支付报文版本号
			
			put(GlobEnvUtil.get("baofoo.member.id"),channelInfoMain);
			
			PaymentChannelInfo channelInfoAssist = new PaymentChannelInfo();
			channelInfoAssist.setMerchantNo(GlobEnvUtil.get("baofoo.assist.member.id"));
			channelInfoAssist.setCutPaymentterminalId(GlobEnvUtil.get("baofoo.assist.cut.terminal.id"));
			channelInfoAssist.setPfxpath(GlobEnvUtil.get("baofoo.assist.pfx.url"));
			channelInfoAssist.setPfxpwd(GlobEnvUtil.get("baofoo.assist.pfx.pwd"));
			channelInfoAssist.setCutPaymentcerPath(GlobEnvUtil.get("baofoo.assist.cut.cer.url"));
			channelInfoAssist.setCutVersion(GlobEnvUtil.get("baofoo.assist.cut.version"));
			channelInfoAssist.setPay4cerpath(GlobEnvUtil.get("baofoo.assist.pay4.cer.url"));
			channelInfoAssist.setPay4terminalId(GlobEnvUtil.get("baofoo.assist.pay4.terminal.id"));
			
			channelInfoAssist.setSinglePayTerminalId(GlobEnvUtil.get("baofoo.assist.agreementpay.singlepay.terminalid"));//协议支付终端号
			channelInfoAssist.setSinglePayDesKey(GlobEnvUtil.get("baofoo.assist.agreementpay.singlepay.aeskey"));//协议支付数字信封desKey
			channelInfoAssist.setSinglePayCerPath(GlobEnvUtil.get("baofoo.assist.agreementpay.singlepay.cerpath"));//协议支付公钥路径
			channelInfoAssist.setSinglePayPfxPath(GlobEnvUtil.get("baofoo.assist.agreementpay.singlepay.pfxpath"));//协议支付私钥路径
			channelInfoAssist.setSinglePayPfxPwd(GlobEnvUtil.get("baofoo.assist.agreementpay.singlepay.pfxpwd"));//协议支付私钥密码
			channelInfoAssist.setSinglePayVersion(GlobEnvUtil.get("baofoo.assist.agreementpay.singlepay.version"));//协议支付报文版本号
			put(GlobEnvUtil.get("baofoo.assist.member.id"),channelInfoAssist);
			
		}
	};
}
