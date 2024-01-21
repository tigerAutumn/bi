package com.pinting.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MoneyUtil {
	public static String format(String v) {
		return format(new BigDecimal(v));
	}

	public static String format(double v) {
		return format(new BigDecimal(v));
	}

	public static String format(BigDecimal v) {
		return new DecimalFormat("#,##0.00").format(round(v, 2, RoundingMode.HALF_UP.ordinal()));
	}

	public static String format3PtCa(String v) {
		return format3PtCa(new BigDecimal(v));
	}

	public static String format3PtCa(BigDecimal v) {
		return new DecimalFormat("#,##0.000").format(round(v, 3, RoundingMode.HALF_UP.ordinal()));
	}

	public static String format3Pt(String v) {
		return format3Pt(new BigDecimal(v));
	}

	public static String format3Pt(BigDecimal v) {
		return new DecimalFormat("0.000").format(round(v, 3, RoundingMode.HALF_UP.ordinal()));
	}

	public static BigDecimal add(String v1, String v2) {
		return add(new BigDecimal(v1), new BigDecimal(v2));
	}

    public static BigDecimal add(double v1, double v2) {
        return add(new BigDecimal(String.valueOf(v1)), new BigDecimal(String.valueOf(v2)));
    }

	private static BigDecimal add(BigDecimal v1, BigDecimal v2) {
		return v1.add(v2);
	}

	public static BigDecimal subtract(String v1, String v2) {
		return subtract(new BigDecimal(v1), new BigDecimal(v2));
	}

    public static BigDecimal subtract(double v1, double v2) {
        return subtract(new BigDecimal(String.valueOf(v1)), new BigDecimal(String.valueOf(v2)));
    }

	private static BigDecimal subtract(BigDecimal v1, BigDecimal v2) {
		return v1.subtract(v2);
	}

	public static BigDecimal multiply(String v1, String v2) {
		return multiply(new BigDecimal(v1), new BigDecimal(v2));
	}

    public static BigDecimal multiply(double v1, double v2) {
        return multiply(new BigDecimal(String.valueOf(v1)), new BigDecimal(String.valueOf(v2)));
    }

	private static BigDecimal multiply(BigDecimal v1, BigDecimal v2) {
		return v1.multiply(v2);
	}

	/**
	 * 两数相除，默认保留小数点后2位（四舍五入）
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static BigDecimal divide(String v1, String v2) {
		return divide(new BigDecimal(v1), new BigDecimal(v2), 2, RoundingMode.HALF_UP.ordinal());
	}

	/**
	 * 两数相除（四舍五入）
	 * @param v1
	 * @param v2
	 * @param scale 小数点后保留位数
	 * @return
	 */
	public static BigDecimal divide(String v1, String v2, int scale) {
		return divide(new BigDecimal(v1), new BigDecimal(v2), scale, RoundingMode.HALF_UP.ordinal());
	}

	/**
	 * 两数相除，默认保留小数点后2位（四舍五入）
	 * @param v1
	 * @param v2
	 * @return
	 */
    public static BigDecimal divide(double v1, double v2) {
        return divide(new BigDecimal(String.valueOf(v1)), new BigDecimal(String.valueOf(v2)), 2, RoundingMode.HALF_UP.ordinal());
    }

	/**
	 * 两数相除（四舍五入）
	 * @param v1
	 * @param v2
	 * @param scale 小数点后保留位数
	 * @return
	 */
    public static BigDecimal divide(double v1, double v2, int scale) {
        return divide(new BigDecimal(String.valueOf(v1)), new BigDecimal(String.valueOf(v2)), scale, RoundingMode.HALF_UP.ordinal());
    }

	private static BigDecimal divide(BigDecimal v1, BigDecimal v2, int scale, int roundingMode) {
		return v1.divide(v2, scale, roundingMode);
	}

	public static int round(double v) {
		return round(new BigDecimal(v), 0, RoundingMode.HALF_UP.ordinal()).intValue();
	}

	public static BigDecimal round(BigDecimal v, int scale, int roundingMode) {
		if ((scale < 0) || (roundingMode < 0)) {
			throw new IllegalArgumentException(
					"The scale or roundingMode must be a positive integer or zero");
		}

		return v.divide(BigDecimal.ONE, scale, roundingMode);
	}

	public static BigDecimal defaultRound(String v) {
		return defaultRound(new BigDecimal(v));
	}

	public static BigDecimal defaultRound(double v) {
		return defaultRound(new BigDecimal(v));
	}

	public static BigDecimal defaultRound(BigDecimal v) {
		return round(v, 2, RoundingMode.HALF_UP.ordinal());
	}

	public static long format2Long(double v) {
		return (long)v;
	}

    /**
     * 数字金额大写转换，思想先写个完整的然后将如零拾替换成零
     * 要用到正则表达式
     */
	public static String digitUppercase(String v) {
		return digitUppercase(Double.valueOf(v));
	}

    public static String digitUppercase(double n){
        String fraction[] = {"角", "分"};
        String digit[] = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
        String unit[][] = {{"元", "万", "亿"},
                     {"", "拾", "佰", "仟"}};

        String head = n < 0? "负": "";
        n = Math.abs(n);

        String s = "";
        for (int i = 0; i < fraction.length; i++) {
        	s += (digit[(int)(Math.floor(n * 10 * Math.pow(10, i)) % 10)] + fraction[i]).replaceAll("(零.)+", "");
        }
        if(s.length()<1){
            s = "整";
        }
        int integerPart = (int)Math.floor(n);

        for (int i = 0; i < unit[0].length && integerPart > 0; i++) {
            String p ="";
            for (int j = 0; j < unit[1].length && n > 0; j++) {
                p = digit[integerPart%10]+unit[1][j] + p;
                integerPart = integerPart/10;
            }
            s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
        }
        return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", "零元整");
    }

    /**
     * 返回100的整数倍
     * @param inter
     * @return
     */
    public static Double formatHundred(Double inter){
    	BigDecimal temp = divide(new BigDecimal(inter), new BigDecimal(100), 0, RoundingMode.FLOOR.ordinal());
    	return multiply(temp, new BigDecimal(100)).doubleValue();
    }
    /**
     * 返回#XX.XX的FLOOR处理
     * */
    public static Double formatCash(Double cash , Integer term, Integer scale ) {
    	BigDecimal temp = divide(new BigDecimal(cash), new BigDecimal(term), scale, RoundingMode.FLOOR.ordinal());
    	return temp.doubleValue();
    }
    /**
     * 放大100倍的整数值(d * 100)
     *
     * @param inter
     * @return
     */
    public static Integer formatEnlarge(String inter) {
        return multiply(new BigDecimal(inter), new BigDecimal(100)).intValue();
    }
}

