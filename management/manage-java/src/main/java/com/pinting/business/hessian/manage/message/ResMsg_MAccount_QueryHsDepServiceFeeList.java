package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 杭商信息服务费查询
 * @author SHENGUOPING
 * @date  2017年10月19日 下午4:53:56
 */
public class ResMsg_MAccount_QueryHsDepServiceFeeList extends ResMsg {

	private static final long serialVersionUID = -6236887263238575768L;
	
	private ArrayList<HashMap<String,Object>> valueList;

    private Integer totalRows;

    private Integer pageNum;

    private Integer numPerPage;

    private Double sumBuyAmount;	//购买总金额
    
    private Double sumHsServiceFee;  //信息服务费
    
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

	public Double getSumHsServiceFee() {
		return sumHsServiceFee;
	}

	public void setSumHsServiceFee(Double sumHsServiceFee) {
		this.sumHsServiceFee = sumHsServiceFee;
	}

}
