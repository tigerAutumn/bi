package com.pinting.business.util;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsRedPacketInfoMapper;
import com.pinting.business.model.BsRedPacketInfo;
import com.pinting.business.model.BsRedPacketInfoExample;

public class ScheduledThreadPoolUtil {

	private static ScheduledExecutorService executor;
	
	static {
		executor = Executors.newScheduledThreadPool(5);
	}
	
	public static void execute(final Integer id, final BsRedPacketInfoMapper redPacketInfoMapper) {
		executor.schedule(new Runnable() {
			
			@Override
			public void run() {
				BsRedPacketInfoExample example = new BsRedPacketInfoExample();
				example.createCriteria().andIdEqualTo(id).andStatusEqualTo(Constants.RED_PACKET_STATUS_BUYING);
				List<BsRedPacketInfo> infoList = redPacketInfoMapper.selectByExample(example);
				if(!CollectionUtils.isEmpty(infoList)) {
					BsRedPacketInfo info = infoList.get(0);
					info.setStatus(Constants.RED_PACKET_STATUS_INIT);
					info.setUpdateTime(new Date());
					info.setOrderNo("");
					redPacketInfoMapper.updateByPrimaryKeySelective(info);
				}
				
			}
		}, 1, TimeUnit.HOURS);
	}
}
