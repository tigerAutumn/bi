package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Author:      shh
 * Date:        2017/7/4
 * Description: 当日系统理财未回款查询VO
 */
public class SysNotReceivableVO {

    /* 购买信息 */

    /* 资产方 */
    private String propertySymbol;

    /* 购买批次号 */
    private String sendBatchId;

    /* 购买金额 */
    private Double buyAmount;

    /* 购买期限 */
    private Integer term;

    /* 预期最后一期回款时间 */
    private Date expectTime;

    /* 购买状态 */
    private String status;

    /* 回款信息 */

    /* 回款类型 */
    private String type;

    /* 回款金额 */
    private Double receiveAmount;

    /* 回款期次序号 */
    private Integer productReturnTerm;

    /* 回款完成时间 */
    private Date payFinshTime;

    public String getPropertySymbol() {
        return propertySymbol;
    }

    public void setPropertySymbol(String propertySymbol) {
        this.propertySymbol = propertySymbol;
    }

    public String getSendBatchId() {
        return sendBatchId;
    }

    public void setSendBatchId(String sendBatchId) {
        this.sendBatchId = sendBatchId;
    }

    public Double getBuyAmount() {
        return buyAmount;
    }

    public void setBuyAmount(Double buyAmount) {
        this.buyAmount = buyAmount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Date getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Date expectTime) {
        this.expectTime = expectTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Integer getProductReturnTerm() {
        return productReturnTerm;
    }

    public void setProductReturnTerm(Integer productReturnTerm) {
        this.productReturnTerm = productReturnTerm;
    }

    public Date getPayFinshTime() {
        return payFinshTime;
    }

    public void setPayFinshTime(Date payFinshTime) {
        this.payFinshTime = payFinshTime;
    }
}
