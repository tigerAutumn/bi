package com.pinting.gateway.business.out.service.impl;

import com.pinting.business.hessian.site.message.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.business.out.service.AppMoreBusinessService;

@Service
public class AppMoreBusinessServiceImpl implements AppMoreBusinessService {
    
    @Autowired
    @Qualifier("gatewayMobileService")
    private HessianService siteService;

    @Override
    public ResMsg_User_Feedback feedback(ReqMsg_User_Feedback reqMsg) {
        ResMsg_User_Feedback resMsg = (ResMsg_User_Feedback) siteService.handleMsg(reqMsg);
        return resMsg;
    }

    @Override
    public ResMsg_User_BsSubUserListQuery queryMyRecommend(ReqMsg_User_BsSubUserListQuery req) {
        ResMsg_User_BsSubUserListQuery resMsg = (ResMsg_User_BsSubUserListQuery) siteService.handleMsg(req);
        return resMsg;
    }

	@Override
	public ResMsg_System_NewVersion newVersion(ReqMsg_System_NewVersion req) {
		ResMsg_System_NewVersion resMsg = (ResMsg_System_NewVersion) siteService.handleMsg(req);
		return resMsg;
	}

	@Override
	public ResMsg_News_QueryNewsPage queryNewsList(ReqMsg_News_QueryNewsPage req) {
		ResMsg_News_QueryNewsPage resMsg = (ResMsg_News_QueryNewsPage) siteService.handleMsg(req);
		return resMsg;
	}

	@Override
	public ResMsg_News_Details queryNewsDetail(ReqMsg_News_Details req) {
		ResMsg_News_Details resMsg = (ResMsg_News_Details) siteService.handleMsg(req);
		return resMsg;
	}

	@Override
	public ResMsg_News_QueryNewsPageInfo queryNewsListInfo(ReqMsg_News_QueryNewsPageInfo req) {
		ResMsg_News_QueryNewsPageInfo resMsg = (ResMsg_News_QueryNewsPageInfo) siteService.handleMsg(req);
		return resMsg;
	}
	
	@Override
	public ResMsg_User_Questionnaire myQuestionnaire(ReqMsg_User_Questionnaire req) {
		ResMsg_User_Questionnaire resMsg = (ResMsg_User_Questionnaire) siteService.handleMsg(req);
		return resMsg;
	}

	@Override
	public ResMsg_User_SaveQuestionnaire saveQuestionnaire(
			ReqMsg_User_SaveQuestionnaire req) {
		ResMsg_User_SaveQuestionnaire resMsg = (ResMsg_User_SaveQuestionnaire) siteService.handleMsg(req);
		return resMsg;
	}

	@Override
	public ResMsg_User_RecommendAmount recommendAmount(
			ReqMsg_User_RecommendAmount req) {
		ResMsg_User_RecommendAmount resMsg = (ResMsg_User_RecommendAmount) siteService.handleMsg(req);
		return resMsg;
	}

	@Override
	public ResMsg_Invest_PlatformData platformData(ReqMsg_Invest_PlatformData req) {
		return (ResMsg_Invest_PlatformData) siteService.handleMsg(req);
	}

	@Override
	public ResMsg_DepGuide_Risk risk(ReqMsg_DepGuide_Risk req) {
		return (ResMsg_DepGuide_Risk) siteService.handleMsg(req);
	}

	@Override
	public ResMsg_User_QuestionnaireMoreQuery questionnaireMore(
			ReqMsg_User_QuestionnaireMoreQuery req) {
		ResMsg_User_QuestionnaireMoreQuery resMsg = (ResMsg_User_QuestionnaireMoreQuery) siteService.handleMsg(req);
		return resMsg;
	}
	
}
