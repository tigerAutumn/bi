package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsAgentMapper;
import com.pinting.business.dao.BsWxInfoMapper;
import com.pinting.business.model.vo.WxAgentVO;
import com.pinting.business.service.manage.WxAgentService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公众号渠道管理服务
 *
 * @author shh
 * @date 2018/6/15 9:27
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class WxAgentServiceImpl implements WxAgentService {

    @Autowired
    private BsAgentMapper bsAgentMapper;

    @Autowired
    private BsWxInfoMapper bsWxInfoMapper;

    @Override
    public List<WxAgentVO> queryWxAgentInfo(String agentName, Integer pageNum, Integer numPerPage) {
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        List<WxAgentVO> resultList = bsAgentMapper.selectWxAgentList(agentName, start, numPerPage);
        return CollectionUtils.isEmpty(resultList) ? null : resultList;
    }

    @Override
    public int queryWxAgentCount(String agentName) {
        Integer count = bsAgentMapper.selectWxAgentCount(agentName);
        return count == null ? 0 : count;
    }

    @Override
    public int queryWxInfoCountByAgentId(Integer wxAgentId) {
        Integer count = bsWxInfoMapper.selectWxInfoCountByAgentId(wxAgentId);
        return count == null ? 0 : count;
    }

    @Override
    public List<WxAgentVO> queryWxInfoListByAgentId(Integer wxAgentId, Integer pageNum, Integer numPerPage) {
        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
        List<WxAgentVO> resultList = bsWxInfoMapper.selectWxInfoListByAgentId(wxAgentId, start, numPerPage);
        return CollectionUtils.isEmpty(resultList) ? null : resultList;
    }
}
