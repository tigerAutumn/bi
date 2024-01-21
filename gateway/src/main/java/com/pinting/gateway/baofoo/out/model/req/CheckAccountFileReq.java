package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/7/25.
 */
public class CheckAccountFileReq extends BaoFooOutBaseReq {

    /**
     * 文件类型
     * 收款：fi  出款：fo
     */
    private String fileType;

    /**
     * 清算日期（指定日期的对帐文件）
     * 格式：yyyy-mm-dd （除当天）
     */
    private String settleDate;

    /**
     * 对账方编码
     */
    private String partner;
    
    /**
     * 商户号
     */
    private String merchantNo;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
    
}
