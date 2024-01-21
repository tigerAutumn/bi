package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 收款确认函 、代偿债权转让协议（重新生成） 出参
 * Created by shh on 2017/2/8 17:49.
 */
public class ResMsg_Match_GetUserClaimsTransferInfoListRenew extends ResMsg {

    private static final long serialVersionUID = 4696566385704233161L;

    /* 代偿通知时间 */
    private String createTime;

    /* 四方借款协议编号 */
    private String loanNumber;

    private String yunDaiSelfUserName; //云贷自主放款债权受让人名字

    private String yunDaiSelfIdCard; //云贷自主放款债权受让人身份证号

    private Integer sevenPeriod; //7贷借款期限

    private ArrayList<HashMap<String, Object>> dataGrid; //债权转让列表

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLoanNumber() {
        return loanNumber;
    }

    public void setLoanNumber(String loanNumber) {
        this.loanNumber = loanNumber;
    }

    public String getYunDaiSelfUserName() {
        return yunDaiSelfUserName;
    }

    public void setYunDaiSelfUserName(String yunDaiSelfUserName) {
        this.yunDaiSelfUserName = yunDaiSelfUserName;
    }

    public String getYunDaiSelfIdCard() {
        return yunDaiSelfIdCard;
    }

    public void setYunDaiSelfIdCard(String yunDaiSelfIdCard) {
        this.yunDaiSelfIdCard = yunDaiSelfIdCard;
    }

    public Integer getSevenPeriod() {
        return sevenPeriod;
    }

    public void setSevenPeriod(Integer sevenPeriod) {
        this.sevenPeriod = sevenPeriod;
    }

    public ArrayList<HashMap<String, Object>> getDataGrid() {
        return dataGrid;
    }

    public void setDataGrid(ArrayList<HashMap<String, Object>> dataGrid) {
        this.dataGrid = dataGrid;
    }
}
