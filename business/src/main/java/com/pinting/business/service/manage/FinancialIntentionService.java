package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.Tbdatapermission;
import com.pinting.business.model.Tbdepartment;
import com.pinting.business.model.Tbemployee;
import com.pinting.business.model.vo.BsDafyFinanceChangeRecordVO;
import com.pinting.business.model.vo.CustomerQueryVO;
import com.pinting.business.model.vo.DafyBusinessManagerVO;
import com.pinting.business.model.vo.FinancialIntentionVO;
import com.pinting.business.model.vo.FinancialRechargeRecordVO;
import com.pinting.business.model.vo.FinancialRedeemVO;
import com.pinting.business.model.vo.FinancialSettlementVO;
import com.pinting.business.model.vo.FinancialUserInvestDetailVO;
import com.pinting.business.model.vo.OwnershipVO;

/**
 * 理财意向
 * @author HuanXiong
 * @version 2016-6-2 下午1:55:09
 */
public interface FinancialIntentionService {

    /**
     * 理财意向列表
     * @param req
     * @param productType 产品类型-普通or分期
     * @return
     */
    List<FinancialIntentionVO> queryFinancialIntentionGrid(FinancialIntentionVO req, List<Long> deptIds, String isOrDafyUserId, String productType);
    
    /**
     * 理财意向总个数
     * @param req
     * @param productType 产品类型-普通or分期
     * @return
     */
    Integer countFinancialIntention(FinancialIntentionVO req, List<Long> deptIds, String isOrDafyUserId, String productType);
    
    /**
     * 理财意向--查看收益
     * @return
     */
    FinancialUserInvestDetailVO selectFinancialUserInvestDetail(Integer subAccountId);
    /**
     * 理财意向--充值记录列表
     * @param subAccountId
     * @return
     */
    FinancialRechargeRecordVO financialRechargeRecord(Integer subAccountId);
    
    /**
     * 根据部门编号或者部门名称模糊查询(在客户经理权限范围内)
     * @param param 部门编号或部门名称
     * @param userId 达飞用户id
     * @param deptId 达飞部门id
     * @param isManager 是否达飞部门主管(1-是,0-不是)
     * @return
     */
    List<Tbdepartment> selectDeptListByParam(String param, Long userId, Long deptId, Integer isManager);
    
    /**
     * 根据部门id查询达飞用户(在职)
     * @param deptId 达飞部门id
     * @return
     */
    List<Tbemployee> selectEmployeeByDeptId(Long deptId);

    
    
    /**
     * 根据部门id查询达飞用户(包含离职)
     * @param deptId 达飞部门id
     * @return
     */
    List<Tbemployee> selectAllEmployeeByDeptId(Long deptId);
    
    /**
     * 客户查询-数据
     * @return
     */
    List<CustomerQueryVO> customerQueryIndex(Long dafyUserId,Long dafyDeptId, String userName,String mobile, String idcard,String isBindBank, Integer page, Integer offset);
    
    /**
     * 客户查询-记录数
     * @return
     */
    Integer customerQueryCount(Long dafyUserId,Long dafyDeptId, String userName,String mobile, String idcard,String isBindBank);
    
    
    /**
     * 根据部门编号或者部门名称模糊查询(不考虑权限)
     * @param param 部门编号或部门名称
     * @param userId 达飞用户id
     * @param deptId 达飞部门id
     * @return
     */
    List<Tbdepartment> selectDeptListByParamNoRoles(String param);
    /**
     * 根据用户ID查看某归属变更记录
     * @param userId
     * @return
     */
    List<BsDafyFinanceChangeRecordVO> queryOwnershipChangeRecord(Integer userId, Integer page, Integer offset);
    
    /**
     * 查询达飞用户的营业部权限
     * @param dafyUserId
     * @return
     */
    List<Tbdatapermission> selectUserMission(Long dafyUserId);
    
    /**
     * 根据用户ID查看某归属变更记录条数
     * @param userId
     * @return
     */
    Integer queryOwnershipChangeRecordCount(Integer userId);
    
    
    /**
     * 查询变更前的理财人信息
     * @param req
     * @return
     */
    List<OwnershipVO> queryOwnershipGrid(OwnershipVO req);
    Integer countOwnershipGrid(OwnershipVO req);

    /**
     * 客户归属变更
     * @param dafyUserId
     * @param idList
     */
    void changeOwnership(Integer dafyUserId, List<Integer> idList, Integer opId);

    /**
     * 根据客户经理人ID查询可以查看的部门列表
     * @return
     */
    List<Long> selectDeptListByManagerId(Long dafyUserId, Long deptId,String isManager);
   
    /**
     * 根据客户经理人列表查询客户信息
     * @param userName
     * @param mobile
     * @param idcard
     * @param employeeIds
     * @param page
     * @param offset
     * @return
     */
	List<CustomerQueryVO> customerQueryByEmployee(String userName,
			String mobile, String idcard,   List<Long> employeeIds,Long dafyUserId,String isBindBank,
			Integer page, Integer offset);
	/**
	 * 根据客户经理人列表查询客户信息获取的记录总条数
	 * @param userName
	 * @param mobile
	 * @param idcard
	 * @param employeeIds
	 * @return
	 */
	Integer customerQueryByEmployeeCount(String userName, String mobile,
			String idcard,  List<Long> employeeIds,Long dafyUserId,String isBindBank);
	
	/**
	 * 查询达飞用户的特殊权限
	 * @param userId
	 * @return
	 */
	public List<Tbdepartment> selectLowLevelDept(Long userId);
	
	/**
	 * 根据主键查询达飞部门
	 * @param lid
	 * @return
	 */
	public Tbdepartment selectTbdepartmentByKey(Long lid);
	
	/**
	 * 根据传入参数查询部门
	 * @param example
	 * @return
	 */
	public List<Tbdepartment> selectTbdepartmentSQL(String sql);
	
	/**
	 * 业管查询列表 -- 理财提成核算
	 * @param req
	 * @param deptIds
	 * @param productType 产品类型-普通or分期
	 * @return
	 */
	public List<DafyBusinessManagerVO> selectDafyBusinessManager(DafyBusinessManagerVO req, List<Long> deptIds, String isOrDafyUserId, String productType);
	
	/**
	 * 业管查询列表总记录数 -- 理财提成核算
	 * @param req
	 * @param deptIds
	 * @param productType 产品类型-普通or分期
	 * @return
	 */
	Integer countDafyBusinessManager(DafyBusinessManagerVO req, List<Long> deptIds, String isOrDafyUserId, String productType);

	/**
	 * 达飞财务-理财结算
	 * 达飞财务根据可查看权限查询客户理财信息
	 * @param req
	 * @param deptIds
	 * @param productType 产品类型-普通or分期
	 * @return
	 */
	List<FinancialSettlementVO> queryFinancialSettlementGrid(
			FinancialSettlementVO req, List<Long> deptIds, String isOrDafyUserId, String productType);
	/**
	 * 达飞财务-理财结算
	 * 达飞财务根据可查看权限查询客户理财信息条数
	 * @param copyReq
	 * @param copyDeptIds
	 * @param productType 产品类型-普通or分期
	 * @return
	 */
	Integer countFinancialSettlement(FinancialSettlementVO req,
			List<Long> deptIds, String isOrDafyUserId, String productType);
	/**
	 * 达飞财务-赎回结算
	 * 达飞财务根据可查看权限查询客户理财信息
	 * @param req
	 * @param deptIds
	 * @param productType 产品类型-普通or分期
	 * @return
	 */
	List<FinancialRedeemVO> queryFinancialRedeemGrid(FinancialRedeemVO req,
			List<Long> deptIds, String isOrDafyUserId, String productType);
	/**
	 * 达飞财务-赎回结算
	 * 达飞财务根据可查看权限查询客户理财信息条数
	 * @param copyReq
	 * @param copyDeptIds
	 * @param productType 产品类型-普通or分期
	 * @return
	 */
	Integer countFinancialRedeem(FinancialRedeemVO req,
			List<Long> deptIds, String isOrDafyUserId, String productType);

	Tbemployee selectSingleTbemployee(Long dafyUserId);
}
