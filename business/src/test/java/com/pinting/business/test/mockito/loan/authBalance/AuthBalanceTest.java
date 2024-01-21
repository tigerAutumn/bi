package com.pinting.business.test.mockito.loan.authBalance;

import com.google.common.collect.Lists;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.vo.DailyAmount4LoanVO;
import com.pinting.business.service.loan.AuthBalanaceQueryService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * AUTH账户余额查询AuthBalanaceQueryService
 * 站岗资金保留天数天数查数据库
 * @author bianyatian
 * @2016-9-10 下午1:58:07
 */
public class AuthBalanceTest extends TestBase{

    @Autowired
    private AuthBalanaceQueryService authBalanaceQueryService;

    @Mock
    private BsSubAccountMapper bsSubAccountMapper;
    @Mock
    private LoanRelationshipService loanRelationshipService;

    @Before
    public void mockBefore() {
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(authBalanaceQueryService), "bsSubAccountMapper", bsSubAccountMapper);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(authBalanaceQueryService), "loanRelationshipService", loanRelationshipService);
    }

    @Test
    public void selectTest() {
        //对方法设定预期返回值
    	List<Integer> superUserList = Lists.newArrayList(1,2,3);
    	Mockito.doReturn(superUserList).when(loanRelationshipService).getSuperUserList();
        
    	//查询所有非超级理财人的AUTH账户余额
    	Date now =new Date();
    	String minInterestBeginDate =DateUtil.formatYYYYMMDD( DateUtil.addDays(now, -5));
    	
    	List<DailyAmount4LoanVO> normalList = new ArrayList<DailyAmount4LoanVO>();
    	DailyAmount4LoanVO normalVO = new DailyAmount4LoanVO();
    	normalVO.setTerm(3);
    	normalVO.setSumAmount(10000d);
    	normalList.add(normalVO);
    	DailyAmount4LoanVO normalVO1 = new DailyAmount4LoanVO();
    	normalVO1.setTerm(6);
    	normalVO1.setSumAmount(20000d);
    	normalList.add(normalVO1);
    	Mockito.doReturn(normalList).when(bsSubAccountMapper).getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, Constants.PRODUCT_TYPE_AUTH, superUserList, minInterestBeginDate, "no",Mockito.anyDouble(),null);
    	//查询超级理财人的AUTH账户余额
    	DailyAmount4LoanVO vo = new DailyAmount4LoanVO();
    	vo.setTerm(3);
    	vo.setSumAmount(100d);
    	Mockito.doReturn(Lists.newArrayList(vo)).when(bsSubAccountMapper).getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, Constants.PRODUCT_TYPE_AUTH, superUserList, null, "yes",Mockito.anyDouble(),null);
        
        //执行方法
        Double superAmount = authBalanaceQueryService.getSuperAuthBalance(superUserList);
       
        Double normalAmount = authBalanaceQueryService.getNormalAuthBalance(3,5,superUserList,null);
        System.out.println(superAmount);
        System.out.println(normalAmount);
        //验证mock方法是否被调用
        //当一个方法需要执行多次的时候
        Mockito.verify(loanRelationshipService, Mockito.atLeast(2)).getSuperUserList();
        Mockito.verify(bsSubAccountMapper).getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, Constants.PRODUCT_TYPE_AUTH, superUserList, minInterestBeginDate, "no",Mockito.anyDouble(),null);
        Mockito.verify(bsSubAccountMapper).getSumBalanceByProductType(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN, Constants.PRODUCT_TYPE_AUTH, superUserList, null, "yes",Mockito.anyDouble(),null);
    }
}
