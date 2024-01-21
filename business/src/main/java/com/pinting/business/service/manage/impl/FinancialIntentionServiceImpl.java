package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsDafyFinanceChangeRecordMapper;
import com.pinting.business.dao.BsFinanceDafyRelationMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsUserCustomerManagerMapper;
import com.pinting.business.dao.TbdatapermissionMapper;
import com.pinting.business.dao.TbdepartmentMapper;
import com.pinting.business.dao.TbemployeeMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsUserCustomerManager;
import com.pinting.business.model.Tbdatapermission;
import com.pinting.business.model.TbdatapermissionExample;
import com.pinting.business.model.Tbdepartment;
import com.pinting.business.model.TbdepartmentExample;
import com.pinting.business.model.Tbemployee;
import com.pinting.business.model.TbemployeeExample;
import com.pinting.business.model.vo.BsDafyFinanceChangeRecordVO;
import com.pinting.business.model.vo.CustomerQueryVO;
import com.pinting.business.model.vo.DafyBusinessManagerVO;
import com.pinting.business.model.vo.FinancialIntentionVO;
import com.pinting.business.model.vo.FinancialRechargeRecordVO;
import com.pinting.business.model.vo.FinancialRedeemVO;
import com.pinting.business.model.vo.FinancialSettlementVO;
import com.pinting.business.model.vo.FinancialUserInvestDetailVO;
import com.pinting.business.model.vo.OwnershipVO;
import com.pinting.business.service.manage.FinancialIntentionService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;

/**
 * 理财意向
 * @author HuanXiong
 * @version 2016-6-2 下午1:59:00
 */
@Service
public class FinancialIntentionServiceImpl implements FinancialIntentionService {

    @Autowired
    private BsFinanceDafyRelationMapper bsFinanceDafyRelationMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private TbdatapermissionMapper missionMapper;
    @Autowired
    private TbdepartmentMapper deptMapper;
    @Autowired
    private TbemployeeMapper employeeMapper;
    @Autowired
    private BsDafyFinanceChangeRecordMapper dafyFinanceChangeRecordMapper;

    @Autowired
    private BsUserCustomerManagerMapper bsUserCustomerManagerMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    @Override
    public List<FinancialIntentionVO> queryFinancialIntentionGrid(FinancialIntentionVO req, List<Long> deptIds, String isOrDafyUserId, String productType) {
    	HashMap<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("record", req);
    	paramMap.put("isOrDafyUserId", isOrDafyUserId);
    	//已经指定了客户经理
    	if(!"-1".equals(req.getManagerName())) {
    		deptIds = null;
    	}
    	//未指定客户经理
    	else {
    		req.setManagerName(null);
    		//如果部门是达飞集团(dafyDeptId=12),则直接查询所有购买记录,不需要对部门进行过滤
    		if(!CollectionUtils.isEmpty(deptIds)) {
    			for(Long id : deptIds) {
    				if(id == 12l) {
    					deptIds = null;
    					break;
    				}
    			}
    		}
    		else {
    			//正常情况下未指定客户经理，deptIds是不为空的；如果deptIds为空，设置一个不存在的deptId，查询结果为空
    			deptIds.add(-1l);
    		}
    	}
    	paramMap.put("deptIds", deptIds);
    	List<FinancialIntentionVO> vos = new ArrayList<FinancialIntentionVO>();
    	if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    		vos = bsFinanceDafyRelationMapper.selectFinancialIntentionGridZan(paramMap);
    	}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    		vos = bsFinanceDafyRelationMapper.selectFinancialIntentionGrid(paramMap);
    	}
         
        return vos;
    }

    @Override
    public Integer countFinancialIntention(FinancialIntentionVO req, List<Long> deptIds, String isOrDafyUserId, String productType) {
    	HashMap<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("record", req);
    	paramMap.put("isOrDafyUserId", isOrDafyUserId);
    	//已经指定了客户经理
    	if(!"-1".equals(req.getManagerName())) {
    		deptIds = null;
    	}
    	//未指定客户经理
    	else {
    		req.setManagerName(null);
    		//如果部门是达飞集团(dafyDeptId=12),则直接查询所有购买记录,不需要对部门进行过滤
    		if(!CollectionUtils.isEmpty(deptIds)) {
    			for(Long id : deptIds) {
    				if(id == 12l) {
    					deptIds = null;
    					break;
    				}
    			}
    		}
    		else {
    			//正常情况下未指定客户经理，deptIds是不为空的；如果deptIds为空，设置一个不存在的deptId，查询结果为空
    			deptIds.add(-1l);
    		}
    	}
    	paramMap.put("deptIds", deptIds);
    	Integer count = 0 ;
    	if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    		count = bsFinanceDafyRelationMapper.countFinancialIntentionZan(paramMap);
    	}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    		count = bsFinanceDafyRelationMapper.countFinancialIntention(paramMap);
    	}
    	 
        return count;
    }

	@Override
	public FinancialUserInvestDetailVO selectFinancialUserInvestDetail(Integer subAccountId) {
		FinancialUserInvestDetailVO userInvestDetailVO = bsSubAccountMapper.selectFinancialUserInvestDetail(subAccountId);
		return userInvestDetailVO;
	}

	@Override
	public FinancialRechargeRecordVO financialRechargeRecord(
			Integer subAccountId) {
		FinancialRechargeRecordVO rechargeRecordVO = bsSubAccountMapper.financialRechargeRecord(subAccountId);
		return rechargeRecordVO;
	}

	@Override
	public List<Tbdepartment> selectDeptListByParam(String param, Long userId, Long deptId, Integer isManager) {
		List<Tbdepartment> result = new ArrayList<Tbdepartment>();
		TbdepartmentExample example = new TbdepartmentExample();
		List<Long> deptIds = new ArrayList<Long>();
		Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
		List<Tbdepartment> temp = selectLowLevelDept(userId);
		for(Tbdepartment dept : temp) {
			deptMap.put(dept.getLid(), dept);
		}
		
		//1为部门主管
		Tbdepartment myDept = deptMapper.selectByPrimaryKey(deptId);
		if(isManager == 1) {
			Integer level = myDept.getNcurrentlevel();
			String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
			List<Tbdepartment> deptList = deptMapper.queryLowerLevelDept(sql);
			for(Tbdepartment dept : deptList) {
				deptMap.put(dept.getLid(), dept);
			}
		}
		else {
			deptMap.put(deptId, myDept);
		}
		
		//正常情况下deptMap不应该为空，如果为空，则没有查询记录
		if(CollectionUtils.isEmpty(deptMap)) {
			deptIds.add(-1l);
		}
		else {
			deptIds = new ArrayList<Long>(deptMap.keySet());
		}
		
		//部门编码
		Pattern pCode = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher mCode = pCode.matcher(param);
		if(mCode.matches()) {
			example.createCriteria().andStrdeptcodeLike(param+"%").andLidIn(deptIds);
			result = deptMapper.selectByExample(example);
		}
		//部门名称
		Pattern pName = Pattern.compile("^[\u4E00-\u9FFF（）()、]+$");
		Matcher mName = pName.matcher(param);
		if(mName.matches()) {
			example.createCriteria().andStrdeptnameLike(param+"%").andLidIn(deptIds);
			result = deptMapper.selectByExample(example);
		}
		
		return result;
	}
	
	/**
	 * 查询达飞用户的特殊权限
	 * @param userId
	 * @return
	 */
	public List<Tbdepartment> selectLowLevelDept(Long userId) {
		List<Tbdepartment> result = new ArrayList<Tbdepartment>();
		TbdatapermissionExample dataExample = new TbdatapermissionExample();
		dataExample.createCriteria().andLuseridEqualTo(userId);
		List<Tbdatapermission> dataList = missionMapper.selectByExample(dataExample);
		for(Tbdatapermission data : dataList) {
			String sql = "select * from tbdepartment where strDeptCode"+data.getNcurrentlevel()+"='"+data.getStrdeptcode()+"'";
			List<Tbdepartment> deptList = deptMapper.queryLowerLevelDept(sql);
			if(!CollectionUtils.isEmpty(deptList)) {
				result.addAll(deptList);
			}
		}
		return result;
	}
	
	@Override
	public List<Tbemployee> selectEmployeeByDeptId(Long deptId) {
		List<Tbemployee> list = new ArrayList<Tbemployee>();
		Tbdepartment dept = deptMapper.selectByPrimaryKey(deptId);
		if(dept != null) {
			TbemployeeExample example = new TbemployeeExample();
			example.createCriteria().andStrdeptcodeEqualTo(dept.getStrdeptcode()).andNworkstateEqualTo(1); //在职用户
			list = employeeMapper.selectByExample(example);
		}
		return list;
	}
	
	
	@Override
	public List<Tbemployee> selectAllEmployeeByDeptId(Long deptId) {
		List<Tbemployee> list = new ArrayList<Tbemployee>();
		Tbdepartment dept = deptMapper.selectByPrimaryKey(deptId);
		if(dept != null) {
			TbemployeeExample example = new TbemployeeExample();
			example.createCriteria().andStrdeptcodeEqualTo(dept.getStrdeptcode()); //全部用户
			list = employeeMapper.selectByExample(example);
		}
		return list;
	}

	@Override
	public List<CustomerQueryVO> customerQueryIndex(Long dafyUserId,
			Long dafyDeptId, String userName, String mobile, String idcard,String isBindBank, Integer page, Integer offset) {
			Integer position = (page - 1) * offset;
		 List<CustomerQueryVO> vos = employeeMapper.customerQueryIndex(dafyUserId, dafyDeptId, userName, mobile, idcard,isBindBank,position,offset);
	     return vos;
	}

	@Override
	public Integer customerQueryCount(Long dafyUserId, Long dafyDeptId,
			String userName, String mobile, String idcard,String isBindBank) {
		Integer count = employeeMapper.customerQueryCount(dafyUserId, dafyDeptId, userName, mobile, idcard,isBindBank);
        return count;
	}




	public List<Tbdepartment> selectDeptListByParamNoRoles(String param) {
		List<Tbdepartment> result = new ArrayList<Tbdepartment>();
		TbdepartmentExample example = new TbdepartmentExample();
		//部门编码
		Pattern pCode = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher mCode = pCode.matcher(param);
		if(mCode.matches()) {
			example.createCriteria().andStrdeptcodeLike(param+"%");
			result = deptMapper.selectByExample(example);
		}
		//部门名称
		Pattern pName = Pattern.compile("^[\u4E00-\u9FFF（）()、]+$");
		Matcher mName = pName.matcher(param);
		if(mName.matches()) {
			example.createCriteria().andStrdeptnameLike(param+"%");
			result = deptMapper.selectByExample(example);
		}
		
		return result;
		
	}

    @Override
    public List<Tbdatapermission> selectUserMission(Long dafyUserId) {
        TbdatapermissionExample example = new TbdatapermissionExample();
        example.createCriteria().andLuseridEqualTo(dafyUserId);
        List<Tbdatapermission> result = missionMapper.selectByExample(example);
        return result;
    }

    @Override
    public List<BsDafyFinanceChangeRecordVO> queryOwnershipChangeRecord(Integer userId, Integer page, Integer offset) {
        Integer position = (page - 1) * offset;
        List<BsDafyFinanceChangeRecordVO> dafyFinanceChangeRecordList = dafyFinanceChangeRecordMapper.queryOwnershipChangeRecord(userId,position,offset);
        return dafyFinanceChangeRecordList;
    }

    @Override
    public Integer queryOwnershipChangeRecordCount(Integer userId) {
        Integer count = dafyFinanceChangeRecordMapper.queryOwnershipChangeRecordCount(userId);
        return count;
    }


    @Override
    public List<OwnershipVO> queryOwnershipGrid(OwnershipVO req) {
        List<OwnershipVO> vos = bsUserCustomerManagerMapper.selectOwnershipGrid(req);
        return vos;
    }

	@Override
	public List<Long> selectDeptListByManagerId(Long dafyUserId, Long deptId,String isManager) {
		
		List<Long> deptIds = new ArrayList<Long>();
		
		
		Map<Long,Tbdepartment> deptMap = new HashMap<Long,Tbdepartment>();
		List<Tbdepartment> temp = selectLowLevelDept(dafyUserId);
		for(Tbdepartment dept : temp) {
			deptMap.put(dept.getLid(), dept);
		}
		
		//1为部门主管
		if("1".equals(isManager)) {
			Tbdepartment myDept = deptMapper.selectByPrimaryKey(deptId);
			Integer level = myDept.getNcurrentlevel();
			String sql = "select * from tbdepartment where strDeptCode"+level+"='"+myDept.getStrdeptcode()+"'";
			List<Tbdepartment> deptList = deptMapper.queryLowerLevelDept(sql);
			for(Tbdepartment dept : deptList) {
				deptMap.put(dept.getLid(), dept);
			}
		}
		
		//正常情况下deptMap不应该为空，如果为空，则没有查询记录
		if(CollectionUtils.isEmpty(deptMap)) {
			deptIds.add(-1l);
		}
		else {
			deptIds = new ArrayList<Long>(deptMap.keySet());
		}
		
		
			
		return deptIds;
	}

	@Override
	public List<CustomerQueryVO> customerQueryByEmployee(String userName,
			String mobile, String idcard, List<Long> employeeIds,Long dafyUserId,String isBindBank, Integer page,
			Integer offset) {
		Integer position = (page - 1) * offset;
		
        if (CollectionUtils.isEmpty(employeeIds)) {
        	employeeIds =null;
		}
		List<CustomerQueryVO> customerQueryVO = employeeMapper.customerQueryByEmployee(userName, mobile, idcard, employeeIds,dafyUserId,isBindBank, position, offset);
		return customerQueryVO;
	}

	@Override
	public Integer customerQueryByEmployeeCount(String userName, String mobile,
			String idcard, List<Long> employeeIds,Long dafyUserId,String isBindBank) {
        if (CollectionUtils.isEmpty(employeeIds)) {
        	employeeIds =null;
		}
		Integer count = employeeMapper.customerQueryByEmployeeCount(userName, mobile, idcard, employeeIds,dafyUserId,isBindBank);
		return count;
	}
	
    /** 
     */
    @Override
    public Integer countOwnershipGrid(OwnershipVO req) {
        Integer count = bsUserCustomerManagerMapper.countOwnership(req);
        return count;
    }

    @Override
    public void changeOwnership(final Integer newDafyUserId, final List<Integer> idList, final Integer opId) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                for (Integer id : idList) {
                    BsUserCustomerManager record = bsUserCustomerManagerMapper.selectByPrimaryKey(id);
                    if(newDafyUserId.equals(record.getCustomerManagerDafyId())) {
                        throw new PTMessageException(PTMessageEnum.NEW_OLD_CUSTOMER_ID_IS_SAME);
                    }
                    dafyFinanceChangeRecordMapper.insertChangeOwnership(newDafyUserId, id, opId);
                    record.setCustomerManagerDafyId(newDafyUserId);
                    record.setUpdateTime(new Date());
                    bsUserCustomerManagerMapper.updateByPrimaryKeySelective(record);
                }
                return true;
            }
        });
    }

	@Override
	public Tbdepartment selectTbdepartmentByKey(Long lid) {
		return deptMapper.selectByPrimaryKey(lid);
	}

	@Override
	public List<Tbdepartment> selectTbdepartmentSQL(String sql) {
		return deptMapper.queryLowerLevelDept(sql);
	}

	@Override
	public List<DafyBusinessManagerVO> selectDafyBusinessManager(DafyBusinessManagerVO req, List<Long> deptIds, String isOrDafyUserId, String productType) {
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		if(StringUtil.isNotBlank(req.getSuccessTimeStart())) {
			paramMap.put("successTimeStart", req.getSuccessTimeStart() + " 00:00:00");
		}
		if(StringUtil.isNotBlank(req.getSuccessTimeEnd())) {
			paramMap.put("successTimeEnd", req.getSuccessTimeEnd() + " 23:59:59");
		}
    	paramMap.put("record", req);
    	paramMap.put("isOrDafyUserId", isOrDafyUserId);
    	//已经指定了客户经理
    	if(!"-1".equals(req.getManagerName())) {
    		deptIds = null;
    	}
    	//未指定客户经理
    	else {
    		req.setManagerName(null);
    		//如果部门是达飞集团(dafyDeptId=12),则直接查询所有购买记录,不需要对部门进行过滤
    		if(!CollectionUtils.isEmpty(deptIds)) {
    			for(Long id : deptIds) {
    				if(id == 12l) {
    					deptIds = null;
    					break;
    				}
    			}
    		}
    		else {
    			//正常情况下未指定客户经理，deptIds是不为空的；如果deptIds为空，设置一个不存在的deptId，查询结果为空
    			deptIds.add(-1l);
    		}
    	}
    	paramMap.put("deptIds", deptIds);
    	List<DafyBusinessManagerVO> vos = new ArrayList<>();
    	if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    		vos = bsFinanceDafyRelationMapper.selectDafyBusinessManagerZan(paramMap);
    	}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    		vos = bsFinanceDafyRelationMapper.selectDafyBusinessManager(paramMap);
    	}
        
        return vos;
	}

	@Override
	public Integer countDafyBusinessManager(DafyBusinessManagerVO req, List<Long> deptIds, String isOrDafyUserId, String productType) {
		HashMap<String,Object> paramMap = new HashMap<String,Object>();
		if(StringUtil.isNotBlank(req.getSuccessTimeStart())) {
			paramMap.put("successTimeStart", req.getSuccessTimeStart() + " 00:00:00");
		}
		if(StringUtil.isNotBlank(req.getSuccessTimeEnd())) {
			paramMap.put("successTimeEnd", req.getSuccessTimeEnd() + " 23:59:59");
		}
    	paramMap.put("record", req);
    	paramMap.put("isOrDafyUserId", isOrDafyUserId);
    	//已经指定了客户经理
    	if(!"-1".equals(req.getManagerName())) {
    		deptIds = null;
    	}
    	//未指定客户经理
    	else {
    		req.setManagerName(null);
    		//如果部门是达飞集团(dafyDeptId=12),则直接查询所有购买记录,不需要对部门进行过滤
    		if(!CollectionUtils.isEmpty(deptIds)) {
    			for(Long id : deptIds) {
    				if(id == 12l) {
    					deptIds = null;
    					break;
    				}
    			}
    		}
    		else {
    			//正常情况下未指定客户经理，deptIds是不为空的；如果deptIds为空，设置一个不存在的deptId，查询结果为空
    			deptIds.add(-1l);
    		}
    	}
    	paramMap.put("deptIds", deptIds);
    	Integer count = 0;
    	if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    		count = bsFinanceDafyRelationMapper.countDafyBusinessManagerZan(paramMap);
    	}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    		count = bsFinanceDafyRelationMapper.countDafyBusinessManager(paramMap);
    	}
        return count;
	}

	@Override
	public List<FinancialSettlementVO> queryFinancialSettlementGrid(
			FinancialSettlementVO req, List<Long> deptIds, String isOrDafyUserId, String productType) {
		if(Constants.PRODUCT_TYPE_ZAN.equals(productType) && req.getStatus() != null){
    		//赞分期
    		if(req.getStatus() == Constants.SUBACCOUNT_STATUS_FINANCING){
    			//投资中
    			req.setStrStatus(Constants.LOAN_RELATION_STATUS_SUCCESS);
    			
    		}else if(req.getStatus() == Constants.SUBACCOUNT_STATUS_SETTLE){
    			//已完成
    			req.setStrStatus(Constants.LOAN_RELATION_STATUS_REPAID);
    		}
    	}
    	HashMap<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("record", req);
    	paramMap.put("isOrDafyUserId", isOrDafyUserId);
    	//已经指定了客户经理
    	if(!"-1".equals(req.getManagerName())) {
    		deptIds = null;
    	}
    	//未指定客户经理
    	else {
    		req.setManagerName(null);
    		//如果部门是达飞集团(dafyDeptId=12),则直接查询所有购买记录,不需要对部门进行过滤
    		if(!CollectionUtils.isEmpty(deptIds)) {
    			for(Long id : deptIds) {
    				if(id == 12l) {
    					deptIds = null;
    					break;
    				}
    			}
    		}
    		else {
    			//正常情况下未指定客户经理，deptIds是不为空的；如果deptIds为空，设置一个不存在的deptId，查询结果为空
    			deptIds.add(-1l);
    		}
    	}
    	paramMap.put("deptIds", deptIds);
    	List<FinancialSettlementVO> vos = new ArrayList<FinancialSettlementVO>();
    	if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    		vos = bsFinanceDafyRelationMapper.queryFinancialSettlementGridZan(paramMap);
    	}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    		vos = bsFinanceDafyRelationMapper.queryFinancialSettlementGrid(paramMap);
    	}
        
        return vos;
	}

	@Override
	public Integer countFinancialSettlement(FinancialSettlementVO req,
			List<Long> deptIds, String isOrDafyUserId, String productType) {
    	HashMap<String,Object> paramMap = new HashMap<String,Object>();
    	if(Constants.PRODUCT_TYPE_ZAN.equals(productType) && req.getStatus() != null){
    		//赞分期
    		if(req.getStatus() == Constants.SUBACCOUNT_STATUS_FINANCING){
    			//投资中
    			req.setStrStatus(Constants.LOAN_RELATION_STATUS_SUCCESS);
    			
    		}else if(req.getStatus() == Constants.SUBACCOUNT_STATUS_SETTLE){
    			//已完成
    			req.setStrStatus(Constants.LOAN_RELATION_STATUS_REPAID);
    		}
    	}
    	paramMap.put("record", req);
    	paramMap.put("isOrDafyUserId", isOrDafyUserId);
    	//已经指定了客户经理
    	if(!"-1".equals(req.getManagerName())) {
    		deptIds = null;
    	}
    	//未指定客户经理
    	else {
    		req.setManagerName(null);
    		//如果部门是达飞集团(dafyDeptId=12),则直接查询所有购买记录,不需要对部门进行过滤
    		if(!CollectionUtils.isEmpty(deptIds)) {
    			for(Long id : deptIds) {
    				if(id == 12l) {
    					deptIds = null;
    					break;
    				}
    			}
    		}
    		else {
    			//正常情况下未指定客户经理，deptIds是不为空的；如果deptIds为空，设置一个不存在的deptId，查询结果为空
    			deptIds.add(-1l);
    		}
    	}
    	paramMap.put("deptIds", deptIds);
    	Integer count = 0;
    	if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    		count = bsFinanceDafyRelationMapper.countFinancialSettlementZan(paramMap);
    	}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    		count = bsFinanceDafyRelationMapper.countFinancialSettlement(paramMap);
    	}
    	
        return count;
	}

	@Override
	public List<FinancialRedeemVO> queryFinancialRedeemGrid(
			FinancialRedeemVO req, List<Long> deptIds, String isOrDafyUserId, String productType) {

		//理财状态转换，7-结算中，5-已结算，8-结算失败
		if(Constants.PRODUCT_TYPE_ZAN.equals(productType) && req.getStatus() != null){
    		//赞分期
    		if(req.getStatus() == Constants.SUBACCOUNT_STATUS_SETTLEING){
    			//结算中
    			req.setStrStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
    			
    		}else if(req.getStatus() == Constants.SUBACCOUNT_STATUS_SETTLE){
    			//已结算
    			req.setStrStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
    			
    		}else if(req.getStatus() == 8){
    			//结算失败
    			req.setStrStatus(Constants.FINANCE_REPAY_SATAE_FAIL);
    			
    		}
    	}
    	HashMap<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("record", req);
    	paramMap.put("isOrDafyUserId", isOrDafyUserId);
    	//已经指定了客户经理
    	if(!"-1".equals(req.getManagerName())) {
    		deptIds = null;
    	}
    	//未指定客户经理
    	else {
    		req.setManagerName(null);
    		//如果部门是达飞集团(dafyDeptId=12),则直接查询所有购买记录,不需要对部门进行过滤
    		if(!CollectionUtils.isEmpty(deptIds)) {
    			for(Long id : deptIds) {
    				if(id == 12l) {
    					deptIds = null;
    					break;
    				}
    			}
    		}
    		else {
    			//正常情况下未指定客户经理，deptIds是不为空的；如果deptIds为空，设置一个不存在的deptId，查询结果为空
    			deptIds.add(-1l);
    		}
    	}
    	paramMap.put("deptIds", deptIds);
    	List<FinancialRedeemVO> vos = new ArrayList<FinancialRedeemVO>();
    	if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    		vos = bsFinanceDafyRelationMapper.queryFinancialRedeemGridZan(paramMap);
    	}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    		vos = bsFinanceDafyRelationMapper.queryFinancialRedeemGrid(paramMap);
    	}
    	
        return vos;
	}

	@Override
	public Integer countFinancialRedeem(FinancialRedeemVO req,
			List<Long> deptIds, String isOrDafyUserId, String productType) {
		//理财状态转换，7-结算中，5-已结算，8-结算失败
		if(Constants.PRODUCT_TYPE_ZAN.equals(productType) && req.getStatus() != null){
    		//赞分期
    		if(req.getStatus() == Constants.SUBACCOUNT_STATUS_SETTLEING){
    			//结算中
    			req.setStrStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
    			
    		}else if(req.getStatus() == Constants.SUBACCOUNT_STATUS_SETTLE){
    			//已结算
    			req.setStrStatus(Constants.FINANCE_REPAY_SATAE_REPAIED);
    			
    		}else if(req.getStatus() == 8){
    			//结算失败
    			req.setStrStatus(Constants.FINANCE_REPAY_SATAE_FAIL);
    			
    		}
    	}
		
    	HashMap<String,Object> paramMap = new HashMap<String,Object>();
    	paramMap.put("record", req);
    	paramMap.put("isOrDafyUserId", isOrDafyUserId);
    	//已经指定了客户经理
    	if(!"-1".equals(req.getManagerName())) {
    		deptIds = null;
    	}
    	//未指定客户经理
    	else {
    		req.setManagerName(null);
    		//如果部门是达飞集团(dafyDeptId=12),则直接查询所有购买记录,不需要对部门进行过滤
    		if(!CollectionUtils.isEmpty(deptIds)) {
    			for(Long id : deptIds) {
    				if(id == 12l) {
    					deptIds = null;
    					break;
    				}
    			}
    		}
    		else {
    			//正常情况下未指定客户经理，deptIds是不为空的；如果deptIds为空，设置一个不存在的deptId，查询结果为空
    			deptIds.add(-1l);
    		}
    	}
    	paramMap.put("deptIds", deptIds);
    	Integer count = 0;
    	if(Constants.PRODUCT_TYPE_ZAN.equals(productType)){
    		count = bsFinanceDafyRelationMapper.countFinancialRedeemZan(paramMap);
    	}else if(Constants.PRODUCT_TYPE_NORMAL.equals(productType)){
    		count = bsFinanceDafyRelationMapper.countFinancialRedeem(paramMap);
    	}
    	
        return count;
	}

	@Override
	public Tbemployee selectSingleTbemployee(Long dafyUserId) {
		return employeeMapper.selectByPrimaryKey(dafyUserId);
	}

}
