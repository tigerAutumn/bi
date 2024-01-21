/**

 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.site;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.util.MoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.model.vo.RedPacketInfoVO;
import com.pinting.business.service.site.RedPacketService;

/**
 * 
 * @author HuanXiong
 * @version $Id: RedPacketFacade.java, v 0.1 2016-3-1 下午3:14:22 HuanXiong Exp $
 */
@Component("RedPacketInfo")
public class RedPacketInfoFacade {
    
    @Autowired
    private RedPacketService redPacketService;
    
    /**
     * 我的红包
     * @param req
     * @param res
     */
    public void redPacketList(ReqMsg_RedPacketInfo_RedPacketList req, ResMsg_RedPacketInfo_RedPacketList res) {
        redPacketService.redPacketList(req, res);
    }

	/**
	 * 我的红包
	 * @param req
	 * @param res
	 */
	public void queryRedPacketList(ReqMsg_RedPacketInfo_QueryRedPacketList req, ResMsg_RedPacketInfo_QueryRedPacketList res) {
		DecimalFormat format = new DecimalFormat("#.##");
		redPacketService.queryRedPacketList(req, res);
		ArrayList<HashMap<String, Object>> dataGrid = res.getDataGrid();
		Double full = 0d;
		for (HashMap<String, Object> map: dataGrid) {
			if(map.get("full") instanceof Double) {
				full = (Double) map.get("full");
			} else {
				String fullStr = (String) map.get("full");
				full = Double.valueOf(fullStr);
			}
			if(full.compareTo(10000d) >= 0) {
				Double fullWan = MoneyUtil.divide(full, 10000d).doubleValue();
				map.put("full", format.format(fullWan));
				map.put("isWan", "yes");
			} else {
				map.put("full", format.format(full));
			}
		}
	}

    /**
     * 选择红包返回相关信息
     * @param req
     * @param res
     */
    public void chooseRedPacket(ReqMsg_RedPacketInfo_ChooseRedPacket req,
                         ResMsg_RedPacketInfo_ChooseRedPacket res) {
        redPacketService.findById(req, res);
    }
    
    /**
     * 根据用户编号，获取用户注册时获得的红包列表，无红包则列表为空
     * @param req
     * @param res
     */
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
    
    /**
     * PC：获得红包信息
     * @param req
     * @param res
     */
    public void getRedPacket(ReqMsg_RedPacketInfo_GetRedPacket req,
                             ResMsg_RedPacketInfo_GetRedPacket res) {
        redPacketService.getRedPacket(req, res);
    }
}
