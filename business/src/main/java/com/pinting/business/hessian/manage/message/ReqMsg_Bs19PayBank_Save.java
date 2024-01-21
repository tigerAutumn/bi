package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Bs19PayBank_Save extends ReqMsg{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1021023218671075387L;

	private Integer id;

    private Integer bankId;

    private String pay19BankCode;

    private Integer payType;

    private Double oneTop;

    private Double dayTop;

    private Double monthTop;

    private String forbiddenStart;

    private String forbiddenEnd;

    private Integer isAvailable;
    
    private String notice;
    
    private Integer channelPriority;
    
    private Integer isMain;
    
    private String dailyNotice;
    
	private ArrayList<HashMap<String, Object>> bsBanks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getPay19BankCode() {
        return pay19BankCode;
    }

    public void setPay19BankCode(String pay19BankCode) {
        this.pay19BankCode = pay19BankCode;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Double getOneTop() {
        return oneTop;
    }

    public void setOneTop(Double oneTop) {
        this.oneTop = oneTop;
    }

    public Double getDayTop() {
        return dayTop;
    }

    public void setDayTop(Double dayTop) {
        this.dayTop = dayTop;
    }

    public Double getMonthTop() {
        return monthTop;
    }

    public void setMonthTop(Double monthTop) {
        this.monthTop = monthTop;
    }

    public String getForbiddenStart() {
        return forbiddenStart;
    }

    public void setForbiddenStart(String forbiddenStart) {
        this.forbiddenStart = forbiddenStart;
    }

    public String getForbiddenEnd() {
        return forbiddenEnd;
    }

    public void setForbiddenEnd(String forbiddenEnd) {
        this.forbiddenEnd = forbiddenEnd;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

	public ArrayList<HashMap<String, Object>> getBsBanks() {
		return bsBanks;
	}

	public void setBsBanks(ArrayList<HashMap<String, Object>> bsBanks) {
		this.bsBanks = bsBanks;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Integer getChannelPriority() {
		return channelPriority;
	}

	public void setChannelPriority(Integer channelPriority) {
		this.channelPriority = channelPriority;
	}

	public Integer getIsMain() {
		return isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}

	public String getDailyNotice() {
		return dailyNotice;
	}

	public void setDailyNotice(String dailyNotice) {
		this.dailyNotice = dailyNotice;
	}

}
