package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.Bs2016CheckInUserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.Bs2016CheckInUserMapper;
import com.pinting.business.model.Bs2016CheckInUser;
import com.pinting.business.model.vo.BsCheckInUserVO;
import com.pinting.business.service.manage.BsCheckInUserService;
import com.pinting.core.util.DateUtil;

/**
 * Created by shh on 2016/11/25 20:00.
 */
@Service
public class BsCheckInUserServiceImpl implements BsCheckInUserService {
	
	private Logger log = LoggerFactory.getLogger(BsCheckInUserServiceImpl.class);
	@Autowired
	private Bs2016CheckInUserMapper bs2016CheckInUserMapper;
	
	@Override
	public boolean insertCheckInUser(String mobile) {
		int record = 0;
		Bs2016CheckInUser result = bs2016CheckInUserMapper.selectByMobile(mobile);
		if(result == null) {
			Bs2016CheckInUser user = new Bs2016CheckInUser();
			user.setMobile(mobile);
			user.setCheckInTime(new Date());
			record = bs2016CheckInUserMapper.insertSelective(user);
		}else{
			log.info("2016客户年终答谢会签到表中已存在的手机号：" + result.getMobile());
		}
		if(record > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	@Override
	public void batchInsertCheckInUser(List<String> mobileList) {
		if(mobileList != null && mobileList.size() > 0) {
			String time = DateUtil.format(new Date());
			String sql = "insert into bs_2016_check_in_user(mobile,check_in_time) values";
			StringBuffer insert = new StringBuffer();
			for(String mobile : mobileList) {
				//插入时判断手机号是否已经存在
				Bs2016CheckInUser result = bs2016CheckInUserMapper.selectByMobile(mobile);
				if(result == null) {
					insert.append("("+mobile+",");
					insert.append("'"+time+"'),");
				}else{
					Bs2016CheckInUser check = new Bs2016CheckInUser();
					check.setCheckInTime(new Date());
					Bs2016CheckInUserExample example = new Bs2016CheckInUserExample();
					example.createCriteria().andMobileEqualTo(mobile);
					bs2016CheckInUserMapper.updateByExampleSelective(check, example);
					log.info("批量导入时客户年终答谢会表中已存在的手机号：" + result.getMobile());
				}
			}
			//导入模板中的手机号全部都在库中
			String phones = insert.toString();
			if(phones != null && phones.length() != 0) {
				sql += insert.toString();
				sql =  sql.substring(0, sql.length()-1);
				bs2016CheckInUserMapper.insertCheckInUser(sql);
			}
		}
	}

	@Override
	public List<BsCheckInUserVO> queryCheckInUserList(BsCheckInUserVO record) {
		return bs2016CheckInUserMapper.findCheckInUserList(record);
	}

	@Override
	public int queryCheckInUserCount(BsCheckInUserVO record) {
		return bs2016CheckInUserMapper.findCheckInUserCount(record);
	}
	
}
