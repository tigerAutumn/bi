package com.pinting.mall.service.site.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.core.util.DateUtil;
import com.pinting.mall.dao.MallUserSignMapper;
import com.pinting.mall.model.MallUserSign;
import com.pinting.mall.model.MallUserSignExample;
import com.pinting.mall.service.site.MallUserSignService;

@Service
public class MallUserSignServiceImpl implements MallUserSignService {

	@Autowired
	private MallUserSignMapper mallUserSignMapper;

	@Override
	public boolean userIsSign(Integer userId) {
		MallUserSignExample userSignExample = new MallUserSignExample();
		userSignExample.createCriteria().andUserIdEqualTo(userId);
		List<MallUserSign> list = mallUserSignMapper.selectByExample(userSignExample);
		if(CollectionUtils.isNotEmpty(list) 
				&& DateUtil.isSameDay(list.get(0).getSignTime(), new Date())){
			return true;
		}
		
		return false;
	}
	
}
