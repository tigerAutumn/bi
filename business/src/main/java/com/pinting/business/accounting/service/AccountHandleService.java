package com.pinting.business.accounting.service;

/**
 * 账户处理接口
 * @Project: business
 * @Title: AccountHandleService.java
 * @author dingpf
 * @date 2015-1-25 下午2:45:13
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface AccountHandleService {
	/**
	 * 用于 账户号码 生成
	 * @param accountType 账户类型：
		 * 1:主账户，
		 * 2：结算户，
		 * 3：定期产品户-三个月，
		 * 4：定期产品户-六个月，
		 * 5：定期产品户-一年，
	 * @param userId 用户编号
	 * @return
	 */
	public String generateAccount(Integer accountPrefix, Integer userId);
	
	/**
	 * 生成产品子账户
	 * @param productId 产品编码
	 * @param userId 用户编号
	 * @return
	 */
	public String generateProductAccount(Integer productId, Integer userId);

}
