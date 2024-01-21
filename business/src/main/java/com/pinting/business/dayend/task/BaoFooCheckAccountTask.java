package com.pinting.business.dayend.task;

import java.util.List;

import com.pinting.business.accounting.loan.service.SysDailyBizService;
import com.pinting.business.dao.BsPaymentChannelMapper;
import com.pinting.business.model.BsPaymentChannel;
import com.pinting.business.model.BsPaymentChannelExample;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 宝付对账
 *
 * @author liujz
 * @Project: business
 * @Title: CheckAccountJob.java
 * @date 2016-10-09 下午2:45:33
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class BaoFooCheckAccountTask {
    private Logger log = LoggerFactory.getLogger(BaoFooCheckAccountTask.class);

    @Autowired
    private SysDailyBizService sysDailyBizService;
    @Autowired
    private BsPaymentChannelMapper paymentChannelMapper;

    /**
     * 任务执行
     */
    public void execute() {
        //宝付对账
        checkAccountJob();
    }


    /**
     * 宝付对账
     */
    private void checkAccountJob() {
    	BsPaymentChannelExample bsPaymentChannelExample = new BsPaymentChannelExample();
    	bsPaymentChannelExample.createCriteria().andTransTypeEqualTo("DK").andIsMainEqualTo(1);
        List<BsPaymentChannel> bsPaymentChannels = paymentChannelMapper.selectByExample(bsPaymentChannelExample);
        if (!CollectionUtils.isEmpty(bsPaymentChannels)) {
        	sysDailyBizService.downloadCheckAccountFile(bsPaymentChannels.get(0));
		}
        
        
        //宝付辅助通道对账
        BsPaymentChannelExample paymentChannelExample = new BsPaymentChannelExample();
        paymentChannelExample.createCriteria().andTransTypeEqualTo("DK").andIsMainEqualTo(2);
        List<BsPaymentChannel> paymentChannels = paymentChannelMapper.selectByExample(paymentChannelExample);
        if (!CollectionUtils.isEmpty(paymentChannels)) {
            for (BsPaymentChannel bsPaymentChannel : paymentChannels) {
           	 	sysDailyBizService.downloadCheckAccountFileAssist(bsPaymentChannel);
            }
		}

       
    }


    public static void main(String[] args) {
    }
}
