package com.pinting.business.hessian.site.message;

import java.util.List;

import com.pinting.business.model.vo.QuestionnaireSubjectVo;
import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_Questionnaire extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4184791439408404102L;

	/** 用户id */
	private Integer userId;
	/** 是否再次，弃用 */ 
	private Integer isOnceMore;
	/** 分数 */
	private Integer score;
	/**  类型  */
	private String assessType;
	/**  题目选择列表 */
	private List<QuestionnaireSubjectVo> subjectList;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getIsOnceMore() {
		return isOnceMore;
	}
	public void setIsOnceMore(Integer isOnceMore) {
		this.isOnceMore = isOnceMore;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getAssessType() {
		return assessType;
	}
	public void setAssessType(String assessType) {
		this.assessType = assessType;
	}
	public List<QuestionnaireSubjectVo> getSubjectList() {
		return subjectList;
	}
	public void setSubjectList(List<QuestionnaireSubjectVo> subjectList) {
		this.subjectList = subjectList;
	}
	
}
