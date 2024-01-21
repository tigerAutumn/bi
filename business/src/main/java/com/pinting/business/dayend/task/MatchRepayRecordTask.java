package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsLoanRelativeRecordMapper;
import com.pinting.business.dao.BsRepayJnlMapper;
import com.pinting.business.model.BsRepayJnl;
import com.pinting.business.model.BsRepayJnlExample;
import com.pinting.business.model.vo.BsLoanRelativeAmountChangeVO;
import com.pinting.business.service.manage.BsLoanRelativeRepayJnlService;
import com.pinting.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 债权关系还款记录拉取
 * @author bianyatian
 * @2016-6-28 上午10:12:17
 */
@Service
public class MatchRepayRecordTask {
	private Logger log = LoggerFactory.getLogger(MatchRepayRecordTask.class);
	@Autowired
	private BsRepayJnlMapper bsRepayJnlMapper;
	@Autowired
    private BsLoanRelativeRecordMapper bsLoanRelativeRecordMapper;
	@Autowired
	private BsLoanRelativeRepayJnlService bsLoanRelativeRepayJnlService;

	private static List<BsLoanRelativeAmountChangeVO> bsLoanRelativeRecords;
	
	 /**
	     * 任务执行
	     */
	public void execute() {
	    	
		//债权关系还款记录拉取
		matchRepayRecord();
	}

	/* 产生随机数，获得时间，判断是否需要获取*/
	private void matchRepayRecord() {
		Random rand = new Random();
        Integer mm = rand.nextInt(9) + 2;    // [2,10]的数字
        Date beforeTime = DateUtil.addMinutes(new Date(), -mm);
        Date yestoday = DateUtil.addDays(new Date(), -1);
        
        BsRepayJnlExample bsRepayJnlExample = new BsRepayJnlExample();
        bsRepayJnlExample.createCriteria().andCreateTimeGreaterThan(yestoday);
        bsRepayJnlExample.setOrderByClause("create_time desc");
        List<BsRepayJnl> list = bsRepayJnlMapper.selectByExample(bsRepayJnlExample);
        if(CollectionUtils.isEmpty(list)){
        	getMatchRepayRecord();
        }else{
        	BsRepayJnl bsRepayJnl = list.get(0);
        	if(bsRepayJnl.getCreateTime().compareTo(beforeTime) <= 0){
        		getMatchRepayRecord();
        	}
        	
        }
        
        
	}

	/*
	 *1、查询需要获取的金额变动表数据及债权关系记录数据
	 *2、获取数据，存库 
	 */
	private void getMatchRepayRecord() {
		if(CollectionUtils.isEmpty(bsLoanRelativeRecords)){
			bsLoanRelativeRecords = bsLoanRelativeRecordMapper
					.selectNoPull();//每次取1000条
		}
        if(!CollectionUtils.isEmpty(bsLoanRelativeRecords)){
        	Random rand = new Random();
            Integer index = rand.nextInt(bsLoanRelativeRecords.size());    // [0,bsLoanRelativeRecords.size()-1]的数字
            BsLoanRelativeAmountChangeVO vo = bsLoanRelativeRecords.get(index);
            bsLoanRelativeRepayJnlService.drawRepayment(vo.getBorrowerCustomerId(), 
            		vo.getBorrowId(), vo.getId().toString());
			bsLoanRelativeRecords.remove(index);
        }
        
	}
}
