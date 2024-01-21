package com.pinting.business.test.mockito;

import com.google.common.collect.Lists;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.vo.DailyAmount4LoanVO;
import com.pinting.business.service.loan.AuthBalanaceQueryService;
import com.pinting.business.test.TestBase;
import com.pinting.business.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

/**
 * Author: lenovo
 * Date: 2016/9/6
 * Time: 14:07
 * Description:
 */
public class UnitTesting extends TestBase{

    @Autowired
    private AuthBalanaceQueryService authBalanaceQueryService;

    @Mock
    private BsSubAccountMapper bsSubAccountMapper;
    @Mock
    private LoanRelationshipService loanRelationshipService;

    @Before
    public void mockBefore() {
        MockitoAnnotations.initMocks(this);
        //mRoleFunctionMapper为mRoleFunctionService中需要mock的对象，functionMapper为mock对象
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(authBalanaceQueryService), "bsSubAccountMapper", bsSubAccountMapper);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(authBalanaceQueryService), "loanRelationshipService", loanRelationshipService);
    }

    @Test
    public void selectTest() {
        //对方法设定预期返回值
    	List<Integer> superUserList = Lists.newArrayList(1,2,3);
    	DailyAmount4LoanVO vo = new DailyAmount4LoanVO();
    	vo.setTerm(1);
    	vo.setSumAmount(100d);
    	Mockito.doReturn(superUserList).when(loanRelationshipService).getSuperUserList();
        Mockito.doReturn(Lists.newArrayList(vo)).when(bsSubAccountMapper).getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, Constants.PRODUCT_TYPE_AUTH, superUserList, null, "yes",Mockito.anyDouble(),null);
        
        
        //执行方法
        Double d = authBalanaceQueryService.getSuperAuthBalance(superUserList);
        System.out.println(d);

        //验证mock方法是否被调用
        Mockito.verify(bsSubAccountMapper).getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, Constants.PRODUCT_TYPE_AUTH, superUserList, null, "yes",Mockito.anyDouble(),null);
        Mockito.verify(loanRelationshipService).getSuperUserList();
    }
}
