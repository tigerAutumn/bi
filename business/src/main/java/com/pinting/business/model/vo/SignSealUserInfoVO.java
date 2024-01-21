package com.pinting.business.model.vo;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SignSealUserInfo.java, v 0.1 2015-9-18 下午2:13:45 BabyShark Exp $
 */
public class SignSealUserInfoVO {
    private Integer userId;
    private String  userName;
    private String  idCard;
    private String  pdfPath; //pdf源路径
    private String  orderNo;
    private String  pdfPath2; //pdf2源路径
    private String  money;
    private	String  agreementNo; //借款协议编号
    private	String	loanId;  //借款编号
    private Integer sealFileId; //用户签章文件记录ID
    
    // 新增合作方标识
    private String partnerCode;
    
    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPdfPath2() {
        return pdfPath2;
    }

    public void setPdfPath2(String pdfPath2) {
        this.pdfPath2 = pdfPath2;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}

	public String getLoanId() {
		return loanId;
	}

	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}

	public Integer getSealFileId() {
		return sealFileId;
	}

	public void setSealFileId(Integer sealFileId) {
		this.sealFileId = sealFileId;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
    
}
