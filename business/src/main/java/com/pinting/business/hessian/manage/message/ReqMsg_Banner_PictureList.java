package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 图片列表
 * @author SHENGP
 * @date  2017年7月14日 下午4:04:28
 */
public class ReqMsg_Banner_PictureList extends ReqMsg {

	private static final long serialVersionUID = 5575500683647969047L;
	
	private String channel;

    private String subject;
    
	private String status;
	
	// 展示位置
	private String showPageLabel;

	private int pageNum;
	
	/**
	 * 每页显示的记录数(默认为20条,可以通过set改变其数量)
	 */
	private int numPerPage = 20;

	public int getPageNum() {
		if (pageNum < 1) {

			this.pageNum = 1;
		}
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	
    public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getShowPageLabel() {
		return showPageLabel;
	}

	public void setShowPageLabel(String showPageLabel) {
		this.showPageLabel = showPageLabel;
	}
	
}
