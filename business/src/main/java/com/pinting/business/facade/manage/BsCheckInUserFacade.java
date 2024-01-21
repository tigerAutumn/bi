package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_BsCheckInUser_CheckInUserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsCheckInUser_CheckInUserListQuery;
import com.pinting.business.model.vo.BsCheckInUserVO;
import com.pinting.business.service.manage.BsCheckInUserService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.StringUtil;

/**
 * Created by shh on 2016/11/28 15:00.
 */
@Component("BsCheckInUser")
public class BsCheckInUserFacade {
	
	@Autowired
	private BsCheckInUserService bsCheckInUserService;
	
	public void checkInUserListQuery(ReqMsg_BsCheckInUser_CheckInUserListQuery req, ResMsg_BsCheckInUser_CheckInUserListQuery res) {
		int pageNum = req.getPageNum();
		int numPerPage = req.getNumPerPage();
		BsCheckInUserVO userVO = new BsCheckInUserVO();
		userVO.setPageNum(pageNum);
		userVO.setNumPerPage(numPerPage);
		if(StringUtil.isNotEmpty(req.getMobile())) {
			userVO.setMobile(req.getMobile().trim());
		}
		if(StringUtil.isNotEmpty(req.getIsRegisterTime())) {
			userVO.setIsRegisterTime(req.getIsRegisterTime());
		}
		if(StringUtil.isNotEmpty(req.getIsWin())) {
			userVO.setIsWin(req.getIsWin());
		}
		if(StringUtil.isNotEmpty(req.getActivityAwardId())) {
			userVO.setActivityAwardId(Integer.parseInt(req.getActivityAwardId()));
		}
		Integer totalRows = bsCheckInUserService.queryCheckInUserCount(userVO);
		if(totalRows > 0) {
			List<BsCheckInUserVO> list = bsCheckInUserService.queryCheckInUserList(userVO);
			ArrayList<HashMap<String, Object>> checkInUserList = BeanUtils.classToArrayList(list);
			res.setValueList(checkInUserList);
		}
		res.setTotalRows(totalRows);
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
	}

}
