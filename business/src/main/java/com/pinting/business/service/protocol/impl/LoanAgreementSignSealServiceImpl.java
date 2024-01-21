package com.pinting.business.service.protocol.impl;

import java.util.ArrayList;
import java.util.List;

import com.pinting.gateway.out.service.loan.DafyNoticeService;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.ProtocolSealVO;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.accounting.service.impl.process.LoanAgreementSignSealProcess;
import com.pinting.business.accounting.service.impl.process.SignSeal4BorrowServicesZsdProcess;
import com.pinting.business.accounting.service.impl.process.SignSeal4ZsdLoanAgreementProcess;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.vo.BsUser4LoanVO;
import com.pinting.business.model.vo.SignSealUserInfoVO;
import com.pinting.business.service.protocol.LoanAgreementSignSealService;
import com.pinting.core.util.GlobEnvUtil;
@Service
public class LoanAgreementSignSealServiceImpl implements
		LoanAgreementSignSealService {

    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private SignSealService signSealService;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    /* 通知7贷的服务 */
    @Autowired
    private DepLoan7NoticeService depLoan7NoticeService;
    /* 通知云贷的服务 */
    @Autowired
    private DafyNoticeService dafyNoticeService;
	
    /**
     * 协议签章
     * @param partner       合作方
     * @param protocolSeal  相关信息
     */
	@Override
	public void protocolSeal(PartnerEnum partner, ProtocolSealVO protocolSeal) {
        LnLoan lnLoan = protocolSeal.getLnLoan();
        List<LnLoanRelation> relationList = protocolSeal.getLoanRelations();
        LnUser loanUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());
        if (loanUser != null) {
            List<BsUser4LoanVO> fnUserList = new ArrayList<>();
            SignSealUserInfoVO loanUserInfo = new SignSealUserInfoVO();
            loanUserInfo.setIdCard(loanUser.getIdCard());
            loanUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
            loanUserInfo.setUserId(loanUser.getId());
            loanUserInfo.setUserName(loanUser.getUserName());
            loanUserInfo.setOrderNo(lnLoan.getPartnerLoanId());
            loanUserInfo.setLoanId(lnLoan.getPartnerLoanId());
            loanUserInfo.setMoney(String.valueOf(lnLoan.getApproveAmount()));
            loanUserInfo.setAgreementNo(protocolSeal.getAgreementNo());
            switch (partner) {
                case ZSD:
                    //借款咨询与服务协议签章
                    SignSeal4BorrowServicesZsdProcess process = new SignSeal4BorrowServicesZsdProcess();
                    process.setSignSealService(signSealService);
                    SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
                    signSealUserInfo.setIdCard(loanUser.getIdCard());
                    signSealUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
                    signSealUserInfo.setUserId(loanUser.getId());
                    signSealUserInfo.setUserName(loanUser.getUserName());
                    signSealUserInfo.setOrderNo(lnLoan.getPartnerLoanId());
                    signSealUserInfo.setMoney(String.valueOf(lnLoan.getApproveAmount()));
                    process.setSignSealUserInfo(signSealUserInfo);
                    process.setLnLoan(lnLoan);
                    new Thread(process).start();

                    for (LnLoanRelation relation : relationList) {
                        BsUser4LoanVO vo = new BsUser4LoanVO();
                        BsUser bsUser = bsUserMapper.selectByPrimaryKey(relation.getBsUserId());
                        vo.setUserId(bsUser.getId());
                        vo.setUserIdCardNo(bsUser.getIdCard());
                        vo.setUserName(bsUser.getUserName());
                        fnUserList.add(vo);
                    }

                    //借款协议 签章
                    SignSeal4ZsdLoanAgreementProcess loanAgreementProcess = new SignSeal4ZsdLoanAgreementProcess();
                    loanAgreementProcess.setSignSealUserInfo(loanUserInfo);
                    loanAgreementProcess.setLnLoan(lnLoan);
                    loanAgreementProcess.setBsUserList(fnUserList);
                    loanAgreementProcess.setSignSealService(signSealService);
                    new Thread(loanAgreementProcess).start();
                    break;
                case YUN_DAI_SELF:
                    //云贷借款协议 签章
                    fnUserList = lnLoanRelationMapper.selectBsUserByLoanId(lnLoan.getId());
                    new Thread(new LoanAgreementSignSealProcess(
                            signSealService, loanUserInfo, lnLoan, fnUserList, partner, depLoan7NoticeService, dafyNoticeService)).start();
                    break;
                case SEVEN_DAI_SELF:
                    //7贷借款协议 签章
                    fnUserList = lnLoanRelationMapper.selectBsUserByLoanId(lnLoan.getId());
                    new Thread(new LoanAgreementSignSealProcess(
                            signSealService, loanUserInfo, lnLoan, fnUserList, partner, depLoan7NoticeService, dafyNoticeService)).start();
                    break;
                default:
                    break;
            }
        }
    }
	

}
