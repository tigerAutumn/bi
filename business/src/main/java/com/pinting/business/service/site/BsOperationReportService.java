package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.hessian.site.message.ReqMsg_OperationReport_queryOperationReport;
import com.pinting.business.hessian.site.message.ResMsg_OperationReport_queryOperationReport;
import com.pinting.business.model.BsOperationReport;

public interface BsOperationReportService {
	/**
	 * 查询运营报告列表信息
	 * @param req
	 * @param res
	 */
	public void queryOperationReport(ReqMsg_OperationReport_queryOperationReport req,
			ResMsg_OperationReport_queryOperationReport res);
	
	/**
	 * 查询前六个运营报告信息，排序规则参照上面查询运营报告列表信息
	 * @param req
	 * @param res
	 */
	public List<BsOperationReport> querySixOperationReport();
	
	/**
	 * 根据年份查询运营报告列表信息
	 * @param req
	 * @param res
	 */
	public void queryOperationReportByYear(ReqMsg_OperationReport_queryOperationReport req,
			ResMsg_OperationReport_queryOperationReport res);
	
}
