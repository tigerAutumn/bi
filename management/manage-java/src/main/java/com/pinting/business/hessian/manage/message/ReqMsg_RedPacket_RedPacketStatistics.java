package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RedPacket_RedPacketStatistics extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8283307812875552326L;
	
	private String serialName;
	private String applyNo;
	private String applicant;
	private Integer pageNum = 1;
    private Integer numPerPage = 20;
    private Integer start = 1;
    
	public String getSerialName() {
		return serialName;
	}
	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}
	public Integer getStart() {
		start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
    
}
