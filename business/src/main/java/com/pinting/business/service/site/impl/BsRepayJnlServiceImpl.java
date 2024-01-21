package com.pinting.business.service.site.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsRepayJnlMapper;
import com.pinting.business.model.BsRepayJnl;
import com.pinting.business.service.site.BsRepayJnlService;
@Service
public class BsRepayJnlServiceImpl implements BsRepayJnlService {

	@Autowired
	private BsRepayJnlMapper bsRepayJnlMapper;
	
	@Override
	public void addReplyJnl(BsRepayJnl replyJnl) {
		replyJnl.setCreateTime(new Date());
		bsRepayJnlMapper.insertSelective(replyJnl);
	}

}
