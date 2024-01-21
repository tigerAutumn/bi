package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.model.BsProduct;

/**
 * 活动相关标的
 * @author bianyatian
 * @2016-12-12 下午4:22:26
 */
public interface ActiveProductService {
	
	/**
	 * 根据显示端查询活动显示的标的列表
	 * @return
	 */
	public List<BsProduct> select00ActiveProdcutList(String terminal);

}
