package com.pinting.business.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 亿美电信错误码
 * @author bianyatian
 * @2016-10-29 上午11:15:22
 */
public enum SmsErrorCode4EMayTelecomEnum {

	EM_TELE_1("1", "系统忙"),
	EM_TELE_2("2", "达到最大连接数"),
	EM_TELE_10("10", "消息包错误"),
	EM_TELE_11("11", "命令字错误"),
	EM_TELE_12("12", "序列号错误"),
	EM_TELE_20("20", "IP错"),
	EM_TELE_21("21", "登陆失败"),
	EM_TELE_22("22", "版本号错误"),
	EM_TELE_30("30", "无效的消息类型错误"),
	EM_TELE_31("31", "无效的优先级"),
	EM_TELE_32("32", "无效的付费类型"),
	EM_TELE_33("33", "无效的Fee Code"),
	EM_TELE_34("34", "编码类型错误"),
	EM_TELE_36("36", "消息长度错误"),
	EM_TELE_37("37", "超过有效期"),
	EM_TELE_38("38", "无效的查询类型"),
	EM_TELE_39("39", "路由失败"),
	EM_TELE_40("40", "无效的Fixed Fee"),
	EM_TELE_43("43", "无效的业务代码"),
	EM_TELE_44("44", "无效的有效期"),
	EM_TELE_45("45", "无效的计划下发时间"),
	EM_TELE_46("46", "无效的源号码"),
	EM_TELE_47("47", "无效的目的号码"),
	EM_TELE_48("48", "无效的计费号码"),
	EM_TELE_49("49", "无效的ICP ID"),
	EM_TELE_50("50", "非预付费用户"),
	EM_TELE_51("51", "余额不足"),
	EM_TELE_52("52", "非注册用户"),
	EM_TELE_53("53", "非注册ICP"),
	EM_TELE_54("54", "无效的帐号"),
	EM_TELE_100("100", "手机号码不存在"),
	EM_TELE_101("101", "手机号码错误"),
	EM_TELE_102("102", "用户停机"),
	EM_TELE_103("103", "用户欠费"),
	EM_TELE_104("104", "用户没有使用该业务的权限"),
	EM_TELE_105("105", "业务代码错误"),
	EM_TELE_106("106", "服务代码错误"),
	EM_TELE_107("107", "业务不存在"),
	EM_TELE_108("108", "该业务暂停服务"),
	EM_TELE_109("109", "该服务种类不存在"),
	EM_TELE_110("110", "该服务种类尚未开通"),
	EM_TELE_111("111", "该业务尚未开通"),
	EM_TELE_112("112", "SP 代码错误"),
	EM_TELE_113("113", "SP 不存在"),
	EM_TELE_114("114", "SP 暂停服务"),
	EM_TELE_115("115", "用户没有定购该业务"),
	EM_TELE_116("116", "用户暂停定购该业务"),
	EM_TELE_117("117", "该业务不能对该用户开放"),
	EM_TELE_118("118", "用户已经订购了该业务"),
	EM_TELE_119("119", "用户不能取消该业务"),
	EM_TELE_120("120", "话单格式错误"),
	EM_TELE_121("121", "没有该类业务"),
	EM_TELE_122("122", "接收异常"),
	EM_TELE_123("123", "业务价格为负"),
	EM_TELE_124("124", "业务价格格式错误"),
	EM_TELE_125("125", "业务价格超出范围"),
	EM_TELE_126("126", "保留"),
	EM_TELE_127("127", "响应超时"),
	EM_TELE_128("128", "网关处理系统错误"),
	EM_TELE_129("129", "预付费鉴权失败"),
	EM_TELE_130("130", "用户在BOSS 中没有相关用户数据"),
	EM_TELE_131("131", "BOSS 系统数据同步出错"),
	EM_TELE_132("132", "相关信息不存在"),
	EM_TELE_133("133", "用户数据同步出错"),
	EM_TELE_134("134", "SP 数据同步出错"),
	EM_TELE_135("135", "业务数据同步出错"),
	EM_TELE_136("136", "用户密码错误"),
	EM_TELE_137("137", "伪码信息错误"),
	EM_TELE_138("138", "用户相关信息不存在"),
	EM_TELE_139("139", "没有状态报告返回"),
	EM_TELE_140("140", "下发serviceid与用户上行serviceid不匹配"),
	EM_TELE_141("141", "扣月租失败，业务处于试用期或已经扣除月租"),
	EM_TELE_142("142", "系统不支持该类计费方式"),
	EM_TELE_143("143", "SP处于测试中，不能访问"),
	EM_TELE_144("144", "不能给该用户下发消息，与SP没有订购关系或最近没有访问该SP"),
	EM_TELE_145("145", "linkId填写错误"),
	EM_TELE_146("146", "网关下发失败，超时错误记录"),
	EM_TELE_147("147", "SP下发参数错误"),
	EM_TELE_148("148", "找不到计费数据"),
	EM_TELE_149("149", "目前不支持此种计费模式"),
	EM_TELE_150("150", "下发次数超出业务限制"),
	EM_TELE_151("151", "扣款确认时没有对应上行或下行的纪录"),
	EM_TELE_152("152", "用户消费达到限额"),
	EM_TELE_153("153", "目的用户不在上行的内容中"),
	EM_TELE_154("154", "不能通过web订购定位业务"),
	EM_TELE_155("155", "反向退订时有正常订阅关系但退订失败"),
	EM_TELE_156("156", "反向退订时订购关系不存在但此用户曾经订购过此业务"),
	EM_TELE_157("157", "反向退订时订购关系不存在且此用户未曾订购过此业务"),
	EM_TELE_158("158", "反向退订无权限"),
	EM_TELE_159("159", "反向订购无权限"),
	EM_TELE_160("160", "反向订购时无订购关系但订购失败"),
	EM_TELE_161("161", "反向订购时有正常订购关系"),
	EM_TELE_162("162", "业务没有直接扣费权限"),
	EM_TELE_163("163", "用户为黑名单用户"),
	EM_TELE_164("164", "不支持反向定购定位业务"),
	EM_TELE_165("165", "全国SPMS群发请求格式错误"),
	EM_TELE_166("166", "全国SPMS号码错误"),
	EM_TELE_255("255", "鉴权批价系统错误"),
	EM_TELE_301("301", "用户不能通信"),
	EM_TELE_302("302", "用户忙"),
	EM_TELE_303("303", "终端无此部件号"),
	EM_TELE_304("304", "非法用户"),
	EM_TELE_305("305", "用户在黑名单内(短信中心的黑名单)"),
	EM_TELE_306("306", "系统错误"),
	EM_TELE_307("307", "用户内存满"),
	EM_TELE_308("308", "非信息终端"),
	EM_TELE_309("309", "数据错误"),
	EM_TELE_310("310", "数据丢失"),
	EM_TELE_999("999", "未知错误"),
	
	EM_TELE_5000("-5000", "系统错误"),
	EM_TELE_6000("-6000", "网络错误"),
	EM_TELE_7000("-7000", "参数错误"),
	EM_TELE_7001("-7001", "TransactionID不正确"),
	EM_TELE_3002("-3002", "数据包格式不正确"),
	EM_TELE_3003("-3003", "数据校验不正确"),
	EM_TELE_3004("-3004", "计费用户号码（ChargeTermID）无效"),
	EM_TELE_3005("-3005", "SP服务代码（SPCode）无效"),
	EM_TELE_8000("-8000", "业务逻辑错误"),
	EM_TELE_8001("-8001", "SP服务代码已经存在"),
	
	;
	private SmsErrorCode4EMayTelecomEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	private String code;

	private String description;

	private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	static {
		for (SmsErrorCode4EMayTelecomEnum s : EnumSet.allOf(SmsErrorCode4EMayTelecomEnum.class)) {
			Map<String, Object> lookup = new HashMap<String, Object>();
			lookup.put("code", s.getCode());
			lookup.put("description", s.getDescription());
			list.add(lookup);
		}
	}

	public static List<Map<String, Object>> getMapList() {
		return list;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
