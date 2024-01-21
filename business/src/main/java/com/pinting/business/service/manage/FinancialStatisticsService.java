package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsCompanyCustomer;
import com.pinting.business.model.BsCompanyDept;
import com.pinting.business.model.BsCompanyEmployee;
import com.pinting.business.model.vo.*;

public interface FinancialStatisticsService {

	//公司部门查询
    public List<BsCompanyDeptVO> queryFinancialStatisticsIndex(BsCompanyDeptVO req);
	//查询公司部门数量
    public Integer queryFinancialStatisticsCount(BsCompanyDeptVO req);
    //检查部门信息是否能被删除
    public Boolean checkDeptDeleteCheck(String deptId); 
    //删除部门信息
    public void deleteDept(String deptId); 
    //查询部门信息
    public BsCompanyDept queryDeptInfo(String deptId); 
    //查询子部门信息
    public List<BsCompanyDept> queryChildDeptInfo(Integer deptId,Integer noDeptId); 
    //更新部门信息
    public Boolean updateDeptInfo(BsCompanyDept bsCompanyDept);
    //新增部门信息
	public Boolean addDeptInfo(BsCompanyDept req);
	
	//公司职员查询
    public List<BsCompanyEmployeeVO> queryCompanyEmployeeIndex(BsCompanyEmployeeVO req);
	//公司职员数量查询
    public Integer queryCompanyEmployeeCount(BsCompanyEmployeeVO req);
    //删除职员信息
	public Boolean deleteEmployee(String id);
    //查询部门信息
    public BsCompanyEmployee queryEmployeeInfo(String employeeId); 
    //更新职员信息
    public Boolean updateEmployeeInfo(BsCompanyEmployee bsCompanyEmployee);
    //新增职员信息
	public Boolean addEmployeeInfo(BsCompanyEmployee req);
	
    /**
     * 判断当前部门节点是父节点
     * @param id
     * @return
     */
    public Boolean isParentDept(Integer id);
    
    /**
     * 统计公司客户数量
     * @param req
     * @return
     */
    public int queryCompanyCustomerCount(CompanyCustomerVO req);
    
    /**
     * 分页查询公司客户信息
     * @param req
     * @return
     */
    public List<BsCompanyCustomerVO> queryCompanyCustomerIndex(CompanyCustomerVO req);
    
    
    /**
     * 统计公司业务新增客户数量
     * @param req
     * @return
     */
    public int queryCompanyCustomerBusinessCount(CompanyCustomerVO req);
    
    /**
     * 分页查询公司业务新增客户信息
     * @param req
     * @return
     */
    public List<BsCompanyCustomerVO> queryCompanyCustomerBusinessIndex(CompanyCustomerVO req);
    
    /**
     * 删除公司客户，级联操作
     * @param id
     * @return
     */
    public Boolean deleteCustomer(String id);
    
    /**
     * 查询子客户
     * @param parentId
     * @return
     */
    public List<BsCompanyCustomer> queryCustomerChildren(int parentId);
    
    /**
     * 判断当前客户节点是父节点
     * @param id
     * @return
     */
    public Boolean isParentCustomer(Integer id);
    
    /**
     * @param req
     * @return
     */
    public Boolean updateCompanyCustomer(BsCompanyCustomer req);
    
    /**
     * @param req
     */
    public void addCompanyCustomer(BsCompanyCustomer req);
    /**
     * @param id
     * @return
     */
    public BsCompanyCustomer queryCustomerById(String id);
    /**
     * 查询客户客户代码值是多少
     * @return
     */
    String queryRootCompanyCustomerMaxCode(Integer customerId,String parentId);
    /**
     * 查询用户ID是否已经和公司客户关联
     * @param userId
     * @return
     */
    public Boolean queryUserIsRelationInCompanyCustomer(Integer customerId,Integer userId);
    /**
     * 查询需要导出的公司客户数据
     * @param req
     * @return
     */
	public List<CompanyCustomerVO> queryCompanyCustomerExport(
			CompanyCustomerVO req);
	/**
	 * 根据部门名称查询部门信息
	 * @param dept_name
	 * @return
	 */
	public List<BsCompanyDept> selectDeptByName(String dept_name);
	
	
    /**
     * 查询部门代码值是多少
     * @return
     */
    String queryRootCompanyDeptMaxCode(String parentId);
    
    /**
     * 查询需要导出的公司业务增量客户数据
     * @param req
     * @return
     */
	public List<CompanyCustomerVO> queryCompanyCustomerBusinessExport(
			CompanyCustomerVO req);
	

	/**
	 * 管理台资产方费用结算查询
	 * @param partnerCode
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	BsPartnerServiceFeeVO queryPartnerServiceFee(String partnerCode, String startTime, String endTime);

    /**
     * 资产方费用结算查询，协议支付手续费明细，默认显示100条记录
     * @param partnerCode
     * @param startTime
     * @param endTime
     * @return
     */
    List<AgreementFeeDetailVO> queryAgreementFeeDetailList(String partnerCode, String startTime, String endTime);

    /**
     * 资产方费用结算查询，协议支付手续费明细，导出excel查询sql
     * @param partnerCode
     * @param startTime
     * @param endTime
     * @return
     */
    List<AgreementFeeDetailVO> queryAgreementFeeDetailListExportXls(String partnerCode, String startTime, String endTime);

}
