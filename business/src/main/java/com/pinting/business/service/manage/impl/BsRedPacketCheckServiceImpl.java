package com.pinting.business.service.manage.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsRedPacketCheckMapper;
import com.pinting.business.dao.BsRedPacketPreDetailMapper;
import com.pinting.business.model.BsRedPacketCheck;
import com.pinting.business.service.manage.BsRedPacketCheckService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;

@Service
public class BsRedPacketCheckServiceImpl implements BsRedPacketCheckService {

	@Autowired
	BsRedPacketCheckMapper bsRedPacketCheckMapper;
	@Autowired
	BsRedPacketPreDetailMapper redPacketPreDetailMapper;
	
	@Override
	public int saveRedPacketCheck(BsRedPacketCheck record,List<Integer> userIdList) {
		//添加用户红包预发放记录
		int count = userIdList.size() % Constants.everyNum == 0 ? userIdList.size()/Constants.everyNum : userIdList.size()/Constants.everyNum +1;
		try {
			for(int j = 0; j < count; j++) {
				StringBuffer buffer = new StringBuffer("insert into bs_red_packet_pre_detail(serial_no,user_id,create_time,update_time) VALUES");
				for (int i = Constants.everyNum * j; i < Constants.everyNum*(j +1); i++) {
					if(i >= userIdList.size()) {
						break;
					}else {
						buffer.append("('"+record.getSerialNo()+"',"+userIdList.get(i)+",'"+DateUtil.format(new Date())+"','"+DateUtil.format(new Date())+"'),");
					}
				}
				redPacketPreDetailMapper.saveRedPacketPreDetail(buffer.toString().substring(0, buffer.toString().length()-1));
			}
			
			return bsRedPacketCheckMapper.insertSelective(record);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<BsRedPacketCheck> getListByPolicyType(String policyType) {
		return bsRedPacketCheckMapper.selectByPolicyType(policyType);
	}
}
