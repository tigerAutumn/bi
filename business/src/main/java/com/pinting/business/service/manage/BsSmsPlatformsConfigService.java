package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsSmsPlatformsConfig;
import com.pinting.business.model.vo.BsSmsPlatformsConfigVO;

/**
 * 短信平台管理
 * @author bianyatian
 * @2017-6-20 下午3:08:09
 */
public interface BsSmsPlatformsConfigService {
	
	/**
	 * 查询所有短信平台按优先级排序，并查询每个平台在当前时间之前的60分钟内的成功率，有返回状态的总数低于20不计成功率
	 * @return
	 */
	List<BsSmsPlatformsConfigVO> selectAllList();
	
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	BsSmsPlatformsConfig selectById(Integer id);
	
	/**
	 * 修改
	 * @param config
	 */
	void updateSmsPlatforms(BsSmsPlatformsConfig config);

}
