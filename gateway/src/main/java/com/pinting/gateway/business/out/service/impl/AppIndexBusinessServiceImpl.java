package com.pinting.gateway.business.out.service.impl;

import com.pinting.business.hessian.site.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.business.out.service.AppIndexBusinessService;

@Service
public class AppIndexBusinessServiceImpl implements AppIndexBusinessService {

	@Autowired
	@Qualifier("gatewayMobileService")
	private HessianService siteService;
	
	@Override
	public ResMsg_User_Login appIndexLogin(ReqMsg_User_Login req) {
		ResMsg_User_Login res = (ResMsg_User_Login) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_User_Regist appIndexRegister(ReqMsg_User_Regist req) {
		ResMsg_User_Regist res = (ResMsg_User_Regist) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_SMS_Generate appIndexSendCode(ReqMsg_SMS_Generate req) {
		ResMsg_SMS_Generate res = (ResMsg_SMS_Generate) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_User_InfoValidation appIndexCheckMobile(ReqMsg_User_InfoValidation req) {
		ResMsg_User_InfoValidation res = (ResMsg_User_InfoValidation) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Home_InfoQuery appIndexIndexPage(ReqMsg_Home_InfoQuery req) {
		ResMsg_Home_InfoQuery res = (ResMsg_Home_InfoQuery) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_User_InfoQuery appIndexUserInfo(ReqMsg_User_InfoQuery req) {
		ResMsg_User_InfoQuery res = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_User_FindPassword appIndexForgetPassword(ReqMsg_User_FindPassword req) {
		ResMsg_User_FindPassword res = (ResMsg_User_FindPassword) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Profile_PasswordModify appIndexPasswordChange(
			ReqMsg_Profile_PasswordModify req) {
        //发起Hessian通讯（登录密码修改）
        ResMsg_Profile_PasswordModify res = (ResMsg_Profile_PasswordModify) siteService.handleMsg(req);
        return res;
	}

	@Override
	public ResMsg_RedPacketInfo_GetUserRegistRedPakt appIndexRedPacket(ReqMsg_RedPacketInfo_GetUserRegistRedPakt req) {
		ResMsg_RedPacketInfo_GetUserRegistRedPakt res = (ResMsg_RedPacketInfo_GetUserRegistRedPakt) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_AutoRedPacket_RedPacketSend sendRegisterRedPacket(ReqMsg_AutoRedPacket_RedPacketSend req) {
		ResMsg_AutoRedPacket_RedPacketSend res = (ResMsg_AutoRedPacket_RedPacketSend) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_BannerConfig_getList bannerList(ReqMsg_BannerConfig_getList req) {
		ResMsg_BannerConfig_getList res = (ResMsg_BannerConfig_getList) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_AppActive_AppActiveList activeList(ReqMsg_AppActive_AppActiveList req) {
		ResMsg_AppActive_AppActiveList res = (ResMsg_AppActive_AppActiveList) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Invest_PlatformStatistics platformData(ReqMsg_Invest_PlatformStatistics req) {
		ResMsg_Invest_PlatformStatistics res = (ResMsg_Invest_PlatformStatistics) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_AppActive_FindPublishTime findPublishTime(ReqMsg_AppActive_FindPublishTime req) {
		ResMsg_AppActive_FindPublishTime res = (ResMsg_AppActive_FindPublishTime) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_ActiveUserRecord_AddRecord addRecord(
			ReqMsg_ActiveUserRecord_AddRecord req) {
		ResMsg_ActiveUserRecord_AddRecord res = (ResMsg_ActiveUserRecord_AddRecord) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_User_AppVersion appVersion(
			ReqMsg_User_AppVersion req) {
		ResMsg_User_AppVersion res = (ResMsg_User_AppVersion) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_BannerConfig_AppStart appStart(
			ReqMsg_BannerConfig_AppStart req) {
		ResMsg_BannerConfig_AppStart res = (ResMsg_BannerConfig_AppStart) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_News_CurrentNews currentNews(ReqMsg_News_CurrentNews req) {
		ResMsg_News_CurrentNews res = (ResMsg_News_CurrentNews) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_AppActive_IsRead activeIsRead(ReqMsg_AppActive_IsRead req) {
		ResMsg_AppActive_IsRead res = (ResMsg_AppActive_IsRead) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_News_ReadMessage readMessage(ReqMsg_News_ReadMessage req) {
		ResMsg_News_ReadMessage res = (ResMsg_News_ReadMessage) siteService.handleMsg(req);
		return res;
	}

	@Override
	public ResMsg_Agreement_GetList getList(ReqMsg_Agreement_GetList req) {
		ResMsg_Agreement_GetList res = (ResMsg_Agreement_GetList) siteService.handleMsg(req);
		return res;
	}
	
	@Override
	public ResMsg_User_AddApplication addApplication(ReqMsg_User_AddApplication req) {
		ResMsg_User_AddApplication res = (ResMsg_User_AddApplication) siteService.handleMsg(req);
		return res;
	}
	
	@Override
	public ResMsg_User_AppAddUserAddress appAddUserAddress(ReqMsg_User_AppAddUserAddress req) {
		ResMsg_User_AppAddUserAddress res = (ResMsg_User_AppAddUserAddress) siteService.handleMsg(req);
		return res;
	}
}
