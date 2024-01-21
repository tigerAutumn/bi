package com.pinting.business.service.site.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsOperationReportMapper;
import com.pinting.business.hessian.site.message.ReqMsg_OperationReport_queryOperationReport;
import com.pinting.business.hessian.site.message.ResMsg_OperationReport_queryOperationReport;
import com.pinting.business.model.BsOperationReport;
import com.pinting.business.model.BsOperationReportExample;
import com.pinting.business.model.vo.BsOperationReportVO;
import com.pinting.business.service.site.BsOperationReportService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.DateUtil;
@Service
public class BsOperationReportServiceImpl implements BsOperationReportService {
	
	@Autowired
	private BsOperationReportMapper bsOperationReportMapper;
	
	@Override
	public void queryOperationReport(
			ReqMsg_OperationReport_queryOperationReport req,
			ResMsg_OperationReport_queryOperationReport res) {
		BsOperationReportExample example = new BsOperationReportExample();
		example.createCriteria();
		example.setOrderByClause("is_sugguest DESC,update_time DESC");
		List<BsOperationReport>  list = bsOperationReportMapper.selectByExample(example);
		res.setReportList(BeanUtils.classToArrayList(list));
	}

	@Override
	public List<BsOperationReport> querySixOperationReport() {
		BsOperationReportExample example = new BsOperationReportExample();
		example.createCriteria();
		example.setOrderByClause("is_sugguest DESC,update_time DESC LIMIT 0,6");
		List<BsOperationReport>  list = bsOperationReportMapper.selectByExample(example);
		return list;
	}

	@Override
	public void queryOperationReportByYear(
			ReqMsg_OperationReport_queryOperationReport req,
			ResMsg_OperationReport_queryOperationReport res) {
		List<BsOperationReportVO> list = bsOperationReportMapper.selectByYear(req.getYear(), req.getStart(), req.getNumPerPage());
		res.setReportList(BeanUtils.classToArrayList(list));
		int count = 0;
        count = bsOperationReportMapper.selectByYear(req.getYear(), 0, Integer.MAX_VALUE).size();
        if(!CollectionUtils.isEmpty(list)) {
        	res.setReportList(BeanUtils.classToArrayList(list));
        } else {
        	res.setReportList(new ArrayList<HashMap<String, Object>>());
        }
        res.setCurrYear(DateUtil.getCurrentYear());
        res.setCount(count);
	}
	
}
