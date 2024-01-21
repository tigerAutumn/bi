package com.pinting.business.test.dao;

import com.google.zxing.pdf417.encoder.PDF417;
import com.itextpdf.text.DocumentException;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.ProtocolSealVO;
import com.pinting.business.accounting.loan.service.DepFixedLoanPaymentService;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.vo.DepGuideVO;
import com.pinting.business.service.protocol.LoanAgreementSignSealService;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.HFBankDepSiteService;
import com.pinting.business.test.TestBase;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;

import net.sf.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestCheng extends TestBase {

	private Logger logger = LoggerFactory.getLogger(TestCheng.class);
	private DepFixedLoanPaymentService depFixedLoanPaymentService;

	@Autowired
	private HFBankDepSiteService hfBankDepSiteService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private LoanAgreementSignSealService loanAgreementSignSealService;

	//@Test
	public void test() {
		String agreementNo = "partnerLoanId7306";
		LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(7306);
		List<LnLoanRelation> loanRelations = new ArrayList<>();
		loanAgreementSignSealService.protocolSeal(PartnerEnum.SEVEN_DAI_SELF, new ProtocolSealVO(agreementNo, lnLoan, loanRelations));
		try {
			Thread.sleep(1000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void pdf(){
        String buyHtml = "";
        String buyPdfPath = "";
        String  dateString = DateUtil.formatDateTime(new Date(), "yyyyMM");
        buyPdfPath = GlobEnvUtil.get("self.loan.7dai.seal.path") +"/quartetAgree_"+dateString+"/"+"xieyibianhao001"+".pdf";
        buyHtml = GlobEnvUtil.get("cfca.seal.loanAgreement7.pdfSrcHtml") + "?partnerLoanId=longmaotestloanid20180305001";
        try {
			ITextPdfUtil.createHtm2Pdf(buyHtml, buyPdfPath, "借款协议", "借款协议", "xieyibianhao001");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(buyPdfPath);
	}
	
	public static void main(String[] args) {
		System.out.println(new Date().getTime());
	}

}
