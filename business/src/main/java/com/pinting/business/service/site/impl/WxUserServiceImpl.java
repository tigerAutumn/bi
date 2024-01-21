package com.pinting.business.service.site.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsWxInfoMapper;
import com.pinting.business.dao.BsWxUserInfoMapper;
import com.pinting.business.model.BsWxInfo;
import com.pinting.business.model.BsWxUserInfo;
import com.pinting.business.model.BsWxUserInfoExample;
import com.pinting.business.service.site.WxUserService;

@Service
public class WxUserServiceImpl implements WxUserService{
	@Autowired
	private BsWxUserInfoMapper bsWxUserInfoMapper;
	@Autowired
	private BsWxInfoMapper bsWxInfoMapper;
	@Autowired
	private TransactionTemplate transactionTemplate;

	@Override
	public Integer registerWxUser(BsWxUserInfo bsWxUserInfo) {
		return bsWxUserInfoMapper.insertSelective(bsWxUserInfo);
	}

	@Override
	public Integer isWxUserExist(String openId) {
		BsWxUserInfoExample example = new BsWxUserInfoExample();
		example.createCriteria().andOpenIdEqualTo(openId);
		
		List<BsWxUserInfo> bsUserList = bsWxUserInfoMapper.selectByExample(example);
		if(bsUserList != null && bsUserList.size() > 0)
		{
			return bsUserList.get(0).getId();
		}
		return 0;
	}

	@Override
	public List<BsWxUserInfo> findWxUser(String openId) {
		BsWxUserInfoExample example = new BsWxUserInfoExample();
		example.createCriteria().andOpenIdEqualTo(openId);
		
		List<BsWxUserInfo> bsUserList = bsWxUserInfoMapper.selectByExample(example);
		if(bsUserList != null && bsUserList.size() > 0)
		{
			return bsUserList;
		}
		return null;
	}

	@Override
	public BsWxUserInfo isWxUserExistWithUserIdNull(String openId) {
		BsWxUserInfoExample example = new BsWxUserInfoExample();
		example.createCriteria().andOpenIdEqualTo(openId).andUserIdIsNull();
		List<BsWxUserInfo> bsUserList = bsWxUserInfoMapper.selectByExample(example);
		if(bsUserList != null && bsUserList.size() > 0)
		{
			return bsUserList.get(0);
		}
		return null;
	}

	@Override
	public BsWxUserInfo isWxUserExistWithUserId(String openId, String userId) {
		BsWxUserInfoExample example = new BsWxUserInfoExample();
		example.createCriteria().andOpenIdEqualTo(openId).andUserIdEqualTo(Integer.valueOf(userId));
		List<BsWxUserInfo> bsUserList = bsWxUserInfoMapper.selectByExample(example);
		if(bsUserList != null && bsUserList.size() > 0)
		{
			return bsUserList.get(0);
		}
		return null;
	}

	@Override
	public void modifyWxUser4Follow(Integer id, String isFollow) {
		BsWxInfo info = new BsWxInfo();
		info.setId(id);
		info.setIsFollow(isFollow);
		bsWxInfoMapper.updateByPrimaryKey(info);
	}

	@Override
	public BsWxUserInfo getWxUserByUserId(Integer userId) {
		BsWxUserInfoExample example = new BsWxUserInfoExample();
		example.createCriteria().andUserIdEqualTo(userId);
		
		List<BsWxUserInfo> bsUserList = bsWxUserInfoMapper.selectByExample(example);
		if(bsUserList != null && bsUserList.size() > 0)
		{
			return bsUserList.get(0);
		}
		return null;
	}

	@Override
	public void modifyWxUserInfo(BsWxUserInfo wxUser) {
		bsWxUserInfoMapper.updateByPrimaryKeySelective(wxUser);
	}

    @Override
    public void unbindWxOpenId(final Integer userId) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                BsWxUserInfoExample bsWxUserInfoExample = new BsWxUserInfoExample();
                bsWxUserInfoExample.createCriteria().andUserIdEqualTo(userId);
                bsWxUserInfoMapper.deleteByExample(bsWxUserInfoExample);
                return true;
            }
        });
    }

	@Override
	public void deleteByOpenId(String openId, Integer userId) {
		BsWxUserInfoExample example = new BsWxUserInfoExample();
		example.createCriteria().andOpenIdEqualTo(openId).andUserIdNotEqualTo(userId);
		List<BsWxUserInfo> bsUserList = bsWxUserInfoMapper.selectByExample(example);
		if(bsUserList != null && bsUserList.size() > 0)
		{
			bsWxUserInfoMapper.deleteByExample(example);
		}
	}
}
