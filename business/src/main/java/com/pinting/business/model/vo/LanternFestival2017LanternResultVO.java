package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/2/3
 * Description:
 */
public class LanternFestival2017LanternResultVO implements Serializable {

    private static final long serialVersionUID = -135115945739404157L;

    /* 获奖用户列表 */
    private List<LanternFestival2017LanternDetailVO> list;

    /* 总记录数 */
    private Integer totalRows;

    /* 总页数 */
    private Integer totalPages;

    public List<LanternFestival2017LanternDetailVO> getList() {
        return list;
    }

    public void setList(List<LanternFestival2017LanternDetailVO> list) {
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
