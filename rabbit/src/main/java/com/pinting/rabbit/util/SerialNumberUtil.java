package com.pinting.rabbit.util;

import java.text.DecimalFormat;
import java.util.Date;

public class SerialNumberUtil {

    /**
     * 生成队列流水号，只是目前暂用 前缀+Long时间+4位不重复随机数
     *
     * @param prefix
     * @return
     */
    public static String generateQueueNo(String prefix) {
        String noStr = String.valueOf(new Date().getTime());
        int randomNum = (int) (Math.random() * 10000);
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        return prefix + noStr + decimalFormat.format(randomNum);
    }

    public static void main(String[] args) {
        System.out.println(generateQueueNo("maomao"));
    }
}
