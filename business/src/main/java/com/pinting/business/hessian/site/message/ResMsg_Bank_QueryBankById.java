package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 根据银行ID查询银行信息 出参
 * @author shiyulong
 * @2015-11-21 下午4:17:50
 */
public class ResMsg_Bank_QueryBankById extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6142473042015407539L;

	/*bsBank主键id*/
	private Integer id;
	/*银行名称*/
    private String name;
    /*银行卡状态（暂时停用）*/
    private Integer status;
    /*备注*/
    private String note;
    /*小图标*/
    private String smallLogo;
    /*大图标*/
    private String largeLogo;
    /*创建时间*/
    private Date createTime;
    /*更新时间*/
    private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
    
    
}
