package com.pinting.gateway.in.facade.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_Agreement_GetList;
import com.pinting.business.hessian.site.message.ResMsg_Agreement_GetList;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.in.util.InterfaceVersion;

/**
 * 
 * @author zhangpeng
 * @date 2018/05/03
 * 
 */
@Component("appAgreement")
public class AgreementFacade {
	@Autowired	
	private BsSysConfigService bsSysConfigService;
	
	@InterfaceVersion("GetList/1.0.0")
	public void getList(ReqMsg_Agreement_GetList req, ResMsg_Agreement_GetList res){
		//注册页面《注册账户服务协议》
		BsSysConfig registerAgreement = bsSysConfigService.findKey(Constants.REGISTER_AGREEMENT);
		res.setRegisterAgreement(registerAgreement.getConfValue());
		//购买页面港湾计划《授权委托书》
		BsSysConfig buyAgreementGW = bsSysConfigService.findKey(Constants.BUY_AGREEMENT_GW);
		res.setBuyAgreementGW(buyAgreementGW.getConfValue());
		//购买页面港湾计划《风险提示》
		BsSysConfig riskWarningGW = bsSysConfigService.findKey(Constants.RISK_WARNING_GW);
		res.setRiskWarningGW(riskWarningGW.getConfValue());
		//购买页面赞分期《自动出借计划协议》
		BsSysConfig buyAgreementZAN = bsSysConfigService.findKey(Constants.BUY_AGREEMENT_ZAN);
		res.setBuyAgreementZAN(buyAgreementZAN.getConfValue());
		//购买页面赞分期《风险提示》
		BsSysConfig riskWarningZAN = bsSysConfigService.findKey(Constants.RISK_WARNING_ZAN);
		res.setRiskWarningZAN(riskWarningZAN.getConfValue());
		//购买页面赞时贷《自动出借计划协议》
		BsSysConfig buyAgreementZSD = bsSysConfigService.findKey(Constants.BUY_AGREEMENT_ZSD);
		res.setBuyAgreementZSD(buyAgreementZSD.getConfValue());
		//购买页面赞时贷《风险提示》
		BsSysConfig riskWarningZSD = bsSysConfigService.findKey(Constants.RISK_WARNING_ZSD);
		res.setRiskWarningZSD(riskWarningZSD.getConfValue());
		//计划管理页面定期类计划《授权委托书》,powerAttorneyFlag为yes
		BsSysConfig regularAgreementYes = bsSysConfigService.findKey(Constants.REGULAR_AGREEMENT_YES);
		res.setRegularAgreementYes(regularAgreementYes.getConfValue());
		//计划管理页面定期类计划《债权明细》,powerAttorneyFlag为yes
		BsSysConfig regularDetailsYes = bsSysConfigService.findKey(Constants.REGULAR_DETAILS_YES);
		res.setRegularDetailsYes(regularDetailsYes.getConfValue());
		//计划管理页面定期类计划《授权委托书》,powerAttorneyFlag为no
		BsSysConfig regularAgreementNo = bsSysConfigService.findKey(Constants.REGULAR_AGREEMENT_NO);
		res.setRegularAgreementNo(regularAgreementNo.getConfValue());
		//计划管理页面定期类计划《债权明细》,powerAttorneyFlag为no
		BsSysConfig regularDetailsNo = bsSysConfigService.findKey(Constants.REGULAR_DETAILS_NO);
		res.setRegularDetailsNo(regularDetailsNo.getConfValue());
		//计划管理页面分期类计划《自动出借计划协议》
		BsSysConfig instalmentAgreement = bsSysConfigService.findKey(Constants.INSTALMENT_AGREEMENT);
		res.setInstalmentAgreement(instalmentAgreement.getConfValue());
		//计划管理页面分期类计划《债权明细》
		BsSysConfig instalmentDetail = bsSysConfigService.findKey(Constants.INSTALMENT_DETAILS);
		res.setInstalmentDetail(instalmentDetail.getConfValue());
		//充值页面《支付协议》和《授权委托书》
		BsSysConfig paymentAgreement = bsSysConfigService.findKey(Constants.PAYMENT_AGREEMENT);
		res.setPaymentAgreement(paymentAgreement.getConfValue());
		//激活页面《网络交易资金存管账户服务协议》
		BsSysConfig activateAgreement = bsSysConfigService.findKey(Constants.ACTIVATE_AGREEMENT);
		res.setActivateAgreement(activateAgreement.getConfValue());
		//开通页面《网络交易资金存管账户服务协议》
		BsSysConfig openAgreement = bsSysConfigService.findKey(Constants.OPEN_AGREEMENT);
		res.setOpenAgreement(openAgreement.getConfValue());
		//标题
		//注册页面《注册账户服务协议》
		BsSysConfig registerAgreementTitle = bsSysConfigService.findKey(Constants.REGISTER_AGREEMENT_TITLE);
		res.setRegisterAgreementTitle(registerAgreementTitle.getConfValue());
		//购买页面港湾计划《授权委托书》
		BsSysConfig buyAgreementTitleGW = bsSysConfigService.findKey(Constants.BUY_AGREEMENT_TITLE_GW);
		res.setBuyAgreementTitleGW(buyAgreementTitleGW.getConfValue());
		//购买页面港湾计划《风险提示》
		BsSysConfig riskWarningTitleGW = bsSysConfigService.findKey(Constants.RISK_WARNING_TITLE_GW);
		res.setRiskWarningTitleGW(riskWarningTitleGW.getConfValue());
		//购买页面赞分期   自动出借计划协议（模板）
		BsSysConfig buyAgreementTitleZAN = bsSysConfigService.findKey(Constants.BUY_AGREEMENT_TITLE_ZAN);
		res.setBuyAgreementTitleZAN(buyAgreementTitleZAN.getConfValue());
		//购买页面赞分期《风险提示》
		BsSysConfig riskWarningTitleZAN = bsSysConfigService.findKey(Constants.RISK_WARNING_TITLE_ZAN);
		res.setRiskWarningTitleZAN(riskWarningTitleZAN.getConfValue());
		//购买页面赞时贷《自动出借计划协议》
		BsSysConfig buyAgreementTitleZSD = bsSysConfigService.findKey(Constants.BUY_AGREEMENT_TITLE_ZSD);
		res.setBuyAgreementTitleZSD(buyAgreementTitleZSD.getConfValue());
		//购买页面赞时贷《风险提示》
		BsSysConfig riskWarningTitleZSD = bsSysConfigService.findKey(Constants.RISK_WARNING_TITLE_ZSD);
		res.setRiskWarningTitleZSD(riskWarningTitleZSD.getConfValue());
		//计划管理页面定期类计划《授权委托书》,powerAttorneyFlag为yes
		BsSysConfig regularAgreementTitleYes = bsSysConfigService.findKey(Constants.REGULAR_AGREEMENT_TITLE_YES);
		res.setRegularAgreementTitleYes(regularAgreementTitleYes.getConfValue());
		//计划管理页面定期类计划《债权明细》,powerAttorneyFlag为yes
		BsSysConfig regularDetailsTitleYes = bsSysConfigService.findKey(Constants.REGULAR_DETAILS_TITLE_YES);
		res.setRegularDetailsTitleYes(regularDetailsTitleYes.getConfValue());
		//计划管理页面定期类计划《授权委托书》,powerAttorneyFlag为no
		BsSysConfig regularAgreementTitleNo = bsSysConfigService.findKey(Constants.REGULAR_AGREEMENT_TITLE_NO);
		res.setRegularAgreementTitleNo(regularAgreementTitleNo.getConfValue());
		//计划管理页面定期类计划《债权明细》,powerAttorneyFlag为no
		BsSysConfig regularDetailsTitleNo = bsSysConfigService.findKey(Constants.REGULAR_DETAILS_TITLE_NO);
		res.setRegularDetailsTitleNo(regularDetailsTitleNo.getConfValue());
		//计划管理页面分期类计划《自动出借计划协议》
		BsSysConfig instalmentAgreementTitle = bsSysConfigService.findKey(Constants.INSTALMENT_AGREEMENT_TITLE);
		res.setInstalmentAgreementTitle(instalmentAgreementTitle.getConfValue());
		//计划管理页面分期类计划《债权明细》
		BsSysConfig instalmentDetailTitle = bsSysConfigService.findKey(Constants.INSTALMENT_DETAILS_TITLE);
		res.setInstalmentDetailTitle(instalmentDetailTitle.getConfValue());
		//充值页面    宝付支付用户服务协议
		BsSysConfig paymentAgreementTitle = bsSysConfigService.findKey(Constants.PAYMENT_AGREEMENT_TITLE);
		res.setPaymentAgreementTitle(paymentAgreementTitle.getConfValue());
		//激活页面《网络交易资金存管账户服务协议》
		BsSysConfig activateAgreementTitle = bsSysConfigService.findKey(Constants.ACTIVATE_AGREEMENT_TITLE);
		res.setActivateAgreementTitle(activateAgreementTitle.getConfValue());
		//开通页面《网络交易资金存管账户服务协议》
		BsSysConfig openAgreementTitle = bsSysConfigService.findKey(Constants.OPEN_AGREEMENT_TITLE);
		res.setOpenAgreementTitle(openAgreementTitle.getConfValue());
	}
}
