package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsSysStatus;
/**
 * 系统状态
 * @Project: business
 * @Title: MSysStatusService.java
 * @author linkin
 * @date 2015-3-9 13:35:18
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface MSysStatusService {

	/**
	 * 查询系统状态表信息
	 * @return
	 */
	public List<BsSysStatus> findBsStatisticsList();
	
	/**
	 * 保存修改记录
	 * @param bsSysStatus
	 * @return
	 */
	public Boolean updateBsStatus(BsSysStatus bsSysStatus);
	
	/**
	 * 根据id查询数据
	 * @param id
	 * @return
	 */
	public BsSysStatus findById(Integer id);
	
	/**
	 * 系统状态表信息计数
	 * @return
	 */
	public int countBsStatistics(); 
	
	/**
	 * 查询系统状态表信息，带分页
	 * @return
	 */
	public List<BsSysStatus> findBsStatisticsList(int pageNum, int numPerPage);
	
}
