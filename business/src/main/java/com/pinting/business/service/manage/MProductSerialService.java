package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsProductSerial;
import com.pinting.business.model.BsProductSerialExample;

public interface MProductSerialService {
	/**
	 * 查询产品系列列表
	 * @param example
	 * @return List<BsProductSerial>
	 * @since JDK 1.7
	 */
	public List<BsProductSerial> findBsProductSerials(BsProductSerialExample example);
	
	/**
	 * 根据主键查询产品系列
	 * @param id
	 * @return BsProductSerial
	 * @since JDK 1.7
	 */
	public BsProductSerial findBsProductSerialsById(Integer id);
}
