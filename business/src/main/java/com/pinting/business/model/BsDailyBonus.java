package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsDailyBonus extends PageInfoObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2362975012599614108L;

	private Integer id;

    private Integer userId;

    private Integer beRecommendUserId;

    private Integer subAccountId;

    private Double bonus;

    private Date time;

    private String type;
    
    private String note;  //备注
    
    private Date useTime; //加息券使用时间
    
    private Double ticketApr;  //加息幅度
    
    private String productName;  //产品名称
    
    private String detail;   //展示细节：拼接使用时间，加息幅度和产品名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBeRecommendUserId() {
        return beRecommendUserId;
    }

    public void setBeRecommendUserId(Integer beRecommendUserId) {
        this.beRecommendUserId = beRecommendUserId;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public Double getTicketApr() {
		return ticketApr;
	}

	public void setTicketApr(Double ticketApr) {
		this.ticketApr = ticketApr;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
}