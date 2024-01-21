package com.pinting.gateway.util;

import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by cyb on 2018/4/11.
 */
public class GatewayMoneyUtil extends MoneyUtil {

    public static String format2Point(String amount) {
        if(StringUtil.isBlank(amount)) return "0.00";
        Double amountDouble = Double.valueOf(amount);
        if(amount.indexOf(".") > 0) {
            String[] resultList = StringUtil.split(amount, ".");
            if(resultList[1].length() > 2) {
                return amount;
            } else {
                return new DecimalFormat("0.00").format(amountDouble);
            }
        } else {
            return new DecimalFormat("0.00").format(amountDouble);
        }
    }

    public static String format2Point(Double amount) {
        if(amount == null) return "0.00";
        DecimalFormat format = (DecimalFormat) NumberFormat.getPercentInstance();
        format.applyPattern("##########.##########");
        return format2Point(format.format(amount));
    }

    public static void main(String[] args) {
        System.out.println(1000000002.343 + "\t" + GatewayMoneyUtil.format2Point(1000000002.343));
        System.out.println(GatewayMoneyUtil.format2Point("1000000002.343"));
    }

}
