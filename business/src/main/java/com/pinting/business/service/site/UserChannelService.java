package com.pinting.business.service.site;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsUserChannel;

public interface UserChannelService {
	/**
	 * 查询用户配置优先支付通道
	 * @param userId 用户ID
	 * @return  List<BsUserChannel> ,空List 表示没有配置用户支付通道数据
	 */
	public List<BsUserChannel> queryUserChannel(Integer userId);
}
