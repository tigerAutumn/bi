package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.vo.AnnualMeetingEmpVO;

/**
 * 2016公司年会抽奖列表查询
 * Created by shh on 2017/01/13 15:28.
 */
public interface AnnualMeetingEmployeeService {
	
	/**
	 * 2016公司年会抽奖列表
	 * @param record
	 * @return
	 */
	List<AnnualMeetingEmpVO> queryAnnualMeetingEmpList(AnnualMeetingEmpVO record);
    
    /**
     * 2016公司年会抽奖统计
     * @param record
     * @return
     */
    int queryAnnualMeetingCount(AnnualMeetingEmpVO record);
    
    /**
	 * 批量年会抽奖表
	 * @param checkInUserList
	 */
    void batchInsertAnnualMeetingEmp(List<String> annualMeetingEmpList);

}
