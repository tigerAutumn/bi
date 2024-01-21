package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Bs19PayBank_Bs19PayBankPrimaryKey extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 550859526433260932L;
	
	private Integer id;

    private Integer bankId;

    private String pay19BankCode;

    private Integer payType;

    private String oneTop;

    private String dayTop;

    private String monthTop;

    private Date forbiddenStart;

    private Date forbiddenEnd;

    private Integer isAvailable;
    
    private String notice;
    
    private Integer isMain;
    
    private Integer channelPriority;
    
    private String channel;
    
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

    public String getOneTop() {
		return oneTop;
	}

	public void setOneTop(String oneTop) {
		this.oneTop = oneTop;
	}

	public String getDayTop() {
		return dayTop;
	}

	public void setDayTop(String dayTop) {
		this.dayTop = dayTop;
	}

	public String getMonthTop() {
		return monthTop;
	}

	public void setMonthTop(String monthTop) {
		this.monthTop = monthTop;
	}

	public Date getForbiddenStart() {
        return forbiddenStart;
    }

    public void setForbiddenStart(Date forbiddenStart) {
        this.forbiddenStart = forbiddenStart;
    }

    public Date getForbiddenEnd() {
        return forbiddenEnd;
    }

    public void setForbiddenEnd(Date forbiddenEnd) {
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

	public Integer getIsMain() {
		return isMain;
	}

	public void setIsMain(Integer isMain) {
		this.isMain = isMain;
	}

	public Integer getChannelPriority() {
		return channelPriority;
	}

	public void setChannelPriority(Integer channelPriority) {
		this.channelPriority = channelPriority;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getDailyNotice() {
		return dailyNotice;
	}

	public void setDailyNotice(String dailyNotice) {
		this.dailyNotice = dailyNotice;
	}



}
