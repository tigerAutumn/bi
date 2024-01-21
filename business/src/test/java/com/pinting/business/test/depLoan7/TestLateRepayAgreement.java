package com.pinting.business.test.depLoan7;

import com.itextpdf.text.DocumentException;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.model.LnCompensateDetail;
import com.pinting.business.service.loan.LateRepayAgreementService;
import com.pinting.business.test.TestBase;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.util.DateUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Author:      shh
 * Date:        2017/12/26
 * Description: 云贷、7贷生成代偿协议
 */
public class TestLateRepayAgreement extends TestBase {

    private Logger logger = LoggerFactory.getLogger(TestLateRepayAgreement.class);

    @Autowired
    private LateRepayAgreementService lateRepayAgreementService;

    //云贷、7贷生成代偿协议
    @Test
    public void createAgreementPdfTest() {
        List<LnCompensateDetail> detailList = new ArrayList<LnCompensateDetail>();
        LnCompensateDetail detail = new LnCompensateDetail();
        detail.setId(7745);
        detail.setCompensateId(862);
        detail.setPartnerUserId("1081611170013678");
        detail.setPartnerLoanId("000101321");
        detail.setPartnerRepayId("1215");
        detail.setRepaySerial(1);
        detail.setRepayType("NORMAL");
        detail.setTotal(6.60d);
        detail.setPrincipal(0d);
        detail.setInterest(6.6d);
        detail.setPrincipalOverdue(0d);
        detail.setInterestOverdue(null);
        detail.setStatus("SUCC");
        detail.setCreateTime(DateUtil.parseDateTime("2017-12-13 14:59:59"));
        detail.setUpdateTime(DateUtil.parseDateTime("2017-12-13 15:00:05"));
        detailList.add(detail);

        // 代偿签章协议生成走定时任务：LateAgreementGenerateTask
        // 代偿签章协议通知走定时任务：LateAgreementNotifyTask
    }
    
	public static void main(String[] args) {
    	String urlString = "https://mtest.bigangwan.com/micro2.0/agreement/receiptConfirmServiceFee4Pdf?orderNo=7dai1021043&partnerEnum=7_DAI_SELF";
    	
    	try {
			ITextPdfUtil.createHtm2Pdf(urlString, "D://self/a.pdf", "收款确认函服务费", "收款确认函服务费", "1212");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
