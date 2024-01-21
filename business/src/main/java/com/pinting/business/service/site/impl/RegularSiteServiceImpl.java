package com.pinting.business.service.site.impl;

import com.pinting.business.accounting.finance.service.UserBonusGrantService;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.site.RegularSiteService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.IdcardUtils;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.in.util.MethodRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2016/8/29
 * Description: 投资理财相关服务实现s
 */
@Service
public class RegularSiteServiceImpl implements RegularSiteService {

    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private BsSubAccountPairMapper bsSubAccountPairMapper;
    @Autowired
    private UserBonusGrantService userBonusGrantService;
    @Autowired
    private LnLoanAmountChangeMapper lnLoanAmountChangeMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private LnCompensateRelationMapper lnCompensateRelationMapper;
    @Autowired
    private LnCreditTransferMapper lnCreditTransferMapper;
    @Autowired
    private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;

    @Override
    public List<LnLoanRelationVO> queryLnLoanRelationList(int userId, int subAccountId, int pageNum, int numPerPage) {
//        int start =  (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        int start = pageNum;
        List<LnLoanRelationVO> voList = lnLoanRelationMapper.selectByBsUserId(userId, subAccountId, start, numPerPage);
        return voList;
    }

    @Override
    public int countLnLoanRelation(int userId, int subAccountId) {
    	BsSubAccountPairExample example = new BsSubAccountPairExample();
    	example.createCriteria().andAuthAccountIdEqualTo(subAccountId);
    	List<BsSubAccountPair> bsSubAccountPairs = bsSubAccountPairMapper.selectByExample(example);
    	if(CollectionUtils.isEmpty(bsSubAccountPairs)){
			throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
		}
        LnLoanRelationExample lnLoanRelationExample = new LnLoanRelationExample();
        lnLoanRelationExample.createCriteria().andBsUserIdEqualTo(userId).andBsSubAccountIdEqualTo(bsSubAccountPairs.get(0).getRegDAccountId());
        int totalRows = lnLoanRelationMapper.countByExample(lnLoanRelationExample);
        return totalRows;
    }

    @Override
    public List<LnFinanceRepaySchedule> queryRepayList(int loanRelationId) {
        LnFinanceRepayScheduleExample lnFinanceRepayScheduleExample = new LnFinanceRepayScheduleExample();
        lnFinanceRepayScheduleExample.createCriteria().andRelationIdEqualTo(loanRelationId);
        lnFinanceRepayScheduleExample.setOrderByClause("repay_serial ASC");
        List<LnFinanceRepaySchedule> list = lnFinanceRepayScheduleMapper.selectByExample(lnFinanceRepayScheduleExample);
        int relationId;
        if(!CollectionUtils.isEmpty(list)) {
            relationId = list.get(0).getRelationId();
            LnLoanRelation lnLoanRelation = lnLoanRelationMapper.selectByPrimaryKey(relationId);
            LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnLoanRelation.getLoanId());
            LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
            lnRepayScheduleExample.createCriteria().andLoanIdEqualTo(lnLoan.getId());

            List<LnRepaySchedule> lnRepayList = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);
            for (LnRepaySchedule lnRepaySchedule : lnRepayList) {
                if (Constants.LN_REPAY_LATE_REPAIED.equals(lnRepaySchedule.getStatus()) || Constants.LN_REPAY_LATE_NOT.equals(lnRepaySchedule.getStatus())) {
                    for (LnFinanceRepaySchedule repaySchedule : list) {
                        if(repaySchedule.getRepaySerial().equals(lnRepaySchedule.getSerialId())) {
                            repaySchedule.setStatus(Constants.REPAY_ADVANCE);
                        }
                    }
                }
            }
        } else {
            return list;
        }
        return list;
    }

    @Override
    public LnLoanRelationVO queryLnLoanRelation(int loanRelationId) {
        LnLoanRelationVO vo = lnLoanRelationMapper.selectByLoanRelationId(loanRelationId);
        return vo;
    }

    @Override
    public LnLoanRelationVO queryAuthAccountId(int loanRelationId) {
        LnLoanRelationVO voId = lnLoanRelationMapper.selectAuthAccountId(loanRelationId);
        return voId;
    }

    @Override
    public LnLoanRelationVO queryUserByRelationId(int loanRelationId) {
        LnLoanRelationVO lnLoanRelationVO = lnLoanRelationMapper.selectUserByRelationId(loanRelationId);
        return lnLoanRelationVO;
    }

    @Override
    public List<LnLoanRelationVO> queryUserByLnUserId(int lnUserId) {
        List<LnLoanRelationVO> list = lnLoanRelationMapper.selectUserByLnUserId(lnUserId);
        return list;
    }

    @Override
    public Double queryTotalAmount(int authAccountId) {
        return lnLoanRelationMapper.sumTotalAmountByAuthId(authAccountId);
    }

    @Override
    public Double queryLeftAmount(int authAccountId) {
        return lnLoanRelationMapper.sumLeftAmountByAuthId(authAccountId);
    }

    @Override
    public List<LnLoanRelationVO> queryUserByLoanId(int loanId) {
        List<LnLoanRelationVO> list = lnLoanRelationMapper.selectUserByLoanId(loanId);
        return list;
    }

    @Override
    public LnLoanAmountChange queryAmountByRelationId(int relationId) {
        LnLoanAmountChange loanAmountChange = lnLoanAmountChangeMapper.selectAmountByRelationId(relationId);
        return loanAmountChange;
    }

    @Override
    public Double queryLoanInterestRate(int loanId) {
        return lnLoanMapper.selectLoanInterestRate(loanId);
    }

    @Override
    public LoanDetailInfoVO queryLoanInfoByLoanId(int loanId) {
        LoanDetailInfoVO loanDetailInfoVO = lnLoanMapper.selectLoanInfoByLoanId(loanId);
        return loanDetailInfoVO;
    }

    @Override
    public LnLoanRelation queryRecordByUserIdAndId(Integer userId, Integer id) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("userId", userId);
        paramMap.put("id", id);
        return lnLoanRelationMapper.selectRecordByUserIdAndId(paramMap);
    }

	@Override
	public LnUser queryLnUserInfoById(int lnUserId) {
		 LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnUserId);
	     return lnUser;
	}

	@Override
	public BsUser queryYunDaiSelfCreditor() {
		 return bsUserMapper.selectYunDaiSelfCreditor();
	}

	@Override
	public CompensateTransfersVO queryCompensateTransfersByLoanId(
			String partnerLoanId) {
        LnLoan lnLoan = lnLoanMapper.selectLoanByPartnerLoanId(partnerLoanId);
        //查询借款相关数据
        CompensateTransfersVO vo = lnRepayScheduleMapper.selectCompensateTransfersByLoanId(lnLoan.getId());
        //查询代偿本金记所付协议利率的利息
        LnCompensateRelation compRelation = lnCompensateRelationMapper.selectSumAmountByLoanId(lnLoan.getId());
        vo.setBeforeAmount(compRelation.getPrincipal());
        vo.setAgreementAmount(compRelation.getAmount());
        vo.setNoBillingPeriod(compRelation.getInterestDay());
        return vo;
	}

	@Override
	public LnLoan queryLoanByPartnerLoanId(String partnerLoanId) {
		 return lnLoanMapper.selectLoanByPartnerLoanId(partnerLoanId);
	}

	@Override
	public CompensateTransfersVO selectCompensateTransferInfo(int loanId,
			int lnLoanRelationId) {
		return lnLoanRelationMapper.selectCompensateTransferInfo(loanId, lnLoanRelationId);
	}

	@Override
	public List<CompensateTransfersVO> queryCompensateTransferList(int loanId) {
		 List<CompensateTransfersVO> voList = lnLoanRelationMapper.selectCompensateTransferList(loanId);
	     return voList;
	}

    @Override
    @MethodRole("APP")
    public int queryDepClaimsCountBySubAccountId(int subAccountId) {
        int totalRows = lnLoanRelationMapper.selectDepClaimsCount(subAccountId);
        return totalRows;
    }

    @Override
    @MethodRole("APP")
    public List<DetailsOfDebtVO> queryDepClaimsListBySubAccountId(int subAccountId, int pageNum, int numPerPage) {
        int start = pageNum;
        List<DetailsOfDebtVO> detailsOfDebtVOList = lnLoanRelationMapper.selectDepClaimsListBySubAccountId(subAccountId, start, numPerPage);
        return detailsOfDebtVOList;
    }

    @Override
    public LnLoanRelation queryNotRepaidPrincipal(int lnLoanRelationId) {
        return lnLoanRelationMapper.selectByPrimaryKey(lnLoanRelationId);
    }

    @Override
    public List<LnLoanRelationVO> queryCustodyFinancialManagement(int loanId) {
        List<LnLoanRelationVO> list = lnLoanRelationMapper.selectCustodyFinancialManagement(loanId);
        return list;
    }

    @Override
    public LnLoan queryLoanInfoById(int loanId) {
        LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(loanId);
        return lnLoan;
    }

    @Override
    public LnLoanRelation queryLoanRelationById(int lnLoanRelationId) {
        return lnLoanRelationMapper.selectByPrimaryKey(lnLoanRelationId);
    }

    @Override
    public List<DebtTransferRecordsVO> selectCustodyLoanTransferClaims(int loanId) {
        List<DebtTransferRecordsVO> list = lnLoanRelationMapper.selectCustodyLoanTransferClaims(loanId);
        return list;
    }

    @Override
    public BsLoanRelationTransferVO queryCustodyTransferClaims(int lnLoanRelationId) {
        BsLoanRelationTransferVO vo = lnCreditTransferMapper.selectCustodyTransferClaims(lnLoanRelationId);
        return vo;
    }

    @Override
    public List<LnCreditTransfer> queryInAmountById(int subAccountId) {
        LnCreditTransferExample example = new LnCreditTransferExample();
        example.createCriteria().andInSubAccountIdEqualTo(subAccountId);
        List<LnCreditTransfer> list = lnCreditTransferMapper.selectByExample(example);
        return list.size() > 0 ? list : null;
    }

    @Override
    public LoanDetailInfoVO queryZsdPrincipalInterest(int loanId) {
        return lnLoanMapper.selectZsdPrincipalInterest(loanId);
    }

    @Override
    @MethodRole("APP")
    public List<DetailsOfDebtVO> queryDepClaimsListBySubAccountIdNew(int subAccountId, int pageNum, int numPerPage) {
        int start = pageNum;
        List<DetailsOfDebtVO> detailsOfDebtVOList = lnLoanRelationMapper.selectDepClaimsListBySubAccountIdNew(subAccountId, start, numPerPage);
        return detailsOfDebtVOList;
    }

    @Override
    public BorrowerInfoVO queryBorrowerInfo(Integer userId, String partnerCode, Integer loanRelationId) {
        LnLoanRelation lnLoanRelation =  lnLoanRelationMapper.selectByPrimaryKey(loanRelationId);
        if(!lnLoanRelation.getBsUserId().equals(userId)) {
            throw new PTMessageException(PTMessageEnum.NO_THIS_BORROWER_INFO_ERROR);
        }
        if(PartnerEnum.ZAN.getCode().equals(partnerCode)
                || PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerCode)
                || PartnerEnum.SEVEN_DAI_SELF.getCode().equals(partnerCode)
                || PartnerEnum.ZSD.getCode().equals(partnerCode)
                || PartnerEnum.FREE.getCode().equals(partnerCode)) {
            BorrowerInfoVO info = lnLoanRelationMapper.selectBorrowerInfo(partnerCode, loanRelationId);
            info.setAge(IdcardUtils.getAgeByIdCard(info.getIdCard()));
            info.setSex(IdcardUtils.getGenderByIdCard(info.getIdCard()));
            StringUtil.left(info.getUserName(), 1);
            info.setUserName(StringUtil.left(info.getUserName(), 1) + "**");
            info.setIdCard(StringUtil.left(info.getIdCard(), 3) + "*************" + StringUtil.right(info.getIdCard(), 2));
            return info;
        } else {
            return null;
        }
    }

    @Override
    public BsUser query7DaiSelfCreditor() {
        return bsUserMapper.select7DaiSelfCreditor();
    }

    @Override
    public CheckLnUserPartnerCodeVO queryCheckLnUserPartnerCode(int loanRelationId) {
        return lnLoanRelationMapper.selectCheckLnUserPartnerCode(loanRelationId);
    }

    @Override
    public List<CompensateTransfersVO> queryCompensateTransferListRenew(int loanId) {
        List<CompensateTransfersVO> voList = lnLoanRelationMapper.selectCompensateTransferListRenew(loanId);
        return voList;
    }

    @Override
    public List<CompensateTransfersPdfVO> queryCompensateTransfer4StageList(int loanId) {
        List<CompensateTransfersPdfVO> voList = lnDepositionRepayScheduleMapper.selectCompensateTransfer4StageList(loanId);
        return voList;
    }
}
