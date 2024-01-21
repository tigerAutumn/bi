package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSysStatusMapper;
import com.pinting.business.model.BsSysStatus;
import com.pinting.business.model.BsSysStatusExample;
import com.pinting.business.service.manage.MSysStatusService;
import com.pinting.business.util.Constants;
@Service
public class MSysStatusServiceImpl implements MSysStatusService {
	@Autowired
	private BsSysStatusMapper bsSysStatusMapper;
	
	@Override
	public List<BsSysStatus> findBsStatisticsList() {
		BsSysStatusExample example = new BsSysStatusExample();
		List<BsSysStatus> mUserList =bsSysStatusMapper.selectByExample(example);
		return mUserList;
	}
	/**
	 * 修改记录，若类型为定时，延时处理定时的开始时间和结束时间
	 */
	@Override
	public Boolean updateBsStatus(BsSysStatus bsSysStatus) {
		bsSysStatus.setUpdatetime(new Date());
		return bsSysStatusMapper.updateByPrimaryKeySelective(bsSysStatus)>0;
	}
	
	@Override
	public BsSysStatus findById(Integer id) {
		return bsSysStatusMapper.selectByPrimaryKey(id);
	}

	@Override
	public int countBsStatistics() {
		return 0;
	}

	@Override
	public List<BsSysStatus> findBsStatisticsList(int pageNum, int numPerPage) {
		Integer position = (pageNum - 1) * numPerPage;
		return bsSysStatusMapper.findBsStatisticsList(position, numPerPage);
	}

}
