/**
 * SimFinancing 业务逻辑实现类
 * @author yanwl
 * @date 2015-11-19
 */
package com.dafy.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dafy.core.enums.FinancingDetailStatus;
import com.dafy.core.helper.DaoHelper;
import com.dafy.core.util.AssertUtil;
import com.dafy.model.pojo.SimFinancing;
import com.dafy.model.pojo.SimFinancingDetail;
import com.dafy.model.vo.LoanInfoVO;
import com.dafy.service.SimFinancingDetailService;
import com.dafy.service.SimFinancingService;

@Service
public class SimFinancingServiceImpl  implements SimFinancingService {
	//声明工作空间
	private final static String nameSpace = "com.dafy.mapper.SimFinancingMapper";
	
	@Autowired
	private SimFinancingDetailService simFinancingDetailService;
	
	
	@Transactional
	@Override
	public boolean insertSimFinancing(SimFinancing simFinancing,List<SimFinancingDetail> list) {
		AssertUtil.assertTrue(DaoHelper.insert(nameSpace, "insertSimFinancing", simFinancing));
		boolean isSuccess = true;
		for (SimFinancingDetail simFinancingDetail : list) {
			simFinancingDetail.setTotalId(simFinancing.getId());
			simFinancingDetail.setStatus(FinancingDetailStatus.RECEIVED.getCode());
			isSuccess = simFinancingDetailService.insertSimFinancingDetail(simFinancingDetail);
			AssertUtil.assertTrue(isSuccess);
		}
		/*SimFinancingDetail sfd = new SimFinancingDetail();
		sfd.setTotalId(simFinancing.getId());
		sfd.setProductAmount(100d);
		sfd.setProductCode("ppppp");
		sfd.setProductOrderNo("2424234324");
		boolean isSuccess = simFinancingDetailService.insertSimFinancingDetail(sfd);
		AssertUtil.assertTrue(isSuccess);*/
		return isSuccess;
	}

	@Transactional
	@Override
	public boolean updateSimFinancing(SimFinancing simFinancing) {
		try {
			return DaoHelper.update(nameSpace, "updateSimFinancing", simFinancing);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	@Override
	public boolean deleteSimFinancing(Integer simFinancingId) {
		try {
			return DaoHelper.delete(nameSpace, "deleteSimFinancing", simFinancingId);
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SimFinancing> selectAllSimFinancings(Map<String, Object> map) {
		return (List<SimFinancing>)DaoHelper.list(nameSpace, "selectAllSimFinancings", map);
	}

	@Override
	public SimFinancing selectSimFinancingById(Integer simFinancingId) {
		return (SimFinancing)DaoHelper.query(nameSpace, "selectSimFinancingById", simFinancingId);
	}

	@Override
	public int countSimFinancings(Map<String, Object> map) {
		return DaoHelper.count(nameSpace, "countSimFinancings", map);
	}

	@Override
	public LoanInfoVO selectLoanInfoByPartnerOrderNo(String orderNo) {
		return (LoanInfoVO)DaoHelper.query(nameSpace, "selectLoanInfoByPartnerOrderNo", orderNo);
	}
}
