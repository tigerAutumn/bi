package com.pinting.schedule.mongodb.service;


public interface MongoStatisticsService {

	/**
	 * 主商户汇总
	 * */
	public void generateMainCheckGacha();
	
	/**
	 * 辅商户汇总
	 * */
	public void generateAssistCheckGacha();
	
	/**
	 * 测试接口
	 * */
	public void testMongoInterface();
	
	/**
	 * 恒丰对账结果汇总
	 */
	public void generateHfBankCheckGacha();
	
}
