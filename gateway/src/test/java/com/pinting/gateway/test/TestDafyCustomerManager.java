package com.pinting.gateway.test;

import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.gateway.business.in.facade.CustomerFacade;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByIdReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByIdResModel;
import com.pinting.gateway.dafy.out.service.SendDafyService;
import org.junit.Test;
/**
 * 达飞客户经理相关
 * @author Dragon & cat
 * @date 2016-11-23
 */
public class TestDafyCustomerManager extends TestBase{

    @Autowired
    private CustomerFacade customerFacade;
    @Autowired
    private SendDafyService sendDafyService;
    
    
    /**
     * 根据客户经理ID查询达飞客户经理信息
     */
	@Test
    public void dafyQueryUserByIdTest() {
        QueryUserInfoByIdReqModel req = new QueryUserInfoByIdReqModel();
        req.setlUserId("1448645");
        QueryUserInfoByIdResModel res = sendDafyService.queryUserInfoById(req);
        System.out.println(res.getCode());
    }
        

	
}
