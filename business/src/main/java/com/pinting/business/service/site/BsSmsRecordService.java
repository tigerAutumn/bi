package com.pinting.business.service.site;

import com.pinting.business.model.BsSmsRecord;

public interface BsSmsRecordService {
	
	/**
	 * 添加一条短信记录
	 * @param smsRecord
	 */
	public void addSmsRecord(BsSmsRecord smsRecord);
	
	/**
	 * 根据userId查询短信发送记录
	 * @param userId
	 * @return
	 */
	public BsSmsRecord selectByMobile(String mobile);

	/**
	 * 增量修改
	 * 发送条数+1，其他传值
	 * @param smsRecord
	 */
	public void updateByIncrement(BsSmsRecord smsRecord);
}
