package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * 中奖列表
 */
public class ResMsg_Activity_LuckyDrawList extends ResMsg {

    private static final long serialVersionUID = -3112125010266692067L;

    /* 获奖用户列表 */
    private List<HashMap<String, Object>> list;

    /* 总记录数 */
    private Integer totalRows;

    /* 总页数 */
    private Integer totalPages;

    public List<HashMap<String, Object>> getList() {
        return list;
    }

    public void setList(List<HashMap<String, Object>> list) {
        this.list = list;
    }

    public Integer getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(Integer totalRows) {
        this.totalRows = totalRows;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
}
