/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.util.StringUtil;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_RedPacket_FindRedPacketInfoGrid.java, v 0.1 2016-2-29 下午5:43:04 HuanXiong Exp $
 */
public class ReqMsg_RedPacket_FindRedPacketInfoGrid extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -5959457344949817237L;

    private String useTimeStart;
    
    private String useTimeEnd;
    
    private String distributeTimeStart; // 发放开始时间
    
    private String distributeTimeEnd; // 发放结束时间
    
    private Integer agentId;
    
    private String serialName;
    
    private String status;  
    
    private String mobile;
    
    private String serialNo;    // 批次号
    
    private String triggerType;   // （自动distributeType+triggerType）
    
    private String distributeType;  // 手动||自动
    
    private Integer pageNum = 1;
    
    private Integer numPerPage = 20;
    
    private Integer start = 1;
    
    private String queryFlag;
    
    private String agentIds;
	
    private String nonAgentId;
    
    private String redPacketName; //红包名称
    
    private String usedTimeStart; 	//红包被使用
    
    private String usedTimeEnd;		//红包被使用
    
    public Integer getStart() {
        start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getUseTimeStart() {
        return StringUtil.isBlank(useTimeStart)? null : useTimeStart;
    }

    public void setUseTimeStart(String useTimeStart) {
        this.useTimeStart = useTimeStart;
    }

    public String getUseTimeEnd() {
        return StringUtil.isBlank(useTimeEnd)? null : useTimeEnd;
    }

    public void setUseTimeEnd(String useTimeEnd) {
        this.useTimeEnd = useTimeEnd;
    }

    public String getDistributeTimeStart() {
        return StringUtil.isBlank(distributeTimeStart)? null : distributeTimeStart;
    }

    public void setDistributeTimeStart(String distributeTimeStart) {
        this.distributeTimeStart = distributeTimeStart;
    }

    public String getDistributeTimeEnd() {
        return StringUtil.isBlank(distributeTimeEnd)? null : distributeTimeEnd;
    }

    public void setDistributeTimeEnd(String distributeTimeEnd) {
        this.distributeTimeEnd = distributeTimeEnd;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public String getSerialName() {
        return StringUtil.isBlank(serialName)? null : serialName;
    }

    public void setSerialName(String serialName) {
        this.serialName = serialName;
    }

    public String getStatus() {
        return StringUtil.isBlank(status)? null : status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMobile() {
        return StringUtil.isBlank(mobile)? null : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSerialNo() {
        return StringUtil.isBlank(serialNo)? null : serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getTriggerType() {
        return StringUtil.isBlank(triggerType)? null : triggerType;
    }

    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    public String getDistributeType() {
        return StringUtil.isBlank(distributeType)? null : distributeType;
    }

    public void setDistributeType(String distributeType) {
        this.distributeType = distributeType;
    }

	public String getQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(String queryFlag) {
		this.queryFlag = queryFlag;
	}

	public String getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(String agentIds) {
		this.agentIds = agentIds;
	}

	public String getNonAgentId() {
		return nonAgentId;
	}

	public void setNonAgentId(String nonAgentId) {
		this.nonAgentId = nonAgentId;
	}

	public String getRedPacketName() {
		return redPacketName;
	}

	public void setRedPacketName(String redPacketName) {
		this.redPacketName = redPacketName;
	}

	public String getUsedTimeStart() {
		return usedTimeStart;
	}

	public void setUsedTimeStart(String usedTimeStart) {
		this.usedTimeStart = usedTimeStart;
	}

	public String getUsedTimeEnd() {
		return usedTimeEnd;
	}

	public void setUsedTimeEnd(String usedTimeEnd) {
		this.usedTimeEnd = usedTimeEnd;
	}
}
