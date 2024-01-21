package com.pinting.business.service.manage;

import com.pinting.business.model.vo.WxAgentVO;

import java.util.List;

/**
 * 公众号渠道管理Service
 *
 * @author shh
 * @date 2018/6/15 9:19
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface WxAgentService {

    /**
     * 公众号渠道管理列表
     * @param agentName
     * @param pageNum
     * @param numPerPage
     * @return
     */
    public List<WxAgentVO> queryWxAgentInfo(String agentName, Integer pageNum, Integer numPerPage);

    /**
     * 公众号渠道管理统计
     * @param agentName
     * @return
     */
    public int queryWxAgentCount(String agentName);

    /**
     * 根据渠道编号，查询该渠道关注的人数
     * @param wxAgentId
     * @return
     */
    public int queryWxInfoCountByAgentId(Integer wxAgentId);

    /**
     * 根据渠道编号，查询该渠道关注的人数列表
     * @param wxAgentId
     * @return
     */
    public List<WxAgentVO> queryWxInfoListByAgentId(Integer wxAgentId, Integer pageNum, Integer numPerPage);

}
