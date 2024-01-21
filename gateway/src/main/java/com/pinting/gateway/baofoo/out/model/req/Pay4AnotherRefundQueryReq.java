package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public class Pay4AnotherRefundQueryReq extends BaoFooOutBaseReq{

    /**
     * 查询起始时间
     * 格式：YYYYMMDD（最多查询一天记录）
     */
    private String trans_btime;

    /**
     * 查询结束时间
     * 格式：YYYYMMDD（最多查询一天记录）
     */
    private String trans_etime;

    public String getTrans_btime() {
        return trans_btime;
    }

    public void setTrans_btime(String trans_btime) {
        this.trans_btime = trans_btime;
    }

    public String getTrans_etime() {
        return trans_etime;
    }

    public void setTrans_etime(String trans_etime) {
        this.trans_etime = trans_etime;
    }
}
