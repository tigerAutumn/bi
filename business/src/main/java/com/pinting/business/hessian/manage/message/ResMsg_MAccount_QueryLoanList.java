package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 财务功能-借款人提现 出参
 * @author SHENGUOPING
 * @date  2017年9月26日 下午7:13:47
 */
public class ResMsg_MAccount_QueryLoanList extends ResMsg {

	private static final long serialVersionUID = -5419903627577661286L;
	
    /* 借款人账户余额 */
    private Double loanAvailableAmount;
    
	private ArrayList<HashMap<String,Object>> valueList;

    private Integer totalRows;

    private Integer pageNum;

    private Integer numPerPage;

    public Double getLoanAvailableAmount() {
		return loanAvailableAmount;
	}

	public void setLoanAvailableAmount(Double loanAvailableAmount) {
		this.loanAvailableAmount = loanAvailableAmount;
	}

	public ArrayList<HashMap<String, Object>> getValueList() {
        return valueList;
    }

    public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
        this.valueList = valueList;
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

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

}
