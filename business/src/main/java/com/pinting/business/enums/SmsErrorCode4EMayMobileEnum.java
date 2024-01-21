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
public enum SmsErrorCode4EMayMobileEnum {

	EM_MOB_1("1", "消息结构错 "),
	EM_MOB_2("2", "命令字错 "),
	EM_MOB_3("3", "消息序号重复 "),
	EM_MOB_4("4", "消息长度错"),
	EM_MOB_5("5", "资费错"),
	EM_MOB_6("6", "超过最大信息长"),
	EM_MOB_7("7", "业务代码错"),
	EM_MOB_8("8", "流量控制错"),
	EM_MOB_9("9", "本网关不负责服务此计费号码"),
	EM_MOB_10("10", "Src_Id错误"),
	EM_MOB_11("11", "Msg_src错误"),
	EM_MOB_12("12", "Fee_terminal_Id错误"),
	EM_MOB_13("13", "Dest_terminal_Id错误"),
	
	EM_MOB_CB_0001("CB:0001", "非神州行预付费用户,号码无效或者空号 清除订购关系"),
	EM_MOB_CB_0005("CB:0005", "PPS用户状态异常（包括未头次使用、储值卡被封锁、储值卡进入保留期、储值卡挂失）移动用户帐户数据异常 清除订购关系 "),
	EM_MOB_CB_0007("CB:0007", "用户余额不足,不能扣费，影响包月话单 连续两个月扣费不成功，清除订购关系"),
	EM_MOB_CA_0054("CA:0054", "发送消息失败,超时未接收到响应消息 移动内部错误 不处理"),
	EM_MOB_CB_0002("CB:0002", "数据库操作失败"),
	EM_MOB_CB_0053("CB:0053", "梦网用户不存在,号码无效或者空号 清除订购关系"),
	EM_MOB_EXPIRED("EXPIRED", "因为用户长时间关机或者不在服务区等导致的短消息超时没有递交到用户手机上 超时 不处理"),
	EM_MOB_REJECTD("REJECTD", "消息因为某些原因被拒绝 不同的网关具有不同的错误原因 和运营商对日志"),
	EM_MOB_UNDELIV("UNDELIV", "全球通用户因为状态不正确如处于停机、挂起等状态而导致用户无法接收到短信 不可及 不处理 "),
	EM_MOB_MB_0066("MB:0066", "短信中心回的，超作最大发送次数 可能是手机满了。"),
	EM_MOB_MK_0015("MK:0015", "可能是手机满了。"),
	EM_MOB_DA_0054("DA:0054", "出现这个问题是由于网关发送鉴权批价请求到MISC时，MISC不给响应或者网关和MISC连接阻塞引起；"),
	EM_MOB_DB_0100("DB:0100", "手机号码不存在"),
	EM_MOB_DB_0101("DB:0101", "手机号码错误"),
	EM_MOB_DB_0102("DB:0102", "用户停机 用户冲值后，要主动上行一条信息到SP，才能激活用户的短信接收服务"),
	EM_MOB_DB_0103("DB:0103", "用户欠费"),
	EM_MOB_DB_0104("DB:0104", "用户没有使用该业务的权限"),
	EM_MOB_DB_0105 ("DB:0105 ", "业务代码错误"),
	EM_MOB_DB_0106("DB:0106", "服务代码错误"),
	EM_MOB_DB_0107("DB:0107", "业务不存在"),
	EM_MOB_DB_0108("DB:0108", "该业务暂停服务"),
	EM_MOB_DB_0109("DB:0109", "该服务种类不存在"),
	EM_MOB_DB_0110 ("DB:0110 ", "该服务种类尚未开通 "),
	EM_MOB_DB_0111 ("DB:0111 ", "该业务尚未开通 "),
	EM_MOB_DB_0112 ("DB:0112 ", "SP代码错误 "),
	EM_MOB_DB_0113 ("DB:0113 ", "SP不存在 "),
	EM_MOB_DB_0114 ("DB:0114", "SP暂停服务 "),
	EM_MOB_DB_0115 ("DB:0115 ", "用户没有定购该业务 "),
	EM_MOB_DB_0116("DB:0116", "用户暂停定购该业务 "),
	EM_MOB_DB_0117("DB:0117", "该业务不能对该用户开放 "),
	EM_MOB_DB_0118("DB:0118", "用户已经订购了该业务 "),
	EM_MOB_DB_0119("DB:0119", " 用户不能取消该业务 "),
	EM_MOB_DB_0120("DB:0120", "话单格式错误 "),
	EM_MOB_DB_0121("DB:0121", " 没有该类业务 "),
	EM_MOB_DB_0122("DB:0122", "接收异常 "),
	EM_MOB_DB_0123("DB:0123", "业务价格为负 "),
	EM_MOB_DB_0124("DB:0124", "业务价格格式错误"),
	EM_MOB_DB_0125("DB:0125", "业务价格超出范围 "),
	EM_MOB_DB_0126("DB:0126", "该用户不是神州行用户 "),
	EM_MOB_DB_0127("DB:0127", "该用户没有足够的余额 "),
	EM_MOB_DB_0128("DB:0128", "补款,冲正失败 "),
	EM_MOB_DB_0129("DB:0129", "用户已经是梦网用户 "),
	EM_MOB_DB_0130("DB:0130", "用户在BOSS中没有相关用户数据 "),
	EM_MOB_DB_0131("DB:0131", "BOSS系统数据同步出错"),
	EM_MOB_DB_0132("DB:0132", "相关信息不存在 "),
	EM_MOB_DB_0133("DB:0133", "用户数据同步出错 "),
	EM_MOB_DB_0134("DB:0134", "SP数据同步出错 "),
	EM_MOB_DB_0135("DB:0135", "业务数据同步出错 "),
	EM_MOB_DB_0136("DB:0136", "用户密码错误 "),
	EM_MOB_DB_0137("DB:0137", "伪码信息错误"),
	EM_MOB_DB_0138("DB:0138", "用户相关信息不存在"),
	EM_MOB_DB_0140("DB:0140", "用户未点播该业务 "),
	EM_MOB_DB_9001("DB:9001", "网络异常"),
	EM_MOB_DB_9007("DB:9007", "业务网关超过限制的流量"),
	EM_MOB_CA_0051("CA:0051", "尚未建立连接 移动内部错误 不处理 "),
	EM_MOB_CA_0052("CA:0052", "尚未成功登录 移动内部错误 不处理 "),
	EM_MOB_CA_0111("CA:0111", "SCP厂家自定义的错误码 移动内部错误 不处理"),
	EM_MOB_CB_0016("CB:0016", "参数错误 移动内部错误 不处理 "),
	EM_MOB_CB_0018("CB:0018", "重复发送消息序列号msgid相同的计费请求消息 移动内部错误 不处理 "),
	EM_MOB_CB_0022("CB:0022", "SCP互联失败 移动内部错误 不处理 "),
	EM_MOB_CB_0047("CB:0047", "过期用户或者用户不支持梦网业务 清除订购关系"),
	

	
	
	
	//==================管理平台DSMP返回的错误==============
	//DA类错误：短信网关和DSMP之间连接发送时产生错误的状态报告 
//	EM_MOB_DA_0054("DA:0054", "超时未接收到响应消息 移动内部错误 不处理"),
	//DB类错误：DSMP返回处理错误结果的状态报告 
//	EM_MOB_DB_0101("DB:0101", "手机号码错误 号码无效或者空号 清除订购关系 "),
//	EM_MOB_DB_0102("DB:0102", "用户停机 用户已经停机 若连续两个月停机，清除订购关系"),
//	EM_MOB_DB_0103("DB:0103", "用户欠费"),
//	EM_MOB_DB_0104("DB:0104", "用户没有使用该业务的权限"),
//	EM_MOB_DB_0105("DB:0105", "业务代码错误"),
//	EM_MOB_DB_0106("DB:0106", "服务代码错误"),
//	EM_MOB_DB_0107("DB:0107", "业务不存在 多用于点播业务鉴权 程序检查LinkID和业务代码是否正确"),
//	EM_MOB_DB_0108("DB:0108", "该业务暂停服务"),
//	EM_MOB_DB_0109("DB:0109", "该服务种类不存在"),
//	EM_MOB_DB_0110("DB:0110", "该服务种类尚未开通"),
//	EM_MOB_DB_0111("DB:0111", "该业务尚未开通"),
//	EM_MOB_DB_0112("DB:0112", "SP代码错误"),
//	EM_MOB_DB_0113("DB:0113", "SP不存在"),
//	EM_MOB_DB_0114("DB:0114", "SP暂停服务"),
//	EM_MOB_DB_0115("DB:0115", "用户没有定购该业务"),
//	EM_MOB_DB_0116("DB:0116", "用户暂停定购该业务"),
//	EM_MOB_DB_0117("DB:0117", "该业务不能对该用户开放"),
//	EM_MOB_DB_0118("DB:0118", "用户已经订购了该业务"),
//	EM_MOB_DB_0119("DB:0119", "用户不能取消该业务"),
//	EM_MOB_DB_0120("DB:0120", "话单格式错误"),
//	EM_MOB_DB_0121("DB:0121", "没有该类业务"),
//	EM_MOB_DB_0122("DB:0122", "接收异常"),
//	EM_MOB_DB_0123("DB:0123", "业务价格为负"),
//	EM_MOB_DB_0124("DB:0124", "业务价格格式错误"),
//	EM_MOB_DB_0125("DB:0125", "业务价格超出范围"),
//	EM_MOB_DB_0126("DB:0126", "该用户不是神州行用户"),
//	EM_MOB_DB_0127("DB:0127", "该用户没有足够的余额"),
//	EM_MOB_DB_0128("DB:0128", "补款,冲正失败"),
//	EM_MOB_DB_0129("DB:0129", "用户已经是梦网用户"),
//	EM_MOB_DB_0130("DB:0130", "用户在BOSS中没有相关用户数据"),
//	EM_MOB_DB_0131("DB:0131", "BOSS系统数据同步出错"),
//	EM_MOB_DB_0132("DB:0132", "相关信息不存在"),
//	EM_MOB_DB_0133("DB:0133", "用户数据同步出错"),
//	EM_MOB_DB_0134("DB:0134", "SP数据同步出错"),
//	EM_MOB_DB_0135("DB:0135", "业务数据同步出错"),
//	EM_MOB_DB_0136("DB:0136", "用户密码错误"),
//	EM_MOB_DB_0137("DB:0137", "伪码信息错误"),
//	EM_MOB_DB_0138("DB:0138", "用户相关信息不存在"),
//	
//	EM_MOB_DB_9001("DB:9001", "网络异常 移动内部错误 不处理"),
//	EM_MOB_DB_9007("DB:9007", "业务网关超过限制的流量 移动内部错误 不处理"),
	
	//==================下一级短信网关ISMG返回的错误==============
	//IB类错误：下一级ISMG返回错误响应消息时的状态报告
	EM_MOB_IB_0008("IB:0008", "流量控制错 移动内部错误 不处理"),
	EM_MOB_IB_0009("IB:0009", "前转判断错误 移动内部错误 不处理"),
	EM_MOB_IB_0070("IB:0070", "网络断连或者目的设备关闭端口 移动内部错误 不处理"),
	EM_MOB_IB_0100("IB:0100", "移动内部错误 不处理"),
	EM_MOB_IB_0113("IB:0113", "移动内部错误 不处理"),
	EM_MOB_IB_0255("IB:0255", "移动内部错误 不处理"),
	//IC类错误：下一级ISMG无响应消息时的状态报告
	EM_MOB_IC_0154("IC:0154", "移动内部错误 不处理"),
	
	
	//==================短信中心SMSC返回的错误=====================
	//MA类错误：ISMG连接SMSC产生错误时的状态报告 
	EM_MOB_MA_0051("MA:0051", "尚未建立连接 移动内部错误 不处理"),
	EM_MOB_MA_0054("MA:0054", "超时未接收到响应消息 移动内部错误 不处理"),
	EM_MOB_MA_0191("MA:0191", "SMSC厂家自定义的错误码 移动内部错误 不处理"),
	
	//MB类错误：SMSC返回错误时的状态报告 
	EM_MOB_MB_0019("MB:0019", "移动内部错误 不处理"),
	EM_MOB_MB_0020("MB:0020", "无效的SYSTEMID 移动内部错误 不处理"),
	EM_MOB_MB_0065("MB:0065", "目的地址错误 移动内部错误 不处理"),
//	EM_MOB_MB_0066("MB:0066", "无效的定时时间 移动内部错误 不处理"),
	EM_MOB_MB_0070("MB:0070", "移动内部错误 不处理"),
	EM_MOB_MB_0077("MB:0077", "移动内部错误 不处理"),
	EM_MOB_MB_0078("MB:0078", "移动内部错误 不处理"),
	EM_MOB_MB_0145("MB:0145", "SMSC厂家自定义的错误码 移动内部错误 不处理"),
	EM_MOB_MB_0147("MB:0147", "SMSC厂家自定义的错误码 移动内部错误 不处理"),
	EM_MOB_MB_0192("MB:0192", "SMSC厂家自定义的错误码 移动内部错误 不处理"),
	EM_MOB_MB_0193("MB:0193", "SMSC厂家自定义的错误码 移动内部错误 不处理"),
	EM_MOB_MB_0241("MB:0241", "SMSC厂家自定义的错误码 移动内部错误 不处理"),
	EM_MOB_MB_0244("MB:0244", "SMSC厂家自定义的错误码 移动内部错误 不处理"),
	EM_MOB_MB_0250("MB:0250", "SMSC厂家自定义的错误码 移动内部错误 不处理"),
	EM_MOB_MB_4024("MB:4024", "移动内部错误 不处理"),
	EM_MOB_MB_4025("MB:4025", "移动内部错误 不处理"),
	
	//MC类错误：SMSC无返回时的状态报告
	EM_MOB_MC_0015("MC:0015", "移动内部错误 不处理"),
	EM_MOB_MC_0021("MC:0021", "移动内部错误 不处理"),
	EM_MOB_MC_0055("MC:0055", "移动内部错误 不处理"),
	EM_MOB_MC_0151("MC:0151", "移动内部错误 不处理"),
	EM_MOB_MC_0199("MC:0199", "移动内部错误 不处理"),
	//MH类错误：
	EM_MOB_MH_0000("MH:0000", "移动内部错误 不处理"),
	//MI类错误：同EXPIRED
	EM_MOB_MI_0000("MI:0000", "移动内部错误 不处理"),
	EM_MOB_MI_0008("MI:0008", "移动内部错误 不处理"),
	EM_MOB_MI_0013("MI:0013", "移动内部错误 不处理"),
	EM_MOB_MI_0022("MI:0022", "移动内部错误 不处理"),
	EM_MOB_MI_0024("MI:0024", "移动内部错误 不处理"),
	EM_MOB_MI_0029("MI:0029", "移动内部错误 不处理"),
	EM_MOB_MI_0036("MI:0036", "移动内部错误 不处理"),
	EM_MOB_MI_0045("MI:0045", "移动内部错误 不处理"),
	EM_MOB_MI_0057("MI:0057", "移动内部错误 不处理"),
	EM_MOB_MI_0255("MI:0255", "移动内部错误 不处理"),
	//MJ类错误：
	EM_MOB_MJ_0000("MJ:0000", "移动内部错误 不处理"),
	//MK类错误：同UNDELIV
	EM_MOB_MK_0001("MK:0001", "不存在的用户"),
	EM_MOB_MK_0002("MK:0002", "停机"),
	EM_MOB_MK_0003("MK:0003", "非法用户"),
	EM_MOB_MK_0004("MK:0004", "电信业务不支持  不支持短信功能"),
	EM_MOB_MK_0005("MK:0005", "呼叫被禁止 用户的语音功能被关闭造成短信功能也一起停用"),
	EM_MOB_MK_0008("MK:0008", "用户不在服务区"),
//	EM_MOB_MK_0015("MK:0015", "MS(MobileStation)端错误 手机端问题"),
	EM_MOB_MK_0016("MK:0016", "MS未装备 手机端问题"),
	EM_MOB_MK_0017("MK:0017", "手机内存满"),
	EM_MOB_MK_0023("MK:0023", "用户忙"),
	EM_MOB_MK_0024("MK:0024", "用户关机")

	;
	private SmsErrorCode4EMayMobileEnum(String code, String description) {
		this.code = code;
		this.description = description;
	}

	private String code;

	private String description;

	private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

	static {
		for (SmsErrorCode4EMayMobileEnum s : EnumSet.allOf(SmsErrorCode4EMayMobileEnum.class)) {
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
