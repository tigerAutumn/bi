package com.pinting.business.accounting.loan.model;

import java.util.List;

/**
 * Created by babyshark on 2017/4/4.
 */
public class Withdraw2DepRepayCardReq extends DepBaseReq{
    private Double amount;//总金额
    private List<DepRepaySchedule> depRepaySchedules;//存管账单信息
    //存管还款实体户信息提供！
    private static String depRepayCard = "857110010122836181";
    private static String depRepayCardName = "杭州币港湾科技有限公司归集专户";
    private static String depBankName = "恒丰银行";
    private static String depProvinceName = "浙江省";
    private static String depCityName = "杭州市";
    private static String depAccDept = "杭州分行营业部";

    public List<DepRepaySchedule> getDepRepaySchedules() {
        return depRepaySchedules;
    }

    public void setDepRepaySchedules(List<DepRepaySchedule> depRepaySchedules) {
        this.depRepaySchedules = depRepaySchedules;
    }

    public static String getDepRepayCardName() {
        return depRepayCardName;
    }

    public static String getDepProvinceName() {
        return depProvinceName;
    }

    public static String getDepCityName() {
        return depCityName;
    }

    public static String getDepAccDept() {
        return depAccDept;
    }

    public static String getDepBankName() {
        return depBankName;
    }

    public static String getDepRepayCard() {
        return depRepayCard;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
