package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsWxAutoReplyMapper;
import com.pinting.business.model.BsWxAutoReply;
import com.pinting.business.model.vo.BsWxAutoReplyVO;
import com.pinting.business.service.manage.BsWxAutoReplyService;

@Service
public class BsWxAutoReplyServiceImpl implements BsWxAutoReplyService {

	@Autowired
	private BsWxAutoReplyMapper bsWxAutoReplyMapper;

	@Override
	public void addWxAutoReply(BsWxAutoReply bsWxAutoReply) {
		bsWxAutoReply.setCreateTime(new Date());
		bsWxAutoReply.setUpdateTime(new Date());
		bsWxAutoReplyMapper.insertSelective(bsWxAutoReply);
	}

	@Override
	public void updateReply(BsWxAutoReply bsWxAutoReply) {
		bsWxAutoReply.setUpdateTime(new Date());
		bsWxAutoReplyMapper.updateByPrimaryKeySelective(bsWxAutoReply);
		
	}

	@Override
	public List<BsWxAutoReply> getListByReplyVO(BsWxAutoReplyVO bsWxAutoReplyVO) {
		
		return bsWxAutoReplyMapper.getListByReplyVO(bsWxAutoReplyVO);
	}

	@Override
	public int getCountByReplyVO(BsWxAutoReplyVO bsWxAutoReplyVO) {
		return bsWxAutoReplyMapper.getCountByReplyVO(bsWxAutoReplyVO);
	}

	@Override
	public BsWxAutoReply getById(Integer id) {
		return bsWxAutoReplyMapper.selectByPrimaryKey(id);
	}

	@Override
	public void deleteReply(Integer id) {
		bsWxAutoReplyMapper.deleteByPrimaryKey(id);
		
	}
	
	
}
