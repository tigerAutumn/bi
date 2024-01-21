package com.pinting.gateway.hfbank.out.model;

/**
 * Author:      shh
 * Date:        2017/5/9
 * Description: 对账文件账户余额数据响应信息
 */
public class BalanceInfoResModel extends BaseResModel {

    /**
     * 目标文件地址
     */
    private String destFileName;

    private String resResult;

    public String getDestFileName() {
        return destFileName;
    }

    public void setDestFileName(String destFileName) {
        this.destFileName = destFileName;
    }

    public String getResResult() {
        return resResult;
    }

    public void setResResult(String resResult) {
        this.resResult = resResult;
    }
}
