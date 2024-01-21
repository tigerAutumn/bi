package com.pinting.business.accounting.finance.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.finance.service.DepQuitApplyService;
import com.pinting.business.dao.BsDepositionQuitApplyMapper;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.model.BsDepositionQuitApply;
import com.pinting.business.model.BsDepositionQuitApplyExample;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;

/**
 * 
 * @project business
 * @title DepQuitApplyServiceImpl.java
 * @author Dragon & cat
 * @date 2017-4-5
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class DepQuitApplyServiceImpl implements DepQuitApplyService {
	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	  
	@Autowired
	private		BsDepositionQuitApplyMapper bsDepositionQuitApplyMapper;	  
	  
	@Override
	public int addDepositionQuitApply(BsDepositionQuitApply record) {
		int rowNum = 0;
    	try {
    		jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEPFIXED_QUIT_APPLY.getKey());
            //判断该笔记录是否已经存在
            BsDepositionQuitApplyExample example = new BsDepositionQuitApplyExample();
            example.createCriteria().andSubAccountIdEqualTo(record.getSubAccountId()).andStatusNotEqualTo(Constants.DEP_QUIT_APPLY_REFU);
            List<BsDepositionQuitApply> list = bsDepositionQuitApplyMapper.selectByExample(example);
            if(list != null && list.size() > 0) {
                //记录已经存在，不能重复申请
                return rowNum;
            }else {
                rowNum = bsDepositionQuitApplyMapper.insertSelective(record);
            }
    		
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEPFIXED_QUIT_APPLY.getKey());
		}

        return rowNum;
	}

}
