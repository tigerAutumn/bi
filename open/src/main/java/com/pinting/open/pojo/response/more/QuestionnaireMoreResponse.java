package com.pinting.open.pojo.response.more;

import com.pinting.open.base.response.Response;

/**
 * 风险测评再次打开
 * @author SHENGP
 * @date  2017年7月5日 下午3:49:43
 */
public class QuestionnaireMoreResponse extends Response {

	/** 是否进行风险测评  */
	private Integer hasQuestionnaire;
	/** 风险测评类型  */
	private String assessType;
	/** 风险评测过期时间 */
	private String expireTime;
	
	public Integer getHasQuestionnaire() {
		return hasQuestionnaire;
	}
	public void setHasQuestionnaire(Integer hasQuestionnaire) {
		this.hasQuestionnaire = hasQuestionnaire;
	}
	public String getAssessType() {
		return assessType;
	}
	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}
	
}
