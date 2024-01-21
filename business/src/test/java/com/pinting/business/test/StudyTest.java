package com.pinting.business.test;

import com.itextpdf.text.DocumentException;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.util.GlobEnvUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Calendar;

public class StudyTest extends TestBase {

	private Logger log = LoggerFactory.getLogger(ShenTest.class);

	// 快捷、网银充值成功，生成《支付协议》和《授权委托书》协议pdf
	@Test
	public void pdf4TopUp(){
		String topUpHtml = "";
		String topUpPdfPath = "";
		topUpPdfPath = GlobEnvUtil.get("cfca.agreement.pdfSrcPath") +"/topUpAgreement"+".pdf";
		topUpHtml = GlobEnvUtil.get("cfca.seal.rechargeDelegateAuthorization.pdfSrcHtml") + "?userId=1001605";
		try {
			ITextPdfUtil.createHtm2Pdf(topUpHtml, topUpPdfPath, "《支付协议》和《授权委托书》", "《支付协议》和《授权委托书》", "1001605");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		log.info("===================生成《支付协议》和《授权委托书》协议pdf路径===================" + topUpPdfPath);
	}

	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance(); // 获取当天指定点上的时间
		cal.set(Calendar.HOUR_OF_DAY, 0); // 时
		cal.set(Calendar.MINUTE, 0); // 分
		cal.set(Calendar.SECOND, 0); // 秒
		cal.set(Calendar.MILLISECOND, 0); // 毫秒
//		System.out.println(cal.getTimeInMillis()/1000);
		System.out.println(cal.getTime());
	}

}

