package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.vo.BsUserTransDetailVO;

public interface MUserTransDetailService {
	/**
	 * 根据条件查询进行分页查询，条件可以为空
	 * @param pageNum
	 * @param numPerPage
	 * @param sMobile
	 * @param sUserName
	 * @param sTransType
	 * @return 返回用户交易记录列表
	 */
	List<BsUserTransDetailVO> findUserTransDetailListQueryByPage(String sMobile,String sUserName,String sTransType,int pageNum,
			int numPerPage);
	
	/**
	 * 根据条件查询进行分页查询，条件可以为空
	 * @param sMobile
	 * @param sUserName
	 * @param sTransType
	 * @return 用户交易总记录数
	 */
	public int countUserTransDetail(String sMobile,String sUserName,String sTransType);
}
