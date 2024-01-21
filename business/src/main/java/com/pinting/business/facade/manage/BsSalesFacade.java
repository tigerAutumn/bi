package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_MatrixImage;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_Modify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_PrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_StatusModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSales_UserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_MatrixImage;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_Modify;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_PrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_StatusModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsSales_UserListQuery;
import com.pinting.business.model.BsDept;
import com.pinting.business.model.BsDeptSales;
import com.pinting.business.model.BsSales;
import com.pinting.business.model.vo.BsSalesVO;
import com.pinting.business.service.manage.BsDeptService;
import com.pinting.business.service.manage.BsSalesService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.GlobEnvUtil;

@Component("BsSales")
public class BsSalesFacade {
	
	@Autowired
	private BsSalesService bsSalesService;
	
	@Autowired
	private BsDeptService bsDeptService;
	
	public void listQuery(ReqMsg_BsSales_ListQuery req,ResMsg_BsSales_ListQuery res){
		BsSalesVO bsSalesVO = new BsSalesVO();
		bsSalesVO.setSalesName(req.getSalesName());
		bsSalesVO.setStartTime(req.getStartTime());
		bsSalesVO.setEndTime(req.getEndTime());
		bsSalesVO.setmUserId(req.getmUserId());
		bsSalesVO.setDeptName(req.getDeptName());
		bsSalesVO.setPageNum(Integer.parseInt(req.getPageNum()));
		bsSalesVO.setNumPerPage(Integer.parseInt(req.getNumPerPage()));
		bsSalesVO.setOrderDirection(req.getOrderDirection());
		bsSalesVO.setOrderField(req.getOrderField());
		int totalRows = bsSalesService.count(bsSalesVO);
		if (totalRows > 0) {
			List<BsSalesVO> list = bsSalesService.page(bsSalesVO);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setSalesList(mapList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(String.valueOf(totalRows));
		List<BsDept> depts = bsDeptService.findDeptName(new BsDept());
		res.setDeptList(BeanUtils.classToArrayList(depts));
		
	}
	
	public void userListQuery(ReqMsg_BsSales_UserListQuery req,ResMsg_BsSales_UserListQuery res){
		BsSalesVO bsSalesVO = new BsSalesVO();
		bsSalesVO.setId(req.getId());
		bsSalesVO.setGrade(req.getGrade());
		bsSalesVO.setSalesName(req.getSalesName());
		bsSalesVO.setStartTime(req.getStartTime());
		bsSalesVO.setEndTime(req.getEndTime());
		if(StringUtils.isNotBlank(req.getStartMoney())){
			bsSalesVO.setStartMoney(Double.parseDouble(req.getStartMoney()));
		}
		if(StringUtils.isNotBlank(req.getEndMoney())){
			bsSalesVO.setEndMoney(Double.parseDouble(req.getEndMoney()));
		}
		bsSalesVO.setMobile(req.getMobile());
		bsSalesVO.setUserName(req.getUserName());
		bsSalesVO.setPageNum(Integer.parseInt(req.getPageNum()));
		bsSalesVO.setNumPerPage(Integer.parseInt(req.getNumPerPage()));
		int totalRows = bsSalesService.userCount(bsSalesVO);
		if (totalRows > 0) {
			List<BsSalesVO> list = bsSalesService.userPage(bsSalesVO);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setUserSalesList(mapList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(String.valueOf(totalRows));
	}
	
	public void statusModify(ReqMsg_BsSales_StatusModify req,ResMsg_BsSales_StatusModify res){
		BsSales sales = new BsSales();
		sales.setStatus(req.getStatus());
		sales.setId(req.getId());
		bsSalesService.updateBsSales(sales);
	}
	
	public void primaryKey(ReqMsg_BsSales_PrimaryKey req,ResMsg_BsSales_PrimaryKey res){
		if(null != req.getId() && 0 != req.getId()){
			 BsSales bsSales = bsSalesService.selectByPrimaryKey(req.getId());
			 res.setId(bsSales.getId());
			 res.setSalesName(bsSales.getSalesName());
			 res.setMobile(bsSales.getMobile());
			 res.setInviteCode(bsSales.getInviteCode());
			 res.setEntryTime(bsSales.getEntryTime());
			 res.setNote(bsSales.getNote());
			 
			 List<BsDeptSales> bsDeptSaleList = bsSalesService.selectDeptBySaleId(req.getId());
			 if (!CollectionUtils.isEmpty(bsDeptSaleList)) {
				res.setDeptId(bsDeptSaleList.get(0).getDeptId());
			 }
		}
		
		List<BsDept> bsDeptList = bsSalesService.selectDeptList();
		res.setDeptList(BeanUtils.classToArrayList(bsDeptList));
	}
	
	public void modify(ReqMsg_BsSales_Modify req,ResMsg_BsSales_Modify res){
		BsSales bsSales = new BsSales();
		bsSales.setInviteCode(req.getInviteCode());
		BsSales result = bsSalesService.selectBsSales(bsSales);
		bsSales.setId(req.getId());
		bsSales.setMobile(req.getMobile());
		bsSales.setNote(req.getNote());
		bsSales.setSalesName(req.getSalesName());
		bsSales.setEntryTime(req.getEntryTime());
		if(null != req.getId()&&0 != req.getId()){//编辑
			bsSales.setUpdateTime(new Date());
			if(null != result){
				if(result.getId().equals(req.getId())){
					bsSalesService.updateBsSales(bsSales);
					bsSalesService.updateBsDeptSales(req.getId(),req.getDeptId());
					res.setFlag(ResMsg_BsSales_Modify.MODIFY_SUCCESS);
				}else{
					res.setFlag(ResMsg_BsSales_Modify.REPEAT_NAME);
				}
			}else{
				bsSalesService.updateBsSales(bsSales);
				bsSalesService.updateBsDeptSales(req.getId(),req.getDeptId());
				res.setFlag(ResMsg_BsSales_Modify.MODIFY_SUCCESS);
			}
		}else{//新增
			if(null == result){
				bsSales.setStatus(1);
				bsSales.setCreateTime(new Date());
				Integer saleId = bsSalesService.addBsSales(bsSales);
				bsSalesService.addBsDeptSales(bsSales.getId(),req.getDeptId());
				res.setFlag(ResMsg_BsSales_Modify.INSERT_SUCCESS);
			}else{
				res.setFlag(ResMsg_BsSales_Modify.REPEAT_NAME);
			}
		}
	}
	
	public void matrixImage(ReqMsg_BsSales_MatrixImage req,ResMsg_BsSales_MatrixImage res){
		BsSales bsSales = bsSalesService.selectByPrimaryKey(req.getId());
		String link = GlobEnvUtil.get("wechat.server.web")+"/micro2.0/user/register_first_index?recommendId="+bsSales.getInviteCode();
		res.setId(bsSales.getId());
		res.setName(bsSales.getSalesName());
		res.setLink(link);
	}

}
