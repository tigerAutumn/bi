package com.pinting.business.model.vo;

import com.pinting.business.model.BsServiceFee;

/**
 * Author:      cyb
 * Date:        2017/1/23
 * Description:
 */
public class BsServiceFeeVO extends BsServiceFee {

    private Integer rowNo;

    private String userName;

    private String mobile;

    /* 收取端口 宝付/恒丰 */
    private String channel;

    /* 资产方编码 */
    private String partnerCode;

    public Integer getRowNo() {
        return rowNo;
    }

    public void setRowNo(Integer rowNo) {
        this.rowNo = rowNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }
}
