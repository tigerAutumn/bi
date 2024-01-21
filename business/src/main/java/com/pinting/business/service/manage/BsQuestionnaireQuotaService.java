package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsQuestionnaireQuota;

/**
 * 风险测评出借额度服务
 * @author SHENGUOPING
 * @date  2018年1月19日 下午1:43:24
 */
public interface BsQuestionnaireQuotaService {

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	BsQuestionnaireQuota findById(Integer id);
	
	/**
	 * 查询风险测评出借额度列表
	 * @return
	 */
	List<BsQuestionnaireQuota> selectList();
	
	/**
	 * 风险测评出借额度 计数
	 * @return
	 */
	int countQuestionnaireQuota();
	
	/**
	 * 操作风险测评出借额度 
	 * @param quotaId
	 * @param mUserId 操作人
	 * @param periodLimit 标的期限限制
	 * @param amountLimit 出借额度限制
	 */
	void operateQuestionnaireQuota(Integer quotaId, Integer mUserId, Integer periodLimit, Double amountLimit);
	
}
