package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.dao.BsOperationReportMapper;
import com.pinting.business.model.BsOperationReport;
import com.pinting.business.model.BsOperationReportExample;
import com.pinting.business.model.vo.BsOperationReportVO;
import com.pinting.business.service.manage.OperationReportService;

@Service
public class OperationReportServiceImpl implements OperationReportService {
	@Autowired
	private BsOperationReportMapper bsOperationReportMapper;
	
	@Override
	@ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.HOME_INFOQUERY})
	public int addOperationReport(BsOperationReport report) {
		int num = bsOperationReportMapper.insertSelective(report);
		return num;
	}

	@Override
	public List<BsOperationReportVO> queryOperationReportList(Integer start,
			Integer numPerPage) {
		List<BsOperationReportVO> list = bsOperationReportMapper.queryOperationReportList(start,numPerPage);
		return list;
	}

	@Override
	public Integer queryOperationReportCount() {
		Integer num = bsOperationReportMapper.queryOperationReportCount();
		return num;
	}

	@Override
	public BsOperationReport queryOperationReportById(Integer id) {
		BsOperationReport report = bsOperationReportMapper.selectByPrimaryKey(id);
		return report;
	}

	@Override
	@ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.HOME_INFOQUERY})
	public int updateOperationReport(BsOperationReport report) {
		int num = bsOperationReportMapper.updateByPrimaryKeySelective(report);
		return num;
	}

	@Override
	@ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.HOME_INFOQUERY})
	public int deleteOperationReportById(Integer id) {
		int num = bsOperationReportMapper.deleteByPrimaryKey(id);
		return num;
	}
	
}
