package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 19付银行渠道维护
 * @author caonengwen
 *
 */
public class ReqMsg_Bs19PayBank_BankListQuery  extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6870882025639717442L;
	private String pageNum;
	private String numPerPage;
	/** 银行名称 **/
	private String name;
	/** 19付银行编码 **/
	private String pay19BankCode;
	/** 类型 **/
	private Integer payType;
	/**是否可用**/
	private Integer isAvailable; 
	/**是否主通道**/
	private Integer isMain; 
	/**优先级**/
	private Integer channelPriority; 
	
	
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}
	public String getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(String numPerPage) {
		this.numPerPage = numPerPage;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name == null ? name : name.trim();
	}
	public String getPay19BankCode() {
		return pay19BankCode;
	}
	public void setPay19BankCode(String pay19BankCode) {
		this.pay19BankCode = pay19BankCode == null ? pay19BankCode : pay19BankCode.trim();
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getIsAvailable() {
		return isAvailable;
	}
	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
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


}
