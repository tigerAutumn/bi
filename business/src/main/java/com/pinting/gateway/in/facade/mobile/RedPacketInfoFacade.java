/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.in.facade.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.business.hessian.site.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.model.vo.RedPacketInfoVO;
import com.pinting.business.service.site.RedPacketService;
import com.pinting.gateway.in.util.InterfaceVersion;

/**
 * 
 * @author HuanXiong
 * @version $Id: RedPacketFacade.java, v 0.1 2016-3-1 下午3:14:22 HuanXiong Exp $
 */
@Component("appRedPacketInfo")
public class RedPacketInfoFacade {
    
    @Autowired
    private RedPacketService redPacketService;
    
    /**
     * 我的红包
     * @param req
     * @param res
     */
    @InterfaceVersion("RedPacketList/1.0.0")
    public void redPacketList(ReqMsg_RedPacketInfo_RedPacketList req, ResMsg_RedPacketInfo_RedPacketList res) {
        redPacketService.redPacketList(req, res);
    }

	/**
	 * 我的红包（只给我的红包列表使用）
	 * @param req
	 * @param res
	 */
	@InterfaceVersion("QueryRedPacketList/1.0.0")
	public void queryRedPacketList(ReqMsg_RedPacketInfo_QueryRedPacketList req, ResMsg_RedPacketInfo_QueryRedPacketList res) {
		redPacketService.queryRedPacketList(req, res);
	}

    /**
     * 根据用户编号，获取用户注册时获得的红包列表，无红包则列表为空
     * @param req
     * @param res
     */
    @InterfaceVersion("GetUserRegistRedPakt/1.0.0")
    public void getUserRegistRedPakt(ReqMsg_RedPacketInfo_GetUserRegistRedPakt req,
            ResMsg_RedPacketInfo_GetUserRegistRedPakt res) {
    	List<RedPacketInfoVO> redPaktVOs = redPacketService.getUserRegistRedPakt(req.getUserId());
    	List<Map<String, Object>> redPakts = new ArrayList<Map<String, Object>>();
    	if(redPaktVOs != null && redPaktVOs.size() > 0){
    		for (RedPacketInfoVO vo : redPaktVOs) {
    			Map<String, Object> redPaktMap = new HashMap<String, Object>();
        		redPaktMap.put("id", vo.getId());
        		redPaktMap.put("userId", vo.getUserId());
        		redPaktMap.put("serialName", vo.getSerialName());
        		redPaktMap.put("full", vo.getFull());
        		redPaktMap.put("subtract", vo.getSubtract());
        		redPaktMap.put("useTimeStart", vo.getUseTimeStart());
        		redPaktMap.put("useTimeEnd", vo.getUseTimeEnd());
        		redPaktMap.put("status", vo.getStatus());
        		redPakts.add(redPaktMap);
			}
    	}
    	res.setUserRegistRedPakts(redPakts);
	}




}
