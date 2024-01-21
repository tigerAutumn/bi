package com.pinting.manage.enums;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author bianyatian
 * 特殊敏感链接（链接，所属菜单链接）
 */
public enum SpecialURLEnum {
	
	UserMessage_ExportXls("/userMessage/exportXls.htm","/bsuser/index.htm"),//1、用户管理：导出 
	
	Salers_ExportXls("/sales/xls.htm","/sales/index.htm"), //2、销售人员管理：导出、增删改
	Salers_Detail("/sales/save.htm","/sales/index.htm"), //2、销售人员管理：新增、修改
	Salers_Delete("/sales/status.htm","/sales/index.htm"), //2、销售人员管理：删除
	
	BuyMessage_ExportXls("/buyMessage/exportXls.htm","/statistics/buyMessage/query/index.htm?accountStatus=2"),//3、投资购买查询：导出  
	
	Investmet_BackSetion_ExportXls("/investmentBackSection/exportXls.htm","/statistics/investmentBackSection/query/index.htm"), //4、投资回款查询：导出
	
	Account_Order_ExportXls("/account/order/exportExcel.htm","/account/order/index.htm"), //5、系统订单查询：导出
	
	Send_Message("/bsuser/sms.htm","/bsuser/sms/index.htm"),//6、营销短信：发送-梦网
	Send_Welink_Message("/bsuser/sendWelinkMessage.htm","/bsuser/sms/index.htm"),//6、营销短信：发送-微网
	
	BankCard_StatusModifyCard("/bank/statusModifyCard.htm","/bank/card/index.htm"), //7、绑定银行卡管理：解绑
	
	APP_Add_Notice("/app/notice/addNotice.htm","/app/notice/index.htm"), //8、APP通知管理：新增
	
	APP_Add_Version("/app/version/addVersion.htm","/app/version/index.htm"), //9、APP版本控制：新增
	APP_Delete_Version("/app/version/deleteVersion.htm","/app/version/index.htm"), //9、APP版本控制：删除 
	
	RedPacket_Apply_Pass("/bsRedPacketApply/updatePass.htm","/bsRedPacketApply/indexCheck.htm"), //11、红包预算审核：通过    
	RedPacket_Apply_Refuse("/bsRedPacketApply/updateRefuse.htm","/bsRedPacketApply/indexCheck.htm"), //11、红包预算审核：拒绝  
    
	Channel_Withdraw("/channelWithdraw/channelWithdraw.htm","/channelWithdraw/withdrawIndex.htm"), //12、财务提现：提现 
    Channel_Withdraw_ConfirmTransfer("/channelWithdraw/confirmTransfer.htm","/channelWithdraw/withdrawIndex.htm"), //12、财务提现：确认转账
    
    Channel_Withdraw_Audit_Check("/channelWithdraw/auditWithdrawCheck.htm","/channelWithdraw/withdrawIndex.htm"), //13、财务确认处理：审核通过   
    
    Financial_ADD_Registry("/financial/financialRegistry.htm","/financial/financeWithdrawRecords.htm"), //14、财务线下提现登记：添加财务提现确认
    Financial_Confirm_Registry("/financial/confirmFinancialRegistry.htm","/financial/financeWithdrawRecords.htm"), //14、财务线下提现登记：确认
    
    RedPacket_Apply_Save("/bsRedPacketApply/save.htm","/bsRedPacketApply/index.htm"), //15、红包申请管理：申请   
    
    RedPacket_GrantManagement_AutoPacket_Create("/autoPacket/save.htm","/redPacket/redPacketGrantManagementIndex.htm"), //16、红包发放管理：发自动红包保存
    RedPacket_GrantManagement_ManualPacket_Create("/manualPacket/sendRedPacket.htm","/redPacket/redPacketGrantManagementIndex.htm"), //16、红包发放管理：新增1发送红包、新增2发送红包
    RedPacket_ManualPacket_Send("/redPacket/manualRedPacketSend.htm","/redPacket/redPacketGrantManagementIndex.htm"), //16、红包发放管理：发手动红包
    
    RedPacket_Check_Pass("/redPacket/redPacketCheckPass.htm","/redPacket/redPacketCheckIndex.htm"), // 17、红包发放审核：通过
    RedPacket_Check_Refuse("/redPacket/redPacketCheckRefuse.htm","/redPacket/redPacketCheckIndex.htm"), // 17、红包发放审核：不通过
	
    APP_Active_Detail("/app/active/activeModify.htm","/app/active/index.htm"),//18、APP活动管理：增改
	APP_Active_Delete("/app/active/activeDelete.htm","/app/active/index.htm"),//18、APP活动管理：删
	
	User_Operate_APP_Push("/user/operate/push.htm","/user/operate/index.htm"),//19、细分用户运营：APP推送
	
	SysMessage_Detail("/sysMessage/save.htm","/sysMessage/index.htm"),//20、微信公告管理：增删改
	SysMessage_Delete("/sysMessage/delete.htm","/sysMessage/index.htm"),//20、微信公告管理：删
	
    SysNews_Delete("/sysNews/delete.htm","/sysNews/index.htm"),//   21、网站新闻管理：删除
    SysNews_Save("/sysNews/save.htm","/sysNews/index.htm"),//  21、网站新闻管理：新增、编辑
    SysNews_Pubilsh("/sysNews/publish.htm","/sysNews/index.htm"),//   21、网站新闻管理：发布、撤下
    
    
    Banner_AddOrUpdate("/banner/addOrUpdate.htm","/banner/banner_index.htm"),//   22、banner管理：新增、编辑、删除
    
    Account_CheckError_tTransRepeatSend("/account/acctTransRepeatSend/index.htm","/account/checkError/index.htm"),//  23、差错账目管理：差错产品转账购买重发
    Account_CheckError_TransAllSend("/account/acctTransAllSend.htm","/account/checkError/index.htm"),//  23、差错账目管理：前一天所有已对账产品转账购买重发
    
	Bank_AddOrUpdate("/bank/save.htm","/bank/index.htm"),//  24、银行列表维护：新增、编辑
	Bank_IsAvailableModify("/bank/statusModify.htm","/bank/index.htm"),//  24、银行列表维护：禁用启用
    
	
	Bank_Bs19PayBank_AddOrUpdate("/bank/bs19PayBank/save.htm","/bank/bs19PayBank/index.htm"),// 25、银行渠道维护：新增、编辑
	Bank_Bs19PayBank_IsAvailableModify("/bank/isAvailableModify.htm","/bank/bs19PayBank/index.htm"),// 25、银行渠道维护：禁用启用
    
	
	User_Management_AddOrUpdate("/user/management/save.htm","/user/management/index.htm"),// 26、后台用户：增、改
	User_Management_Delete("/user/management/delete.htm","/user/management/index.htm"),// 26、后台用户：销户
	User_Management_UpdatePasswd("/user/management/update.htm","/user/management/index.htm"),// 26、后台用户：重置启用
	
	Sys_Rolemenu_Update("/sys/rolemenu/update.htm","/sys/rolemenu/index.htm"),// 27、角色权限分配：保存 
	
	Sys_Role_AddOrUpdate("/sys/role/save.htm","/sys/role/index.htm"),//28、角色管理：增改
	Sys_Role_Delete("/sys/role/delete.htm","/sys/role/index.htm"),//28、角色管理：删
	
	Sys_Config_Detail("/sys/config/update.htm","/sys/config/index.htm"),//29、系统配置设置：修改
	
	Sys_Maintainace_Halt_Detail("/sys/halt/update.htm","/sys/maintainace/index.htm"),//30、系统维护设置：挂起启用
	
	Wx_Menu_Add("/wx/menu/addMenu.htm","/wx/menu/index.htm"),//31、微信自定义菜单：新增
	Wx_Menu_Delete("/wx/menu/deleteMenu.htm","/wx/menu/index.htm"),//31、微信自定义菜单：删除
	Wx_Menu_CreateMenu("/wx/menu/wechat/createMenu.htm","/wx/menu/index.htm"),//31、微信自定义菜单：发布
	
	Wx_Auto_Reply_AddOrUpdate("/wx/autoReply/addOrUpdate.htm","/wx/autoReply/index.htm"),//32、微信消息自动回复：新增 或编辑
	Wx_Auto_Reply_Delete("/wx/autoReply/delete.htm","/wx/autoReply/index.htm"),//32、微信消息自动回复：删除

    ;
    
    private SpecialURLEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    private String code;
    
    private String description;

    private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

    static {
        for (SpecialURLEnum s : EnumSet.allOf(SpecialURLEnum.class)) {
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
