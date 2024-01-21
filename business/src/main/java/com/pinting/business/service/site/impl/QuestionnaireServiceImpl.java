package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsQuestionnaireMapper;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.facade.site.UserFacade;
import com.pinting.business.hessian.site.message.ReqMsg_User_Questionnaire;
import com.pinting.business.hessian.site.message.ReqMsg_User_SaveQuestionnaire;
import com.pinting.business.model.BsQuestionnaire;
import com.pinting.business.model.BsQuestionnaireExample;
import com.pinting.business.model.vo.BsQuestionnaireVo;
import com.pinting.business.service.site.QuestionnaireService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class QuestionnaireServiceImpl implements QuestionnaireService {

	@Autowired
	private BsQuestionnaireMapper questionnaireMapper;
	private static final Logger log = LoggerFactory.getLogger(UserFacade.class);
	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	  
	@Override
	public BsQuestionnaireVo findQuestionnaireByUserId(Integer userId) {
		BsQuestionnaireExample questionnaireExample = new BsQuestionnaireExample();
		questionnaireExample.createCriteria().andUserIdEqualTo(userId);
        List<BsQuestionnaire> list = questionnaireMapper.selectByExample(questionnaireExample);
        if (!CollectionUtils.isEmpty(list)) {
        	BsQuestionnaire bsQuestionnaire = list.get(0);
        	BsQuestionnaireVo questionnaireVo = new BsQuestionnaireVo();
        	questionnaireVo.setHasQuestionnaire(Constants.HAS_QUESTIONNAIRE);
        	questionnaireVo.setIsExpired(DateUtil.compareTo(bsQuestionnaire.getExpireTime(), new Date())==1?
        			Constants.IS_EXPIRED_NO:Constants.IS_EXPIRED_YES);
        	questionnaireVo.setAssessType(getAssessType(bsQuestionnaire.getScore()));
        	questionnaireVo.setExpireTime(bsQuestionnaire.getExpireTime());
        	return questionnaireVo;
        }
		return null;
	}

	private String getAssessType(Integer score) {
		String assessType = "";
		if (score <= 20) {
			assessType = Constants.CONSERVATIVE_TYPE;
		} else if (score <= 45) {
			assessType = Constants.STEADY_TYPE;
		} else if (score <= 70) {
			assessType = Constants.BALANCED_TYPE;
		} else if (score <= 85) {
			assessType = Constants.AGGRESSIVE_TYPE;
		} else {
			assessType = Constants.RADICAL_TYPE;
		}			
		return assessType;
	}
	
	private String getAssessTypeCN(Integer score) {
		String assessType = "";
		if (score <= 20) {
			assessType = Constants.CONSERVATIVE_TYPE_CN;
		} else if (score <= 45) {
			assessType = Constants.STEADY_TYPE_CN;
		} else if (score <= 70) {
			assessType = Constants.BALANCED_TYPE_CN;
		} else if (score <= 85) {
			assessType = Constants.AGGRESSIVE_TYPE_CN;
		} else{
			assessType = Constants.RADICAL_TYPE_CN;
		}			
		return assessType;
	}
	
	@Override
	public BsQuestionnaire findByUserId(Integer userId) {
		BsQuestionnaireExample questionnaireExample = new BsQuestionnaireExample();
		questionnaireExample.createCriteria().andUserIdEqualTo(userId);
        List<BsQuestionnaire> list = questionnaireMapper.selectByExample(questionnaireExample);
        if (!CollectionUtils.isEmpty(list)) {
        	return list.get(0);
        }
		return null;
	}

	@Override
	public String doQuestionnaire(ReqMsg_User_Questionnaire req) {
		 try {
	        
				jsClientDaoSupport.lock(RedisLockEnum.LOCK_QUESTIONNAIRE.getKey() + req.getUserId());
		    	
				BsQuestionnaireExample questionnaireExample = new BsQuestionnaireExample();
				questionnaireExample.createCriteria().andUserIdEqualTo(req.getUserId());
		        List<BsQuestionnaire> selectList = questionnaireMapper.selectByExample(questionnaireExample);
		    	// 
		        if (StringUtil.isBlank(req.getAssessType())) {
		    		BsQuestionnaireVo questionnaireVo = findQuestionnaireByUserId(req.getUserId());
		    		return questionnaireVo==null? "" : questionnaireVo.getAssessType();
		    	}
		        if (!CollectionUtils.isEmpty(selectList)) {
		    		BsQuestionnaire updQuestionnaire = new BsQuestionnaire();
		    		updQuestionnaire.setId(selectList.get(0).getId());
		    		updQuestionnaire.setScore(req.getScore());
		    		updQuestionnaire.setUpdateTime(new Date());
		    		updQuestionnaire.setExpireTime(com.pinting.business.util.DateUtil.nextDay(updQuestionnaire.getUpdateTime(), Constants.ASSESS_EXPIRED_DAYS));
		    		questionnaireMapper.updateByPrimaryKeySelective(updQuestionnaire);
		    	} else {
		    		BsQuestionnaire questionnaire = new BsQuestionnaire();
		        	questionnaire.setUserId(req.getUserId());
		        	questionnaire.setQuestionType(req.getAssessType());
		        	questionnaire.setExpireDays(Constants.ASSESS_EXPIRED_DAYS);
		        	questionnaire.setScore(req.getScore());
		        	questionnaire.setCreateTime(new Date());
		        	questionnaire.setExpireTime(com.pinting.business.util.DateUtil.nextDay(questionnaire.getCreateTime(), Constants.ASSESS_EXPIRED_DAYS));
		        	questionnaireMapper.insertSelective(questionnaire);
		    	}
		  	} finally {
	            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_QUESTIONNAIRE.getKey() + req.getUserId());
	        }
		return getAssessType(req.getScore());
	}

	@Override
	public String saveQuestionnaire(ReqMsg_User_SaveQuestionnaire req) {
		BsQuestionnaireExample questionnaireExample = new BsQuestionnaireExample();
		questionnaireExample.createCriteria().andUserIdEqualTo(req.getUserId());
        List<BsQuestionnaire> selectList = questionnaireMapper.selectByExample(questionnaireExample);
    	if (!CollectionUtils.isEmpty(selectList)) {
    		BsQuestionnaire updQuestionnaire = new BsQuestionnaire();
    		updQuestionnaire.setId(selectList.get(0).getId());
    		updQuestionnaire.setScore(req.getScore());
    		updQuestionnaire.setUpdateTime(new Date());
    		updQuestionnaire.setExpireTime(com.pinting.business.util.DateUtil.nextDay(updQuestionnaire.getUpdateTime(), Constants.ASSESS_EXPIRED_DAYS));
    		questionnaireMapper.updateByPrimaryKeySelective(updQuestionnaire);
    	} else {
    		BsQuestionnaire questionnaire = new BsQuestionnaire();
        	questionnaire.setUserId(req.getUserId());
        	questionnaire.setQuestionType(Constants.QUESTION_TYPE_RISK_ASSESS);
        	questionnaire.setExpireDays(Constants.ASSESS_EXPIRED_DAYS);
        	questionnaire.setScore(req.getScore());
        	questionnaire.setCreateTime(new Date());
        	questionnaire.setExpireTime(com.pinting.business.util.DateUtil.nextDay(questionnaire.getCreateTime(), Constants.ASSESS_EXPIRED_DAYS));
        	questionnaireMapper.insertSelective(questionnaire);
    	}
		return getAssessType(req.getScore());
	}
	
}
