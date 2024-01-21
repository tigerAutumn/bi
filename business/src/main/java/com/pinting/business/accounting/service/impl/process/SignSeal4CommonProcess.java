package com.pinting.business.accounting.service.impl.process;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.dao.BsFileSealRelationMapper;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.BsFileSealRelation;
import com.pinting.business.model.BsFileSealRelationExample;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.vo.SignSeal4PdfInfoVO;
import com.pinting.business.model.vo.UserSealFileVO;

/**
 * 改版签章
 * 查询bs_file_seal_relation中保存的数据进行签章
 * 和基础业务无关
 * 此服务可以作为通用服务使用
 * 
 * @project business
 * @title SignSeal4CommonProcess.java
 * @author Dragon & cat
 * @date 2018-4-8
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class SignSeal4CommonProcess implements Runnable {
	private Logger log = LoggerFactory.getLogger(SignSeal4CommonProcess.class);
	
	private	  BsUserSealFile	bsUserSealFile;
	private	  BsFileSealRelationMapper  bsFileSealRelationMapper;
	private   SignSealService     signSealService;
	
	
	public void setBsUserSealFile(BsUserSealFile bsUserSealFile) {
		this.bsUserSealFile = bsUserSealFile;
	}

	public void setBsFileSealRelationMapper(
			BsFileSealRelationMapper bsFileSealRelationMapper) {
		this.bsFileSealRelationMapper = bsFileSealRelationMapper;
	}

	public void setSignSealService(SignSealService signSealService) {
		this.signSealService = signSealService;
	}

	@Override
	public void run() {
		//1.查询BsFileSealRelation数据
		BsFileSealRelationExample bsFileSealRelationExample = new BsFileSealRelationExample();
		bsFileSealRelationExample.createCriteria().andSealFileIdEqualTo(bsUserSealFile.getId());
		List<BsFileSealRelation> bsFileSealRelations = bsFileSealRelationMapper.selectByExample(bsFileSealRelationExample);
		
		if (CollectionUtils.isEmpty(bsFileSealRelations)) {
			return ;
		}
		//2.循环开始签章业务
		boolean signFlag = true;
		for (BsFileSealRelation bsFileSealRelation : bsFileSealRelations) {
    		SignSeal4PdfInfoVO companySignSeal4PdfReq = signSealService.getSignSeal(bsUserSealFile,bsFileSealRelation);
            if (companySignSeal4PdfReq == null) {
            	return ;
			}
    		boolean enterpriseSignFlag = signSealService.signSeal4PdfByKeyword(companySignSeal4PdfReq);
            if (!enterpriseSignFlag) {
				signFlag = enterpriseSignFlag;
				log.error("执行签章失败......relationId="+bsFileSealRelation.getId());
				updateUserSealInfo(bsUserSealFile.getId(),SealStatus.FAIL.getCode(), null);
				break;
			}
		}
		//3.签章成功
		if (signFlag) {
			updateUserSealInfo(bsUserSealFile.getId(),SealStatus.SUCC.getCode(), null);
		}
	}
	
	
    private void updateUserSealInfo(Integer id,String sealStatus, String fileAddress) {
		UserSealFileVO userSealFile = new UserSealFileVO();
		userSealFile.setSealStatus(sealStatus);
		userSealFile.setFileAddress(fileAddress);
		userSealFile.setId(id);
		signSealService.updateUserSealFile(userSealFile);
	}

}
