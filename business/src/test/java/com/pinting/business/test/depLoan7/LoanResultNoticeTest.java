package com.pinting.business.test.depLoan7;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_LoanResultNotice;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

/**
 * 
 * @author zhangpeng
 * 测试"放款结果通知"接口
 */

public class LoanResultNoticeTest extends TestBase {
	
	@Autowired
	private  DepLoan7NoticeService depLoan7NoticeService;

	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;

  	@Test
    public void loanResultNoticeTest() {
		// 获取借款服务费率（100=1%）
		BsSysConfig loanServiceRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_SERVICE_RATE_SEVEN_DAI);

  		B2GReqMsg_DepLoan7Notice_LoanResultNotice req = new B2GReqMsg_DepLoan7Notice_LoanResultNotice();
  		req.setBgwOrderNo("a3d40eae822104680571");
  		req.setOrderNo("2018013100000102");
  		req.setLoanId("2018013100000102");
  		req.setChannel("HFBANK");
  		req.setFinishTime(new Date());
  		req.setResultCode("SUCCESS");
  		req.setResultMsg("标的出账成功");
  		ArrayList<HashMap<String, Object>> list = new ArrayList<>();
  		HashMap<String, Object> map = new HashMap<>();
  		map.put("lenderName", "史玉隆");
  		map.put("lenderIdcard", "520203199012161817");
  		map.put("investAmount", 3000.0);
  		map.put("mobile", "15868157902");
  		list.add(map);
  		req.setLenders(list);
		req.setLoanServiceRate(StringUtils.isNotBlank(loanServiceRateConfig.getConfValue()) ? MoneyUtil.formatEnlarge(loanServiceRateConfig.getConfValue()).intValue() : null);
  		depLoan7NoticeService.noticeLoan(req);
    }
}
