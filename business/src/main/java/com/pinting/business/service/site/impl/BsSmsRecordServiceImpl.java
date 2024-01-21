package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsSmsRecordMapper;
import com.pinting.business.model.BsSmsRecord;
import com.pinting.business.model.BsSmsRecordExample;
import com.pinting.business.service.site.BsSmsRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class BsSmsRecordServiceImpl implements BsSmsRecordService {

	@Autowired
	private BsSmsRecordMapper bsSmsRecordMapper;
	
	@Override
	public void addSmsRecord(BsSmsRecord smsRecord) {
		smsRecord.setLastSendTime(new Date());
		bsSmsRecordMapper.insertSelective(smsRecord);
	}

	@Override
	public BsSmsRecord selectByMobile(String mobile) {
		BsSmsRecordExample example = new BsSmsRecordExample();
		example.createCriteria().andMobileEqualTo(mobile);
		List<BsSmsRecord> records = bsSmsRecordMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(records))
			return null;
		BsSmsRecord record = bsSmsRecordMapper.selectByPk4Lock(records.get(0).getId());
		return record;
	}

	@Override
	public void updateByIncrement(BsSmsRecord smsRecord) {
		smsRecord.setLastSendTime(new Date());
		bsSmsRecordMapper.updateByIncrement(smsRecord);
	}
	
	 

}
