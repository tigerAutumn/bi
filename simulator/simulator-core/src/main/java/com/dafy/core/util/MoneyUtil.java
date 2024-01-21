/**
 * @project
 * @author yanwl
 * @version 1.0
 * @date 2015-11-21
 */
package com.dafy.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额处理的工具类
 *
 * @author yanwl
 * @version 1.0
 * @date 2015-11-21
 */
public class MoneyUtil {
  
	/**
	 * 金额数字格式化：三位逗号分割，保留两位小数
	 * 
	 * @param v
	 * @return
	 */
    public static String format(String v) {
    	
		return format(new BigDecimal(v));
	}

    /**
	 * 金额数字格式化：三位逗号分割，保留两位小数
	 * 
	 * @param v
	 * @return
	 */
	public static String format(double v) {
		
		return format(new BigDecimal(v));
	}

	/**
	 * 金额数字格式化：三位逗号分割，保留两位小数
	 * 
	 * @param v
	 * @return
	 */
	public static String format(BigDecimal v) {
		//默认精度,即保留小数点2位
		return new DecimalFormat("#,##0.00").format(round(v, 2, BigDecimal.ROUND_HALF_UP));
	}
	/**
	 * 金额数字格式化：三位逗号分割，保留3位小数
	 * 
	 * @param v
	 * @return
	 */
	public static String format2(String v) {
		
		return format2(new BigDecimal(v));
	}
	/**
	 * 金额数字格式化：三位逗号分割，保留3位小数
	 * 
	 * @param v
	 * @return
	 */
	public static String format2(BigDecimal v) {
		
		return new DecimalFormat("#,##0.000").format(round(v, 3, BigDecimal.ROUND_HALF_UP));
	}
	
	/**
	 * 数字格式化：保留3位小数
	 * 
	 * @param v
	 * @return
	 */
	public static String format3(String v) {
		
		return format3(new BigDecimal(v));
	}
	/**
	 * 数字格式化：保留3位小数
	 * 
	 * @param v
	 * @return
	 */
	public static String format3(BigDecimal v) {
		
		return new DecimalFormat("0.000").format(round(v, 3, BigDecimal.ROUND_HALF_UP));
	}
	
	/**
	 * 数字格式化：只保留2位小数，不做格式转换((不做四舍五入))
	 * @param v
	 * @return
	 */
	public static String format4(String v) {
		
		return format4(new BigDecimal(v));
	}
	
	/**
	 * 数字格式化：只保留2位小数，不做格式转换(不做四舍五入)
	 * @param v
	 * @return
	 */
	public static String format4(BigDecimal v) {
		
		return new DecimalFormat("###.##").format(round(v, 2, BigDecimal.ROUND_HALF_UP));
	}

	/** 
	 * 提供精确的加法运算。 
	 * @param v1 加数 
	 * @param v2 被加数 
	 * @return 两个参数的和 
	 */
	public static BigDecimal add(String v1, String v2) {
		
		return add(new BigDecimal(v1), new BigDecimal(v2));
	}

	/** 
	 * 提供精确的加法运算。 
	 * @param v1 加数 
	 * @param v2 被加数 
	 * @return 两个参数的和 
	 */
	public static BigDecimal add(double v1, double v2) {
		
		return add(new BigDecimal(v1), new BigDecimal(v2));
	}

	/** 
	 * 提供精确的加法运算。 
	 * @param v1 加数 
	 * @param v2 被加数 
	 * @return 两个参数的和 
	 */
	public static BigDecimal add(BigDecimal v1, BigDecimal v2) {
		
		return v1.add(v2);
	}

	/** 
	 * 提供精确的减法运算。 
	 * @param v1 减数 
	 * @param v2 被减数 
	 * @return 两个参数的差 
	 */
	public static BigDecimal subtract(String v1, String v2) {
		
		return subtract(new BigDecimal(v1), new BigDecimal(v2));
	}

	/** 
	 * 提供精确的减法运算。 
	 * @param v1 减数 
	 * @param v2 被减数 
	 * @return 两个参数的差 
	 */
	public static BigDecimal subtract(double v1, double v2) {
		
		return subtract(new BigDecimal(v1), new BigDecimal(v2));
	}

	/** 
	 * 提供精确的减法运算。 
	 * @param v1 减数 
	 * @param v2 被减数 
	 * @return 两个参数的差 
	 */
	public static BigDecimal subtract(BigDecimal v1, BigDecimal v2) {
		
		return v1.subtract(v2);
	}

	/** 
	 * 提供精确的乘法运算。 
	 * @param v1 乘数 
	 * @param v2 被乘数 
	 * @return 两个参数的积 
	 */
	public static BigDecimal multiply(String v1, String v2) {
		
		return multiply(new BigDecimal(v1), new BigDecimal(v2));
	}

	/** 
	 * 提供精确的乘法运算。 
	 * @param v1 乘数 
	 * @param v2 被乘数 
	 * @return 两个参数的积 
	 */
	public static BigDecimal multiply(double v1, double v2) {
		
		return multiply(new BigDecimal(v1), new BigDecimal(v2));
	}

	/** 
	 * 提供精确的乘法运算。 
	 * @param v1 乘数 
	 * @param v2 被乘数 
	 * @return 两个参数的积 
	 */
	public static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
		
		return v1.multiply(v2);
	}

	/** 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，保留2位小数，数字四舍五入。 
	 * @param v1 除数 
	 * @param v2 被除数
	 * @return 两个参数的商 
	 */
	public static BigDecimal divide(String v1, String v2) {
		
		return divide(new BigDecimal(v1), new BigDecimal(v2));
	}

	/** 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，保留2位小数，数字四舍五入。 
	 * @param v1 除数 
	 * @param v2 被除数
	 * @return 两个参数的商 
	 */
	public static BigDecimal divide(double v1, double v2) {
		
		return divide(new BigDecimal(v1), new BigDecimal(v2));
	}

	/** 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，保留2位小数，数字四舍五入。 
	 * @param v1 除数 
	 * @param v2 被除数
	 * @return 两个参数的商 
	 */
	public static BigDecimal divide(BigDecimal v1, BigDecimal v2) {
		
		return divide(v1, v2, 2, BigDecimal.ROUND_HALF_UP);
	}
	
	/** 
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，保留小数位数：scale，舍入模式：roundingMode。 
	 * @param v1 除数 
	 * @param v2 被除数
	 * @param scale 小数点后保留几位
	 * @param roundingMode 进位类型 
	 * @return 两个参数的商
	 */
	public static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale, int roundingMode) {
		
		return v1.divide(v2, scale, roundingMode);
	}
	
	/** 
	 * 提供精确的小数位处理:四舍五入。 
	 * @param v 数字
	 * @return 整数处理结果 
	 */
	
	public static int round(double v) {
		
		return round(new BigDecimal(v), 0, BigDecimal.ROUND_HALF_UP).intValue();
	}

	/** 
	 * 提供精确的小数位处理。 
	 * @param v 数字 
	 * @param scale 小数点后保留几位
	 * @param roundingMode 进位类型 
	 * @return 小数处理结果 
	 */
	public static BigDecimal round(BigDecimal v, int scale, int roundingMode) {
		
		if (scale < 0 || roundingMode < 0) {
			throw new IllegalArgumentException(
					"The scale or roundingMode must be a positive integer or zero");
		}
		
		return v.divide(BigDecimal.ONE, scale, roundingMode);
	}
	
	/** 
	 * 提供精确的小数位处理:保留位自定义，四舍五入。 
	 * @param v 数字
	 * @return 小数处理结果 
	 */
	public static BigDecimal defaultRound(int scale,double v) {
		
		return round(new BigDecimal(v), scale, BigDecimal.ROUND_HALF_UP);
	}

	/** 
	 * 提供精确的小数位处理:保留2位，四舍五入。 
	 * @param v 数字
	 * @return 小数处理结果 
	 */
	public static BigDecimal defaultRound(String v) {
		
		return defaultRound(new BigDecimal(v));
	}

	/** 
	 * 提供精确的小数位处理:保留2位，四舍五入。 
	 * @param v 数字
	 * @return 小数处理结果 
	 */
	public static BigDecimal defaultRound(double v) {
		
		return defaultRound(new BigDecimal(v));
	}

	/** 
	 * 提供精确的小数位处理:保留2位，四舍五入。 
	 * @param v 数字
	 * @return 小数处理结果 
	 */
	
	public static BigDecimal defaultRound(BigDecimal v) {
		
		return round(v, 2, BigDecimal.ROUND_HALF_UP);
	}
}