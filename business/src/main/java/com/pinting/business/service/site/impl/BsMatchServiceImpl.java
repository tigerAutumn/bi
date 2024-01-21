package com.pinting.business.service.site.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsMatchMapper;
import com.pinting.business.dao.BsMatchRepayDetailMapper;
import com.pinting.business.model.BsMatch;
import com.pinting.business.model.BsMatchExample;
import com.pinting.business.model.BsMatchRepayDetail;
import com.pinting.business.model.BsMatchRepayDetailExample;
import com.pinting.business.model.vo.BsMatchVO;
import com.pinting.business.model.vo.BsMatchWarnVO;
import com.pinting.business.service.site.BsMatchService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.in.util.MethodRole;
@Service
public class BsMatchServiceImpl implements BsMatchService {
	@Autowired
    private BsMatchMapper bsMatchMapper;
	@Autowired
	private BsMatchRepayDetailMapper bsMatchRepayDetailMapper;
	
	@Override
	public Double sumAccountRepay(Integer subAccountId, String Channel, String isRepay) {
		Double amount = bsMatchMapper.sumAccountRepay(subAccountId, Channel, isRepay);
		return amount==null?0.0:amount;
	}

	@Override
	public void addBsMatch(BsMatch bsMatch) {
		bsMatch.setMatchDate(DateUtils.addDays(new Date(), -1));
		bsMatch.setCreateTime(new Date());
		bsMatch.setUpdateTime(new Date());
		bsMatchMapper.insertSelective(bsMatch);
		
	}

	@Override
	@MethodRole("APP")
	public List<BsMatchVO> getMatchListByUserIdProductId(Integer userId,
			Integer productId, Integer subAccountId,Integer start,Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productId", productId);
		paramMap.put("userId", userId);
		paramMap.put("start", start);
		paramMap.put("pageSize", pageSize); 
		paramMap.put("subAccountId", subAccountId);
		return bsMatchMapper.getMatchListByUserIdProductId(paramMap);
	}
	
	@Override
	@MethodRole("APP")
	public List<BsMatchVO> getMatchListIncludePostMigration(Integer userId,
			Integer productId, Integer subAccountId,Integer start,Integer pageSize) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productId", productId);
		paramMap.put("userId", userId);
		paramMap.put("start", start);
		paramMap.put("pageSize", pageSize); 
		paramMap.put("subAccountId", subAccountId);
		return bsMatchMapper.getMatchListIncludePostMigration(paramMap);
	}

	@Override
	@MethodRole("APP")
	public int countMatchListByUserIdProductId(Integer userId, Integer productId, Integer subAccountId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productId", productId);
		paramMap.put("userId", userId);
		paramMap.put("subAccountId", subAccountId);
		int count = bsMatchMapper.countMatchListByUserIdProductId(paramMap);
		return count;
	}
	
	@Override
	@MethodRole("APP")
	public int countMatchListIncludePostMigration(Integer userId, Integer productId, Integer subAccountId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productId", productId);
		paramMap.put("userId", userId);
		paramMap.put("subAccountId", subAccountId);
		int count = bsMatchMapper.countMatchListIncludePostMigration(paramMap);
		return count;
	}

	@Override
	public List<BsMatchRepayDetail> getListByMatchId(Integer matchId) {
		List<BsMatchRepayDetail> list = new ArrayList<BsMatchRepayDetail>();
		BsMatchRepayDetailExample example = new BsMatchRepayDetailExample();
		example.setOrderByClause("create_time desc");
		example.createCriteria().andMatchIdEqualTo(matchId);
		list = bsMatchRepayDetailMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<BsMatch> findMatchesByLoanRelativeId(Integer loanRelativeId) {
		BsMatchExample example = new BsMatchExample();
		example.createCriteria().andLoanRelativeIdEqualTo(loanRelativeId).andRepayStatusNotEqualTo(Constants.REPAY_ALL);
		example.setOrderByClause("left_principal asc");
		List<BsMatch> matches = bsMatchMapper.selectByExample(example);
		return matches;
	}

	@Override
	public void modifyMatch4Repay(BsMatch bsMatch, Double repayAmount) {
		//修改剩余本金、还款金额和状态
		BsMatch match = new BsMatch();
		match.setId(bsMatch.getId());
		Double leftPrincipal = MoneyUtil.subtract(bsMatch.getLeftPrincipal(), repayAmount).doubleValue();
		Double newRepayAmount = MoneyUtil.add(bsMatch.getRepayAmount(), repayAmount).doubleValue();
		match.setLeftPrincipal(leftPrincipal);
		match.setRepayAmount(newRepayAmount);
		if(leftPrincipal == 0){
			match.setRepayStatus(Constants.REPAY_ALL);
			match.setNote(Constants.HISTORY_LEND);
		}else{
			match.setRepayStatus(Constants.REPAY_PART);
			match.setNote(Constants.REPAY_PART_AMOUNT);
		}
		Date now = new Date();
		match.setLastRepayDate(DateUtil.addDays(now, -1));
		match.setUpdateTime(now);
		bsMatchMapper.updateByPrimaryKeySelective(match);
		
		//记录还款明细
		BsMatchRepayDetail detail = new BsMatchRepayDetail();
		detail.setCreateTime(now);
		detail.setLeftPrincipal(leftPrincipal);
		detail.setMatchId(bsMatch.getId());
		detail.setRepayAmount(repayAmount);
		detail.setRepayTime(DateUtil.addDays(now, -1));
		bsMatchRepayDetailMapper.insertSelective(detail);
	}

	@Override
	public List<BsMatchWarnVO> getMatchDiffBatchBuyAmount() {
		
		return bsMatchMapper.getMatchDiffBatchBuyAmount();
	}

	@Override
	public List<BsMatchWarnVO> getAvgAmountWarn(Double limitAmount) {
		return bsMatchMapper.getAvgAmountWarn(limitAmount);
	}

	@Override
	public List<BsMatch> findOverMatches(String propertySymbol) {
		return bsMatchMapper.selectOverMatches(propertySymbol);
	}

}
