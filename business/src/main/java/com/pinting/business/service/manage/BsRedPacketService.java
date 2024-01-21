package com.pinting.business.service.manage;

import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_AutoRedPocketDisable;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_AutoRedPocketReview;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_GetNewAutoRedPacket;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_GrantManagermentGrid;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_ManualRedPocketReview;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckGrid;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckRefuse;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckPass;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketStatistics;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_AutoRedPocketDisable;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_AutoRedPocketReview;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_GetNewAutoRedPacket;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_GrantManagermentGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_ManualRedPocketReview;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckRefuse;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckPass;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketStatistics;
import com.pinting.business.model.BsAutoRedPacketRule;
import com.pinting.business.model.BsRedPacketCheck;

public interface BsRedPacketService {
	
	
	/**
	 * 查询红包发放管理列表数据
	 * @param req
	 * @param res
	 */
	 void grantManagermentGrid(ReqMsg_RedPacket_GrantManagermentGrid req, ResMsg_RedPacket_GrantManagermentGrid res);
	 /**
	  * 自动发放红包停用
	  * @param req
	  * @param res
	  */
	 void autoRedPocketDisable(ReqMsg_RedPacket_AutoRedPocketDisable req,ResMsg_RedPacket_AutoRedPocketDisable res);
	 /**
	  * 查看自动红包详情信息
	  * @param req
	  * @param res
	  */
	 void autoRedPocketReview(ReqMsg_RedPacket_AutoRedPocketReview req, ResMsg_RedPacket_AutoRedPocketReview res);
	 /**
	  * 查看手动红包详情信息
	  * @param req
	  * @param res
	  */
	 void manualRedPocketReview(ReqMsg_RedPacket_ManualRedPocketReview req,ResMsg_RedPacket_ManualRedPocketReview res);
	 
	 /**
	  * 审核表添加
	  * @param check
	  * @param rule
	  */
	 void addAutoPacketCheck(BsRedPacketCheck check);
	 
	 /**
	  * 自动红包规则添加
	  * @param rule
	  */
	 void addAutoPacketRule(BsAutoRedPacketRule rule);
	 
	/**
	 * 查询红包管理管理列表数据
	 * @param req
	 * @param res
	 */
	 void redPacketCheckGrid(ReqMsg_RedPacket_RedPacketCheckGrid req,ResMsg_RedPacket_RedPacketCheckGrid res);
	/**
	 * 红包发放审核通过操作
	 * @param req
	 * @param res
	 */
	 void redPacketCheckPass(ReqMsg_RedPacket_RedPacketCheckPass req,ResMsg_RedPacket_RedPacketCheckPass res);
	/**
	 * 红包发放审核拒绝操作
	 * @param req
	 * @param res
	 */
	 void redPacketCheckRefuse(ReqMsg_RedPacket_RedPacketCheckRefuse req,ResMsg_RedPacket_RedPacketCheckRefuse res);
	/**
	 * 红包数据统计
	 * @param req
	 * @param res
	 */
	 void redPacketStatistics(ReqMsg_RedPacket_RedPacketStatistics req,ResMsg_RedPacket_RedPacketStatistics res);
	 
	 /**
	  * 手动红包发放
	  * @param ids
	  */
	 void manualRedPacketSend(String ids);
	 
	 /**
	  * 获取最新的自动红包
	  */
	 void getNewAutoRedPacket(ReqMsg_RedPacket_GetNewAutoRedPacket req, ResMsg_RedPacket_GetNewAutoRedPacket res);
}
