package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.LnDepositionRepayScheduleMapper;
import com.pinting.business.model.vo.RepayDSDFVO;
import com.pinting.business.service.manage.FinancialDSDFService;

/**
 *  财务统计-代收代付
 * @author bianyatian
 * @2017-11-9 下午3:56:38
 */
@Service
public class FinancialDSDFServiceImpl implements FinancialDSDFService {

	@Autowired
	private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
	@Override
	public List<RepayDSDFVO> getRepayDSDFList(String name, String mobile,
			String type, String timeStart, String timeEnd, String status, Integer page,
			Integer offset) {
		Integer position = (page - 1) * offset;
		List<RepayDSDFVO> list = lnDepositionRepayScheduleMapper.getRepayDSDFList(name, mobile, type, 
				timeStart, timeEnd, status, position, offset);
		return list;
	}

	@Override
	public Integer countRepayDSDF(String name, String mobile, String type,
			String timeStart, String timeEnd, String status) {
		Integer count = lnDepositionRepayScheduleMapper.countRepayDSDF(name, mobile, type, timeStart, timeEnd, status);
		return count;
	}

	@Override
	public RepayDSDFVO sumRepayDSDF(String name, String mobile, String type,
			String timeStart, String timeEnd, String status) {
		RepayDSDFVO vo = lnDepositionRepayScheduleMapper.sumRepayDSDF(name, mobile, type, timeStart, timeEnd, status);
		return vo;
	}

	@Override
	public List<RepayDSDFVO> queryRepayDSDFBatchFlowList(String time) {
		List<RepayDSDFVO> list = lnDepositionRepayScheduleMapper.selectRepayDSDFBatchFlowList(time);
		return list;
	}
}
