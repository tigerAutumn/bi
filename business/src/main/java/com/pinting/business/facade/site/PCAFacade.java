package com.pinting.business.facade.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_PCA_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_PCA_InfoQuery;
import com.pinting.business.model.BsPCA;
import com.pinting.business.service.site.ProvinceService;
import com.pinting.business.util.BeanUtils;

/**
 * 
 * @Project: business
 * @Title: PCAFacade.java
 * @author Huang MengJian
 * @date 2015-2-11 下午7:59:34
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("PCA")
public class PCAFacade {
	
	@Autowired
	public ProvinceService provinceService;
	
	public void infoQuery(ReqMsg_PCA_InfoQuery req,ResMsg_PCA_InfoQuery res){
		
		List<BsPCA> pcaList = provinceService.findPCAByParentId(req.getParentId());
		
		res.setProvinceList(BeanUtils.classToArrayList(pcaList));
	}
}
