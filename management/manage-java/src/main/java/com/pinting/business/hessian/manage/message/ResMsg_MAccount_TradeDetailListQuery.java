package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 用户交易明细应答
 * @author yanwl
 * @date 2015-11-13
 */
public class ResMsg_MAccount_TradeDetailListQuery extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 1657772310353077320L;

	private ArrayList<HashMap<String,Object>> userTransDetail;

	/**
	 * 手机号
	 */
	private String sMobile;
	
	/**
	 * 用户名
	 */
	private String sUserName;
	
	/**
	 * 交易类型
	 */
	private String sTransType;
	
	/**
	 * 页码
	 */
	private Integer pageNum;
	
	/**
	 * 页码
	 */
    private Integer numPerPage;
	
    /**
     * 总条数
     */
	private Integer totalRows;
	
	
	public Integer getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}

	public Integer getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(Integer totalRows) {
		this.totalRows = totalRows;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public ArrayList<HashMap<String, Object>> getUserTransDetail() {
		return userTransDetail;
	}

	public void setUserTransDetail(
			ArrayList<HashMap<String, Object>> userTransDetail) {
		this.userTransDetail = userTransDetail;
	}

	public String getsMobile() {
		return sMobile;
	}

	public void setsMobile(String sMobile) {
		this.sMobile = sMobile;
	}

	public String getsUserName() {
		return sUserName;
	}

	public void setsUserName(String sUserName) {
		this.sUserName = sUserName;
	}

	public String getsTransType() {
		return sTransType;
	}

	public void setsTransType(String sTransType) {
		this.sTransType = sTransType;
	}
}
