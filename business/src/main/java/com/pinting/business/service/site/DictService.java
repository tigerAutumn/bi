package com.pinting.business.service.site;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.model.BsDict;
import com.pinting.business.model.BsUser;
/**
 * @Project: business
 * @Title: DictService.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:48:18
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface DictService {
	/**
	 * 根据字典编号查询字典信息
	 * @param dictId 字典编号
	 * @return 如果找到信息，返回BsDict集合，找不到则返回null
	 */
	public List<BsDict> findDictById(Integer dictId);
}
