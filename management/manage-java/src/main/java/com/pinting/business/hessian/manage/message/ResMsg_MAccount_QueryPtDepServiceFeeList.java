package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 品听信息服务费查询
 * @author SHENGUOPING
 * @date  2017年10月19日 下午4:59:25
 */
public class ResMsg_MAccount_QueryPtDepServiceFeeList extends ResMsg {

	private static final long serialVersionUID = 7239121730286814590L;

	private ArrayList<HashMap<String,Object>> valueList;

    private Integer totalRows;

    private Integer pageNum;

    private Integer numPerPage;
    
    private Double sumBuyAmount;	//购买总金额
    
    private Double sumPtServiceFee;  //信息服务费
    
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

	public Double getSumBuyAmount() {
		return sumBuyAmount;
	}

	public void setSumBuyAmount(Double sumBuyAmount) {
		this.sumBuyAmount = sumBuyAmount;
	}

	public Double getSumPtServiceFee() {
		return sumPtServiceFee;
	}

	public void setSumPtServiceFee(Double sumPtServiceFee) {
		this.sumPtServiceFee = sumPtServiceFee;
	}
	
}
