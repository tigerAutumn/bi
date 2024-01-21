package com.pinting.business.service.manage.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSysBalanceDailySnapMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_BGWDailySnapQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_BGWDailySnapQuery;
import com.pinting.business.model.vo.BGWDailySnapVO;
import com.pinting.business.model.vo.HFDailySnapVO;
import com.pinting.business.service.manage.BsSysBalanceDailySnapService;
import com.pinting.business.util.Constants;

/**
 *
 * @author SHENGP
 * @date  2017年6月15日 上午9:36:35
 */
@Service
public class BsSysBalanceDailySnapServiceImpl implements BsSysBalanceDailySnapService {

	@Autowired
	private BsSysBalanceDailySnapMapper bsSysBalanceDailySnapMapper;

	
	@Override
	public List<HashMap<String, Object>> findHfSysBalanceDailySnap(HFDailySnapVO vo) {
		return bsSysBalanceDailySnapMapper.findHfSysBalanceDailySnap(vo);
	}

	@Override
	public int queryHfSysBalanceDailySnapCount() {
		return bsSysBalanceDailySnapMapper.queryHfSysBalanceDailySnapCount();
	}

	@Override
	public int queryBgwSysBalanceDailySnapCount() {
		return bsSysBalanceDailySnapMapper.queryBgwSysBalanceDailySnapCount();
	}

	@Override
	public List<HashMap<String, Object>> findBgwSysBalanceDailySnap(
			BGWDailySnapVO vo) {
		return bsSysBalanceDailySnapMapper.findBgwSysBalanceDailySnap(vo);
	}

	@Override
	public int queryBaofooSysBalanceDailySnapCount() {
		return bsSysBalanceDailySnapMapper.queryBaofooSysBalanceDailySnapCount();
	}

	@Override
	public List<HashMap<String, Object>> findBaofooSysBalanceDailySnap(
			BGWDailySnapVO vo) {
		return bsSysBalanceDailySnapMapper.findBaofooSysBalanceDailySnap(vo);
	}

	@Override
	public void getListByTimePropertySymbol(ReqMsg_Statistics_BGWDailySnapQuery req,
			ResMsg_Statistics_BGWDailySnapQuery res) {
		String propertySymbol = req.getPropertySymbol();
		if(Constants.SYS_SNAP_ACC_TYPE_BGW.equals(propertySymbol)){
			int count = bsSysBalanceDailySnapMapper.queryBgwSysBalanceDailySnapCount();
			if(count > 0){
				res.setTotalRows(count);
				res.setValueList(bsSysBalanceDailySnapMapper.selectBgwSysBalanceDailySnap());
			}
		} else if (Constants.SYS_SNAP_ACC_TYPE_CW_BAOFOO.equals(propertySymbol)) {
			int count = bsSysBalanceDailySnapMapper.queryBaofooSysBalanceDailySnapCount();
			if(count > 0){
				res.setTotalRows(count);
				res.setValueList(bsSysBalanceDailySnapMapper.selectBaofooSysBalanceDailySnap());
			}
		}
	}

}
