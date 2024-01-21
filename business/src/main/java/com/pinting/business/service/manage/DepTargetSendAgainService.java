package com.pinting.business.service.manage;

import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnDepositionTargetJnl;

import java.util.Map;

/**
 * 管理台标的操作重发：标的废除、标的成立、标的出账
 * @author bianyatian
 * @2017-5-3 上午11:23:24
 */
public interface DepTargetSendAgainService {
	
	/**
	 * 标的废除重发
	 * @param targetJnlId
	 */
	public void targetAbandon(Integer targetJnlId);
	
	
	/**
	 * 标的成立重发 
	 * @param targetJnlId
	 */
	public void targetEstablish(Integer targetJnlId);

	/**
	 * 标的出账重发
	 * @param targetJnlId
     */
	void targetChargeOff(Integer targetJnlId);
	
	
	/**
	 * 标的出账回调重发
	 * @param targetJnlId
	 */
	void targetChargeOffCallBack(Integer targetJnlId);

	/**
	 * 标的流水查询
	 * @param targetJnlId	标的流水ID
	 * @param pageNum		当前页数（初始：1）
	 * @param numPerPage	每页显示条数
	 * @return	list: 数据列表；count：总条数
     */
	Map<String, Object> queryDepositionTargetJnl(Integer targetJnlId, Integer pageNum, Integer numPerPage);

	/**
	 * 根据ID查询
	 * @param id
	 * @return
     */
	LnDepositionTargetJnl queryById(Integer id);


}
