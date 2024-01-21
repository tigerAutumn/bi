package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ResMsg_BsBank_BsBankPrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBank_BankListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBank_BsBankModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBank_BsBankPrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_BsBank_BsBankStatusModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsBank_BankListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsBank_BsBankModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsBank_BsBankStatusModify;
import com.pinting.business.model.BsBank;
import com.pinting.business.service.manage.BsBankService;
import com.pinting.business.util.BeanUtils;

@Component("BsBank")
public class BsBankFacade {

	@Autowired
	private BsBankService bsBankService;
	
	public void bankListQuery(ReqMsg_BsBank_BankListQuery req,ResMsg_BsBank_BankListQuery res){
		BsBank bsBank = new BsBank();
		bsBank.setStatus(req.getStatus());
		bsBank.setName(req.getName());
		bsBank.setPageNum(Integer.parseInt(req.getPageNum()));
		bsBank.setNumPerPage(Integer.parseInt(req.getNumPerPage()));
		int totalRows = bsBankService.baBankCount(bsBank);
		if (totalRows > 0) {
			List<BsBank> list = bsBankService.bsBankPage(bsBank);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setBanks(mapList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(String.valueOf(totalRows));
	}
	
	public void bsBankStatusModify(ReqMsg_BsBank_BsBankStatusModify req,ResMsg_BsBank_BsBankStatusModify res){
		BsBank bsBank = new BsBank();
		bsBank.setStatus(req.getStatus());
		bsBank.setId(req.getId());
		bsBankService.updateBsBank(bsBank);
	}
	
	public void bsBankPrimaryKey(ReqMsg_BsBank_BsBankPrimaryKey req,ResMsg_BsBank_BsBankPrimaryKey res){
		if(null != req.getId() && 0 != req.getId()){
			BsBank bsBank = bsBankService.bsBankPrimaryKey(req.getId());
			res.setId(bsBank.getId());
			res.setName(bsBank.getName());
			res.setStatus(bsBank.getStatus());
			res.setNote(bsBank.getNote());
			res.setLargeLogo(bsBank.getLargeLogo());
			res.setSmallLogo(bsBank.getSmallLogo());
		}
	}
	
	public void bsBankModify(ReqMsg_BsBank_BsBankModify req,ResMsg_BsBank_BsBankModify res){
		BsBank bsBank = new BsBank();
		bsBank.setName(req.getName());
		BsBank result = bsBankService.selectBank(bsBank);//查询银行是否存在
		bsBank.setId(req.getId());
		bsBank.setStatus(req.getStatus());
		bsBank.setNote(req.getNote());
		bsBank.setSmallLogo(req.getSmallLogo());
		bsBank.setLargeLogo(req.getLargeLogo());
		if(null != req.getId()&&0 != req.getId()){//编辑
			if(null != result){
				if(result.getId().equals(req.getId())){
					bsBankService.updateBsBank(bsBank);
					res.setFlag(ResMsg_BsBank_BsBankModify.MODIFY_SUCCESS);
				}else{
					res.setFlag(ResMsg_BsBank_BsBankModify.REPEAT_NAME);
				}
			}else{
				bsBankService.updateBsBank(bsBank);
				res.setFlag(ResMsg_BsBank_BsBankModify.MODIFY_SUCCESS);
			}
		}else{//新增
			if(null == result){
				bsBank.setStatus(1);
				bsBankService.addBsbank(bsBank);
				res.setFlag(ResMsg_BsBank_BsBankModify.INSERT_SUCCESS);
			}else{
				res.setFlag(ResMsg_BsBank_BsBankModify.REPEAT_NAME);
			}
		}
	}
}
