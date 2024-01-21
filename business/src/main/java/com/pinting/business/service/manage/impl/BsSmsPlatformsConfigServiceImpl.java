package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSmsPlatformsConfigMapper;
import com.pinting.business.model.BsSmsPlatformsConfig;
import com.pinting.business.model.BsSmsPlatformsConfigExample;
import com.pinting.business.model.vo.BsSmsPlatformsConfigVO;
import com.pinting.business.service.manage.BsSmsPlatformsConfigService;

@Service
public class BsSmsPlatformsConfigServiceImpl implements
		BsSmsPlatformsConfigService {
	@Autowired
	private BsSmsPlatformsConfigMapper bsSmsPlatformsConfigMapper;

	@Override
	public List<BsSmsPlatformsConfigVO> selectAllList() {
		
		return bsSmsPlatformsConfigMapper.selectAllList();
	}

	@Override
	public BsSmsPlatformsConfig selectById(Integer id) {
		return bsSmsPlatformsConfigMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateSmsPlatforms(BsSmsPlatformsConfig config) {
		//修改前
		BsSmsPlatformsConfig smsPlatformsConfigTemp = bsSmsPlatformsConfigMapper.selectByPrimaryKey(config.getId());
		//修改
		config.setUpdateTime(new Date());
		bsSmsPlatformsConfigMapper.updateByPrimaryKeySelective(config);
		
		if(config.getPriority() != smsPlatformsConfigTemp.getPriority()){
			//互换优先级
			BsSmsPlatformsConfig temp = new BsSmsPlatformsConfig();
			temp.setPriority(smsPlatformsConfigTemp.getPriority());
			
			BsSmsPlatformsConfigExample example = new BsSmsPlatformsConfigExample();
			example.createCriteria().andPriorityEqualTo(config.getPriority())
			.andIdNotEqualTo(config.getId())
			.andPlatformsTypeEqualTo(smsPlatformsConfigTemp.getPlatformsType());
			List<BsSmsPlatformsConfig> list = bsSmsPlatformsConfigMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(list)){
				bsSmsPlatformsConfigMapper.updateByExampleSelective(temp, example);
			}
		}
	}

}
