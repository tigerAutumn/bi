package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsBankCard;

public class BsBindBankVO extends BsBankCard{

	private static final long serialVersionUID = 2534735787680511599L;

    private Double oneTop;

    private Double dayTop;
    
    private Double monthTop;
    
    private Integer isAvailable;
    
    private Date forbiddenStart;

    private Date forbiddenEnd;
    
    private Integer id; // 银行卡ID
    
    private String smallLogo;
    
    private String largeLogo;
    
    private String userName;
    
    private String dailyNotice;
    
	public String getDailyNotice() {
        return dailyNotice;
    }

    public void setDailyNotice(String dailyNotice) {
        this.dailyNotice = dailyNotice;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSmallLogo() {
        return smallLogo;
    }

    public void setSmallLogo(String smallLogo) {
        this.smallLogo = smallLogo;
    }

    public String getLargeLogo() {
        return largeLogo;
    }

    public void setLargeLogo(String largeLogo) {
        this.largeLogo = largeLogo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
	
}
