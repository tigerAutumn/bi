package com.pinting.business.test.mockito.finance;

import com.pinting.business.accounting.finance.model.QuickPayResultInfo;
import com.pinting.business.accounting.finance.service.UserTopUpService;
import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by babyshark on 2016/9/13.
 */
public class TopUpAspectTest extends TestBase {
    @Autowired
    private UserTopUpService userTopUpService;//201609101506251022904002687889

    @Before
    public void mockBefore() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void superTopUp(){

        QuickPayResultInfo resultInfo = new QuickPayResultInfo();
        resultInfo.setStatus(OrderStatus.FAIL.getCode());
        resultInfo.setOrderId("201607310027041756001685995");
        resultInfo.setAmount(500d);
        resultInfo.setMpOrderId("XXX1506251022904002687889");
        userTopUpService.notify(resultInfo);

    }


}
