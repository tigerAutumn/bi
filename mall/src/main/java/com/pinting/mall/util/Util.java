package com.pinting.mall.util;

import com.pinting.core.util.DateUtil;

import java.text.DecimalFormat;
import java.util.Date;

public class Util {

    /**
     * @param userId
     * @return
     * @throws
     * @Title: generateOrderNo
     * @Description: 生成用户订单号，用于积分商城兑换
     */
    public static String generateOrderNo(Integer userId) {
        String startVal = DateUtil.formatDateTime(new Date(), "yyMMddHHmmssSSS");
        int randomNum = (int) (Math.random() * 10000);
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        startVal += userId + decimalFormat.format(randomNum);
        return startVal;
    }
}
