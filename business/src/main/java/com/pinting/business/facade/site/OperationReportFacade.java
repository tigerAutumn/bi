package com.pinting.business.facade.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.pinting.business.hessian.site.message.ReqMsg_OperationReport_queryOperationReport;
import com.pinting.business.hessian.site.message.ResMsg_OperationReport_queryOperationReport;
import com.pinting.business.service.site.BsOperationReportService;

@Component("OperationReport")
public class OperationReportFacade {
	@Autowired
	private BsOperationReportService bsOperationReportService;
	
	public void queryOperationReport(ReqMsg_OperationReport_queryOperationReport req, ResMsg_OperationReport_queryOperationReport res){
		bsOperationReportService.queryOperationReportByYear(req,res);
	}
}
