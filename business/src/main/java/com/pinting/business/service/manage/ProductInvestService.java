package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsProductInvestView;

public interface ProductInvestService {

	public List<BsProductInvestView> selectProductInvestList(String startTime,String endTime,String pageNum,String numPerPage);
	
	public int selectProductInvestCount(String startTime,String endTime);
}
