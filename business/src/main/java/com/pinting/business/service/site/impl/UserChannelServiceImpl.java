package com.pinting.business.service.site.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsUserChannelMapper;
import com.pinting.business.model.BsUserChannel;
import com.pinting.business.model.BsUserChannelExample;
import com.pinting.business.service.site.UserChannelService;
import com.pinting.gateway.in.util.MethodRole;

@Service
public class UserChannelServiceImpl implements UserChannelService {
	@Autowired
	private BsUserChannelMapper bsUserChannelMapper;
	
	@Override
	@MethodRole("APP")
	public List<BsUserChannel> queryUserChannel(Integer userId) {
		BsUserChannelExample bsUserChannelExample = new BsUserChannelExample();
		bsUserChannelExample.createCriteria().andUserIdEqualTo(userId);
		List<BsUserChannel> bsUserChannels= bsUserChannelMapper.selectByExample(bsUserChannelExample);
		return bsUserChannels;
	}

}
