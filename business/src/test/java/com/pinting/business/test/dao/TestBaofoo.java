package com.pinting.business.test.dao;

import com.pinting.business.accounting.finance.service.UserCardOperateService;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ResMsg_UserBalance_Withdraw;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.test.TestBase;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.out.service.BaoFooTransportService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Author:      cyb
 * Date:        2016/8/27
 * Description:
 */
public class TestBaofoo extends TestBase {

    @Autowired
    private UserCardOperateService userCardOperateService;

//    @Test
    public void preBindCard() {
        String userName = "程裕兵";
        String idCard = "33012719930116511X";
        String cardNo = "6228480322465194114";
        String mobile = "15868150199";
        String bankId = "3";   // 农业银行
        String userId = "1022903";
        String terminalType = "1";
        String orderNo = userCardOperateService.preBindCard(userName, idCard, cardNo, mobile, bankId, userId, terminalType);
        System.out.println(orderNo);
    }

//    @Test
    public void bindCard() {
//        String bgwOrderNo, String smsCode, String userId
        String bgwOrderNo = "201608271344511022903048478289";
        String smsCode = "032556";
        String userId = "1022903";
        userCardOperateService.bindCard(bgwOrderNo, smsCode, userId);
    }

    public void preTopUp() {
        int userId = 1022903;
        double amount = 10d;
        String cardNo = "6228480322465194114";
        String mobile = "15868150199";
        String idCard = "33012719930116511X";
        String userName = "程裕兵";
        String bankName = "农业银行";
        int bankId = 3;   // 农业银行
        int terminalType = 1;
        ResMsg_RegularBuy_Order res = new ResMsg_RegularBuy_Order();
        //baoFooService.preTopUp(userId, amount, cardNo, mobile, idCard, userName, bankName, bankId, terminalType, res);
    }

//    @Test
    public void topUp() {
        ReqMsg_RegularBuy_Order req = new ReqMsg_RegularBuy_Order();
        ResMsg_RegularBuy_Order res = new ResMsg_RegularBuy_Order();
        req.setOrderNo("201608271459121022903050155658");
        req.setAmount(10d);
        req.setUserId(1022903);
        req.setVerifyCode("219936");
        //baoFooService.confirmTopUp(req, res);
    }

    @Test
    public void withdraw() {
        ReqMsg_UserBalance_Withdraw req = new ReqMsg_UserBalance_Withdraw();
        ResMsg_UserBalance_Withdraw res = new ResMsg_UserBalance_Withdraw();
        req.setAmount(10d);
        req.setPassword("123123");
        req.setTerminalType(1);
        req.setUserId(1022903);
        //userBalanceService.withdraw(req, res);
    }

    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Test
    public void loan() {
        B2GReqMsg_BaoFooQuickPay_Pay4Trans req1 = new B2GReqMsg_BaoFooQuickPay_Pay4Trans();
        B2GResMsg_BaoFooQuickPay_Pay4Trans res = new B2GResMsg_BaoFooQuickPay_Pay4Trans();
        req1.setTo_acc_no("6214835892879857");
        req1.setTo_acc_name("袁其亮");
        req1.setTo_bank_name("招商银行");
        req1.setTrans_money("1000.00");
        req1.setTrans_no("35022534164547590");
        req1.setTrans_card_id("身份证号");
        req1.setTrans_mobile("预留手机号");
        baoFooTransportService.pay4Trans(req1);
    }

    @Autowired
    private LoanUserService loanUserService;
    @Test
    public void calc(){

        loanUserService.calTotalRepay(70, 1);

    }

}
