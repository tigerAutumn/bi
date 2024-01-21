package com.pinting.business.test.depLoan7;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.DocumentException;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedLoanRelationshipService;
import com.pinting.business.dayend.task.SignServiceTask;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealPdfPathEnum;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.dafy.model.Agreements;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransferInfo;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransfers;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_AgreementNotice;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

public class AgreementNoticeTest extends TestBase {
	@Autowired
	private  DepLoan7NoticeService depLoan7NoticeService;
	@Autowired
	private  DepFixedLoanRelationshipService depFixedLoanRelationshipService;
	@Autowired
	private SignServiceTask signServiceTask;
	
  	@Test
    public void agreementNotice() {
  		B2GReqMsg_DepLoan7Notice_AgreementNotice req = new B2GReqMsg_DepLoan7Notice_AgreementNotice();
  		req.setOrderNo("2018012300000001016");

  		List<Agreements> list = new ArrayList<Agreements>();
  		Agreements agreements = new Agreements();
  		agreements.setLoanId("2018012300000001");
  		agreements.setServiceFeeConfirmUrl("d:/");
  		agreements.setDebtTransConfirmUrl("d:/");
  		agreements.setDebtTransNoticesUrl("d:/");
  		agreements.setBorrowerName("史玉隆");
  		agreements.setBorrowerIdCard("520203199012161817");

  		List<DebtTransferInfo> debtTransferInfos = new ArrayList<>();
  		DebtTransferInfo debtTransferInfo = new DebtTransferInfo();
  		debtTransferInfo.setOutUserName("蔡飞虎");
  		debtTransferInfo.setOutIdCard("420682199207143057");
  		debtTransferInfo.setTransAmount((long)3000);
  		debtTransferInfos.add(debtTransferInfo);

  		List<DebtTransfers> debtTransferList = new ArrayList<>();
  		DebtTransfers  debtTransfers = new DebtTransfers();
  		debtTransfers.setDebtTransferUrl("d:/");
  		debtTransferList.add(debtTransfers);
  		agreements.setDebtTransferInfo(debtTransferInfos);
  		agreements.setDebtTransfers(debtTransferList);

  		list.add(agreements);
  		req.setAgreements(list);

		depLoan7NoticeService.agreementNotice(req);
  	}

//  	/**
//	 * 云贷、7贷债权转让协议PDF签章
//  	 */
//	@Test
//	public void creditTransferAgreement() throws IOException, DocumentException {
//		depFixedLoanRelationshipService.creditTransferAgreement( 1205, 1000247, PartnerEnum.YUN_DAI_SELF);
//
//		// 云贷自主放款
////		String pdfHtml = "http://localhost:8080/site/micro2.0/agreement/hfCustodyClaimsAgreementInitPdf?loanRelationId="
////				+ 1205;
//		String pdfHtml = "http://localhost:8080/site/micro2.0/agreement/entrancePdf?loanRelationId="
//				+ 1205;
//
//		BsUserSealFile file = new BsUserSealFile();
//		file.setAgreementNo("27100000" + 1205);
//		file.setSrcAddress(pdfHtml);
//		file.setSealStatus(SealStatus.UNDOWNLOAD.getCode());
//		file.setSealType(SealBusiness.DEBT_TRANSFER.getCode());
//		file.setUserId(1205);
//		file.setUserSrc(SealPdfPathEnum.BIGANGWAN.getCode());
//
//		String htmlUrl = file.getSrcAddress();
//		//创建pdf文件,初始路径+签章类型（资产方）+业务+日期(年月)+文件名（签章类型+文件id）
//		String partner = StringUtil.isEmpty(file.getUserSrc()) ? SealPdfPathEnum.BIGANGWAN.getName()
//				: SealPdfPathEnum.getEnumByCode(file.getUserSrc()).getName();
//		String pdfPath = partner+"/"+file.getSealType()
//				+"/"+ DateUtil.formatDateTime(file.getCreateTime(), "yyyyMM")+"/"+file.getUserId()+"/"
//				+file.getAgreementNo()+".pdf";
//
//		ITextPdfUtil.createHtm2Pdf(htmlUrl, GlobEnvUtil.get("sign.task.pdfPath")+"/"+ pdfPath, SealBusiness.DEBT_TRANSFER.getDescription(), SealBusiness.DEBT_TRANSFER.getDescription(),
//				file.getAgreementNo());
//	}
}
