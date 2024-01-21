package com.pinting.business.service.site.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.LnCompensateAgreementAddress;
import com.pinting.business.model.LnCompensateAgreementAddressExample;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.site.BsLoanRelationShipService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransferInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BsLoanRelationShipServiceImpl implements BsLoanRelationShipService {

	private Logger log = LoggerFactory.getLogger(BsLoanRelationShipServiceImpl.class);

	@Autowired
	private LnCreditTransferMapper lnCreditTransferMapper;
	
	@Autowired
	private LnLoanMapper lnLoanMapper;
	
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;

	@Autowired
	private AlgorithmService algorithmService;
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private LnCompensateAgreementAddressMapper compenAgreeAddressMapper;
	@Autowired
	private LnCompensateRelationMapper lnCompensateRelationMapper;
	@Autowired
	private LnUserMapper lnUserMapper;
	@Autowired
	private DepFixedRepayPaymentService depFixedRepayPaymentService;

	@Override
	public BsLoanRelationTransferVO getLoanTransferInfos( Integer inLoanRelationId) {
		if(inLoanRelationId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*查询数据*/
		BsLoanRelationTransferVO transferVO = lnCreditTransferMapper.selectVOByLoanRelationId(inLoanRelationId);

		if(transferVO != null){

			transferVO.setLeftTerm(transferVO.getTerm()+1-transferVO.getFirstTerm());
			transferVO.setRepayAmount4All(
					MoneyUtil.multiply(transferVO.getRepayAmount4Month(), transferVO.getTerm()).doubleValue());
			transferVO.setInUserName(getBlurName(transferVO.getInUserName()));
			transferVO.setOutUserName(getBlurName(transferVO.getOutUserName()));
			transferVO.setBorrowUserName(getBlurName(transferVO.getBorrowUserName()));

			transferVO.setInUserMobile(getBlurMobile(transferVO.getInUserMobile()));
			transferVO.setOutUserMobile(getBlurMobile(transferVO.getOutUserMobile()));

			transferVO.setInUserIdCardNo(getBlurIdNo(transferVO.getInUserIdCardNo()));
			transferVO.setOutUserIdCardNo(getBlurIdNo(transferVO.getOutUserIdCardNo()));
			transferVO.setBorrowUserIdCardNo(getBlurIdNo(transferVO.getBorrowUserIdCardNo()));
		}
		return transferVO;
	}

	@Override
	public BsLoanRelationTransferVO getLoanTransferInfoNew( Integer inLoanRelationId) {
		if(inLoanRelationId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*查询数据*/
		BsLoanRelationTransferVO transferVO = lnCreditTransferMapper.selectVOByLoanRelationIdNew(inLoanRelationId);

		if(transferVO != null){

			//等额本息方法获得列表
			List<AverageCapitalPlusInterestVO> avgList = algorithmService.calAverageCapitalPlusInterestPlan(transferVO.getAmount(), transferVO.getTerm(), transferVO.getProductRate());
			Double amount = 0.0;
			/*数据处理*/
			if(avgList != null){
				for(int i = 0; i < avgList.size(); i++){
					amount = MoneyUtil.add(amount, avgList.get(i).getPlanInterest()).doubleValue();
				}
			}
			transferVO.setExpectProfit(amount);
			transferVO.setLeftTerm(transferVO.getTerm()+1-transferVO.getFirstTerm());
			transferVO.setRepayAmount4All(
					MoneyUtil.multiply(transferVO.getRepayAmount4Month(), transferVO.getTerm()).doubleValue());
			transferVO.setInUserName(getBlurName(transferVO.getInUserName()));
			transferVO.setOutUserName(getBlurName(transferVO.getOutUserName()));
			transferVO.setBorrowUserName(getBlurName(transferVO.getBorrowUserName()));

			transferVO.setInUserMobile(getBlurMobile(transferVO.getInUserMobile()));
			transferVO.setOutUserMobile(getBlurMobile(transferVO.getOutUserMobile()));

			transferVO.setInUserIdCardNo(getBlurIdNo(transferVO.getInUserIdCardNo()));
			transferVO.setOutUserIdCardNo(getBlurIdNo(transferVO.getOutUserIdCardNo()));
			transferVO.setBorrowUserIdCardNo(getBlurIdNo(transferVO.getBorrowUserIdCardNo()));
		}
		return transferVO;
	}
	
	private String getBlurName(String name) {
		String str = "";
		if(StringUtil.isNotBlank(name)){
			str = name.substring(0, 1);
			for (int i = 1; i < name.length(); i++) {
				str += "*";
			}
		}
		return str;
	}

	/**
	 * 手机号模糊处理
	 * @param mobile
	 * @return
	 */
	private String getBlurMobile(String mobile) {
		String str = "";
		if(StringUtil.isNotBlank(mobile)){
			str = mobile.substring(0, 3);
			str = str + "****"+mobile.substring(mobile.length()-4);
		}
		return str;
	}
	
	
	/**
	 * 18位的隐藏掉中间10位，如123456**********12；15位隐藏中间8位，如123456********1
	 * 身份证
	 * @param idNo
	 * @return
	 */
	private String getBlurIdNo(String idNo) {
		String str = "";
		if(StringUtil.isNotBlank(idNo)){
			str = idNo.substring(0, 6);
			if(idNo.length() == 18){
				str += "**********" + idNo.substring(idNo.length()-2);
			}else{
				str += "********" + idNo.substring(idNo.length()-1);
			}
		}
		
		return str;
	}

	@Override
	public LoanDetailInfoVO getLoanDetailInfoVO(String partnerLoanId) {
		/*查询借款人相关数据*/
		LoanDetailInfoVO vo = lnLoanMapper.selectLoanDetailInfo(partnerLoanId, Constants.PROPERTY_SYMBOL_ZAN);
		if(vo != null){
			/*查询每月应还本息和利息*/
			Double amount = lnLoanMapper.selectPlanPIByLoanId(vo.getId());
			vo.setNeedRepayMoney4Month(amount);
			/*查询出借人信息*/
			List<BsUser4LoanVO> userList = lnLoanRelationMapper.selectBsUserByLoanId(vo.getId());

			//根据借款表id查询借款年化利率,值为万分之xx
			Double interestRate = lnLoanMapper.selectLoanInterestRate(vo.getId());
			vo.setLnRate(interestRate/10000*100*12);

			/*出借人信息模糊化*/
			if(userList != null && userList.size() >0){
				List<BsUser4LoanVO> dataList = new ArrayList<BsUser4LoanVO>();
				for (BsUser4LoanVO bsUser4LoanVO : userList) {
					BsUser4LoanVO data = new BsUser4LoanVO();
					data.setUserAccount((bsUser4LoanVO.getUserAccount()));
					data.setUserIdCardNo((bsUser4LoanVO.getUserIdCardNo()));
					data.setUserName((bsUser4LoanVO.getUserName()));
					data.setOutAmount(bsUser4LoanVO.getOutAmount());
					data.setTerm(bsUser4LoanVO.getTerm());
					data.setUserId(bsUser4LoanVO.getUserId());
					dataList.add(data);
				}
				vo.setBsUserInfo(dataList);
			}

			/*借款人相关数据模糊化*/
			vo.setLnUserName((vo.getLnUserName()));
			vo.setLnUserIdCard((vo.getLnUserIdCard()));
			vo.setLnUserZANAccount((vo.getLnUserZANAccount()));
			
			/*第一期还款日、最后一期还款日、每月还款日*/
			if(vo.getLoanTime() != null){
				vo.setLnRepayStartDate(DateUtil.addMonths(vo.getLoanTime(), 1));
				vo.setLnRepayEndDate(DateUtil.addMonths(vo.getLoanTime(), vo.getPeriod()));
				vo.setDay4Month(Integer.parseInt(DateUtil.formatDateTime(vo.getLoanTime(), "dd")));
			}
		}
		
		return vo;
	}

	@Override
	public List<DebtTransferInfo> queryTransferInfo(String loanId) {
		List<DebtTransferInfo> transferList =  new ArrayList<DebtTransferInfo>();
		//1、合作方借款id转成借款id
		LnLoan lnLoan = lnLoanMapper.selectLoanByPartnerLoanId(loanId);
		LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
//		BsUser bsUser = new BsUser();
//		if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(lnUser.getPartnerCode())) {
//			//a、受让人（代偿人）云贷
//			bsUser = bsUserMapper.selectYunDaiSelfCreditor();
//		}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(lnUser.getPartnerCode())){
//			//b、受让人（代偿人）7贷
//			bsUser = bsUserMapper.select7DaiSelfCreditor();
//		}
		
		BsUserCompensateVO vo = depFixedRepayPaymentService.compensaterInfo(lnLoan.getLoanTime(), lnUser.getPartnerCode());
         

		//2、根据借款ID查询这一笔借款中最后一笔还款的借贷关系id
		List<LnCompensateRelationVO> loanRelationList = lnCompensateRelationMapper.selectRelationIdListByLoanId(lnLoan.getId());
		// 统计借款人这一笔借款-代偿的次数
		int sevenCompensateCount = lnCompensateRelationMapper.select7CompensationCount(lnLoan.getId());
		
		if(CollectionUtils.isNotEmpty(loanRelationList)){
			for (LnCompensateRelationVO lnCompensateRelationVO : loanRelationList) {
				
				DebtTransferInfo transferVO = new DebtTransferInfo();
				transferVO.setOutUserName(getBlurName(lnCompensateRelationVO.getBsUserName())); //债权出让人姓名
				transferVO.setOutIdCard(getBlurIdNo(lnCompensateRelationVO.getBsIdCard())); //债权出让人身份证号
				
				if(vo != null) {
					transferVO.setInUserName(getBlurName(vo.getUserName())); //债权受让人姓名
					transferVO.setInIdCard(getBlurIdNo(vo.getIdCard())); //债权受让人身份证号
				}
				//债权转让金额 Double转成长整形
				transferVO.setTransAmount(MoneyUtil.multiply(lnCompensateRelationVO.getAmount(), 100).longValue());

//				//3、根据借款编号查询最后一笔还款计划的本金，及借款本金期限
//				CompensateTransfersVO compensateTransfersVO =  lnRepayScheduleMapper.selectCompensateTransfersByLoanId(id);
//				if(compensateTransfersVO != null) {
//					//债权转让金额 = 未还本金*借款利率*未还账单期限/365+未还本金 Double转成长整形
//					transferVO.setTransAmount(MoneyUtil.add(MoneyUtil.divide(MoneyUtil.multiply(MoneyUtil.multiply(compensateTransfersVO.getBeforeAmount(), compensateTransfersVO.getAgreementRate()).doubleValue(), noBillingPeriod).doubleValue(), 365).doubleValue(),
//							compensateTransfersVO.getBeforeAmount()).longValue());
//				}
				transferVO.setPeroid(sevenCompensateCount); //剩余期数-代偿了多少期
				//4、转让时间(协议的生成时间)
				LnCompensateAgreementAddressExample lnCompensateAgreementAddressExample = new LnCompensateAgreementAddressExample();
				if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(lnUser.getPartnerCode())) {
					lnCompensateAgreementAddressExample.createCriteria().andPartnerLoanIdEqualTo(loanId).andPartnerCodeEqualTo(PartnerEnum.YUN_DAI_SELF.getCode());
				}else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(lnUser.getPartnerCode())){
					lnCompensateAgreementAddressExample.createCriteria().andPartnerLoanIdEqualTo(loanId).andPartnerCodeEqualTo(PartnerEnum.SEVEN_DAI_SELF.getCode());
				}
				List<LnCompensateAgreementAddress> agreementAddressList = compenAgreeAddressMapper.selectByExample(lnCompensateAgreementAddressExample);
				if(agreementAddressList != null && agreementAddressList.size() > 0) {
					transferVO.setTransTime(agreementAddressList.get(0).getCreateTime());
				}
				transferVO.setNote(""); //备注
				transferList.add(transferVO);
			}
		}
		return transferList;
	}
	
	@Override
	public List<String> queryCompenAgreeAddressList(String partnerCode,
			String agreementType, String partnerLoanId) {
		List<String> compenAgreeAddressList= compenAgreeAddressMapper.selectCompenAgreeAddressList(partnerCode,agreementType,partnerLoanId);
		return compenAgreeAddressList;
	}

	@Override
	public LoanDetailInfoVO getZsdLoanDetailInfoVO(String partnerLoanId) {
		/*查询借款人相关数据*/
		LoanDetailInfoVO vo = lnLoanMapper.selectLoanDetailInfo(partnerLoanId, Constants.PROPERTY_SYMBOL_ZSD);
		if(vo != null){
			/*查询出借人信息*/
			List<BsUser4LoanVO> userList = lnLoanRelationMapper.selectBsUserByLoanId(vo.getId());

			/*出借人信息模糊化*/
			if(userList != null && userList.size() >0){
				List<BsUser4LoanVO> dataList = new ArrayList<BsUser4LoanVO>();
				for (BsUser4LoanVO bsUser4LoanVO : userList) {
					BsUser4LoanVO data = new BsUser4LoanVO();
					data.setUserAccount((bsUser4LoanVO.getUserAccount()));
					data.setUserIdCardNo((bsUser4LoanVO.getUserIdCardNo()));
					data.setUserName((bsUser4LoanVO.getUserName()));
					data.setOutAmount(bsUser4LoanVO.getOutAmount());
					data.setTerm(bsUser4LoanVO.getTerm());
					data.setUserId(bsUser4LoanVO.getUserId());
					dataList.add(data);
				}
				vo.setBsUserInfo(dataList);
			}

			vo.setLnUserName(vo.getLnUserName());
			vo.setLnUserIdCard(vo.getLnUserIdCard());
			vo.setLnUserZANAccount(vo.getLnUserZANAccount());
		}
		return vo;
	}

	@Override
	public BsLoanRelationTransferVO getLoanTransferInfoNewForPdf( Integer inLoanRelationId) {
		if(inLoanRelationId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*查询数据*/
		BsLoanRelationTransferVO transferVO = lnCreditTransferMapper.selectVOByLoanRelationIdNew(inLoanRelationId);

		if(transferVO != null){

			//等额本息方法获得列表
			List<AverageCapitalPlusInterestVO> avgList = algorithmService.calAverageCapitalPlusInterestPlan(transferVO.getAmount(), transferVO.getTerm(), transferVO.getProductRate());
			Double amount = 0.0;
			/*数据处理*/
			if(avgList != null){
				for(int i = 0; i < avgList.size(); i++){
					amount = MoneyUtil.add(amount, avgList.get(i).getPlanInterest()).doubleValue();
				}
			}
			transferVO.setExpectProfit(amount);
			transferVO.setLeftTerm(transferVO.getTerm()+1-transferVO.getFirstTerm());
			transferVO.setRepayAmount4All(
					MoneyUtil.multiply(transferVO.getRepayAmount4Month(), transferVO.getTerm()).doubleValue());
			transferVO.setInUserName(transferVO.getInUserName());
			transferVO.setOutUserName(transferVO.getOutUserName());
			transferVO.setBorrowUserName(transferVO.getBorrowUserName());

			transferVO.setInUserMobile(transferVO.getInUserMobile());
			transferVO.setOutUserMobile(transferVO.getOutUserMobile());

			transferVO.setInUserIdCardNo(transferVO.getInUserIdCardNo());
			transferVO.setOutUserIdCardNo(transferVO.getOutUserIdCardNo());
			transferVO.setBorrowUserIdCardNo(transferVO.getBorrowUserIdCardNo());
		}
		return transferVO;
	}
	
	@Override
	public LoanDetailInfoVO getYunLoanDetailInfoVO(String partnerLoanId) {
		/*查询借款人相关数据*/
		LoanDetailInfoVO vo = lnLoanMapper.selectYunLoanDetailInfo(partnerLoanId, Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
		if(vo != null){
			/*查询出借人信息*/
			List<BsUser4LoanVO> userList = lnLoanRelationMapper.selectBsUserByLoanId(vo.getId());

			/*出借人信息模糊化*/
			if(userList != null && userList.size() >0){
				List<BsUser4LoanVO> dataList = new ArrayList<BsUser4LoanVO>();
				for (BsUser4LoanVO bsUser4LoanVO : userList) {
					BsUser4LoanVO data = new BsUser4LoanVO();
					data.setUserAccount((bsUser4LoanVO.getUserAccount()));
					data.setUserIdCardNo((bsUser4LoanVO.getUserIdCardNo()));
					data.setUserName((bsUser4LoanVO.getUserName()));
					data.setOutAmount(bsUser4LoanVO.getOutAmount());
					data.setTerm(bsUser4LoanVO.getTerm());
					data.setUserId(bsUser4LoanVO.getUserId());
					dataList.add(data);
				}
				vo.setBsUserInfo(dataList);
			}

			/*借款人相关数据模糊化*/
			vo.setLnUserName((vo.getLnUserName()));
			vo.setLnUserIdCard((vo.getLnUserIdCard()));
			vo.setLnUserZANAccount((vo.getLnUserZANAccount()));
			
		}
		return vo;
	}

	@Override
	public LoanDetailInfoVO getSevenLoanDetailInfoVO(String partnerLoanId) {
		/*查询借款人相关数据*/
		LoanDetailInfoVO vo = lnLoanMapper.selectSevenLoanDetailInfo(partnerLoanId, Constants.PROPERTY_SYMBOL_7_DAI_SELF);

		log.info("查询借款时间>>{}" + vo.getLoanTime());

		if(vo != null){
			/*查询出借人信息*/
			List<BsUser4LoanVO> userList = lnLoanRelationMapper.selectBsUserByLoanId(vo.getId());

			/*出借人信息模糊化*/
			if(userList != null && userList.size() >0){
				List<BsUser4LoanVO> dataList = new ArrayList<BsUser4LoanVO>();
				for (BsUser4LoanVO bsUser4LoanVO : userList) {
					BsUser4LoanVO data = new BsUser4LoanVO();
					data.setUserAccount((bsUser4LoanVO.getUserAccount()));
					data.setUserIdCardNo((bsUser4LoanVO.getUserIdCardNo()));
					data.setUserName((bsUser4LoanVO.getUserName()));
					data.setOutAmount(bsUser4LoanVO.getOutAmount());
					data.setTerm(bsUser4LoanVO.getTerm());
					data.setUserId(bsUser4LoanVO.getUserId());
					dataList.add(data);
				}
				vo.setBsUserInfo(dataList);
			}

			/*借款人相关数据模糊化*/
			vo.setLnUserName((vo.getLnUserName()));
			vo.setLnUserIdCard((vo.getLnUserIdCard()));
			vo.setLnUserZANAccount((vo.getLnUserZANAccount()));
			
		}
		return vo;
	}
}
