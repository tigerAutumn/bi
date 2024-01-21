package com.pinting.business.service.manage;

import java.util.Map;

/**
 * 系统子账户服务接口
 * @author yanwl
 * @date 2015-12-07
 *
 */
public interface MBsSysSubAccountService {
	
	/**
	 * 统计系统账户余额 、用户余额  、产品户余额和回款户余额
	 * @param map
	 * @return map
	 */
	public Map<String,Object> countSysSubAccountBalance();


	/**
	 * 财务总账查询(宝付)-统计系统账户余额 、用户余额  、产品户余额和回款户余额
	 * @return
     */
	public Map<String,Object> countThdSysSubAccountBalance();
	
	
	/**
	 * 统计恒丰账户系统账户余额 、用户余额  、产品户余额和回款户余额
	 * @param map
	 * @return map
	 */
	public Map<String,Object> countDepSysSubAccountBalance();
	
}
