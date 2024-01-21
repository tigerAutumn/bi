package com.pinting.gateway.in.service;

import java.util.List; 

import com.pinting.business.model.vo.InvestInfoXiCaiVO;
import com.pinting.business.model.vo.UserInfoXiCaiVO;
import com.pinting.gateway.hessian.message.xicai.model.ProductInfo;

public interface CsaiService {
	
	/**
	 * 
	 * 希财用户信息统计
	 * @param startdate 开始时间(用户注册时间),为空则不限制(格式为 2015-1-20)
	 * @param enddate 结束时间(用户注册时间),为空则不限制(格式为 2015-1-20)
	 * @param page 页码,默认为1
	 * @param pagesize 返回记录条数,默认为10
	 * @return
	 */
	 public List<UserInfoXiCaiVO> findXiCaiUserList(String startdate, String enddate, Integer page, Integer pagesize);
	
	/**
	 * 希财用户数据统计
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public Integer findXiCaiUserCount(String startdate, String enddate);
	
	/**
	 * 希财用户投资信息统计
	 * @param startdate
	 * @param enddate
	 * @param page
	 * @param pagesize
	 * @return
	 */
	public List<InvestInfoXiCaiVO> findXiCaiInvestInfo(String startdate, String enddate, Integer page, Integer pagesize);
	
	/**
	 * 希财用户投资产品数量统计
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	public Integer findXiCaiInvestCount(String startdate, String enddate);
	
	

	/**
	 * 根据产品Id查询产品信息
	 * @param productId 产品ID
	 * @return 成功返回产品信息对象，否则返回null
	 */
	public ProductInfo findProductInfoByProductId(Integer productId);

}
