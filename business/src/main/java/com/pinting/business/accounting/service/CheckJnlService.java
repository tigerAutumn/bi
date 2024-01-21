package com.pinting.business.accounting.service;

import com.pinting.business.model.BsCheckErrorJnl;
import com.pinting.business.model.BsCheckJnl;

/**
 * 对账记录服务接口
 * @Project: business
 * @Title: CheckJnlService.java
 * @author dingpf
 * @date 2015-3-19 下午5:27:31
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface CheckJnlService {
	
	/**
	 * 新增对账记录
	 * @param bsCheckJnl 对账信息
	 * @return 成功返回true，失败返回false
	 */
	public boolean addCheckJnl(BsCheckJnl bsCheckJnl);
	
	/**
	 * 新增对账差错记录
	 * @param bsCheckErrorJnl 差错信息
	 * @return 成功返回true，失败返回false
	 */
	public boolean addCheckErrorJnl(BsCheckErrorJnl bsCheckErrorJnl);

}
