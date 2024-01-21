package com.pinting.business.accounting.loan.service.impl.process;

import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.model.LnRepay;

/**
 * Created by 剑钊
 *
 * @2016/9/20 15:59.
 */
public class RepayProcess implements Runnable{

    private LnRepay repay;

    private RepayPaymentService repayPaymentService;

    private String errMsg;

    @Override
    public void run() {
        /*try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        repayPaymentService.notifyPartner(repay,errMsg);
    }

    public LnRepay getRepay() {
        return repay;
    }

    public void setRepay(LnRepay repay) {
        this.repay = repay;
    }

    public RepayPaymentService getRepayPaymentService() {
        return repayPaymentService;
    }

    public void setRepayPaymentService(RepayPaymentService repayPaymentService) {
        this.repayPaymentService = repayPaymentService;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
