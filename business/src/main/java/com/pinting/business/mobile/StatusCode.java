package com.pinting.business.mobile;

/**
 * 根据错误码获取具体的错误信息
 * @Project: business
 * @Title: StatusCode.java
 * @author yanwl
 * @date 2016-01-19 下午12:47:54
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public enum StatusCode {
    /** 返回状态吗 */
    STATUS_CODE_200("200", "请求成功 "),
    STATUS_CODE_201("201", "创建成功 "),
    STATUS_CODE_202("202", "更新成功 "),
    STATUS_CODE_400("400", "请求的地址不存在或者包含不支持的参数 "),
    STATUS_CODE_401("401", "未授权 "),
    STATUS_CODE_403("403", "被禁止访问 "),
    STATUS_CODE_404("404", "请求的资源不存在 "),
    STATUS_CODE_500("500", "内部错误 "),
    /** 服务错误码信息 */
    ERROR_CODE_1000("1000", "请求参数没有appkey"),
    ERROR_CODE_1001("1001", "请求参数没有payload"),
    ERROR_CODE_1002("1002", "请求参数payload中没有body"),
    ERROR_CODE_1003("1003", "display_type为message时，请求参数没有custom"),
    ERROR_CODE_1004("1004", "请求参数没有display_type"),
    ERROR_CODE_1005("1005", "img url格式不对，请以https或者http开始"),
    ERROR_CODE_1006("1006", "sound url格式不对，请以https或者http开始"),
    ERROR_CODE_1007("1007", "url格式不对，请以https或者http开始"),
    ERROR_CODE_1008("1008", "display_type为notification时，body中ticker不能为空"),
    ERROR_CODE_1009("1009", "display_type为notification时，body中title不能为空"),
    ERROR_CODE_1010("1010", "display_type为notification时，body中text不能为空"),
    ERROR_CODE_1011("1011", "play_vibrate的值只能为true或者false"),
    ERROR_CODE_1012("1012", "play_lights的值只能为true或者false"),
    ERROR_CODE_1013("1013", "play_sound的值只能为true或者false"),
    ERROR_CODE_1014("1014", "task-id没有找到"),
    ERROR_CODE_1015("1015", "请求参数中没有device_tokens"),
    ERROR_CODE_1016("1016", "请求参数没有type"),
    ERROR_CODE_1017("1017", "production_mode只能为true或者false"),
    ERROR_CODE_1018("1018", "appkey错误：指定的appkey尚未开通推送服务"),
    ERROR_CODE_1019("1019", "display_type填写错误"),
    ERROR_CODE_1020("1020", "应用组中尚未添加应用"),
    ERROR_CODE_2000("2000", "该应用已被禁用"),
    ERROR_CODE_2001("2001", "过期时间必须大于当前时间"),
    ERROR_CODE_2002("2002", "定时发送时间必须大于当前时间"),
    ERROR_CODE_2003("2003", "过期时间必须大于定时发送时间"),
    ERROR_CODE_2004("2004", "IP白名单尚未添加, 请到网站后台添加您的服务器IP白名单"),
    ERROR_CODE_2005("2005", "该消息不存在"),
    ERROR_CODE_2006("2006", "validation token错误"),
    ERROR_CODE_2007("2007", "未对请求进行签名"),
    ERROR_CODE_2008("2008", "json解析错误"),
    ERROR_CODE_2009("2009", "请填写alias或者file_id"),
    ERROR_CODE_2010("2010", "与alias对应的device_tokens为空"),
    ERROR_CODE_2011("2011", "alias个数已超过50"),
    ERROR_CODE_2012("2012", "此appkey今天的广播数已超过3次"),
    ERROR_CODE_2013("2013", "消息还在排队，请稍候再查询"),
    ERROR_CODE_2014("2014", "消息取消失败，请稍候再试"),
    ERROR_CODE_2015("2015", "device_tokens个数已超过50"),
    ERROR_CODE_2016("2016", "请填写filter"),
    ERROR_CODE_2017("2017", "添加tag失败"),
    ERROR_CODE_2018("2018", "请填写file_id"),
    ERROR_CODE_2019("2019", "与此file_id对应的文件不存在"),
    ERROR_CODE_2020("2020", "服务正在升级中，请稍候再试"),
    ERROR_CODE_2021("2021", "appkey不存在"),
    ERROR_CODE_2022("2022", "payload长度过长"),
    ERROR_CODE_2023("2023", "文件上传失败，请重试"),
    ERROR_CODE_2024("2024", "限速值必须为正整数"),
    ERROR_CODE_2025("2025", "aps字段不能为空"),
    ERROR_CODE_2026("2026", "1分钟内发送任务次数超出3次"),
    ERROR_CODE_2027("2027", "签名不正确"),
    ERROR_CODE_2028("2028", "时间戳已过期"),
    ERROR_CODE_2029("2029", "content内容不能为空"),
    ERROR_CODE_2030("2030", "launch_from/not_launch_from条件中的日期须小于发送日期"),
    ERROR_CODE_2031("2031", "filter格式不正确"),
    ERROR_CODE_2032("2032", "未上传生产证书，请到Web后台上传"),
    ERROR_CODE_2033("2033", "未上传开发证书，请到Web后台上传"),
    ERROR_CODE_2034("2034", "证书已过期"),
    ERROR_CODE_2035("2035", "定时任务证书过期"),
    ERROR_CODE_2036("2036", "时间戳格式错误"),
    ERROR_CODE_2038("2038", "文件上传失败"),
    ERROR_CODE_2039("2039", "时间格式必须是yyyy-MM-dd HH:mm:ss"),
    ERROR_CODE_2040("2040", "过期时间不能超过7天"),
    ERROR_CODE_3000("3000", "数据库错误"),
    ERROR_CODE_3001("3001", "数据库错误"),
    ERROR_CODE_3002("3002", "数据库错误"),
    ERROR_CODE_3003("3003", "数据库错误"),
    ERROR_CODE_3004("3004", "数据库错误"),
    ERROR_CODE_4000("4000", "系统错误"),
    ERROR_CODE_4001("4001", "系统忙"),
    ERROR_CODE_4002("4002", "操作失败"),
    ERROR_CODE_4003("4003", "appkey格式错误"),
    ERROR_CODE_4004("4004", "消息类型格式错误"),
    ERROR_CODE_4005("4005", "msg格式错误"),
    ERROR_CODE_4006("4006", "body格式错误"),
    ERROR_CODE_4007("4007", "deliverPolicy格式错误"),
    ERROR_CODE_4008("4008", "失效时间格式错误"),
    ERROR_CODE_4009("4009", "单个服务器队列已满"),
    ERROR_CODE_4010("4010", "设备号格式错误"),
    ERROR_CODE_4011("4011", "消息扩展字段无效"),
    ERROR_CODE_4012("4012", "没有权限访问"),
    ERROR_CODE_4013("4013", "异步发送消息失败"),
    ERROR_CODE_4014("4014", "appkey和device_tokens不对应"),
    ERROR_CODE_4015("4015", "没有找到应用信息"),
    ERROR_CODE_4016("4016", "文件编码有误"),
    ERROR_CODE_4017("4017", "文件类型有误"),
    ERROR_CODE_4018("4018", "文件远程地址有误"),
    ERROR_CODE_4019("4019", "文件描述信息有误"),
    ERROR_CODE_4020("4020", "device_token有误(注意，友盟的device_token是严格的44位字符串)"),
    ERROR_CODE_4021("4021", "HSF异步服务超时"),
    ERROR_CODE_4022("4022", "appkey已经注册"),
    ERROR_CODE_4023("4023", "服务器网络异常"),
    ERROR_CODE_4024("4024", "非法访问"),
    ERROR_CODE_4025("4025", "device-token全部失败"),
    ERROR_CODE_4026("4026", "device-token部分失败"),
    ERROR_CODE_4027("4027", "拉取文件失败"),
    ERROR_CODE_5000("5000", "device_token错误"),
    ERROR_CODE_5001("5001", "证书不存在"),
    ERROR_CODE_5002("5002", "p,d是umeng保留字段"),
    ERROR_CODE_5003("5003", "alert字段不能为空"),
    ERROR_CODE_5004("5004", "alert只能是String类型"),
    ERROR_CODE_5005("5005", "device_token格式错误"),
    ERROR_CODE_5006("5006", "创建socket错误"),
    ERROR_CODE_5007("5007", "certificate_revoked错误"),
    ERROR_CODE_5008("5008", "certificate_unkown错误"),
    ERROR_CODE_5009("5009", "handshake_failure错误"),
    ;

    private String code;

    private String desciption;

    private StatusCode(String code, String desciption) {
        this.code = code;
        this.desciption = desciption;
    }

    public static String getDesciption(String code) {
    	StatusCode[] statusEnums = StatusCode.values();
        for (StatusCode statusEnum : statusEnums) {
            if (statusEnum.getCode().equals(code))
                return statusEnum.getDesciption();
        }
        return null;
    }

    /**
     * 
     * @param code
     * @return {@link MessageState} 实例
     */
    public static StatusCode find(String code) {
        for (StatusCode loanStatus : StatusCode.values()) {
            if (loanStatus.getCode().equals(code)) {
                return loanStatus;
            }
        }
        return null;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
    
    public static void main(String[] args) {
		System.out.println(getDesciption("1000"));
	}
}
