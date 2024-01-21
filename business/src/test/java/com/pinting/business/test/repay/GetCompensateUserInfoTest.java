package com.pinting.business.test.repay;

import java.util.Date;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.model.vo.BsUserCompensateVO;
import com.pinting.business.test.TestBase;

public class GetCompensateUserInfoTest extends TestBase  {

	@Autowired
	private DepFixedRepayPaymentService depFixedRepayPaymentService;
	
	
	@Test
    public void getCompensateUserInfoTest() {
		BsUserCompensateVO vo = new BsUserCompensateVO();
    	vo = depFixedRepayPaymentService.compensaterInfo(new Date(),"7_DAI_SELF");
		System.out.println(vo.getId());
    }
	
	
  	
  	public static void main(String[] args) {
  		String compensateUserInfo = "{\"YUN_DAI\": {\"SEPARATE_DATE\": \"2018-03-15\",\"COMPENSATE_USER_ID\": \"1035895|2000055\"},\"7_DAI\": {\"SEPARATE_DATE\": \"2018-03-15\",\"COMPENSATE_USER_ID\": \"1037195|2000055\"}}";
  		JSONObject jsonObject = JSONObject.fromObject(compensateUserInfo);
  		JSONObject yunDaiInfo=  (JSONObject)jsonObject.get("YUN_DAI");
  		System.out.println(yunDaiInfo.get("SEPARATE_DATE"));
	}
}
