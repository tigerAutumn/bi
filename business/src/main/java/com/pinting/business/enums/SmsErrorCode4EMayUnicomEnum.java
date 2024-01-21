package com.pinting.business.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 亿美联通错误码
 * @author bianyatian
 * @2016-10-29 上午11:15:22
 */
public enum SmsErrorCode4EMayUnicomEnum {

	EM_UNI_1("1", "非法登陆，一般为用户名密码错误"),
	EM_UNI_2("2", "重复登陆"),
	EM_UNI_3("3", "连接过多，超过限制"),
	EM_UNI_4("4", "登陆类型错误"),
	EM_UNI_5("5", "参数格式错误"),
	EM_UNI_6("6", "非法手机号码，一般指ChargeNumber和UserNumber的填写不规范"),
	EM_UNI_7("7", "消息ID错"),
	EM_UNI_8("8", "消息长度错误"),
	EM_UNI_9("9", "非法序列号，指序列号重复，第一个序列号即源节点编码错误"),
	
	EM_UNI_32("32", "系统失败（一般指系统消息队列满）"),
	EM_UNI_33("33", "超过流量限制，指发送方在一秒内的流量已经达到限制，拒绝发送"),
	EM_UNI_34("34", "登陆太频繁，指同一个SP或者SMG在一分钟内登陆次数已经达到限制，拒绝登陆"),
	EM_UNI_90("90", "SGIP包长度错误（用于RESP应答）"),
	EM_UNI_21("21", "目的地址不可达"),
	EM_UNI_22("22", "路由错"),
	EM_UNI_23("23", "路由不存在"),
	EM_UNI_24("24", "计费号码无效"),
	EM_UNI_25("25", "用户不能通信"),
	EM_UNI_26("26", "手机内存不足"),
	EM_UNI_27("27", "手机不支持短消息"),
	EM_UNI_28("28", "手机接收短消息出现错误"),
	EM_UNI_29("29", "不知道的用户"),
	EM_UNI_30("30", "不提供此功能"),
	EM_UNI_31("31", "非法设备"),
	EM_UNI_93("93", "后付费用户鉴权失败"),
	EM_UNI_94("94", "预付费用户扣费失败"),
	EM_UNI_201("201", "业务代码未分配"),
	EM_UNI_202("202", "信息费错误"),
	EM_UNI_203("203", "LINKID不匹配"),
	EM_UNI_204("204", "用户未订购"),
	EM_UNI_205("205", "下发用户数不为1"),
	EM_UNI_206("206", "该包格式错误"),
	EM_UNI_207("207", "下发的MT超过最大条数"),
	EM_UNI_208("208", "第三方付费"),
	EM_UNI_209("209", "SP不能将MOFLAG填为3"),
	EM_UNI_210("210", "WEB点播命令字不存在"),
	EM_UNI_211("211", "订制关系成功建立,但通知由SP管理平台发送,SP的MT被拦截"),
	EM_UNI_212("212", "包月话单被拦截"),
	EM_UNI_213("213", "SPNUMBER未分或不合法"),
	EM_UNI_214("214", "停机用户"),
	EM_UNI_215("215", "离网用户"),
	EM_UNI_216("216", "计费号码错"),
	EM_UNI_217("217", "重复订购"),
	EM_UNI_218("218", "重复点播"),
	EM_UNI_219("219", "被禁止的SP非法登录"),
	EM_UNI_220("220", "重复登录"),
	EM_UNI_221("221", "连接过多"),
	EM_UNI_222("222", "登录类型错"),
	EM_UNI_223("223", "参数格式错"),
	EM_UNI_224("224", "非法手机号码"),
	EM_UNI_225("225", "消息ID错"),
	EM_UNI_226("226", "信息长度错"),
	EM_UNI_227("227", "非法序列号"),
	EM_UNI_228("228", "SP节点编号错"),
	EM_UNI_229("229", "节点忙"),
	EM_UNI_230("230", "不提供此功能"),
	EM_UNI_231("231", "等待应答超时"),
	EM_UNI_232("232", "系统错"),
	EM_UNI_233("233", "将发NOTISP包，原流程需要终止"),
	EM_UNI_234("234", "将发NOTIUSER包，原流程需要终止"),
	EM_UNI_235("235", "鉴权中心处理超时"),
	EM_UNI_236("236", "超过最大重试次数"),
	EM_UNI_237("237", "白名单SP，直接返回鉴权成功"),
	EM_UNI_238("238", "手机短信到SPPORTAL定制且不需要确认,这时返回该错误同时发一个NOTITOSP"),
	EM_UNI_239("239", "MO鉴权的时候LINKID异常"),
	EM_UNI_240("240", "用户确认的时候回复N，流程终止，MO不上发"),
	EM_UNI_241("241", "错误的包类型"),
	EM_UNI_242("242", "包长度错误"),
	EM_UNI_243("243", "MD5验证码错误"),
	EM_UNI_5001("5001", "连接SP失败"),
	EM_UNI_5002("5002", "发送BIND到SP失败"),
	EM_UNI_5003("5003", "接收SP的BIND_RESP失败"),
	EM_UNI_5004("5004", "SP返回BIND_RESP登陆失败"),
	EM_UNI_5005("5005", "SP返回的BIND_RESP长度错误"),
	EM_UNI_5007("5007", "SP返回BIND_RESP超时"),
	EM_UNI_5011("5011", "发送MO,REPORT到SP失败"),
	EM_UNI_5012("5012", "SP返回的RESPONSE的CommandID不匹配"),
	EM_UNI_5014("5014", "SP返回的RESPONSE的CommandID不匹配"),
	EM_UNI_5015("5015", "与SP连接断开"),
	EM_UNI_5021("5021", "与SP的连接关闭"),
	EM_UNI_5017("5017", "SP返回RESPONSE超时"),
	
	;
	private SmsErrorCode4EMayUnicomEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	private String code;

	private String description;

	private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	static {
		for (SmsErrorCode4EMayUnicomEnum s : EnumSet.allOf(SmsErrorCode4EMayUnicomEnum.class)) {
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
