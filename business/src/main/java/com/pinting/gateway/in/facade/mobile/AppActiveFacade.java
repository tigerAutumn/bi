package com.pinting.gateway.in.facade.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsUserMessageRead;
import com.pinting.business.service.site.UserMessageReadService;
import com.pinting.business.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.model.BsAppActive;
import com.pinting.business.service.manage.MAppActiveService;
import com.pinting.business.util.BeanUtils;
import com.pinting.gateway.in.util.InterfaceVersion;

@Component("appAppActive")
public class AppActiveFacade{
	
	@Autowired
	private MAppActiveService activeService;
	@Autowired
	private UserMessageReadService userMessageReadService;

	@InterfaceVersion("IsRead/1.0.0")
	public void activityIsRead(ReqMsg_AppActive_IsRead req, ResMsg_AppActive_IsRead res) {
		if (req.getUserId() == null) {
			res.setIsRead(Constants.NO);
		} else {
			BsUserMessageRead read = userMessageReadService.queryByPosition(req.getUserId(), Constants.POSITION_ACTIVITY);
			BsAppActive active = activeService.selectLatestActive();
			if(active == null) {
				res.setIsRead(Constants.YES);
				return;
			}
			if (null == read) {
				res.setIsRead(Constants.NO);
			} else {
				if(null != active) {
					Date publishTime = active.getPublishTime();
					if (publishTime.after(read.getReadTime())) {
						res.setIsRead(Constants.NO);
					} else {
						res.setIsRead(Constants.YES);
					}
				} else {
					res.setIsRead(Constants.YES);
				}
			}
		}
	}

	@InterfaceVersion("AppActiveList/1.0.0")
	public void AppActiveList(ReqMsg_AppActive_AppActiveList req, ResMsg_AppActive_AppActiveList res) {
		Date now = new Date();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<BsAppActive> list = activeService.selectAppActive(now);
		List<BsAppActive> normal = new ArrayList<BsAppActive>();
		List<BsAppActive> fail = new ArrayList<BsAppActive>();
		for(BsAppActive active : list) {
			Date endTime = active.getEndTime();
			//now大于endTime返回1;相等返回0;小于返回-1
			if(now.compareTo(endTime) != -1) {
				fail.add(active);
			}
			else {
				normal.add(active);
			}
		}
		for(BsAppActive active : normal) {
			Map<String,Object> normalMap = BeanUtils.transBeanMap(active);
			Date startTime = active.getStartTime();
			if(now.compareTo(startTime) != -1) {
				normalMap.put("status", 2);
			}
			else {
				normalMap.put("status", 1);
			}
			result.add(normalMap);
		}
		for(BsAppActive active : fail) {
			Map<String,Object> failMap = BeanUtils.transBeanMap(active);
			failMap.put("status", 3);
			result.add(failMap);
		}
		res.setActiveList(result);
	}
	
	@InterfaceVersion("FindPublishTime/1.0.0")
	public void findPublishTime(ReqMsg_AppActive_FindPublishTime req, ResMsg_AppActive_FindPublishTime res) {
		Date now = new Date();
		List<BsAppActive> list = activeService.selectAppActive(now);
		if(!CollectionUtils.isEmpty(list)) {
			if(now.compareTo(list.get(0).getEndTime()) == -1) {
				res.setPublishTime(list.get(0).getPublishTime());
			}
		}
	}
	
}
