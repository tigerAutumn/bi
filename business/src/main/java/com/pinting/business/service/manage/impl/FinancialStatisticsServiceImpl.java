package com.pinting.business.service.manage.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.pinting.business.model.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.BsCompanyCustomerMapper;
import com.pinting.business.dao.BsCompanyDeptMapper;
import com.pinting.business.dao.BsCompanyEmployeeMapper;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.model.BsCompanyCustomer;
import com.pinting.business.model.BsCompanyCustomerExample;
import com.pinting.business.model.BsCompanyDept;
import com.pinting.business.model.BsCompanyDeptExample;
import com.pinting.business.model.BsCompanyEmployee;
import com.pinting.business.service.manage.FinancialStatisticsService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

@Service
public class FinancialStatisticsServiceImpl implements FinancialStatisticsService {
	@Autowired
	private BsCompanyDeptMapper bsCompanyDeptMapper;
	@Autowired
	private BsCompanyEmployeeMapper bsCompanyEmployeeMapper;
	@Autowired
	private BsCompanyCustomerMapper bsCompanyCustomerMapper;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	
	public  boolean checkDeptDeleteCheckFlag = true;
	
	@Override
	public List<BsCompanyDeptVO> queryFinancialStatisticsIndex(
			BsCompanyDeptVO req) {
		
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(req.getStartTime())) {
			startTime =  req.getStartTime()+" 00:00:00";
		}
		if(StringUtil.isNotBlank(req.getEndTime())) {
			endTime = req.getEndTime()+" 23:59:59";
		}
		
		List<BsCompanyDeptVO> bsCompanyDeptList = bsCompanyDeptMapper.queryFinancialStatisticsIndex(req.getDeptCode(),req.getDeptName(),req.getParentId(),req.getParentName(),startTime,endTime,req.getStart(),req.getNumPerPage());
		return bsCompanyDeptList;
	}

	@Override
	public Integer queryFinancialStatisticsCount(BsCompanyDeptVO req) {
		
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(req.getStartTime())) {
			startTime =  req.getStartTime()+" 00:00:00";
		}
		if(StringUtil.isNotBlank(req.getEndTime())) {
			endTime = req.getEndTime()+" 23:59:59";
		}
		Integer count = bsCompanyDeptMapper.queryFinancialStatisticsCount(req.getDeptCode(),req.getDeptName(),req.getParentId(),req.getParentName(),startTime,endTime);
		return count;
	}

	
	
	
	
	@Override
	public Boolean checkDeptDeleteCheck(String deptId) {
		checkDeptDeleteCheckFlag = true;
		treeScan(Integer.parseInt(deptId));
		return checkDeptDeleteCheckFlag;
	}
	
	void treeScan(Integer deptId){
		List<BsCompanyDeptVO> DeptEmployeeList = bsCompanyDeptMapper.queryFinancialStatisticsDeptEmployeeTree(deptId);
		
		if (!CollectionUtils.isEmpty(DeptEmployeeList)) {
			checkDeptDeleteCheckFlag = false;
			return;
		}
		BsCompanyDeptExample example = new BsCompanyDeptExample();
		example.createCriteria().andParentIdEqualTo(deptId);
		List<BsCompanyDept> bsCompanyDeptList = bsCompanyDeptMapper.selectByExample(example);
		
		for (BsCompanyDept bsCompanyDept : bsCompanyDeptList) {
			treeScan(bsCompanyDept.getId());
		}
	}

	@Override
	public void deleteDept(final String deptId) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				treeScanDelete(Integer.parseInt(deptId));
			}
		});
	}
	
	
	void treeScanDelete(Integer deptId){
		bsCompanyDeptMapper.deleteByPrimaryKey(deptId);
		BsCompanyDeptExample example = new BsCompanyDeptExample();
		example.createCriteria().andParentIdEqualTo(deptId);
		List<BsCompanyDept> bsCompanyDeptList = bsCompanyDeptMapper.selectByExample(example);
		
		for (BsCompanyDept bsCompanyDept : bsCompanyDeptList) {
			treeScanDelete(bsCompanyDept.getId());
		}
	}

	@Override
	public BsCompanyDept queryDeptInfo(String deptId) {
		BsCompanyDept bsCompanyDept = bsCompanyDeptMapper.selectByPrimaryKey(Integer.parseInt(deptId));
		return bsCompanyDept;
	}

	@Override
	public List<BsCompanyDept> queryChildDeptInfo(Integer deptId,Integer notDeptId) {
		BsCompanyDeptExample example = new BsCompanyDeptExample();
		example.createCriteria().andParentIdEqualTo(deptId).andIdNotEqualTo(notDeptId);
		List<BsCompanyDept> bsCompanyDeptList = bsCompanyDeptMapper.selectByExample(example);
		return bsCompanyDeptList;
	}

	@Override
	public Boolean updateDeptInfo(BsCompanyDept bsCompanyDept) {
		bsCompanyDeptMapper.updateByPrimaryKeySelective(bsCompanyDept);
		return true;
	}

	@Override
	public Boolean addDeptInfo(BsCompanyDept req) {
		bsCompanyDeptMapper.insertSelective(req);
		return true;
	}

	@Override
	public List<BsCompanyEmployeeVO> queryCompanyEmployeeIndex(
			BsCompanyEmployeeVO req) {
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(req.getStartTime())) {
			startTime =  req.getStartTime()+" 00:00:00";
		}
		if(StringUtil.isNotBlank(req.getEndTime())) {
			endTime = req.getEndTime()+" 23:59:59";
		}
		
		
		List<BsCompanyEmployeeVO> bsCompanyEmployeeList = bsCompanyEmployeeMapper.queryCompanyEmployeeIndex(req.getId(),req.getEmployeeCode(),req.getEmployeeName(),req.getDeptId(),req.getDeptName(),startTime,endTime,req.getStart(),req.getNumPerPage());
		return bsCompanyEmployeeList;
	}

	@Override
	public Integer queryCompanyEmployeeCount(BsCompanyEmployeeVO req) {
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(req.getStartTime())) {
			startTime =  req.getStartTime()+" 00:00:00";
		}
		if(StringUtil.isNotBlank(req.getEndTime())) {
			endTime = req.getEndTime()+" 23:59:59";
		}

		
		Integer count = bsCompanyEmployeeMapper.queryCompanyEmployeeCount(req.getId(),req.getEmployeeCode(),req.getEmployeeName(),req.getDeptId(),req.getDeptName(),startTime,endTime);
		return count;
	}

	@Override
	public Boolean deleteEmployee(String id) {
		if (bsCompanyEmployeeMapper.deleteByPrimaryKey(Integer.parseInt(id))>0) {
			return true;
		}
		return false;
	}

	@Override
	public BsCompanyEmployee queryEmployeeInfo(String employeeId) {
		BsCompanyEmployee bsCompanyEmployee = bsCompanyEmployeeMapper.selectByPrimaryKey(Integer.parseInt(employeeId));
		return bsCompanyEmployee;
	}

	@Override
	public Boolean updateEmployeeInfo(BsCompanyEmployee bsCompanyEmployee) {
		bsCompanyEmployeeMapper.updateByPrimaryKeySelective(bsCompanyEmployee);
		return true;
	}

	@Override
	public Boolean addEmployeeInfo(BsCompanyEmployee req) {
		bsCompanyEmployeeMapper.insertSelective(req);
		return true;
	}

    @Override
    public Boolean isParentDept(Integer id) {
        BsCompanyDeptExample bsCompanyDeptExample = new BsCompanyDeptExample();
        bsCompanyDeptExample.createCriteria().andParentIdEqualTo(id);
        int count = bsCompanyDeptMapper.countByExample(bsCompanyDeptExample);
        return count == 0 ? false : true;
    }

    @Override
    public int queryCompanyCustomerCount(CompanyCustomerVO req) {
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(req.getStartTime())) {
			startTime =  req.getStartTime()+" 00:00:00";
		}
		if(StringUtil.isNotBlank(req.getEndTime())) {
			endTime = req.getEndTime()+" 23:59:59";
		}

        int count = bsCompanyCustomerMapper.countCompanyCustomer(req.getCustomerCode(), req.getCustomerName(), req.getParentName(),startTime,endTime);
        return count;
    }

    @Override
    public List<BsCompanyCustomerVO> queryCompanyCustomerIndex(CompanyCustomerVO req) {
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(req.getStartTime())) {
			startTime =  req.getStartTime()+" 00:00:00";
		}
		if(StringUtil.isNotBlank(req.getEndTime())) {
			endTime = req.getEndTime()+" 23:59:59";
		}
    	
        List<BsCompanyCustomerVO> companyCustomerList = bsCompanyCustomerMapper.selectCompanyCustomer(req.getCustomerCode(), req.getCustomerName(), req.getParentName(),startTime,endTime, req.getStart(),req.getNumPerPage());
        return companyCustomerList;
    }

    @Override
    public Boolean deleteCustomer(final String id) {
        return transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                treeScanDeleteCustomer(Integer.parseInt(id));
                return true;
            }
        });
    }
    
    private void treeScanDeleteCustomer(Integer id) {
        bsCompanyCustomerMapper.deleteByPrimaryKey(id);
        BsCompanyCustomerExample example = new BsCompanyCustomerExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<BsCompanyCustomer> bsCompanyCustomerList = bsCompanyCustomerMapper.selectByExample(example);
        for (BsCompanyCustomer companyCustomer : bsCompanyCustomerList) {
            treeScanDeleteCustomer(companyCustomer.getId());
        }
    }

    @Override
    public List<BsCompanyCustomer> queryCustomerChildren(int parentId) {
        BsCompanyCustomerExample example = new BsCompanyCustomerExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        List<BsCompanyCustomer> customers = bsCompanyCustomerMapper.selectByExample(example);
        return customers;
    }

    @Override
    public Boolean isParentCustomer(Integer id) {
        BsCompanyCustomerExample bsCompanyCustomerExample = new BsCompanyCustomerExample();
        bsCompanyCustomerExample.createCriteria().andParentIdEqualTo(id);
        int count = bsCompanyCustomerMapper.countByExample(bsCompanyCustomerExample);
        return count == 0 ? false : true;
    }

    @Override
    public Boolean updateCompanyCustomer(BsCompanyCustomer req) {
        bsCompanyCustomerMapper.updateByPrimaryKeySelective(req);
        return true;
    }

    @Override
    public void addCompanyCustomer(BsCompanyCustomer req) {
        bsCompanyCustomerMapper.insertSelective(req);
    }

    @Override
    public BsCompanyCustomer queryCustomerById(String id) {
        return bsCompanyCustomerMapper.selectByPrimaryKey(Integer.parseInt(id));
    }

	@Override
	public String queryRootCompanyCustomerMaxCode(Integer customerId,String parentId) {
		
		if ("0".equals(parentId)) {
			BsCompanyCustomerExample example = new BsCompanyCustomerExample();
			example.createCriteria().andParentIdEqualTo(0);
			example.setOrderByClause("customer_code desc");
			List<BsCompanyCustomer> bsCompanyCustomerList = bsCompanyCustomerMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(bsCompanyCustomerList)) {
				return "1";
			}else {
				return String.valueOf(Integer.parseInt(bsCompanyCustomerList.get(0).getCustomerCode())+1);
			}
		}else {
			
			BsCompanyCustomerExample example = new BsCompanyCustomerExample();
			example.createCriteria().andParentIdEqualTo(Integer.parseInt(parentId));
			example.setOrderByClause("customer_code desc");
			List<BsCompanyCustomer> bsCompanyCustomerList = bsCompanyCustomerMapper.selectByExample(example);
			
			if (CollectionUtils.isEmpty(bsCompanyCustomerList)) {
				BsCompanyCustomer bsCompanyCustomer = bsCompanyCustomerMapper.selectByPrimaryKey(Integer.parseInt(parentId));
				String[] codeString  = bsCompanyCustomer.getCustomerCode().split("_");
				return codeString[0] +".1";
			}else {
				
				
				String[] codeStart = bsCompanyCustomerList.get(0).getCustomerCode().split("_");
				
				if (customerId != null) {
					BsCompanyCustomer bsCompanyCustomer = bsCompanyCustomerMapper.selectByPrimaryKey(customerId);
					if (bsCompanyCustomer.getParentId().toString().equals(parentId)) {
						String[] customer = bsCompanyCustomer.getCustomerCode().split("_");
						return customer[0];
					}
				}
				
				String[] codeList = codeStart[0].split("\\.");
				String codeEnd = codeList[codeList.length-1];
				String codeString  = "";
				for (int i = 0; i < codeList.length-1; i++) {
					codeString = codeString + codeList[i] + ".";
				}
				return codeString + String.valueOf(Integer.valueOf(codeEnd)+1);
			}

		}

	}

	@Override
	public Boolean queryUserIsRelationInCompanyCustomer(Integer customerId,Integer userId) {
		
		if (customerId!= null && !"".equals(customerId)) {
			BsCompanyCustomer bsCompanyCustomer = bsCompanyCustomerMapper.selectByPrimaryKey(customerId);
			if (bsCompanyCustomer.getUserId()!=null && bsCompanyCustomer.getUserId().equals(userId)) {
				return false;
			}
		}
		BsCompanyCustomerExample example = new BsCompanyCustomerExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<BsCompanyCustomer> bsCompanyCustomerList = bsCompanyCustomerMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(bsCompanyCustomerList)) {
			return false;
		}else {
			return true;
		}
		
	}

	@Override
	public List<CompanyCustomerVO> queryCompanyCustomerExport(
			CompanyCustomerVO req) {
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(req.getStartTime())) {
			startTime =  req.getStartTime()+" 00:00:00";
		}
		if(StringUtil.isNotBlank(req.getEndTime())) {
			endTime = req.getEndTime()+" 23:59:59";
		}
    	
        List<CompanyCustomerVO> companyCustomerList = bsCompanyCustomerMapper.selectCompanyCustomerExport(req.getCustomerCode(), req.getCustomerName(), req.getParentName(),startTime,endTime, req.getStart(),req.getNumPerPage());
        return companyCustomerList;
	}

	@Override
	public List<BsCompanyDept> selectDeptByName(String dept_name) {
		BsCompanyDeptExample example = new BsCompanyDeptExample();
		example.createCriteria().andDeptNameEqualTo(dept_name);
		return bsCompanyDeptMapper.selectByExample(example);
	}

	@Override
	public String queryRootCompanyDeptMaxCode(String parentId) {

		
		if ("0".equals(parentId)) {
			BsCompanyDeptExample example = new BsCompanyDeptExample();
			example.createCriteria().andParentIdEqualTo(0);
			example.setOrderByClause("dept_code desc");
			List<BsCompanyDept> bsCompanyDeptList = bsCompanyDeptMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(bsCompanyDeptList)) {
				return "101";
			}else {
				String[] codeStart = bsCompanyDeptList.get(0).getDeptCode().split("_");
				
				String[] codeList = codeStart[0].split("\\.");
				String codeEnd = codeList[codeList.length-1];
				String codeString  = "";
				for (int i = 0; i < codeList.length-1; i++) {
					codeString = codeString + codeList[i] + ".";
				}
				return codeString + String.valueOf(Integer.valueOf(codeEnd)+1);
			}
		}else {
			
			BsCompanyDeptExample example = new BsCompanyDeptExample();
			example.createCriteria().andParentIdEqualTo(Integer.parseInt(parentId));
			example.setOrderByClause("dept_code desc");
			List<BsCompanyDept> bsCompanyDeptList = bsCompanyDeptMapper.selectByExample(example);
			
			if (CollectionUtils.isEmpty(bsCompanyDeptList)) {
				BsCompanyDept bsCompanyDept = bsCompanyDeptMapper.selectByPrimaryKey(Integer.parseInt(parentId));
				String[] codeString  = bsCompanyDept.getDeptCode().split("_");
				return codeString[0] +".1";
			}else {
				
				
				String[] codeStart = bsCompanyDeptList.get(0).getDeptCode().split("_");
				
				String[] codeList = codeStart[0].split("\\.");
				String codeEnd = codeList[codeList.length-1];
				String codeString  = "";
				for (int i = 0; i < codeList.length-1; i++) {
					codeString = codeString + codeList[i] + ".";
				}
				return codeString + String.valueOf(Integer.valueOf(codeEnd)+1);
			}

		}

	
	}

	@Override
	public int queryCompanyCustomerBusinessCount(CompanyCustomerVO req) {
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(req.getStartTime())) {
			startTime =  req.getStartTime()+" 00:00:00";
		}
		if(StringUtil.isNotBlank(req.getEndTime())) {
			endTime = req.getEndTime()+" 23:59:59";
		}

        int count = bsCompanyCustomerMapper.countCompanyCustomerBusiness(req.getCustomerCode(), req.getCustomerName(), req.getParentName(),startTime,endTime);
        return count;
	}

	@Override
	public List<BsCompanyCustomerVO> queryCompanyCustomerBusinessIndex(
			CompanyCustomerVO req) {
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(req.getStartTime())) {
			startTime =  req.getStartTime()+" 00:00:00";
		}
		if(StringUtil.isNotBlank(req.getEndTime())) {
			endTime = req.getEndTime()+" 23:59:59";
		}
    	
        List<BsCompanyCustomerVO> companyCustomerList = bsCompanyCustomerMapper.selectCompanyCustomerBusiness(req.getCustomerCode(), req.getCustomerName(), req.getParentName(),startTime,endTime, req.getStart(),req.getNumPerPage());
        return companyCustomerList;
	}
	
	
	@Override
	public List<CompanyCustomerVO> queryCompanyCustomerBusinessExport(
			CompanyCustomerVO req) {
		String startTime = "";
		String endTime = "";
		if(StringUtil.isNotBlank(req.getStartTime())) {
			startTime =  req.getStartTime()+" 00:00:00";
		}
		if(StringUtil.isNotBlank(req.getEndTime())) {
			endTime = req.getEndTime()+" 23:59:59";
		}
    	
        List<CompanyCustomerVO> companyCustomerList = bsCompanyCustomerMapper.selectCompanyCustomerBusinessExport(req.getCustomerCode(), req.getCustomerName(), req.getParentName(),startTime,endTime, req.getStart(),req.getNumPerPage());
        return companyCustomerList;
	}
	
	@Override
	public BsPartnerServiceFeeVO queryPartnerServiceFee(String partnerCode, String startTime, String endTime) {
		BsPartnerServiceFeeVO bsPartnerServiceFeeVO = new BsPartnerServiceFeeVO();
		int smsCount = 0;
		if (PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerCode)) {
			smsCount = lnPayOrdersMapper.countPartnerSmsRecord(partnerCode, startTime, endTime);
		}
		int authCount = lnPayOrdersMapper.countPartnerAuthRecord(partnerCode, startTime, endTime);
		int loanCount = lnPayOrdersMapper.countPartnerLoanRecord(partnerCode, startTime, endTime);
		int repayCount = lnPayOrdersMapper.countPartnerRepayRecord(partnerCode, startTime, endTime);
		Double loanAmount = lnPayOrdersMapper.sumPartnerLoanAmount(partnerCode, startTime, endTime);
		Double agreementFeeAmount = lnPayOrdersMapper.sumAgreementFeeAmount(partnerCode, startTime, endTime);
		bsPartnerServiceFeeVO.setSmsCount(smsCount);
		bsPartnerServiceFeeVO.setAuthCount(authCount);
		bsPartnerServiceFeeVO.setLoanCount(loanCount);
		bsPartnerServiceFeeVO.setRepayCount(repayCount);
		bsPartnerServiceFeeVO.setLoanAmount(loanAmount);
		bsPartnerServiceFeeVO.setAgreementFeeAmount(agreementFeeAmount == null ? 0 : agreementFeeAmount);
		return bsPartnerServiceFeeVO;
	}

	@Override
	public List<AgreementFeeDetailVO> queryAgreementFeeDetailList(String partnerCode, String startTime, String endTime) {
		List<AgreementFeeDetailVO> list = lnPayOrdersMapper.selectAgreementFeeDetailList(partnerCode, startTime, endTime);
		return list.size() > 0 ? list : null;
	}

	@Override
	public List<AgreementFeeDetailVO> queryAgreementFeeDetailListExportXls(String partnerCode, String startTime, String endTime) {
		List<AgreementFeeDetailVO> list = lnPayOrdersMapper.selectAgreementFeeDetailListExportXls(partnerCode, startTime, endTime);
		return list.size() > 0 ? list : null;
	}
}
