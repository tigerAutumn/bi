package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BillInfo;
import com.pinting.business.accounting.loan.model.Loan7BillInfo;
import com.pinting.business.accounting.loan.service.DepFixedBillSyncService;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.accounting.service.impl.process.SignSeal4DafyLoanProcess;
import com.pinting.business.accounting.service.impl.process.SignSeal4DepLoan7Process;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsUser4LoanVO;
import com.pinting.business.model.vo.SignSealUserInfoVO;
import com.pinting.business.service.loan.BsUserSealFileService;
import com.pinting.business.service.loan.impl.DepLoan7CheckAccountServiceImpl;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_QueryBill;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_QueryBill;
import com.pinting.gateway.hessian.message.dafy.model.QueryBillRepayment;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @project business
 * @title DepFixedBillSyncServiceImpl.java
 * @author Dragon & cat
 * @date 2017-4-4
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class DepFixedBillSyncServiceImpl implements DepFixedBillSyncService {
	private Logger log = LoggerFactory.getLogger(DepFixedBillSyncServiceImpl.class);
	
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private LnRepayScheduleDetailMapper lnRepayScheduleDetailMapper;
    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private SignSealService signSealService;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Autowired
	private DafyNoticeService	dafyNoticeService;
	@Autowired
	private BsUserSealFileService bsUserSealFileService;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private LnRepayMapper lnRepayMapper;
	@Autowired
	private DepLoan7NoticeService depLoan7NoticeService;
	
    @Override
    public BillInfo getNewestBill(String partnerUserId, String partnerLoanId) {
    	B2GReqMsg_DafyLoanNotice_QueryBill  queryBillReq = new B2GReqMsg_DafyLoanNotice_QueryBill();
    	queryBillReq.setUserId(partnerUserId);
    	queryBillReq.setLoanId(partnerLoanId);
    	B2GResMsg_DafyLoanNotice_QueryBill  queryBillRes = dafyNoticeService.queryBill(queryBillReq);
    	
    	//判断同步回来的账单信息里面userId和loanId是否一致
    	if (!partnerUserId.equals(queryBillRes.getUserId())) {
    		throw new PTMessageException(PTMessageEnum.BILL_SYNC_INFO_ERROR);
		}
    	
    	if (!partnerLoanId.equals(queryBillRes.getLoanId())) {
    		throw new PTMessageException(PTMessageEnum.BILL_SYNC_INFO_ERROR);
		}
    	
    	
		BillInfo billInfo = new BillInfo();
		billInfo.setPartnerUserId(queryBillRes.getUserId());
		billInfo.setPartnerLoanId(queryBillRes.getLoanId());
		billInfo.setAgreementNo(queryBillRes.getAgreementNo());
		billInfo.setAgreementUrl(queryBillRes.getAgreementUrl());
		billInfo.setRepayments(queryBillRes.getRepayments());
		return billInfo;
    }
    @Override
    public Loan7BillInfo getSevenNewBills(String partnerUserId, String partnerLoanId) {
    	
    	B2GReqMsg_DepLoan7Notice_QueryBill queryBillReq = new B2GReqMsg_DepLoan7Notice_QueryBill();
    	queryBillReq.setUserId(partnerUserId);
    	queryBillReq.setLoanId(partnerLoanId);
    	B2GResMsg_DepLoan7Notice_QueryBill  queryBillRes = depLoan7NoticeService.queryBill(queryBillReq);
    	
    	//判断同步回来的账单信息里面userId和loanId是否一致
    	if (!partnerUserId.equals(queryBillRes.getUserId())) {
    		log.info(">>>>>>>>>>>bgw-parUserId="+partnerUserId+"7dai-parUserId="+queryBillRes.getUserId());
    		throw new PTMessageException(PTMessageEnum.BILL_SYNC_INFO_ERROR);
		}
    	
    	if (!partnerLoanId.equals(queryBillRes.getLoanId())) {
    		log.info(">>>>>>>>>>>bgw-parLoanId="+partnerUserId+"7dai-parLoanId="+queryBillRes.getUserId());
    		throw new PTMessageException(PTMessageEnum.BILL_SYNC_INFO_ERROR);
		}

		Loan7BillInfo billInfo = new Loan7BillInfo();
		billInfo.setPartnerUserId(queryBillRes.getUserId());
		billInfo.setPartnerLoanId(queryBillRes.getLoanId());
		billInfo.setAgreementNo(queryBillRes.getAgreementNo());
		billInfo.setAgreementUrl(queryBillRes.getAgreementUrl());
		billInfo.setRepayments(queryBillRes.getRepayments());
		return billInfo;
    }
    
    /**
     * 账单同步处理
     *
     * @param billInfo
     */
    @Override
	public void loanSyncBill(BillInfo billInfo, final Integer lnLoanId) {
        final List<QueryBillRepayment> repayments = billInfo.getRepayments();

        if(!CollectionUtils.isEmpty(repayments)){
			LnRepayExample exampleRepay = new LnRepayExample();
			exampleRepay.createCriteria().andLoanIdEqualTo(lnLoanId);
			List<LnRepay> lnRepays = lnRepayMapper.selectByExample(exampleRepay);
			if(CollectionUtils.isEmpty(lnRepays)) {
				transactionTemplate.execute(new TransactionCallbackWithoutResult(){
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						//账单存库
						for (QueryBillRepayment bill : repayments){	
							if (!BillInfo.BillStatus.BILL_STATUS_INIT.getCode().equals(bill.getStatus())) {
								throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, ",账单同步未还款的账单");
							}
							LoanStatus localBillStatus = BillInfo.BillStatus.getLoanStatusByCode(bill.getStatus());
							LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
							lnRepayScheduleExample.createCriteria().andLoanIdEqualTo(lnLoanId).andPartnerRepayIdEqualTo(bill.getRepayId());
							List<LnRepaySchedule> schedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);
							if(CollectionUtils.isEmpty(schedules)){
								LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
								lnRepaySchedule.setUpdateTime(new Date());
								lnRepaySchedule.setCreateTime(new Date());
								lnRepaySchedule.setLoanId(lnLoanId);
								lnRepaySchedule.setPartnerRepayId(bill.getRepayId());
								lnRepaySchedule.setPlanDate(bill.getRepayDate());
								lnRepaySchedule.setPlanTotal(bill.getTotal());
								lnRepaySchedule.setStatus(localBillStatus.getCode());
								lnRepaySchedule.setSerialId(bill.getRepaySerial());
								lnRepaySchedule.setPayOrderNo(bill.getBgwOrderNo());
								lnRepayScheduleMapper.insertSelective(lnRepaySchedule);

								LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
								lnRepayScheduleDetail.setUpdateTime(new Date());
								lnRepayScheduleDetail.setCreateTime(new Date());
								lnRepayScheduleDetail.setPlanId(lnRepaySchedule.getId());
								lnRepayScheduleDetail.setPlanAmount(bill.getPrincipal() != null ? bill.getPrincipal() : 0d);
								lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
								lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

								lnRepayScheduleDetail.setPlanAmount(bill.getInterest() != null ? bill.getInterest() : 0d);
								lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
								lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

								lnRepayScheduleDetail.setPlanAmount(bill.getPrincipalOverdue() != null ? bill.getPrincipalOverdue() : 0d);
								lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
								lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

								lnRepayScheduleDetail.setPlanAmount(bill.getInterestOverdue() != null ? bill.getInterestOverdue() : 0d);
								lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
								lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
							}
						}
					}
				});
			}
        }
        //借款协议签章流程，更改为币港湾直接签章, 所以账单推送不再进行签章协议服务调用
        /*String agreementNo = billInfo.getAgreementNo();
        if(StringUtil.isNotEmpty(agreementNo)) {
        	log.info("{}签章协议服务调用>>>>>>>>>>>{}", billInfo.getPartnerCode(), agreementNo);
        	doSignSealProcess(billInfo, lnLoan);
        }*/
    }
    
    private void doSignSealProcess(BillInfo billInfo, LnLoan lnLoan) {
    	//签章协议下载服务调用
    	String agreementNo = billInfo.getAgreementNo();
        BsUserSealFileExample sealFileExample = new BsUserSealFileExample();
        sealFileExample.createCriteria().andUserSrcEqualTo(billInfo.getPartnerCode())
                .andAgreementNoEqualTo(agreementNo);
        List<BsUserSealFile> sealFiles = bsUserSealFileMapper.selectByExample(sealFileExample);
        
        if(CollectionUtils.isEmpty(sealFiles)){
        	log.info("{}签章协议服务调用>>>>>>>>>>> 保存协议信息进行签章", billInfo.getPartnerCode());
        	BsUserSealFile sealFile = new BsUserSealFile();
            sealFile.setUpdateTime(new Date());
            sealFile.setAgreementNo(agreementNo);
            sealFile.setCreateTime(new Date());
            sealFile.setSealStatus(SealStatus.UNDOWNLOAD.getCode());
            sealFile.setSealType(SealBusiness.LOAN_AGREEMENT.getCode());
            sealFile.setSrcAddress(billInfo.getAgreementUrl());
            sealFile.setUserId(lnLoan.getLnUserId());
            sealFile.setUserSrc(billInfo.getPartnerCode());
            sealFile.setRelativeInfo(String.valueOf(lnLoan.getId()));
            bsUserSealFileMapper.insertSelective(sealFile);
            //调用签章协议下载服务进行协议签章
	        if (PartnerEnum.YUN_DAI_SELF.getCode().equals(billInfo.getPartnerCode())) {
	        	doYunSignSeal(billInfo, lnLoan, sealFile);
	        } else if (PartnerEnum.SEVEN_DAI_SELF.getCode().equals(billInfo.getPartnerCode())) {
	        	doDepSevenSignSeal(billInfo, lnLoan, sealFile);
	        }
        }else if(SealStatus.UNDOWNLOAD.getCode().equals(sealFiles.get(0).getSealStatus())){
        	 //调用签章协议下载服务进行协议签章
        	log.info("{}签章协议服务调用>>>>>>>>>>> 直接进行协议签章", billInfo.getPartnerCode());
        	if (PartnerEnum.YUN_DAI_SELF.getCode().equals(billInfo.getPartnerCode())) {        		
        		doYunSignSeal(billInfo, lnLoan, sealFiles.get(0));
        	} else if (PartnerEnum.SEVEN_DAI_SELF.getCode().equals(billInfo.getPartnerCode())) {
        		doDepSevenSignSeal(billInfo, lnLoan, sealFiles.get(0));
        	}
	        
        }else if(SealStatus.FAIL.getCode().equals(sealFiles.get(0).getSealStatus())){
           	 //调用签章协议下载服务进行协议签章
           	log.info("{}签章协议服务调用>>>>>>>>>>> 原协议签章失败直接进行协议签章", billInfo.getPartnerCode());
           	if (PartnerEnum.YUN_DAI_SELF.getCode().equals(billInfo.getPartnerCode())) {
           		doYunSignSeal(billInfo, lnLoan, sealFiles.get(0));
           	} else if (PartnerEnum.SEVEN_DAI_SELF.getCode().equals(billInfo.getPartnerCode())) {
           		doDepSevenSignSeal(billInfo, lnLoan, sealFiles.get(0));
           	}
       }
    }
    	
    private void doYunSignSeal(BillInfo billInfo, LnLoan lnLoan, BsUserSealFile sealFile) {
    	SignSeal4DafyLoanProcess process = new SignSeal4DafyLoanProcess();
        process.setSignSealService(signSealService);
        process.setUserService(userService);
        SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
        signSealUserInfo.setPdfPath(sealFile.getSrcAddress());
        signSealUserInfo.setAgreementNo(billInfo.getAgreementNo());
        signSealUserInfo.setSealFileId(sealFile.getId());
        signSealUserInfo.setLoanId(lnLoan.getPartnerLoanId());
        process.setSignSealUserInfo(signSealUserInfo);
        List<BsUser4LoanVO>   bsUserList = new ArrayList<BsUser4LoanVO>();
        bsUserList = lnLoanRelationMapper.selectBsUserByLoanId(lnLoan.getId());
        process.setBsUserList(bsUserList);
        process.setDafyNoticeService(dafyNoticeService);
        process.setBsUserSealFileService(bsUserSealFileService);
        new Thread(process).start();
    }
    
    private void doDepSevenSignSeal(BillInfo billInfo, LnLoan lnLoan, BsUserSealFile sealFile) {
    	SignSeal4DepLoan7Process process = new SignSeal4DepLoan7Process();
    	process.setSignSealService(signSealService);
    	SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
    	signSealUserInfo.setPdfPath(sealFile.getSrcAddress());
    	signSealUserInfo.setAgreementNo(billInfo.getAgreementNo());
    	signSealUserInfo.setSealFileId(sealFile.getId());
    	signSealUserInfo.setLoanId(lnLoan.getPartnerLoanId());
    	signSealUserInfo.setPartnerCode(billInfo.getPartnerCode());
    	process.setSignSealUserInfo(signSealUserInfo);
    	List<BsUser4LoanVO>   bsUserList = new ArrayList<BsUser4LoanVO>();
    	bsUserList = lnLoanRelationMapper.selectBsUserByLoanId(lnLoan.getId());
    	process.setBsUserList(bsUserList);
    	process.setDepLoan7NoticeService(depLoan7NoticeService);
    	process.setBsUserSealFileService(bsUserSealFileService);
    	new Thread(process).start();
    }
 
	@Override
	public void manualLoanSyncBill(BillInfo billInfo) {
        final List<QueryBillRepayment> repayments = billInfo.getRepayments();
        String partnerUserId = billInfo.getPartnerUserId();
        final String partnerLoanId = billInfo.getPartnerLoanId();
        List<LnLoan> lnLoans = lnLoanMapper.selectByLoadIdAndPartnerCode(partnerLoanId,PartnerEnum.YUN_DAI_SELF.getCode());
        if(CollectionUtils.isEmpty(lnLoans)){
    		throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",账单同步处理时未找到partnerLoanId=" + partnerLoanId + "的借款信息");
        }
        final LnLoan lnLoan = lnLoans.get(0);
        if(!CollectionUtils.isEmpty(repayments)){
			LnRepayExample exampleRepay = new LnRepayExample();
			exampleRepay.createCriteria().andLoanIdEqualTo(lnLoan.getId()).andStatusNotEqualTo(Constants.LN_REPAY_PAY_STATUS_REPAY_FAIL);
			List<LnRepay> lnRepays = lnRepayMapper.selectByExample(exampleRepay);
			if(CollectionUtils.isEmpty(lnRepays)){
				transactionTemplate.execute(new TransactionCallbackWithoutResult(){
					@Override
					protected void doInTransactionWithoutResult(TransactionStatus status) {
						
						//账单存库
						for (QueryBillRepayment bill : repayments){	
							if (!BillInfo.BillStatus.BILL_STATUS_INIT.getCode().equals(bill.getStatus())) {
								throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",账单同步未还款的账单");
							}
							LoanStatus localBillStatus = BillInfo.BillStatus.getLoanStatusByCode(bill.getStatus());
							LnRepayScheduleExample lnRepayScheduleExample = new LnRepayScheduleExample();
							lnRepayScheduleExample.createCriteria().andLoanIdEqualTo(lnLoan.getId()).andPartnerRepayIdEqualTo(bill.getRepayId());
							List<LnRepaySchedule> schedules = lnRepayScheduleMapper.selectByExample(lnRepayScheduleExample);
							if(CollectionUtils.isEmpty(schedules)){
								LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
								lnRepaySchedule.setUpdateTime(new Date());
								lnRepaySchedule.setCreateTime(new Date());
								lnRepaySchedule.setLoanId(lnLoan.getId());
								lnRepaySchedule.setPartnerRepayId(bill.getRepayId());
								lnRepaySchedule.setPlanDate(bill.getRepayDate());
								lnRepaySchedule.setPlanTotal(bill.getTotal());
								lnRepaySchedule.setStatus(localBillStatus.getCode());
								lnRepaySchedule.setSerialId(bill.getRepaySerial());
								lnRepaySchedule.setPayOrderNo(bill.getBgwOrderNo());
								lnRepayScheduleMapper.insertSelective(lnRepaySchedule);

								LnRepayScheduleDetail lnRepayScheduleDetail = new LnRepayScheduleDetail();
								lnRepayScheduleDetail.setUpdateTime(new Date());
								lnRepayScheduleDetail.setCreateTime(new Date());
								lnRepayScheduleDetail.setPlanId(lnRepaySchedule.getId());
								lnRepayScheduleDetail.setPlanAmount(bill.getPrincipal() != null ? bill.getPrincipal() : 0d);
								lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
								lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

								lnRepayScheduleDetail.setPlanAmount(bill.getInterest() != null ? bill.getInterest() : 0d);
								lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
								lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

								lnRepayScheduleDetail.setPlanAmount(bill.getPrincipalOverdue() != null ? bill.getPrincipalOverdue() : 0d);
								lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL_OVERDUE.getCode());
								lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);

								lnRepayScheduleDetail.setPlanAmount(bill.getInterestOverdue() != null ? bill.getInterestOverdue() : 0d);
								lnRepayScheduleDetail.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST_OVERDUE.getCode());
								lnRepayScheduleDetailMapper.insertSelective(lnRepayScheduleDetail);
							}
							//账单推送过来币港湾只管存库，如果已经存在账单则不进行处理
                         /* else if(!localBillStatus.getCode().equals(schedules.get(0).getStatus())){
                              String scheduleStatus = schedules.get(0).getStatus();
                              if(!LoanStatus.REPAY_SCHEDULE_STATUS_REPAIED.getCode().equals(scheduleStatus) &&
                                      !LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(scheduleStatus)){
                                  //！当本地存在账单数据且云贷为催收中的状态，不做任何处理，以本地状态为准
                                  //！当本地不存在账单数据且云贷为催收中的状态，则插入状态为初始的记录
                                  if(!BillInfo.BillStatus.BILL_STATUS_LATE_NOT.getCode().equals(bill.getStatus())){
                                      LnRepaySchedule lnRepaySchedule = new LnRepaySchedule();
                                      lnRepaySchedule.setId(schedules.get(0).getId());
                                      lnRepaySchedule.setStatus(localBillStatus.getCode());
                                      lnRepaySchedule.setUpdateTime(new Date());
                                      lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepaySchedule);
                                  }
                              }
                          }*/
						}
					}

				});
			}
			
			// 借款协议签章流程，更改为币港湾直接签章, 所以账单推送不再进行签章协议服务调用
            //签章协议下载服务调用
            /*String agreementNo = billInfo.getAgreementNo();
            if(StringUtil.isNotEmpty(agreementNo)){
            	log.info("签章协议服务调用>>>>>>>>>>>" + agreementNo);
                BsUserSealFileExample sealFileExample = new BsUserSealFileExample();
                sealFileExample.createCriteria().andUserSrcEqualTo(PartnerEnum.YUN_DAI_SELF.getCode())
                        .andAgreementNoEqualTo(agreementNo);
                List<BsUserSealFile> sealFiles = bsUserSealFileMapper.selectByExample(sealFileExample);
                
                if(CollectionUtils.isEmpty(sealFiles)){
                	log.info("签章协议服务调用>>>>>>>>>>> 保存协议信息进行签章");
                	BsUserSealFile sealFile = new BsUserSealFile();
                    sealFile.setUpdateTime(new Date());
                    sealFile.setAgreementNo(agreementNo);
                    sealFile.setCreateTime(new Date());
                    sealFile.setSealStatus(SealStatus.UNDOWNLOAD.getCode());
                    sealFile.setSealType(SealBusiness.LOAN_AGREEMENT.getCode());
                    sealFile.setSrcAddress(billInfo.getAgreementUrl());
                    sealFile.setUserId(lnLoan.getLnUserId());
                    sealFile.setUserSrc(PartnerEnum.YUN_DAI_SELF.getCode());
                    sealFile.setRelativeInfo(String.valueOf(lnLoan.getId()));
                    bsUserSealFileMapper.insertSelective(sealFile);
                    //调用签章协议下载服务进行协议签章
        	        SignSeal4DafyLoanProcess process = new SignSeal4DafyLoanProcess();
        	        process.setSignSealService(signSealService);
        	        process.setUserService(userService);
        	        SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
        	        signSealUserInfo.setPdfPath(sealFile.getSrcAddress());
        	        signSealUserInfo.setAgreementNo(agreementNo);
        	        signSealUserInfo.setSealFileId(sealFile.getId());
        	        signSealUserInfo.setLoanId(lnLoan.getPartnerLoanId());
        	        process.setSignSealUserInfo(signSealUserInfo);
        	        List<BsUser4LoanVO>   bsUserList = new ArrayList<BsUser4LoanVO>();
        	        bsUserList = lnLoanRelationMapper.selectBsUserByLoanId(lnLoan.getId());
        	        process.setBsUserList(bsUserList);
        	        process.setDafyNoticeService(dafyNoticeService);
        	        process.setBsUserSealFileService(bsUserSealFileService);
        	        new Thread(process).start();
        	        
                }else if(SealStatus.UNDOWNLOAD.getCode().equals(sealFiles.get(0).getSealStatus())){
                	 //调用签章协议下载服务进行协议签章
                	log.info("签章协议服务调用>>>>>>>>>>> 直接进行协议签章");
                	SignSeal4DafyLoanProcess process = new SignSeal4DafyLoanProcess();
        	        process.setSignSealService(signSealService);
        	        process.setUserService(userService);
        	        SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
        	        signSealUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
        	        signSealUserInfo.setAgreementNo(agreementNo);
        	        signSealUserInfo.setLoanId(lnLoan.getPartnerLoanId());
        	        signSealUserInfo.setSealFileId(sealFiles.get(0).getId());
        	        signSealUserInfo.setPdfPath(sealFiles.get(0).getSrcAddress());
        	        process.setSignSealUserInfo(signSealUserInfo);
        	        List<BsUser4LoanVO>   bsUserList = new ArrayList<BsUser4LoanVO>();
        	        bsUserList = lnLoanRelationMapper.selectBsUserByLoanId(lnLoan.getId());
        	        process.setBsUserList(bsUserList);
        	        process.setDafyNoticeService(dafyNoticeService);
        	        process.setBsUserSealFileService(bsUserSealFileService);
        	        new Thread(process).start();
        	        
                }else if(SealStatus.FAIL.getCode().equals(sealFiles.get(0).getSealStatus())){
	               	 //调用签章协议下载服务进行协议签章
	               	log.info("签章协议服务调用>>>>>>>>>>> 原协议签章失败直接进行协议签章");
	               	SignSeal4DafyLoanProcess process = new SignSeal4DafyLoanProcess();
	       	        process.setSignSealService(signSealService);
	       	        process.setUserService(userService);
	       	        SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
	       	        signSealUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
	       	        signSealUserInfo.setAgreementNo(agreementNo);
	       	        signSealUserInfo.setLoanId(lnLoan.getPartnerLoanId());
	       	        signSealUserInfo.setSealFileId(sealFiles.get(0).getId());
	       	        signSealUserInfo.setPdfPath(sealFiles.get(0).getSrcAddress());
	       	        process.setSignSealUserInfo(signSealUserInfo);
	       	        List<BsUser4LoanVO>   bsUserList = new ArrayList<BsUser4LoanVO>();
	       	        bsUserList = lnLoanRelationMapper.selectBsUserByLoanId(lnLoan.getId());
	       	        process.setBsUserList(bsUserList);
	       	        process.setDafyNoticeService(dafyNoticeService);
	       	        process.setBsUserSealFileService(bsUserSealFileService);
	       	        new Thread(process).start();
               }
            }*/
			
        }
    }
	
}
