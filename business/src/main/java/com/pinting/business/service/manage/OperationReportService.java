package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsOperationReport;
import com.pinting.business.model.vo.BsOperationReportVO;

public interface OperationReportService {
	
	/**
	 * 查询运营报告列表
	 * @return
	 */
	List<BsOperationReportVO>  queryOperationReportList(Integer start,Integer numPerPage);
	
	/**
	 * 查询运营报告列表条数
	 * @return
	 */
	Integer  queryOperationReportCount();
	/**
	 * 新增运营报告
	 * @param report
	 * @return
	 */
	public int addOperationReport(BsOperationReport report);
	
	/**
	 * 根据ID查询运营报告信息
	 * @param id
	 * @return
	 */
	BsOperationReport queryOperationReportById(Integer id);
	
	
	/**
	 * 根据ID查询运营报告信息
	 * @param report
	 * @return
	 */
	int updateOperationReport(BsOperationReport report);
	
	/**
	 * 根据ID删除运营报告信息
	 * @param id
	 * @return
	 */
	int deleteOperationReportById(Integer id);
}
