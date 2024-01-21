package com.pinting.business.test.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.accounting.service.impl.process.SignSeal4BuyProcess;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.SignSealUserInfoVO;
import com.pinting.business.service.site.UserService;
import com.pinting.business.test.TestBase;
import com.pinting.core.util.GlobEnvUtil;

public class TestUserSeal extends TestBase{

	@Autowired
	private UserService userService;
	@Autowired
	private SignSealService signSealService;
	
	@Test
	public void myTest(){
		
		//协议签章
		BsUser user = userService.findUserById(1000230);
        SignSeal4BuyProcess process = new SignSeal4BuyProcess();
        process.setSignSealService(signSealService);
        process.setUserService(userService);
        SignSealUserInfoVO signSealUserInfo = new SignSealUserInfoVO();
        signSealUserInfo.setIdCard(user.getIdCard());
        signSealUserInfo.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
        signSealUserInfo.setUserId(user.getId());
        signSealUserInfo.setUserName(user.getUserName());
        signSealUserInfo.setOrderNo(String.valueOf(65192));
        signSealUserInfo.setMoney(String.valueOf(100000));
        process.setSignSealUserInfo(signSealUserInfo);
        //new Thread(process).start();
        process.run();

	}
	
}
