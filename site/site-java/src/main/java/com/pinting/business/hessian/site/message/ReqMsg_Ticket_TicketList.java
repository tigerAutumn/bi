package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2018/3/30.
 */
public class ReqMsg_Ticket_TicketList extends ReqMsg {

    private static final long serialVersionUID = 3319655891465546890L;

    private Integer userId;     // 用户ID
    private String  bizType;    // 业务类型：BUY-投资加入；TICKET-优惠券列表
    private String  type;       // （优惠券列表）类型：RED_PACKET-红包; INTEREST_TICKET-加息券
    private String  status;     // 使用状态：INIT-未使用；USED-已使用；OVER-已过期
    private Integer pageNum;    // （优惠券列表）页码，当前页面，默认传1
    private Integer numPerPage; // （优惠券列表）每页显示条数
    private Double  amount;     // （投资加入）加入金额
    private Integer productId;  // （投资加入）产品ID

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
