package com.pinting.business.service.site.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsAccountJnlMapper;
import com.pinting.business.dao.BsAccountMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.BsAccount;
import com.pinting.business.model.BsAccountExample;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsAccountJnlExample;
import com.pinting.business.model.vo.AvailableClaimVO;
import com.pinting.business.model.vo.ZsdAvailableClaimVO;
import com.pinting.business.service.site.AccountService;
import com.pinting.business.util.Constants;

@Service
public class AccountServiceImpl implements AccountService{
	@Autowired
	private BsAccountMapper accountMapper;
	
	@Autowired
	private BsAccountJnlMapper accountJnlMapper;
	
	@Autowired
	private	BsSubAccountMapper	bsSubAccountMapper;
	
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	
	@Override
	public boolean addAccount(BsAccount bsAccount) {
		return accountMapper.insertSelective(bsAccount)==1;
	}
	
	@Override
	public BsAccount findAccountByAccountCode(String accountCode) {
		BsAccountExample example = new BsAccountExample();
		example.createCriteria().andAccountCodeEqualTo(accountCode);
		List<BsAccount> accountList = accountMapper.selectByExample(example);
		return accountList.size()>0? accountList.get(0) : null;
	}
	
	@Override
	public List<BsAccountJnl> findAccountJnlByUserId(Integer userId, Integer pageIndex, Integer pageSize) {
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("userId", userId);
		data.put("start", pageIndex * pageSize);
		data.put("pageSize", pageSize);
		List<BsAccountJnl> dataList = accountJnlMapper.selectByExamplePage(data);
		return dataList.size() > 0? dataList : null;
	}
	
	@Override
	public Integer findAccountJnlCountByUserId(Integer userId) {
		BsAccountJnlExample example=new BsAccountJnlExample();
		example.createCriteria().andUserId1EqualTo(userId).andStatusEqualTo(1).andTransTypeNotEqualTo(1).andStatusEqualTo(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
		return accountJnlMapper.selectByExample(example).size();
	}

	@Override
	public List<AvailableClaimVO> availableClaim() {
		
		List<Integer> userIds = loanRelationshipService.getSuperUserList();
		Double matchLimitAmount = bsSubAccountMapper.getMatchLimitAmount();
		List<AvailableClaimVO> list = bsSubAccountMapper.availableClaim(userIds, matchLimitAmount);
		return list;
	}

	@Override
	public List<AvailableClaimVO> zsdAvailableClaim() {
		List<Integer> userIds = loanRelationshipService.getSuperUserListBySymbol(Constants.PROPERTY_SYMBOL_ZSD);
		List<AvailableClaimVO> list = bsSubAccountMapper.zsdAvailableClaim(userIds);
		return list;
	}

}
