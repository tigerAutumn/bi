package com.pinting.business.service.manage;

import java.util.ArrayList;
import java.util.List;

import com.pinting.business.model.BsRedPacketApply;
import com.pinting.business.model.vo.RedPaktBudgetStatVO;
import com.pinting.business.model.vo.BsRedPacketApplyVO;

public interface BsRedPacketApplyService {
	
	/**
	 * 红包申请列表
	 * @param rpName 名称
	 * @param status 状态
	 * @param pageNum
	 * @param numPerPage
	 * @param orderDirection
	 * @param orderField
	 * @return
	 */
	public ArrayList<BsRedPacketApplyVO> findBsRedPacketApplyList(String rpName, String checkStatus, Integer creator, 
			Integer pageNum, Integer numPerPage, String orderDirection, String orderField);
	
	/**
	 * 红包申请数量
	 * @param rpName
	 * @param integer
	 * @return
	 */
	public int findBsRedPacketApplyCount(String rpName, String checkStatus, Integer creator);
	
	/**
	 * 添加红包
	 * @param record
	 * @return
	 */
	public int addBsRedPacketApply(BsRedPacketApply record);
	
	/**
	 * 查询红包名称是否已存在
	 * @param record
	 * @return
	 */
	public BsRedPacketApply findBsRedPacketApply(BsRedPacketApply record);
	
	/**
	 * 根据id查询
	 */
	public BsRedPacketApply findByPrimaryKey(Integer id);
	
	/**
	 * 根据id修改
	 * @param record
	 * @return
	 */
	public int updateBsRedPacketApply(BsRedPacketApply record);
	
	/**
	 * 财务红包统计
	 * @return
	 */
	public RedPaktBudgetStatVO queryRedPaktBudgetStat();
	
	/**
	 * 获取可以发放的红包
	 * 即通过审核，status = USING 使用中；check_status = PASS 已通过；申请预算金额budget > 0
	 * @return
	 */
	public List<BsRedPacketApply> findCanPutPacket();
	
	/**
	 * 根据applyNo查询
	 * @param applyNo
	 * @return
	 */
	public BsRedPacketApplyVO selectByApplyNo(String applyNo);
	
	/**
	 * 红包申请人
	 * @param record
	 * @return
	 */
	public List<BsRedPacketApplyVO> findBsRedPacketApplyCreator(BsRedPacketApplyVO record);

}
