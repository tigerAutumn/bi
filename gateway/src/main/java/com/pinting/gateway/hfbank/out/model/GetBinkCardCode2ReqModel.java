package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 短验绑卡申请请求信息
 */
public class GetBinkCardCode2ReqModel extends BaseReqModel {

    /* 平台客户编号：已实名认证用户，参数必填  */
    private String platcust;
    /* 证件类型：未实名认证用户，参数必填 */
    private String id_type;
    /* 证件号码：未实名认证用户，参数必填 */
    private String id_code;
    /* 姓名：未实名认证用户，参数必填 */
    private String name;
    /* 卡号  */
    private String card_no;
    /* 卡类型(1:借记卡，2:信用卡) */
    private String card_type;
    /* 预留手机号 */
    private String pre_mobile;
    /* 支付通道 */
    private String pay_code;
    /* 备注 */
    private String remark;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }
    
	public String getPre_mobile() {
		return pre_mobile;
	}

	public void setPre_mobile(String pre_mobile) {
		this.pre_mobile = pre_mobile;
	}

	public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
