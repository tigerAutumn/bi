/**
 * SimFinancingDetail 业务逻辑实现类
 * @author yanwl
 * @date 2015-11-19
 */
package com.dafy.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dafy.core.helper.DaoHelper;
import com.dafy.model.pojo.SimFinancingDetail;
import com.dafy.service.SimFinancingDetailService;

@Service
public class SimFinancingDetailServiceImpl  implements SimFinancingDetailService {
	//声明工作空间
	private final static String nameSpace = "com.dafy.mapper.SimFinancingDetailMapper";
	
	
	@Transactional
	@Override
	public boolean insertSimFinancingDetail(SimFinancingDetail simFinancingDetail) {
		try {
			return DaoHelper.insert(nameSpace, "insertSimFinancingDetail", simFinancingDetail);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	@Override
	public boolean updateSimFinancingDetail(SimFinancingDetail simFinancingDetail) {
		try {
			return DaoHelper.update(nameSpace, "updateSimFinancingDetail", simFinancingDetail);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	@Override
	public boolean deleteSimFinancingDetail(Integer simFinancingDetailId) {
		try {
			return DaoHelper.delete(nameSpace, "deleteSimFinancingDetail", simFinancingDetailId);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimFinancingDetail> selectAllSimFinancingDetails(Map<String, Object> map) {
		return (List<SimFinancingDetail>)DaoHelper.list(nameSpace, "selectAllSimFinancingDetails", map);
	}

	@Override
	public SimFinancingDetail selectSimFinancingDetailById(Integer simFinancingDetailId) {
		return (SimFinancingDetail)DaoHelper.query(nameSpace, "selectSimFinancingDetailById", simFinancingDetailId);
	}
}
