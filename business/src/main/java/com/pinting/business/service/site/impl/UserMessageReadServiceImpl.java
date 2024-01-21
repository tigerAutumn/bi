package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsUserMessageReadMapper;
import com.pinting.business.model.BsUserMessageRead;
import com.pinting.business.model.BsUserMessageReadExample;
import com.pinting.business.service.site.UserMessageReadService;
import com.pinting.gateway.in.util.MethodRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/6/20
 * Description: 用户消息阅读记录：目的是控制各个消息提示红点
 */
@Service
public class UserMessageReadServiceImpl implements UserMessageReadService {

    @Autowired
    private BsUserMessageReadMapper bsUserMessageReadMapper;

    @Override
    @MethodRole("APP")
    public BsUserMessageRead queryByPosition(Integer userId, String position) {
        if(null == userId) {
            return null;
        }
        BsUserMessageReadExample example = new BsUserMessageReadExample();
        example.createCriteria().andUserIdEqualTo(userId).andPositionEqualTo(position);
        example.setOrderByClause("read_time desc");
        List<BsUserMessageRead> list = bsUserMessageReadMapper.selectByExample(example);
        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    @Override
    public BsUserMessageRead updateMessageRead(Integer id) {
        BsUserMessageRead read = new BsUserMessageRead();
        read.setId(id);
        read.setReadTime(new Date());
        read.setUpdateTime(new Date());
        bsUserMessageReadMapper.updateByPrimaryKeySelective(read);
        return read;
    }

    @Override
    public BsUserMessageRead addMessageRead(Integer userId, String position) {
        BsUserMessageRead read = null;
        if(null != userId) {
            BsUserMessageReadExample example = new BsUserMessageReadExample();
            example.createCriteria().andUserIdEqualTo(userId).andPositionEqualTo(position);
            example.setOrderByClause("read_time desc");
            List<BsUserMessageRead> list = bsUserMessageReadMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list)) {
                read = new BsUserMessageRead();
                read.setUserId(userId);
                read.setPosition(position);
                read.setReadTime(new Date());
                read.setCreateTime(new Date());
                read.setUpdateTime(new Date());
                bsUserMessageReadMapper.insertSelective(read);
            } else {
                read = list.get(0);
            }
        }
        return read;
    }
}
