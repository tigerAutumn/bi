package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by zhangbao on 2017/9/4.
 */
public class ZsdRepayForCheckVO {
    private String ln_user_id;
    private String partner_loan_id;
    private String partner_repay_id;
    private String partner_code;
    private String trans_type;
    private Double plan_total;
    private Double principal;
    private Double interest;
    private Double done_total;
    private String partner_order_no;
    private String order_no;
    private Date create_time;
    private Date done_time;
    private String channel;
    private String channel_trans_type;
    private String status;
    private String return_msg;
    private Double late_fee;
    private Double service_fee;
    private Double platform_service_fee;
    private Double info_certified_fee;
    private Double risk_manage_service_fee;
    private Double collection_channel_fee;
    private Double other;

    public String getLn_user_id() {
        return ln_user_id;
    }

    public void setLn_user_id(String ln_user_id) {
        this.ln_user_id = ln_user_id;
    }

    public String getPartner_loan_id() {
        return partner_loan_id;
    }

    public void setPartner_loan_id(String partner_loan_id) {
        this.partner_loan_id = partner_loan_id;
    }

    public String getPartner_repay_id() {
        return partner_repay_id;
    }

    public void setPartner_repay_id(String partner_repay_id) {
        this.partner_repay_id = partner_repay_id;
    }

    public String getPartner_code() {
        return partner_code;
    }

    public void setPartner_code(String partner_code) {
        this.partner_code = partner_code;
    }

    public String getTrans_type() {
        return trans_type;
    }

    public void setTrans_type(String trans_type) {
        this.trans_type = trans_type;
    }

    public Double getPlan_total() {
        return plan_total;
    }

    public void setPlan_total(Double plan_total) {
        this.plan_total = plan_total;
    }

    public Double getPrincipal() {
        return principal;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Double getDone_total() {
        return done_total;
    }

    public void setDone_total(Double done_total) {
        this.done_total = done_total;
    }

    public String getPartner_order_no() {
        return partner_order_no;
    }

    public void setPartner_order_no(String partner_order_no) {
        this.partner_order_no = partner_order_no;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getDone_time() {
        return done_time;
    }

    public void setDone_time(Date done_time) {
        this.done_time = done_time;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannel_trans_type() {
        return channel_trans_type;
    }

    public void setChannel_trans_type(String channel_trans_type) {
        this.channel_trans_type = channel_trans_type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public Double getLate_fee() {
        return late_fee;
    }

    public void setLate_fee(Double late_fee) {
        this.late_fee = late_fee;
    }

    public Double getService_fee() {
        return service_fee;
    }

    public void setService_fee(Double service_fee) {
        this.service_fee = service_fee;
    }

    public Double getPlatform_service_fee() {
        return platform_service_fee;
    }

    public void setPlatform_service_fee(Double platform_service_fee) {
        this.platform_service_fee = platform_service_fee;
    }

    public Double getInfo_certified_fee() {
        return info_certified_fee;
    }

    public void setInfo_certified_fee(Double info_certified_fee) {
        this.info_certified_fee = info_certified_fee;
    }

    public Double getRisk_manage_service_fee() {
        return risk_manage_service_fee;
    }

    public void setRisk_manage_service_fee(Double risk_manage_service_fee) {
        this.risk_manage_service_fee = risk_manage_service_fee;
    }

    public Double getCollection_channel_fee() {
        return collection_channel_fee;
    }

    public void setCollection_channel_fee(Double collection_channel_fee) {
        this.collection_channel_fee = collection_channel_fee;
    }

    public Double getOther() {
        return other;
    }

    public void setOther(Double other) {
        this.other = other;
    }
}
