package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery;
import com.pinting.business.model.vo.AnnualMeetingEmpVO;
import com.pinting.business.service.manage.AnnualMeetingEmployeeService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.StringUtil;

/**
 * 2016公司年会抽奖
 * Created by shh on 2017/01/13 14:00.
 */
@Component("AnnualMeetingEmp")
public class AnnualMeetingEmployeeFacade {
	
	@Autowired
	private AnnualMeetingEmployeeService annualMeetingEmployeeService;
	
	public void annualMeetingEmpListQuery(ReqMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery req, ResMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery res) {
		int pageNum = req.getPageNum();
		int numPerPage = req.getNumPerPage();
		AnnualMeetingEmpVO annualVO = new AnnualMeetingEmpVO();
		annualVO.setPageNum(pageNum);
		annualVO.setNumPerPage(numPerPage);
		if(StringUtil.isNotEmpty(req.getEmployeeName())) {
			annualVO.setEmployeeName(req.getEmployeeName().trim());
		}
		if(StringUtil.isNotEmpty(req.getIsWin())) {
			annualVO.setIsWin(req.getIsWin());
		}
		if(StringUtil.isNotEmpty(req.getActivityAwardId())) {
			annualVO.setActivityAwardId(Integer.parseInt(req.getActivityAwardId()));
		}
		Integer totalRows = annualMeetingEmployeeService.queryAnnualMeetingCount(annualVO);
		if(totalRows > 0) {
			List<AnnualMeetingEmpVO> list = annualMeetingEmployeeService.queryAnnualMeetingEmpList(annualVO);
			ArrayList<HashMap<String, Object>> checkInUserList = BeanUtils.classToArrayList(list);
			res.setValueList(checkInUserList);
		}
		res.setTotalRows(totalRows);
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
	}

}
