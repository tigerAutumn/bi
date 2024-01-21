package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_BsSpecialJnl_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsSpecialJnl_ListQuery;
import com.pinting.business.model.vo.BsSpecialJnlVO;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.util.BeanUtils;


@Component("BsSpecialJnl")
public class BsSpecialJnlFacade {
	
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	
	
	public void listQuery(ReqMsg_BsSpecialJnl_ListQuery req,ResMsg_BsSpecialJnl_ListQuery res){
		BsSpecialJnlVO record = new BsSpecialJnlVO();
		record.setUserName(req.getUserName());
		record.setMobile(req.getMobile());
		record.setOrderNo(req.getOrderNo());
		record.setType(req.getType());
		record.setTypeList(req.getTypeList());
		record.setStatus(req.getStatus());
		record.setPageNum(Integer.parseInt(req.getPageNum()));
		record.setNumPerPage(Integer.parseInt(req.getNumPerPage()));
		int totalRows = bsSpecialJnlService.bsSpecialJnlCount(record);
		if (totalRows > 0) {
			List<BsSpecialJnlVO> list = bsSpecialJnlService.bsSpecialJnlPage(record);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setSpecialJnls(mapList);
		}
		Double amounts = bsSpecialJnlService.amountSum(record);
		res.setAmounts(amounts);
		res.setPageNum(Integer.parseInt(req.getPageNum()));
		res.setNumPerPage(Integer.parseInt(req.getNumPerPage()));
		res.setTotalRows(totalRows);
	}

}
