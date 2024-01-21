package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsUserTransDetailMapper;
import com.pinting.business.model.vo.BsUserTransDetailVO;
import com.pinting.business.service.manage.MUserTransDetailService;

@Service
public class MUserTransDetailServiceImpl implements MUserTransDetailService {

	@Autowired
	private BsUserTransDetailMapper bsUserTransDetailMapper;

	@Override
	public List<BsUserTransDetailVO> findUserTransDetailListQueryByPage(
			String sMobile, String sUserName, String sTransType, int pageNum,
			int numPerPage) {
		BsUserTransDetailVO userTransDetailVO = new BsUserTransDetailVO();
		userTransDetailVO.setPageNum(pageNum);
		userTransDetailVO.setNumPerPage(numPerPage);
		userTransDetailVO.setMobile(sMobile);
		userTransDetailVO.setUserName(sUserName);
		userTransDetailVO.setTransType(sTransType);
		return bsUserTransDetailMapper.selectUserTransDetailList(userTransDetailVO);
	}

	@Override
	public int countUserTransDetail(String sMobile, String sUserName,
			String sTransType) {
		BsUserTransDetailVO userTransDetailVO = new BsUserTransDetailVO();
		userTransDetailVO.setMobile(sMobile);
		userTransDetailVO.setUserName(sUserName);
		userTransDetailVO.setTransType(sTransType);
		return bsUserTransDetailMapper.countUserTransDetail(userTransDetailVO);
	}
}
