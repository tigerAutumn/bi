package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSpecialJnlMapper;
import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.vo.BsSpecialJnlVO;
import com.pinting.business.service.manage.BsSpecialJnlService;

/**
 * 特殊交易流水接口实现类
 * @Project: business
 * @Title: BsSpecialJnlServiceImpl.java
 * @author dingpf
 * @date 2015-2-4 下午6:01:01
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class BsSpecialJnlServiceImpl implements BsSpecialJnlService {

	@Autowired
	private BsSpecialJnlMapper bsSpecialJnlMapper;
	@Override
	public boolean addSpecialJnl(String type,String detail) {
		BsSpecialJnl bsSpecialJnl = new BsSpecialJnl();
		bsSpecialJnl.setType(type);
		bsSpecialJnl.setDetail(detail);
		bsSpecialJnl.setOpTime(new Date());
		return bsSpecialJnlMapper.insertSelective(bsSpecialJnl) > 0 ? true : false;
	}
	
	@Override
	public List<BsSpecialJnlVO> bsSpecialJnlPage(BsSpecialJnlVO record) {
		return bsSpecialJnlMapper.bsSpecialJnlPage(record);
	}
	
	@Override
	public int bsSpecialJnlCount(BsSpecialJnlVO record) {
		return bsSpecialJnlMapper.bsSpecialJnlCount(record);
	}

	@Override
	public Double amountSum(BsSpecialJnlVO record) {
		return bsSpecialJnlMapper.amountSum(record);
	}

	@Override
	public BsSpecialJnl getByPrimaryKeyInDeal(Integer id) {
		return bsSpecialJnlMapper.selectByPrimaryKeyInDeal(id);
	}

	@Override
	public void update(BsSpecialJnl jnl) {
		jnl.setUpdateTime(new Date());
		bsSpecialJnlMapper.updateByPrimaryKeySelective(jnl);
	}

}
