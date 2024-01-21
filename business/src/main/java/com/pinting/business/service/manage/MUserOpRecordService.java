
package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.MUserOpRecord;
import com.pinting.business.model.vo.MUserOpRecordVO;
/**
 * 管理用户操作登记表操作
 * @author bianyatian
 * @2016-7-26 下午3:40:13
 */
public interface MUserOpRecordService {

	/**
	 * 管理用户操作登记表新增数据
	 * @param record
	 */
	public void addMUserOpRecord(MUserOpRecord record);
	
	/**
	 * 根据条件查询列表条数
	 * @param record
	 * @return
	 */
	public int countMList(MUserOpRecordVO record);
	
	/**
	 * 根据条件查询列表
	 * @param record
	 * @return
	 */
	public List<MUserOpRecordVO> getMList(MUserOpRecordVO record);
}
