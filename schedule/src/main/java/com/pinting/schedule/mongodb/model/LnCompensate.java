package com.pinting.schedule.mongodb.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document ( collection = "LnCompensate" )
public class LnCompensate {
	@Field("id")
    private Integer id;
    
    @Field("partner_code")
    private String partnerCode;

    @Field("order_no")
    private String orderNo;

    @Field("pay_order_no")
    private String payOrderNo;

    @Field("apply_time")
    private Date applyTime;

    @Field("finish_time")
    private Date finishTime;

    @Field("total_mount")
    private Double totalMount;

    @Field("create_time")
    private Date createTime;
    
    @Field("update_time")
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Double getTotalMount() {
        return totalMount;
    }

    public void setTotalMount(Double totalMount) {
        this.totalMount = totalMount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}