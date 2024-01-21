package com.pinting.business.facade.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_Dict_ListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_Login;
import com.pinting.business.hessian.site.message.ResMsg_Dict_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_Login;
import com.pinting.business.model.BsDict;
import com.pinting.business.model.BsUser;
import com.pinting.business.service.site.DictService;
import com.pinting.business.service.site.UserService;
/**
 * @Project: business
 * @Title: DictFacade.java
 * @author Dong Wenkai
 * @date 2015-1-21 下午6:44:06
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Dict")
public class DictFacade{
	@Autowired
	private DictService dictService;
	
	public void listQuery(ReqMsg_Dict_ListQuery req, ResMsg_Dict_ListQuery resp){
		List<HashMap<String, Object>> itemList=null;
		List<BsDict> dictList=dictService.findDictById(req.getDictId());
		if(dictList != null && dictList.size()>0){
			itemList=new ArrayList<HashMap<String,Object>>();
			for (BsDict bsDict : dictList) {
				HashMap<String,Object> dictMap=new HashMap<String, Object>();
				dictMap.put("itemId", bsDict.getItemId());
				dictMap.put("itemName", bsDict.getItemName());
				itemList.add(dictMap);
			}
		}
		resp.setItemList(itemList);
	}
}
