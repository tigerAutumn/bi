package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

import java.util.HashMap;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/2/15
 * Description: 交易明细分期回款列表响应信息
 */
public class PaymentStageDetailsResponse extends Response {

    private List<HashMap<String, Object>> list;

    public List<HashMap<String, Object>> getList() {
        return list;
    }

    public void setList(List<HashMap<String, Object>> list) {
        this.list = list;
    }

}
