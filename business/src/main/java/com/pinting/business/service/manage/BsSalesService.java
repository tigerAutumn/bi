package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsDept;
import com.pinting.business.model.BsDeptSales;
import com.pinting.business.model.BsSales;
import com.pinting.business.model.vo.BsSalesDirectInviteVO;
import com.pinting.business.model.vo.BsSalesVO;

public interface BsSalesService {
	
	/**
	 * 分页查询销售人员
	 * @return
	 */
	public List<BsSalesVO> page(BsSalesVO record);
	public int count(BsSalesVO record);
	
	/**
	 * 分页查询用户
	 * @return
	 */
	public List<BsSalesVO> userPage(BsSalesVO record);
	public int userCount(BsSalesVO record);
	
	/**
	 * 修改
	 * @param record
	 * @return
	 */
	public int updateBsSales(BsSales record);
	
	/**
	 * 查询
	 * @param record
	 * @return
	 */
	public BsSales selectBsSales(BsSales record);
	public BsSales selectByPrimaryKey(Integer id);
	
	/**
	 * 添加
	 * @return
	 */
	public int addBsSales(BsSales record);
	
	/**
	 * 销售人员直接推荐人明细查询（分页查询）
	 * @param salesId
	 * @return
	 */
	public List<BsSalesDirectInviteVO> selectSalesDirectInviteUsers(Integer salesId, String pageNum, String numPerPage);
	
	/**
	 * 销售人员直接推荐人明细总条数
	 * @param salesId
	 * @return
	 */
	public int countSalesDirectInviteUsers(Integer salesId);
	/**
	 * 查询所有的销售部门列表
	 * @return
	 */
	public List<BsDept> selectDeptList();
	/**
	 * 查询某个销售人员所属部门
	 * @param id
	 * @return
	 */
	public List<BsDeptSales> selectDeptBySaleId(Integer id);
	/**
	 * 根据saleId更新 部门信息
	 * @param id
	 * @param deptId
	 */
	public void updateBsDeptSales(Integer id, Integer deptId);
	/**
	 * 根据saleId增加 部门信息
	 * @param saleId
	 * @param deptId
	 */
	public void addBsDeptSales(Integer saleId, Integer deptId);
	

}
