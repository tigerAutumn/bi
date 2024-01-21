package com.pinting.business.model.common;

import com.github.pagehelper.Page;
import com.pinting.business.util.Constants;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PagerModelRspDTO<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private int totalRow = 0;// 总条数

    private List<T> list = new ArrayList<>();// 分页集合列表

    private Integer numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;// 每页显示记录数

    private int offset = 0;// 偏移量

    private int totalPage = 0;// 总页数

    private int pageNum = 1;// 当前页

    public PagerModelRspDTO() {

    }

    public PagerModelRspDTO(List pageList) {
        this.setPageByList(pageList);
    }

    public PagerModelRspDTO(PagerReqDTO pagerReq, List pageList) {
        setPageNum(pagerReq.getPageNum());
        setNumPerPage(pagerReq.getNumPerPage());
        this.setPageByList(pageList);
    }

    public int getOffset() {
        if (pageNum == 0) {
            offset = 0;
        } else {
            offset = (pageNum - 1) * this.numPerPage;
        }
        return offset;
    }

    public PagerModelRspDTO<T> setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public List<T> getList() {
        return list == null ? new ArrayList() : list;
    }

    public PagerModelRspDTO<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    public int getPageNum() {
        return pageNum;
    }

    public PagerModelRspDTO<T> setPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public int getTotalRow() {
        return totalRow;
    }

    public PagerModelRspDTO<T> setTotalRow(int totalRow) {
        this.totalRow = totalRow;
        return this;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public PagerModelRspDTO<T> setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
        return this;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public PagerModelRspDTO<T> setTotalPage(int totalPage) {
        this.totalPage = totalPage;
        return this;
    }

    public PagerModelRspDTO<T> setPageByList(List pageList) {
        if (pageList != null && pageList instanceof Page) {
            Page page = (Page) pageList;
            setPageNum(page.getPageNum());
            setNumPerPage(page.getPageSize());
            setTotalPage(page.getPages());
            setTotalRow(Long.valueOf(page.getTotal()).intValue());
            setList(pageList);
        } else if (CollectionUtils.isNotEmpty(pageList)) {
            setList(pageList);
            setTotalRow(pageList.size());
        }
        return this;
    }
}
