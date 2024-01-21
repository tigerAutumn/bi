package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.ProtocolSealVO;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.accounting.service.impl.process.SignSeal4BorrowServicesZanProcess;
import com.pinting.business.accounting.service.impl.process.SignSeal4BorrowServicesZsdProcess;
import com.pinting.business.accounting.service.impl.process.SignSeal4DafyLoanProcess;
import com.pinting.business.accounting.service.impl.process.SignSeal4LoanAgreementProcess;
import com.pinting.business.accounting.service.impl.process.SignSeal4DepLoan7Process;
import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.dao.BsUserSignSealMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.BsUserSealFileExample;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanExample;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.LnLoanRelationExample;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.vo.BsUser4LoanVO;
import com.pinting.business.model.vo.SignSealUserInfoVO;
import com.pinting.business.service.loan.BsUserSealFileService;
import com.pinting.business.service.manage.SignRepeatSendService;
import com.pinting.business.service.protocol.LoanAgreementSignSealService;
import com.pinting.business.service.site.UserService;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

/**
 * 管理台签章协议进行重发签章实现
 * @project business
 * @title SignRepeatSendServiceImpl.java
 * @author Dragon & cat
 * @date 2017-5-26
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class SignRepeatSendServiceImpl  implements SignRepeatSendService{
	private Logger log = LoggerFactory.getLogger(SignRepeatSendServiceImpl.class);
    @Autowired
    private SignSealService signSealService;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;
	@Autowired
	private UserService userService;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Autowired
	private DafyNoticeService	dafyNoticeService;
	@Autowired
	private DepLoan7NoticeService	depLoan7NoticeService;
	@Autowired
	private BsUserSealFileService bsUserSealFileService;
    @Autowired
    private LoanAgreementSignSealService  loanAgreementSignSealService;
	
	
	@Override
	public void signBorrowServicesZan(Integer id) {
		
		
		BsUserSealFile bsUserSealFile = bsUserSealFileMapper.selectByPrimaryKey(id);
		LnLoan lnLoan = null;
		if ("BORROW_SERVICES".equals(bsUserSealFile.getSealType())) {
			log.info("签章重发：类型为BORROW_SERVICES，id=" + id);
	        LnLoanExample exampleLoan = new LnLoanExample();
	        exampleLoan.createCriteria().andPartnerLoanIdEqualTo(bsUserSealFile.getRelativeInfo());
	        List<LnLoan> lnLoans = lnLoanMapper.selectByExample(exampleLoan);
	        if(CollectionUtils.isEmpty(lnLoans)){
	            throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND,"对应借款信息不存在");
	        }
	        lnLoan = lnLoans.get(0);
		}else {
			throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND,"非赞分期出借协议");
		}
		 
        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
        SignSeal4BorrowServicesZanProcess process = new SignSeal4BorrowServicesZanProcess();
	    process.setSignSealService(signSealService);
	    SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
	    signSealUserInfo.setIdCard(lnUser.getIdCard());
	    signSealUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
	    signSealUserInfo.setUserId(lnUser.getId());
	    signSealUserInfo.setUserName(lnUser.getUserName());
	    signSealUserInfo.setOrderNo(lnLoan.getPartnerLoanId());
	    signSealUserInfo.setMoney(String.valueOf(lnLoan.getApproveAmount()));
	    signSealUserInfo.setSealFileId(id);
	    process.setSignSealUserInfo(signSealUserInfo);
	    process.setLnLoan(lnLoan);
	    new Thread(process).start();

	}


	@Override
	public List<BsUserSealFile> querySignRepeatData(String type,Integer start ,Integer numPerPage) {
		List<BsUserSealFile> userSealFiles = bsUserSealFileMapper.querySignRepeatData( type, start , numPerPage);
		return userSealFiles;
	}


	@Override
	public Integer countSignRepeatData(String type) {
		Integer count = bsUserSealFileMapper.countSignRepeatData(type);
		return count;
	}


	@Override
	public void signLoanAgreementYun(Integer id) {
		BsUserSealFile bsUserSealFile = bsUserSealFileMapper.selectByPrimaryKey(id);
		LnLoan lnLoan = null;
		if ("LOAN_AGREEMENT".equals(bsUserSealFile.getSealType())) {
			log.info("签章重发：类型为LOAN_AGREEMENT，id=" + id);
//	        LnLoanExample exampleLoan = new LnLoanExample();
//	        exampleLoan.createCriteria().andPartnerLoanIdEqualTo(bsUserSealFile.getRelativeInfo());
			lnLoan = lnLoanMapper.selectByPrimaryKey(Integer.parseInt(bsUserSealFile.getRelativeInfo()));
	        if(lnLoan == null){
	            throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND,"对应借款信息不存在");
	        }
	        
	       
		}else {
			throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND,"非云贷四方协议");
		}
		
    	SignSeal4DafyLoanProcess process = new SignSeal4DafyLoanProcess();
        process.setSignSealService(signSealService);
        process.setUserService(userService);
        SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
        signSealUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
        signSealUserInfo.setAgreementNo(bsUserSealFile.getAgreementNo());
        signSealUserInfo.setLoanId(lnLoan.getPartnerLoanId());
        signSealUserInfo.setSealFileId(bsUserSealFile.getId());
        signSealUserInfo.setPdfPath(bsUserSealFile.getSrcAddress());
        process.setSignSealUserInfo(signSealUserInfo);
        List<BsUser4LoanVO>   bsUserList = new ArrayList<BsUser4LoanVO>();
        bsUserList = lnLoanRelationMapper.selectBsUserByLoanId(lnLoan.getId());
        process.setBsUserList(bsUserList);
        process.setDafyNoticeService(dafyNoticeService);
        process.setBsUserSealFileService(bsUserSealFileService);
        new Thread(process).start();
	}

	@Override
	public void signBorrowServicesZsd(Integer id) {
		BsUserSealFile bsUserSealFile = bsUserSealFileMapper.selectByPrimaryKey(id);
		LnLoan lnLoan = null;
		if ("ZSD_BORROW_SERVICES".equals(bsUserSealFile.getSealType())) {
			log.info("签章重发：类型为ZSD_BORROW_SERVICES，id=" + id);
	        LnLoanExample exampleLoan = new LnLoanExample();
	        exampleLoan.createCriteria().andPartnerLoanIdEqualTo(bsUserSealFile.getRelativeInfo());
	        List<LnLoan> lnLoans = lnLoanMapper.selectByExample(exampleLoan);
	        if(CollectionUtils.isEmpty(lnLoans)){
	            throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND, "对应借款信息不存在");
	        }
	        lnLoan = lnLoans.get(0);
		}else {
			throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND, "非赞时贷出借协议");
		}
		 
        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
        SignSeal4BorrowServicesZsdProcess process = new SignSeal4BorrowServicesZsdProcess();
	    process.setSignSealService(signSealService);
	    SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
	    signSealUserInfo.setIdCard(lnUser.getIdCard());
	    signSealUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
	    signSealUserInfo.setUserId(lnUser.getId());
	    signSealUserInfo.setUserName(lnUser.getUserName());
	    signSealUserInfo.setOrderNo(lnLoan.getPartnerLoanId());
	    signSealUserInfo.setMoney(String.valueOf(lnLoan.getApproveAmount()));
	    signSealUserInfo.setSealFileId(id);
	    process.setSignSealUserInfo(signSealUserInfo);
	    process.setLnLoan(lnLoan);
	    new Thread(process).start();
	}

	@Override
	public void signLoanAgreement7(Integer id) {
		BsUserSealFile bsUserSealFile = bsUserSealFileMapper.selectByPrimaryKey(id);
		LnLoan lnLoan = null;
		if ("LOAN_AGREEMENT".equals(bsUserSealFile.getSealType())) {
			log.info("签章重发：类型为LOAN_AGREEMENT，id=" + id);
			lnLoan = lnLoanMapper.selectByPrimaryKey(Integer.parseInt(bsUserSealFile.getRelativeInfo()));
	        if(lnLoan == null){
	            throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND,"对应借款信息不存在");
	        }
	       
		}else {
			throw new PTMessageException(PTMessageEnum.LOAN_DATA_NOT_FOUND,"非七贷四方协议");
		}
		
		SignSeal4DepLoan7Process process = new SignSeal4DepLoan7Process();
        process.setSignSealService(signSealService);
        process.setUserService(userService);
        SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
        signSealUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
        signSealUserInfo.setAgreementNo(bsUserSealFile.getAgreementNo());
        signSealUserInfo.setLoanId(lnLoan.getPartnerLoanId());
        signSealUserInfo.setSealFileId(bsUserSealFile.getId());
        signSealUserInfo.setPdfPath(bsUserSealFile.getSrcAddress());
        signSealUserInfo.setPartnerCode(bsUserSealFile.getUserSrc());
        process.setSignSealUserInfo(signSealUserInfo);
        List<BsUser4LoanVO>   bsUserList = new ArrayList<BsUser4LoanVO>();
        bsUserList = lnLoanRelationMapper.selectBsUserByLoanId(lnLoan.getId());
        process.setBsUserList(bsUserList);
        process.setDepLoan7NoticeService(depLoan7NoticeService);
        process.setBsUserSealFileService(bsUserSealFileService);
        new Thread(process).start();
		
	}
}
