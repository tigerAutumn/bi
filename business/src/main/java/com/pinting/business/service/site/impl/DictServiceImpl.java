package com.pinting.business.service.site.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsDictMapper;
import com.pinting.business.model.BsDict;
import com.pinting.business.model.BsDictExample;
import com.pinting.business.service.site.DictService;
/**
 * @Project: business
 * @Title: DictServiceImpl.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:47:39
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class DictServiceImpl implements DictService{
	@Autowired
	private BsDictMapper bsDictMapper;

	@Override
	public List<BsDict> findDictById(Integer dictId) {
		BsDictExample bsDictExample=new BsDictExample();
		bsDictExample.createCriteria().andIdEqualTo(dictId);
		List<BsDict> dataList = bsDictMapper.selectByExample(bsDictExample);
		return dataList.size() >0? dataList : null;
	}

}
