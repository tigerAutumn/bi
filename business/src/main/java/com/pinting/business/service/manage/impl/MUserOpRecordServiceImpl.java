package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.MUserOpRecordMapper;
import com.pinting.business.model.MUserOpRecord;
import com.pinting.business.model.vo.MUserOpRecordVO;
import com.pinting.business.service.manage.MUserOpRecordService;
@Service
public class MUserOpRecordServiceImpl implements MUserOpRecordService {
	@Autowired
	private MUserOpRecordMapper mUserOpRecordMapper;
	
	@Override
	public void addMUserOpRecord(MUserOpRecord record) {
		record.setOpTime(new Date());
		mUserOpRecordMapper.insertSelective(record);
	}

	@Override
	public int countMList(MUserOpRecordVO record) {
		
		return mUserOpRecordMapper.countMListByRecordVO(record);
	}

	@Override
	public List<MUserOpRecordVO> getMList(MUserOpRecordVO record) {
		
		return mUserOpRecordMapper.getMListByRecordVO(record);
	}

}
