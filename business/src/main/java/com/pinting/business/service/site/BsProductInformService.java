package com.pinting.business.service.site;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsProductInform;
import com.pinting.business.model.vo.ProductInformVO;

public interface BsProductInformService {
	
	/**
	 * 根据用户id和产品id查询是否存在短信提醒记录
	 * @param userId
	 * @param productId
	 * @return
	 */
	public int countByUserIdProductId(BsProductInform record);
	
	/**
	 * 添加产品提醒消息
	 * @param record
	 */
	public void addProductInform(BsProductInform record);
	
	/**
	 * 根据主键删除
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 根据时间和提醒类型查询需要提醒的列表
	 * @param startTime
	 * @param endTime
	 * @param remindType
	 * @return
	 */
	public List<ProductInformVO> getListByTime(Date startTime, Date endTime, String remindType);

	/**
	 * 根据用户id,时间及类型查询是否存在数据
	 * 创建时间>=传入的创建时间
	 * @param record
	 * @return
	 */
	public Integer countByUserTime(BsProductInform record);
}
