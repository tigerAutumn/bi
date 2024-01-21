package com.pinting.gateway.business.out.service;

import com.pinting.business.hessian.site.message.*;

public interface AppMoreBusinessService {

    /**
     * 添加反馈意见
     * @param reqMsg
     * @return
     */
    ResMsg_User_Feedback feedback(ReqMsg_User_Feedback reqMsg);

    /**
     * 查询我的邀请
     * @param req
     * @return
     */
    ResMsg_User_BsSubUserListQuery queryMyRecommend(ReqMsg_User_BsSubUserListQuery req);

    /**
     * 
     * @Title: newVersion 
     * @Description: 查找app新版本
     * @param req
     * @return
     * @throws
     */
    ResMsg_System_NewVersion newVersion(ReqMsg_System_NewVersion req);
    
    /**
     * 港湾资讯
     * @param req
     * @return
     */
    ResMsg_News_QueryNewsPage queryNewsList(ReqMsg_News_QueryNewsPage req);
    
    /**
     * 港湾资讯详情
     * @param req
     * @return
     */
    ResMsg_News_Details queryNewsDetail(ReqMsg_News_Details req);

    /**
     * 港湾资讯、平台公告
     * @param req
     * @return
     */
    ResMsg_News_QueryNewsPageInfo queryNewsListInfo(ReqMsg_News_QueryNewsPageInfo req);
    
	/**
     * 风险测评信息
     * @param req
     * @return
     */
    ResMsg_User_Questionnaire myQuestionnaire(ReqMsg_User_Questionnaire req);
    
    /**
     * 进行风险测评，保存信息
     * @param req
     * @return
     */
    ResMsg_User_SaveQuestionnaire saveQuestionnaire(ReqMsg_User_SaveQuestionnaire req);
    
	/**
     * 风险测评信息再次打开
     * @param req
     * @return
     */
    ResMsg_User_QuestionnaireMoreQuery questionnaireMore(ReqMsg_User_QuestionnaireMoreQuery req);
    
    /**
     * 累计获得推荐奖励
     * @param req
     * @return
     */
    ResMsg_User_RecommendAmount recommendAmount(ReqMsg_User_RecommendAmount req);

    /**
     * 平台数据信息
     * @param res
     * @return
     */
    ResMsg_Invest_PlatformData platformData(ReqMsg_Invest_PlatformData req);

    /**
     * 查询是否风险测评
     * @param req
     * @return
     */
    ResMsg_DepGuide_Risk risk(ReqMsg_DepGuide_Risk req);
}
