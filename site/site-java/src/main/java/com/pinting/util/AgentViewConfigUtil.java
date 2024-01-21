package com.pinting.util;

import com.pinting.business.hessian.site.message.ReqMsg_User_QueryAgentViewConfig;
import com.pinting.business.hessian.site.message.ResMsg_User_QueryAgentViewConfig;
import com.pinting.core.util.SpringBeanUtil;
import com.pinting.site.communicate.CommunicateBusiness;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2016/10/10
 * Description:
 */
public class AgentViewConfigUtil {

    private static List<HashMap<String, Object>> agentViewList = new ArrayList<>();

    static {
        try{
            CommunicateBusiness communicateBusiness = (CommunicateBusiness) SpringBeanUtil.getBean("communicateBusiness");
            ReqMsg_User_QueryAgentViewConfig agentReq = new ReqMsg_User_QueryAgentViewConfig();
            ResMsg_User_QueryAgentViewConfig agentRes = (ResMsg_User_QueryAgentViewConfig)communicateBusiness.handleMsg(agentReq);
            List<HashMap<String, Object>> configList = agentRes.getConfigList();
            agentViewList = configList;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据渠道Id查询对应图片信息
     * @param agentId
     * @return
     */
    public static Map<Object, Object> getView(String agentId) {
        if(CollectionUtils.isEmpty(agentViewList)) {
            CommunicateBusiness communicateBusiness = (CommunicateBusiness) SpringBeanUtil.getBean("communicateBusiness");
            ReqMsg_User_QueryAgentViewConfig agentReq = new ReqMsg_User_QueryAgentViewConfig();
            ResMsg_User_QueryAgentViewConfig agentRes = (ResMsg_User_QueryAgentViewConfig)communicateBusiness.handleMsg(agentReq);
            List<HashMap<String, Object>> configList = agentRes.getConfigList();
            agentViewList = configList;
        }
        Map<Object, Object> result = new HashMap<>();
        if(!CollectionUtils.isEmpty(agentViewList)) {
            if(!StringUtils.isEmpty(agentId)) {
                for (HashMap<String, Object> agentMap : agentViewList) {
                    if(agentMap.get("agentId").equals(Integer.parseInt(agentId))) {
                        result.put(agentMap.get("viewKey"), agentMap.get("viewValue"));
                    }
                }
            }
        }
        return result;
    }

}
