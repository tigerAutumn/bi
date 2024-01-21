package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.model.BsPCA;

public interface ProvinceService {

	/**
	 * 根据key查询省份
	 * @param pcaProvinceKey
	 * @return
	 */
	public List<BsPCA> findProvinces();

	/**
	 * 根据上级id号查询城市
	 * @param parentId
	 * @return
	 */
	public List<BsPCA> findPCAByParentId(Integer parentId);
	
	/**
	 * 根据编码查询PCA
	 * @param itemCode
	 * @return 成功返回BsPCA，否则返回null
	 */
	public BsPCA findPCAByItemCode(String itemCode);

}
