package com.pinting.gateway.business.out.service;

import com.pinting.business.hessian.site.message.*;

/**
 * 
 * @ClassName: AppIndexBusinessService 
 * @Description: app中的index接口向business请求数据
 * @author lenovo
 * @date 2015年12月14日 上午9:56:05 
 *
 */
public interface AppIndexBusinessService {

	public ResMsg_User_Login appIndexLogin(ReqMsg_User_Login req);
	
	public ResMsg_User_Regist appIndexRegister(ReqMsg_User_Regist req);
	
	public ResMsg_SMS_Generate appIndexSendCode(ReqMsg_SMS_Generate req);
	
	public ResMsg_User_InfoValidation appIndexCheckMobile(ReqMsg_User_InfoValidation req);
	
	public ResMsg_Home_InfoQuery appIndexIndexPage(ReqMsg_Home_InfoQuery req);
	
	public ResMsg_User_InfoQuery appIndexUserInfo(ReqMsg_User_InfoQuery req);
	
	public ResMsg_User_FindPassword appIndexForgetPassword(ReqMsg_User_FindPassword req);
	//修改密码
	public ResMsg_Profile_PasswordModify appIndexPasswordChange(ReqMsg_Profile_PasswordModify req);
	
	public ResMsg_RedPacketInfo_GetUserRegistRedPakt appIndexRedPacket(ReqMsg_RedPacketInfo_GetUserRegistRedPakt req);
	
	public ResMsg_AutoRedPacket_RedPacketSend sendRegisterRedPacket(ReqMsg_AutoRedPacket_RedPacketSend req);
	
	public ResMsg_BannerConfig_getList bannerList(ReqMsg_BannerConfig_getList req);
	
	public ResMsg_AppActive_AppActiveList activeList(ReqMsg_AppActive_AppActiveList req);
	
	public ResMsg_Invest_PlatformStatistics platformData(ReqMsg_Invest_PlatformStatistics req);
	
	public ResMsg_AppActive_FindPublishTime findPublishTime(ReqMsg_AppActive_FindPublishTime req);
	
	//添加活跃用户记录
	public ResMsg_ActiveUserRecord_AddRecord addRecord(ReqMsg_ActiveUserRecord_AddRecord req);
	//更新用户APP版本号
	public ResMsg_User_AppVersion appVersion(ReqMsg_User_AppVersion reqAppVersion);
	//APP启动页面
	public ResMsg_BannerConfig_AppStart appStart(ReqMsg_BannerConfig_AppStart req);

	ResMsg_News_CurrentNews currentNews(ReqMsg_News_CurrentNews req);

	ResMsg_AppActive_IsRead activeIsRead(ReqMsg_AppActive_IsRead isReadReq);

	ResMsg_News_ReadMessage readMessage(ReqMsg_News_ReadMessage req);

	ResMsg_Agreement_GetList getList(ReqMsg_Agreement_GetList req);
	
	ResMsg_User_AddApplication addApplication(ReqMsg_User_AddApplication req);
	
	ResMsg_User_AppAddUserAddress appAddUserAddress(ReqMsg_User_AppAddUserAddress req);
}
