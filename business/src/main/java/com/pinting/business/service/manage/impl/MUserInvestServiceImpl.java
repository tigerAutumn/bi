package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsUserInvestViewMapper;
import com.pinting.business.model.BsUserInvestView;
import com.pinting.business.model.BsUserInvestViewExample;
import com.pinting.business.model.vo.BsUserInvestViewVO;
import com.pinting.business.service.manage.MUserInvestViewService;

@Service
public class MUserInvestServiceImpl implements MUserInvestViewService {
	@Autowired
	private BsUserInvestViewMapper userInvestViewMapper;

	@Override
	public List<BsUserInvestView> findUserInvestList(
			BsUserInvestViewVO userInvestView) {
		return userInvestViewMapper.selectUserInvestList(userInvestView);
	}

	@Override
	public int findUserInvestAllCount(BsUserInvestViewVO userInvestView) {
		return userInvestViewMapper.selectUserInvestAllCount(userInvestView);
	}

	@Override
	public int saveUserInvest(BsUserInvestView userInvestView) {
		return userInvestViewMapper.insertSelective(userInvestView);
	}

	@Override
	public BsUserInvestView findUserInvestByDate(BsUserInvestViewExample example) {
		List<BsUserInvestView> list = userInvestViewMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)) {
			return list.size() > 0 ? list.get(0) : null;
		}
		return null;
	}

	@Override
	public int updateUserInvest(BsUserInvestView userInvestView) {
		return userInvestViewMapper.updateByPrimaryKeySelective(userInvestView);
	}
}
