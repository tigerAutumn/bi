package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsRedPacketCheck;


public interface BsRedPacketCheckService {
	/**
	 * 新增红包发放批次审核记录
	 * @param recode
	 */
	 public int saveRedPacketCheck(BsRedPacketCheck record,List<Integer> userIdList);
	 
	 
	 /**
	  * 根据策略可模糊查询列表
	  * @param policyType
	  * @return
	  */
	 public List<BsRedPacketCheck> getListByPolicyType(String policyType);
}
