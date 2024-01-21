package com.pinting.business.service.manage;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsSysBalanceDailyFile;


public interface BsSysBalanceDailyFileService {
	
	/**
	 * 生成日终账务查询文件
	 * @param time
	 */
	void generateSysBalanceDailyFile(String time);
	
	/**
	 * 每日日终账务查询计数
	 * @return
	 */
	public int countSysBalanceDailyFile(Date snapBeginTime, Date snapEndTime);
	
	/**
	 * 每日日终账务查询列表
	 * @return
	 */
	public List<BsSysBalanceDailyFile> querySysBalanceDailyFileList(Date snapBeginTime, Date snapEndTime,
			int pageNum, int numPerPage);
	
}
