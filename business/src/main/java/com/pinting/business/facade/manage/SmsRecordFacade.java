package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_SmsRecord_RecordJnlListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_SmsRecord_RecordJnlListQuery;
import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.model.vo.BsSmsRecordJnlReVo;
import com.pinting.business.service.manage.BsSmsRecordJnlService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;

@Component("SmsRecord")
public class SmsRecordFacade {

	@Autowired
	private BsSmsRecordJnlService bsSmsRecordJnlService;

	public void recordJnlListQuery(ReqMsg_SmsRecord_RecordJnlListQuery req,
			ResMsg_SmsRecord_RecordJnlListQuery res) {
		if(StringUtil.isBlank(req.getType())){
			req.setType(Constants.SMS_TYPE_BUSINESS);
		}
		Integer total = bsSmsRecordJnlService.smsRecordJnlCount(req.getContent(),
				req.getMobile(), req.getBeginTime(), req.getOverTime(),req.getType(),
				req.getStatusCode(), req.getPlatformsCode());
		if(total > 0 ){
			List<BsSmsRecordJnlReVo> list = bsSmsRecordJnlService.smsRecordJnlList(
					req.getContent(), req.getMobile(), req.getBeginTime(),
					req.getOverTime(), req.getType(),req.getStatusCode(), 
					req.getPageNum(), req.getNumPerPage(), req.getPlatformsCode());
			ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(list); 
			res.setValueList(hashList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(total);
	}

}
