package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsWxInfoMapper;
import com.pinting.business.dao.MUserMapper;
import com.pinting.business.dao.MWxMuserInfoMapper;
import com.pinting.business.enums.WxMuserStatusEnum;
import com.pinting.business.model.MUser;
import com.pinting.business.model.MUserExample;
import com.pinting.business.model.MWxMuserInfo;
import com.pinting.business.model.MWxMuserInfoExample;
import com.pinting.business.model.vo.MUpdateWxUserInfoResVO;
import com.pinting.business.model.vo.SysOperationalDataVO;
import com.pinting.business.service.manage.MWxUserService;
import com.pinting.core.util.StringUtil;

@Service
public class MWxUserServiceImpl implements MWxUserService{
	@Autowired
	private BsWxInfoMapper bsWxInfoMapper;
	@Autowired
	private MWxMuserInfoMapper mWxMuserInfoMapper;
	@Autowired
	private MUserMapper mUserMapper;
	
	@Override
	public Map<String, Object> countWxUserNum(Map<String, Object> map) {
		return bsWxInfoMapper.countWxUserNum(map);
	}
	
	@Override
	public Integer countOperationalDataInfo(String nickName) {
		return mWxMuserInfoMapper.countOperationalDataInfo(nickName);
	}
	
	@Override
	public List<SysOperationalDataVO> selectOperationalDataInfoList(String nickName, Integer page,
			Integer offset) {
		Integer position = (page - 1) * offset;
		return mWxMuserInfoMapper.selectOperationalDataInfoList(nickName, position, offset);
	}
	
	@Override
	public Boolean updateStatus(String status, Integer mId, Integer opUserId) {
		Boolean isSuccess = false;
		int count = 0;
		if (StringUtil.isNotEmpty(status)) {
			MWxMuserInfo mWxMuserInfo = this.findById(mId);
			if (mWxMuserInfo != null) {
				mWxMuserInfo.setStatus(status);
				mWxMuserInfo.setUpdateTime(new Date());
				mWxMuserInfo.setOperator(opUserId);
				count = mWxMuserInfoMapper.updateByPrimaryKeySelective(mWxMuserInfo);
			}
		}
		if (count > 0) {
			isSuccess = true;
		}
		return isSuccess;
	}

	@Override
	public MWxMuserInfo findById(Integer id) {
		MWxMuserInfoExample example = new MWxMuserInfoExample();
		example.createCriteria().andIdEqualTo(id);
		List<MWxMuserInfo> list = mWxMuserInfoMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public MUpdateWxUserInfoResVO updateWxUserInfo(SysOperationalDataVO req) {
		
		MUpdateWxUserInfoResVO res = new MUpdateWxUserInfoResVO();
		// 对应真实姓名和状态正常的用户是否存在
		MUserExample example = new MUserExample();
		example.createCriteria().andNameEqualTo(req.getmName()).andStatusEqualTo(1);
		List<MUser> list = mUserMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			MWxMuserInfoExample mWxMuserInfoExample = new MWxMuserInfoExample();
			mWxMuserInfoExample.createCriteria().andIdNotEqualTo(req.getId()).andMuserIdEqualTo(list.get(0).getId()).andStatusEqualTo(WxMuserStatusEnum.WX_MUSER_STATUS_OPEN.getCode());
			List<MWxMuserInfo> mWxUserInfoList = mWxMuserInfoMapper.selectByExample(mWxMuserInfoExample);
			if (CollectionUtils.isEmpty(mWxUserInfoList)) {
				
				// 微信运营用户信息表记录校验
				MWxMuserInfoExample wxMuserInfoExample = new MWxMuserInfoExample();
				wxMuserInfoExample.createCriteria().andIdEqualTo(req.getId()).andStatusEqualTo(WxMuserStatusEnum.WX_MUSER_STATUS_OPEN.getCode());
				List<MWxMuserInfo> wxUserInfoList = mWxMuserInfoMapper.selectByExample(wxMuserInfoExample);
				if (!CollectionUtils.isEmpty(wxUserInfoList)) {
					MWxMuserInfo mWxMuserInfo = wxUserInfoList.get(0);
					mWxMuserInfo.setMuserId(list.get(0).getId());
					mWxMuserInfo.setUpdateTime(new Date());
					mWxMuserInfo.setOperator(req.getOpUserId());
					mWxMuserInfoMapper.updateByPrimaryKeySelective(mWxMuserInfo);
				} else {
					res.setReturnCode("mWxMuserInfo_empty");
					res.setReturnMsg("微信运营用户信息记录不存在，请核对！");
				}
			} else {
				res.setReturnCode("mWxMuserInfo_username_exists");
				res.setReturnMsg("运营数据微信用户管理，真实姓名已存在，请核对！");
			}
		} else {
			res.setReturnCode("muser_empty");
			res.setReturnMsg("用户"+ req.getmName() +"不存在，请核对！");
		}
		return res;
	}

}
