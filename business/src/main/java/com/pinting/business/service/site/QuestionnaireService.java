package com.pinting.business.service.site;

import com.pinting.business.hessian.site.message.ReqMsg_User_Questionnaire;
import com.pinting.business.hessian.site.message.ReqMsg_User_SaveQuestionnaire;
import com.pinting.business.model.BsQuestionnaire;
import com.pinting.business.model.vo.BsQuestionnaireVo;

public interface QuestionnaireService {

	/**
	 * 根据用户id查询风险评测组合信息
	 * @param userId
	 * @return
	 */
	public BsQuestionnaireVo findQuestionnaireByUserId(Integer userId);
	
	/**
	 * 根据用户id查询风险评测对象
	 * @param userId
	 * @return
	 */
	public BsQuestionnaire findByUserId(Integer userId);
	
	/**
	 * 记录风险测评信息，返回结果
	 * @param req
	 * @return
	 */
	public String doQuestionnaire(ReqMsg_User_Questionnaire req);

	/**
	 * app
	 * 记录风险测评信息，返回结果
	 * @param req
	 * @return
	 */
	public String saveQuestionnaire(ReqMsg_User_SaveQuestionnaire req);
	
}

