package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 广告效果信息,付费广告记录新增入参
 * @author shiyulong
 * @2016-3-15 上午10:32:15
 */
public class ReqMsg_AdEffect_AdEffectSaveInfo extends ReqMsg {
    /**
	 *
	 */
	private static final long serialVersionUID = -2169796212069455639L;

	/*主键id*/
	private Integer id;
	/*落地页面*/
    private String url;
    /*监控类型*/
    private String monitorType;
    /*访问时间*/
    private Date visitTime;
    /*注册号码*/
    private String regMobile;
    /*广告系列来源*/
    private String utmSource;
    /*广告系列媒介*/
    private String utmMedium;
    /*广告系列字词*/
    private String utmTerm;
    /*广告系列内容*/
    private String utmContent;
    /*广告系列名称*/
    private String utmCampaign;
    /*创建时间*/
    private Date createTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMonitorType() {
		return monitorType;
	}

	public void setMonitorType(String monitorType) {
		this.monitorType = monitorType;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getRegMobile() {
		return regMobile;
	}

	public void setRegMobile(String regMobile) {
		this.regMobile = regMobile;
	}

	public String getUtmSource() {
		return utmSource;
	}

	public void setUtmSource(String utmSource) {
		this.utmSource = utmSource;
	}

	public String getUtmMedium() {
		return utmMedium;
	}

	public void setUtmMedium(String utmMedium) {
		this.utmMedium = utmMedium;
	}

	public String getUtmTerm() {
		return utmTerm;
	}

	public void setUtmTerm(String utmTerm) {
		this.utmTerm = utmTerm;
	}

	public String getUtmContent() {
		return utmContent;
	}

	public void setUtmContent(String utmContent) {
		this.utmContent = utmContent;
	}

	public String getUtmCampaign() {
		return utmCampaign;
	}

	public void setUtmCampaign(String utmCampaign) {
		this.utmCampaign = utmCampaign;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
    
}
