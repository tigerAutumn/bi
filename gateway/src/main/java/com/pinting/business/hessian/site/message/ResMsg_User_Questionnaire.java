package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_Questionnaire extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 725398260704214945L;

	/** 是否进行风险测评  */
	private Integer hasQuestionnaire;
	/** 是否过期 */
	private Integer isExpired;
	/** 风险测评类型  */
	private String assessType;
    
	public Integer getHasQuestionnaire() {
		return hasQuestionnaire;
	}

	public void setHasQuestionnaire(Integer hasQuestionnaire) {
		this.hasQuestionnaire = hasQuestionnaire;
	}

	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}

	public String getAssessType() {
		return assessType;
	}

	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}
	
}
