package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_ProductSerial_ProductSerialDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductSerial_ProductSerialList;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductSerial_ProductSerialModify;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductSerial_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_ProductSerial_ProductSerialDelete;
import com.pinting.business.hessian.manage.message.ResMsg_ProductSerial_ProductSerialList;
import com.pinting.business.hessian.manage.message.ResMsg_ProductSerial_ProductSerialModify;
import com.pinting.business.hessian.manage.message.ResMsg_ProductSerial_SelectByPrimaryKey;
import com.pinting.business.model.BsProductSerial;
import com.pinting.business.service.manage.BsProductSerialService;
import com.pinting.business.util.BeanUtils;

@Component("ProductSerial")
public class BsProductSerialFacade {
	
	@Autowired
	private BsProductSerialService bsProductSerialService;
	
	/**
	 * 列表
	 * @param req
	 * @param res
	 */
	public void productSerialList(ReqMsg_ProductSerial_ProductSerialList req, ResMsg_ProductSerial_ProductSerialList res) {
		int totalRows = bsProductSerialService.selectCountProductSerial();
		if(totalRows > 0) {
			ArrayList<BsProductSerial> bsProductSerialList = bsProductSerialService.findProductSerialInfo(
					req.getPageNum(), req.getNumPerPage(), req.getOrderDirection(), req.getOrderField());
			ArrayList<HashMap<String, Object>> productSerialList = BeanUtils.classToArrayList(bsProductSerialList);
			res.setValueList(productSerialList);
		}
		res.setTotalRows(totalRows);
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
	}
	
	/**
	 * 进入添加 编辑页面
	 * @param req
	 * @param res
	 */
	public void selectByPrimaryKey(ReqMsg_ProductSerial_SelectByPrimaryKey req, ResMsg_ProductSerial_SelectByPrimaryKey res) {
		if(req.getId() != null && req.getId() != 0) {
			BsProductSerial bsProductSerial = bsProductSerialService.selectByPrimaryId(req.getId());
			bsProductSerial.setId(bsProductSerial.getId());
			bsProductSerial.setSerialName(bsProductSerial.getSerialName());
			bsProductSerial.setTerm(bsProductSerial.getTerm());
			bsProductSerial.setCreateTime(bsProductSerial.getCreateTime());
			bsProductSerial.setUpdateTime(bsProductSerial.getUpdateTime());
		}
	}
	
	/**
	 * 添加 修改
	 * @param req
	 * @param res
	 */
	public void productSerialModify(ReqMsg_ProductSerial_ProductSerialModify req, ResMsg_ProductSerial_ProductSerialModify res) {
		BsProductSerial bsProductSerial = new BsProductSerial();
		bsProductSerial.setSerialName(req.getSerialName().trim()); // 存库时系列名称去掉首尾空格
		BsProductSerial result = bsProductSerialService.selectBsProductSerial(bsProductSerial); // 查询产品系列名称是否已存在
		bsProductSerial.setId(req.getId());
		bsProductSerial.setTerm(req.getTerm());
		/*if(req.getTerm() == 1 || req.getTerm() == 3 || req.getTerm() == 6 || req.getTerm() == 12) {
			bsProductSerial.setTerm(req.getTerm());
		} else {
			bsProductSerial.setTerm(req.getTerm()*(-1));//新手标库中存负数
		}*/
		
		if(req.getId() != null && req.getId() != 0) { // 编辑
			if(result != null ) {
				if(result.getId().equals(req.getId())) { 
					bsProductSerial.setUpdateTime(new Date());
					bsProductSerialService.updateProductSerial(bsProductSerial);
					res.setFlag(ResMsg_ProductSerial_ProductSerialModify.MODIFY_SUCCESS);
				} else {
					res.setFlag(ResMsg_ProductSerial_ProductSerialModify.REPEAT_NAME);
				}
			} else {
				bsProductSerial.setUpdateTime(new Date());
				bsProductSerialService.updateProductSerial(bsProductSerial);
				res.setFlag(ResMsg_ProductSerial_ProductSerialModify.MODIFY_SUCCESS);
			}
		} else { // 添加
			if(result == null) {
				bsProductSerial.setCreateTime(new Date());
				bsProductSerial.setUpdateTime(new Date());
				bsProductSerialService.addProductSerial(bsProductSerial);
				res.setFlag(ResMsg_ProductSerial_ProductSerialModify.INSERT_SUCCESS);
			} else {
				res.setFlag(ResMsg_ProductSerial_ProductSerialModify.REPEAT_NAME);
			}
		}
		
	}
	
	/**
	 * 删除
	 * @param req
	 * @param res
	 */
	public void productSerialDelete(ReqMsg_ProductSerial_ProductSerialDelete req, ResMsg_ProductSerial_ProductSerialDelete res) {
		if(req.getId() != null && req.getId() != 0) {
			bsProductSerialService.deleteProductSerialById(req.getId());
		}
	}
	
	

}
