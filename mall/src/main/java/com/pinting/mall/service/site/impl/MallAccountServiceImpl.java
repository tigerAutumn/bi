package com.pinting.mall.service.site.impl;

import java.util.List;

import com.github.pagehelper.PageHelper;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.vo.MallAccountJnlVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.mall.dao.MallAccountJnlMapper;
import com.pinting.mall.dao.MallAccountMapper;
import com.pinting.mall.enums.MallAccountEnum;
import com.pinting.mall.hessian.site.message.ReqMsg_MallPoints_PointsRecord;
import com.pinting.mall.model.MallAccount;
import com.pinting.mall.model.MallAccountExample;
import com.pinting.mall.model.MallAccountJnl;
import com.pinting.mall.service.site.MallAccountService;

@Service
public class MallAccountServiceImpl implements MallAccountService {

	private Logger logger = LoggerFactory.getLogger(MallAccountServiceImpl.class);
	@Autowired
	private MallAccountMapper accountMapper;
	@Autowired
	private MallAccountJnlMapper accountJnlMapper;
	
	@Override
	public MallAccount getAccountByUserId(Integer userId) {
		MallAccountExample accountExample = new MallAccountExample();
		accountExample.createCriteria().andUserIdEqualTo(userId)
			.andAccountTypeEqualTo(MallAccountEnum.MALL_POINTS_JSH.getCode());
		
		List<MallAccount> list = accountMapper.selectByExample(accountExample);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<MallAccountJnl> getPointsRecordList(
			ReqMsg_MallPoints_PointsRecord req) {
		List<MallAccountJnl> list = accountJnlMapper.selectUserPointsRecord(req.getUserId(),
				req.getStart(), req.getNumPerPage());
		return list;
	}

	@Override
	public Integer countPointsRecordList(ReqMsg_MallPoints_PointsRecord req) {
		int count = accountJnlMapper.countUserPointsRecord(req.getUserId());
		return count;
	}

	@Override
	public List<MallAccountJnlVO> queryMallUserPointsList(MallAccountJnlVO record) {
		List<MallAccountJnlVO> resultList = accountJnlMapper.selectMallUserPointsList(record);
		return (CollectionUtils.isEmpty(resultList)) ? null : resultList;
	}

	@Override
	public Integer queryMallUserPointsCount(MallAccountJnlVO record) {
		return accountJnlMapper.selectMallUserPointsCount(record);
	}

	@Override
	public Double queryMallUserPointsSum(MallAccountJnlVO record) {
		return accountJnlMapper.selectMallUserPointsSum(record);
	}

	@Override
	public PagerModelRspDTO<MallAccountJnlVO> queryMallUserPointsList(MallAccountJnlVO record, PagerReqDTO pagerReq) {
		PageHelper.startPage(pagerReq.getPageNum(), pagerReq.getNumPerPage());
		List<MallAccountJnlVO> resultList =  accountJnlMapper.selectMallUserPointsList(record);
		return new PagerModelRspDTO(pagerReq, resultList);
	}
}
