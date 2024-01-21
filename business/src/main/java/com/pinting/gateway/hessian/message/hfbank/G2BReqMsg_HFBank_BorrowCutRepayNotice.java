package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.hfbank.model.BorrowCutRepayPlatcust;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description:
 */
public class G2BReqMsg_HFBank_BorrowCutRepayNotice extends ReqMsg {

    private static final long serialVersionUID = 101719015465344479L;

    /* 总金额 */
    @NotNull(message="amt为空")
    private Double amt;
    /* 银行卡 */
    @NotEmpty(message="bank_no为空")
    private String bank_no;
    /* 1-入账成功  2-入账失败 */
    @NotEmpty(message="code为空")
    private String code;
    /* 平台客户入账列表 */
    @NotNull(message="platcustList为空")
    private List<BorrowCutRepayPlatcust> platcustList;
    /* 平台编号 */
    @NotEmpty(message="plat_no为空")
    private String plat_no;
    /* 订单号 */
    @NotEmpty(message="order_no为空")
    private String order_no;

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getBank_no() {
        return bank_no;
    }

    public void setBank_no(String bank_no) {
        this.bank_no = bank_no;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<BorrowCutRepayPlatcust> getPlatcustList() {
        return platcustList;
    }

    public void setPlatcustList(List<BorrowCutRepayPlatcust> platcustList) {
        this.platcustList = platcustList;
    }

    public String getPlat_no() {
        return plat_no;
    }

    public void setPlat_no(String plat_no) {
        this.plat_no = plat_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }
}
