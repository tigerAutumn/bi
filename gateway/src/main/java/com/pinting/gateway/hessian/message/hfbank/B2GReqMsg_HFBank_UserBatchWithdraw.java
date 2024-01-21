package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtData;

import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/3
 * Description: 批量提现请求信息
 */
public class B2GReqMsg_HFBank_UserBatchWithdraw extends ReqMsg {

    private static final long serialVersionUID = 6794434778123891009L;
    /*总数量*/
    private	Integer total_num;
    /* JsonArray明细数据 */
    private List<BatchWithdrawExtData> data;
    /* 公私标示，0-个人 1-公司； 默认个人 */
    private String client_property;
    /* 城市编码（富友必填） */
    private String city_code;

    /* 订单号 */
    private String order_no;

    /* 商户交易日期 YYYYMMdd */
    private Date partner_trans_date;

    /* 商户交易时间 HHmmss */
    private Date partner_trans_time;

    public List<BatchWithdrawExtData> getData() {
        return data;
    }

    public void setData(List<BatchWithdrawExtData> data) {
        this.data = data;
    }

    public String getClient_property() {
        return client_property;
    }

    public void setClient_property(String client_property) {
        this.client_property = client_property;
    }

    public String getCity_code() {
        return city_code;
    }

    public void setCity_code(String city_code) {
        this.city_code = city_code;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public Date getPartner_trans_date() {
        return partner_trans_date;
    }

    public void setPartner_trans_date(Date partner_trans_date) {
        this.partner_trans_date = partner_trans_date;
    }

    public Date getPartner_trans_time() {
        return partner_trans_time;
    }

    public void setPartner_trans_time(Date partner_trans_time) {
        this.partner_trans_time = partner_trans_time;
    }

	public Integer getTotal_num() {
		return total_num;
	}

	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
    
}
