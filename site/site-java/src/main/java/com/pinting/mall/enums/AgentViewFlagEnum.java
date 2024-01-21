package com.pinting.mall.enums;

import com.pinting.util.Constants;
import org.apache.commons.lang.StringUtils;

/**
 * 渠道页面跳转标识枚举
 *
 * @author zousheng
 * @date 2018年8月14日 上午9:48:05
 */
public enum AgentViewFlagEnum {

    AGENT_VIEW_ID_BGW("pc", "bgw", "mallpc"), // 显示终端-币港湾
    AGENT_VIEW_ID_QB("pc_a", Constants.AGENT_VIEW_ID_QB, "mallpc/agent"), // 显示终端-钱报
    AGENT_VIEW_ID_KQ("pc_a", Constants.AGENT_VIEW_ID_KQ, "mallpc/agent"), // 显示终端-柯桥
    AGENT_VIEW_ID_HN("pc_a", Constants.AGENT_VIEW_ID_HN, "mallpc/agent"), // 显示终端-海宁
    AGENT_VIEW_ID_RUIAN("pc_a", Constants.AGENT_VIEW_ID_RUIAN, "mallpc/agent"), // 显示终端-瑞安
    AGENT_VIEW_ID_QHD_ST("pc_a", Constants.AGENT_VIEW_ID_QHD_ST, "mallpc/agent"); // 显示终端-视听之友

    private String code;
    
    private String agentViewFlag;

    private String pathPrefix;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAgentViewFlag() {
		return agentViewFlag;
	}

	public void setAgentViewFlag(String agentViewFlag) {
		this.agentViewFlag = agentViewFlag;
	}

	public String getPathPrefix() {
        return pathPrefix;
    }

    public void setPathPrefix(String pathPrefix) {
        this.pathPrefix = pathPrefix;
    }


    /**
     * 私有的构造方法
     *
     * @param code
     */
    private AgentViewFlagEnum(String code, String agentViewFlag, String pathPrefix) {
        this.code = code;
        this.agentViewFlag = agentViewFlag;
        this.pathPrefix = pathPrefix;
    }

    /**
     * @param code
     * @return {@link AgentViewFlagEnum} 实例
     */
    public static AgentViewFlagEnum find(String code, String agentViewFlag) {
        if (Constants.REQUEST_TERMINAL_PC.equals(code)) {
            return AgentViewFlagEnum.AGENT_VIEW_ID_BGW;
        }
        for (AgentViewFlagEnum payPath : AgentViewFlagEnum.values()) {
            if (payPath.getCode().equals(code) && payPath.getAgentViewFlag().equals(agentViewFlag)) {
                return payPath;
            }
        }
        return AgentViewFlagEnum.AGENT_VIEW_ID_BGW;
    }
}
