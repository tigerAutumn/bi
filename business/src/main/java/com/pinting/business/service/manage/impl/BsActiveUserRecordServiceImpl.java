package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsActiveUserRecordMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_BsActiveUserRecord_GetList;
import com.pinting.business.hessian.manage.message.ResMsg_BsActiveUserRecord_GetList;
import com.pinting.business.model.BsActiveUserRecord;
import com.pinting.business.model.vo.BsActiveUserRecordVO;
import com.pinting.business.service.manage.BsActiveUserRecordService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.StringUtil;



@Service
public class BsActiveUserRecordServiceImpl implements BsActiveUserRecordService {

	@Autowired
	private BsActiveUserRecordMapper bsActiveUserRecordMapper;

	@Override
	public void selectList(
			ReqMsg_BsActiveUserRecord_GetList req,
			ResMsg_BsActiveUserRecord_GetList res) {
		List<BsActiveUserRecordVO> list = new ArrayList<BsActiveUserRecordVO>();
		String agentIdStr = req.getAgents();
		List<Object> agentIds = null;
		if(StringUtils.isNotBlank(req.getAgents())){
			if(agentIdStr.endsWith(",")){
				agentIdStr = agentIdStr.substring(0,agentIdStr.length()-1);
			}
			
			if(agentIdStr.indexOf("-1") >=0){
				//所有用户，有普通用户又有渠道用户
				req.setAgentsFlag("-2");
				req.setAgents(agentIdStr.substring(5));
				String[] ids = req.getAgents().split(",");
				if(ids.length > 0) {
					agentIds = new ArrayList<Object>();
					for (String str : ids) {
						if(StringUtil.isNotEmpty(str)) {
							agentIds.add(Integer.valueOf(str));
						}
					}
				}
				req.setAgents(null);
			}else if(req.getAgents().startsWith("0") && req.getAgents().length()>2){
				//有普通用户又有渠道用户
				req.setAgentsFlag("-2");
				req.setAgents(agentIdStr.substring(2));
				String[] ids = req.getAgents().split(",");
				if(ids.length > 0) {
					agentIds = new ArrayList<Object>();
					for (String str : ids) {
						if(StringUtil.isNotEmpty(str)) {
							agentIds.add(Integer.valueOf(str));
						}
					}
				}
			}else if(req.getAgents().startsWith("0") && req.getAgents().length()== 2){
				//只有普通用户
				req.setAgentsFlag("-1");
			}else if(!req.getAgents().startsWith("0")){
				//只有渠道用户
				String[] ids = req.getAgents().split(",");
				if(ids.length > 0) {
					agentIds = new ArrayList<Object>();
					for (String str : ids) {
						if(StringUtil.isNotEmpty(str)) {
							agentIds.add(Integer.valueOf(str));
						}
					}
				}
			}
		}
		
		
		int count = bsActiveUserRecordMapper.countList(agentIds, 
				req.getAgentsFlag(), req.getStartDate(), req.getEndDate());
		
		if(count >0){
			list = bsActiveUserRecordMapper.selectList(agentIds, 
					req.getAgentsFlag(), req.getStartDate(), req.getEndDate(), req.getStart(), req.getNumPerPage());
		}
		res.setTotalRows(count);
		res.setValueList(BeanUtils.classToArrayList(list));
	}
	
	
}
