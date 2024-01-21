package com.pinting.business.service.manage;

import java.util.HashMap;
import java.util.List;

import com.pinting.business.hessian.manage.message.ReqMsg_Statistics_BGWDailySnapQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Statistics_BGWDailySnapQuery;
import com.pinting.business.model.vo.BAOFOODailySnapVO;
import com.pinting.business.model.vo.BGWDailySnapVO;
import com.pinting.business.model.vo.HFDailySnapVO;

/**
 * 日终账务查询service
 * @author SHENGP
 * @date  2017年6月15日 上午9:35:56
 */
public interface BsSysBalanceDailySnapService {

	/**
	 * 查询恒丰日终账务
	 * @param accType
	 * @return
	 */
	List<HashMap<String, Object>> findHfSysBalanceDailySnap(HFDailySnapVO vo);
	
	/**
	 * 查询恒丰日终账务计数
	 * @return
	 */
	int queryHfSysBalanceDailySnapCount();
	
	/**
	 * 查询币港湾日终账务计数
	 * @return
	 */
	int queryBgwSysBalanceDailySnapCount();
	
	/**
	 * 查询币港湾日终账务
	 * @param accType
	 * @return
	 */
	List<HashMap<String, Object>> findBgwSysBalanceDailySnap(BGWDailySnapVO vo);
	
	/**
	 * 查询宝付日终账务计数
	 * @return
	 */
	int queryBaofooSysBalanceDailySnapCount();
	
	/**
	 * 查询宝付日终账务
	 * @param accType
	 * @return
	 */
	List<HashMap<String, Object>> findBaofooSysBalanceDailySnap(BGWDailySnapVO vo);
	
	/**
	 * 
	 * @param req
	 * @param res
	 */
	void getListByTimePropertySymbol(ReqMsg_Statistics_BGWDailySnapQuery req, ResMsg_Statistics_BGWDailySnapQuery res);
	
}
