package com.pinting.core.util;

import java.math.BigDecimal;

/**
 * 涉及到金额运算，请一定要用MoneyUtil类中的以下方法，否则可能会有精度偏移问题：
加：add
减：subtract
乘：multiply
除：divide

另外，比较使用Double或者BigDecimal的compareTo方法的时候。除了使用String作为中间变量，
否则不能对类型转化后再比较。
 * @Project: business
 * @Title: TestPricision.java
 * @author Zhou Changzai
 * @date 2016-1-1下午12:12:02
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class TestPrecision {
	public static void main(String[] args) {
		//1.new BigDecimal的时候，参数为数字型的时候，就会看你产生精度损失
		//2.改成用字符串作为参数，精度问题解决
		//3.以上为直接自己使用BigDecimal中可能产生的风险，建议使用MoneyUtil进行运算
		System.out.println("a:" + new BigDecimal(4029.59).subtract(new BigDecimal(4000.0)).doubleValue());
		System.out.println("b:" + new BigDecimal("4029.59").subtract(new BigDecimal("4000.0")).doubleValue());
		
		//使用MoneyUtil进行运算,解决精度问题
		System.out.println("c:" + MoneyUtil.subtract(4029.59, 4000.0));
		
		//直接进行运算，哪怕是+运算，也会产生精度问题
		System.out.println("d:" + (2.2+0.2));
		
		//使用MoneyUtil进行运算， 解决精度问题
		System.out.println("e:" + MoneyUtil.add(2.2, 0.2));
		
		//把数值类型转化成BigDecimal的时候，需要借助String来转化，否则会产生精度问题
		System.out.println("f:" + new BigDecimal("2.4").compareTo(new BigDecimal(2.4)));
		
		//1.直接用Double包装类直接比较,照样有精度问题
		//2.直接用Double包装String进行比较，照样有精度问题
		//3.使用BigDecimal，进行比较，解决比较问题
		System.out.println("g:" + new Double(1.2345678901234567890d).compareTo(new Double(1.2345678901234567899d)));
		System.out.println("h:" + new Double("1.2345678901234567890d").compareTo(new Double("1.2345678901234567899d")));
		System.out.println("i:" + new BigDecimal("1.2345678901234567890").compareTo(new BigDecimal("1.2345678901234567899")));
		
		//小数点后面位数太多，比较的时候会产生精度问题（使用MoneyUtil运算后，通过截取，不会有那么多保留位）
		System.out.println("j:" + (1.2345678901234567890d < 1.2345678901234567899d));//精度损失
		System.out.println("k:" + (1.2345678901234567890d >= 1.2345678901234567899d));//精度损失
		System.out.println("l:" + (1.2345678901234567890d == 1.2345678901234567899d));//精度损失
		System.out.println("m:" + (1.234d == 1.234d));//精度未损失，正常比较
		System.out.println("n:" + (2.4-0.2==2.2));//计算过程中精度损失，比较失败
		System.out.println("o:" + (MoneyUtil.subtract(2.4, 0.2).doubleValue()==2.2));//通过工具类运算后，精度不损失，正常
		
		
	}
}


