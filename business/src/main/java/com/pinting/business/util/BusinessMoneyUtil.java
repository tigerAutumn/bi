package com.pinting.business.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;

public class BusinessMoneyUtil extends MoneyUtil {

	public static String format4PtCa(String v) {
        return format4PtCa(new BigDecimal(v));
    }
    public static String format4PtCa(double v) {
        return format4PtCa(new BigDecimal(v));
    }

    public static String format4PtCa(BigDecimal v) {
        return (new DecimalFormat("#,##0.0###")).format(round(v, 4, RoundingMode.HALF_UP.ordinal()));
    }


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
        return format2Point(String.valueOf(amount));
    }
}
