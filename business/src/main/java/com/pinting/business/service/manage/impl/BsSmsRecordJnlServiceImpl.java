package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSmsPlatformsConfigMapper;
import com.pinting.business.dao.BsSmsRecordJnlMapper;
import com.pinting.business.enums.SmsPlatformsCodeEnum;
import com.pinting.business.model.BsSmsPlatformsConfig;
import com.pinting.business.model.BsSmsPlatformsConfigExample;
import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.model.vo.BsSmsRecordJnlReVo;
import com.pinting.business.model.vo.BsSmsRecordJnlVO;
import com.pinting.business.service.manage.BsSmsRecordJnlService;

@Service
public class BsSmsRecordJnlServiceImpl implements BsSmsRecordJnlService {
	@Autowired
	private BsSmsRecordJnlMapper bsSmsRecordJnlMapper;
	@Autowired
	private BsSmsPlatformsConfigMapper smsPlatformsConfigMapper;

	@Override
	public List<BsSmsRecordJnlReVo> smsRecordJnlList(String content, String mobile,
			Date beginTime, Date overTime, String type,Integer statusCode, int pageNum, int numPerPage, String platformsCode) {
		BsSmsRecordJnlVO jnlVO = new BsSmsRecordJnlVO();
		jnlVO.setPageNum(pageNum);
		jnlVO.setNumPerPage(numPerPage);
		jnlVO.setBeginTime(beginTime);
		jnlVO.setOverTime(overTime);
		jnlVO.setMobile(mobile);
		jnlVO.setContent(content);
		jnlVO.setType(type);
		jnlVO.setPlatformsCode(platformsCode);
		if(statusCode != null){
			jnlVO.setStatusCode(statusCode);
		}
		
		return bsSmsRecordJnlMapper.selectRecordJnlList(jnlVO);
	}

	@Override
	public int smsRecordJnlCount(String content, String mobile, Date beginTime,
			Date overTime, String type,Integer statusCode, String platformsCode) {
		BsSmsRecordJnlVO jnlVO = new BsSmsRecordJnlVO();
		jnlVO.setBeginTime(beginTime);
		jnlVO.setOverTime(overTime);
		jnlVO.setMobile(mobile);
		jnlVO.setContent(content);
		jnlVO.setType(type);
		jnlVO.setPlatformsCode(platformsCode);
		if(statusCode != null){
			jnlVO.setStatusCode(statusCode);
		}
		return bsSmsRecordJnlMapper.countRecordJnlList(jnlVO);
	}

	@Override
	public void updateJnl(BsSmsRecordJnl jnl) {
		jnl.setUpdateTime(new Date());
		bsSmsRecordJnlMapper.updateByPrimaryKeySelective(jnl);
	}

	@Override
	public void insertJnl(BsSmsRecordJnl jnl) {
		bsSmsRecordJnlMapper.insertSelective(jnl);
		
	}

	@Override
	public BsSmsRecordJnl selectByMobileSerNo(String mobile, String serNo, SmsPlatformsCodeEnum smsEnum) {
		if(smsEnum == null){
			return bsSmsRecordJnlMapper.getByMobileSerNo(mobile, serNo);
		}else{
			BsSmsPlatformsConfigExample example = new BsSmsPlatformsConfigExample();
			example.createCriteria().andPlatformsCodeEqualTo(smsEnum.getCode());
			List<BsSmsPlatformsConfig> list = smsPlatformsConfigMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(list)){
				return bsSmsRecordJnlMapper.getByMobileSerNoPlatformsId(mobile, serNo, list.get(0).getId());
			}else{
				return bsSmsRecordJnlMapper.getByMobileSerNo(mobile, serNo);
			}
		}
		
	}

}
