package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.CashTransferSchemesVO;

import java.util.List;

/**
 * 回款计划详情
 * Created by shh on 2017/3/30.
 */
public class RepayScheduleDetailsResponse extends Response {

    private List<CashTransferSchemesVO> dataList;

    public List<CashTransferSchemesVO> getDataList() {
        return dataList;
    }

    public void setDataList(List<CashTransferSchemesVO> dataList) {
        this.dataList = dataList;
    }
}
