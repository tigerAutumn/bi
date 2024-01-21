package com.pinting.manage.controller.process;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.enums.SmsPlatformsCodeEnum;
import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.service.manage.BsSmsRecordJnlService;
import com.pinting.business.util.SMSUtils;
import com.pinting.business.util.EMaySmsUtils.EMaySmsUtil;

public class SMSCheckProcess implements Runnable {


	private BsSmsRecordJnlService bsSmsRecordJnlService;
	
	private Logger log = LoggerFactory.getLogger(SMSCheckProcess.class);
	@Override
	public void run() {
		List<BsSmsRecordJnl> list =  new ArrayList<BsSmsRecordJnl>();
		//list.addAll(SMSUtils.checkSmsSend());//梦网返回
		list.addAll(EMaySmsUtil.checkSmsSend());//亿美返回
		while(list.size() != 0){
			for (BsSmsRecordJnl bsSmsRecordJnl : list) {
				if(StringUtils.isNotBlank(bsSmsRecordJnl.getMobile()) && 
						StringUtils.isNotBlank(bsSmsRecordJnl.getSerialNo())){
					BsSmsRecordJnl record = bsSmsRecordJnlService.selectByMobileSerNo(bsSmsRecordJnl.getMobile(), bsSmsRecordJnl.getSerialNo(),SmsPlatformsCodeEnum.EMay);
					if(record == null){
						bsSmsRecordJnlService.insertJnl(bsSmsRecordJnl);
					}else{
						bsSmsRecordJnl.setId(record.getId());
						bsSmsRecordJnlService.updateJnl(bsSmsRecordJnl);
					}
				}
			}
			list =  EMaySmsUtil.checkSmsSend();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public void setBsSmsRecordJnlService(BsSmsRecordJnlService bsSmsRecordJnlService) {
		this.bsSmsRecordJnlService = bsSmsRecordJnlService;
	}

}
