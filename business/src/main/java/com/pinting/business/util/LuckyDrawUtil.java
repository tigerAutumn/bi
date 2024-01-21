/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.util;

import com.pinting.core.util.*;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.encrypt.DESUtil;
import org.apache.commons.collections.CollectionUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.*;

/**
 * 抽奖工具类
 * @author HuanXiong
 * @version $Id: LuckyDrawUtil.java, v 0.1 2016-1-22 上午11:38:45 HuanXiong Exp $
 */
public class LuckyDrawUtil {
    
    /**
     * 随机获得的幸运数字[1,10000]
     * 
     * @return
     */
    public static Integer luckyNumber() {
        Random rand = new Random();
        Integer luckyNumber = rand.nextInt(10000) + 1;    // [1,10000]的数字
        return luckyNumber;
    }

    /**
     * 自定义闭区间的随机数字[1,maxNumber]
     * @param maxNumber
     * @return
     */
    public static Integer luckyNumber(Integer maxNumber) {
        if(null == maxNumber) {
            return luckyNumber();
        }
        Random rand = new Random();
        Integer luckyNumber = rand.nextInt(maxNumber) + 1;    // [1,maxNumber]的数字
        return luckyNumber;
    }

    /**
     * 自定义闭区间的随机数字[minNumber,maxNumber]
     * @param minNumber
     * @param maxNumber
     * @return
     */
    public static Integer luckyNumber(Integer minNumber, Integer maxNumber) {
        if(null == maxNumber && null == minNumber) {
            return luckyNumber();
        }
        if(null != maxNumber && null == minNumber) {
            return luckyNumber(maxNumber);
        }
        Random rand = new Random();
        int space = maxNumber - minNumber + 1;
        Integer luckyNumber = rand.nextInt(space) + minNumber;    // [minNumber,maxNumber]的数字
        return luckyNumber;
    }

    /**
     * 递归。获取互不相同的一组幸运数字
     * 自定义闭区间的随机数字[minNumber,maxNumber]
     * @param initDataList
     * @param count             幸运数字总个数
     * @param luckyNumberList   获得幸运数字集合
     */
    public static void luckyNumberList(List<Integer> initDataList, Integer count, List<Integer> luckyNumberList) {
        if(count <= 0 || CollectionUtils.isEmpty(initDataList)) {
            return;
        }
        Integer luckyLumber = LuckyDrawUtil.luckyNumber(0, initDataList.size() - 1);
        luckyNumberList.add(initDataList.get(luckyLumber));
        initDataList.remove(initDataList.get(luckyLumber));
        count--;
        LuckyDrawUtil.luckyNumberList(initDataList, count, luckyNumberList);
    }

    public static void main(String[] args) throws ParseException {
        Double regularAmount = CalculatorUtil.calculate("a-a-a-a", 2863.87, 2269.0, 600.0, 5.13);
        System.out.println(regularAmount);
    }
}
