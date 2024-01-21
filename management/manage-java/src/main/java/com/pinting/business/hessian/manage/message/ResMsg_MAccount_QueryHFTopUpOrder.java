package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 财务功能-恒丰系统充值 出参
 * Created by shh on 2017/4/19.
 */
public class ResMsg_MAccount_QueryHFTopUpOrder extends ResMsg {

    private static final long serialVersionUID = 1311668636833759390L;

    private ArrayList<HashMap<String,Object>> valueList;

    private Integer totalRows;

    private Integer pageNum;

    private Integer numPerPage;

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
