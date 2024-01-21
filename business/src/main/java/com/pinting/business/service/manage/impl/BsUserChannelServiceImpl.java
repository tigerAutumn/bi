package com.pinting.business.service.manage.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsUserChannelMapper;
import com.pinting.business.model.BsUserChannel;
import com.pinting.business.model.vo.BsUserChannelVO;
import com.pinting.business.service.manage.BsUserChannelService;

@Service
public class BsUserChannelServiceImpl implements BsUserChannelService{
	
	@Autowired
	private BsUserChannelMapper bsUserChannelMapper;

	@Override
	public ArrayList<BsUserChannelVO> findUserChannelList(String userName,
			String mobile, int pageNum, int numPerPage, String orderDirection,
			String orderField) {
		BsUserChannelVO userChannel = new BsUserChannelVO();
		if (userName != null && !"".equals(userName)) {
			userChannel.setUserName(userName);
		}
		if (mobile != null && !"".equals(mobile)) {
			userChannel.setMobile(mobile);
		}
		if(orderDirection != null && (!"".equals(orderDirection)) && orderField != null && (!"".equals(orderField))) {
			userChannel.setOrderDirection(orderDirection);
			userChannel.setOrderField(orderField);
		}
		userChannel.setPageNum(pageNum);
		userChannel.setNumPerPage(numPerPage);
		return bsUserChannelMapper.selectUserChannelListPageInfo(userChannel);
	}

	@Override
	public int findCountUserChannel(String userName, String mobile) {
		BsUserChannelVO userChannel = new BsUserChannelVO();
		if (userName != null && !"".equals(userName)) {
			userChannel.setUserName(userName);
		}
		if (mobile != null && !"".equals(mobile)) {
			userChannel.setMobile(mobile);
		}
		return bsUserChannelMapper.selectCountUserChannel(userChannel);
	}
	
	/**
	 * 根据id修改信息
	 */
	@Override
	public int updateUserChannel(BsUserChannel record) {
		return bsUserChannelMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 添加
	 */
	@Override
	public int addUserChannel(BsUserChannel record) {
		return bsUserChannelMapper.insert(record);
	}

	/**
	 * 根据id查询
	 */
	@Override
	public BsUserChannelVO bsUserChannelPrimaryKey(Integer id) {
		return bsUserChannelMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据id删除
	 */
	@Override
	public int deleteUserChannelById(Integer id) {
		return bsUserChannelMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 查询用户优先支付渠道是否已存在
	 */
	@Override
	public BsUserChannel selectUserChannel(BsUserChannel record) {
		return bsUserChannelMapper.selectUserChananel(record);
	}

	@Override
	public ArrayList<BsUserChannelVO> find19payCardNameChannelPriority(
			BsUserChannelVO bsUserChannelVO) {
		return bsUserChannelMapper.select19payCardNameChannelPriority(bsUserChannelVO);
	}
	
}
