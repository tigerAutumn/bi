package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 2016公司年会抽奖活动 抽奖滚屏数据 出参
 * Created by shh on 2017/1/14 15:21.
 */
public class ResMsg_EndOf2016CompanyAnnual_LotteryScrollingData extends ResMsg {

    private static final long serialVersionUID = 7285656923757550246L;

    private ArrayList<HashMap<String,Object>> valueList;

    public ArrayList<HashMap<String, Object>> getValueList() {
        return valueList;
    }

    public void setValueList(ArrayList<HashMap<String, Object>> valueList) {
        this.valueList = valueList;
    }
}
