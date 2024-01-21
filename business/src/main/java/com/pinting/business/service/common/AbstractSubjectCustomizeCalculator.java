package com.pinting.business.service.common;

/**
 * 借款科目自定义计算类基类
 * @Project: business
 * @Title: AbstractSubjectCustomizeCalculator.java
 * @author Zhou Changzai
 * @date 2016-9-7下午3:00:28
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public abstract class AbstractSubjectCustomizeCalculator {
	
	/**
	 * 由子类来实现的计算方法，当科目信息表中customized_class字段中有值时
	 * 需要调用此类的子类本实现方法
	 * @param loanId 借款编号
	 * @return 计算出来的数值，保留2位小数
	 */
	public abstract double calculate(int loanId);
}


