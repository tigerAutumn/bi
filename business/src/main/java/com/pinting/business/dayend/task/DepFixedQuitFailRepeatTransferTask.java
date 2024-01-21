package com.pinting.business.dayend.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.finance.service.UserDepFixedQuitService;
import com.pinting.business.dao.BsDepositionQuitApplyMapper;
import com.pinting.business.model.BsDepositionQuitApply;
import com.pinting.business.model.BsDepositionQuitApplyExample;
import com.pinting.business.util.Constants;
/**
 * 
 * @project business
 * @title DepFixedQuitFailRepeatTransferTask.java
 * @author Dragon & cat
 * @date 2017-6-7
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class DepFixedQuitFailRepeatTransferTask {
	private Logger log = LoggerFactory.getLogger(DepFixedQuitFailRepeatTransferTask.class);
	
	@Autowired
	private BsDepositionQuitApplyMapper bsDepositionQuitApplyMapper;
	@Autowired
	private	UserDepFixedQuitService userDepFixedQuitService;
	
	public void execute() {
		log.info("==================【存管定期产品理财人退出重发】 开始 =================");
		try {
			
			BsDepositionQuitApplyExample example = new BsDepositionQuitApplyExample();
			example.createCriteria().andStatusEqualTo(Constants.DEP_QUIT_APPLY_FAIL);
			List<BsDepositionQuitApply> applyList = bsDepositionQuitApplyMapper.selectByExample(example);
			if(CollectionUtils.isNotEmpty(applyList)){
				for (BsDepositionQuitApply bsDepositionQuitApply : applyList) {
					try {
						//退出
						userDepFixedQuitService.quit(bsDepositionQuitApply.getSubAccountId());
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
			}else {
				log.info("==================【存管定期产品理财人退出重发】 没有对应记录 =================");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("==================【存管定期产品理财人退出重发】 结束 =================");
	}
	

}
