package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.BsRedPacketApplyMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_BsRedPacketApplyCheckList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_BsRedPacketApplyList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_BsRedPacketApplyModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_BsRedPacketApplyRefuse;
import com.pinting.business.hessian.manage.message.ReqMsg_BsRedPacketApply_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_BsRedPacketApplyCheckList;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_BsRedPacketApplyList;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_BsRedPacketApplyModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_BsRedPacketApplyRefuse;
import com.pinting.business.hessian.manage.message.ResMsg_BsRedPacketApply_SelectByPrimaryKey;
import com.pinting.business.model.BsRedPacketApply;
import com.pinting.business.model.vo.BsRedPacketApplyVO;
import com.pinting.business.service.manage.BsRedPacketApplyService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;

@Component("BsRedPacketApply")
public class BsRedPacketApplyFacade {
	
	@Autowired
	private BsRedPacketApplyService redPacketApplyService;
	
	@Autowired
	private BsSysSubAccountService sysSubAccountService;
	
	@Autowired
	private BsRedPacketApplyMapper bsRedPacketApplyMapper;
	
	/**
	 * 红包申请列表
	 * @param req
	 * @param res
	 */
	public void bsRedPacketApplyList(ReqMsg_BsRedPacketApply_BsRedPacketApplyList req, ResMsg_BsRedPacketApply_BsRedPacketApplyList res) {
		int totalRows = redPacketApplyService.findBsRedPacketApplyCount(req.getRpName(), req.getCheckStatus(), req.getCreator());
		if (totalRows > 0) {
			ArrayList<BsRedPacketApplyVO> bsRedPacketApplyList = redPacketApplyService.findBsRedPacketApplyList(req.getRpName(), req.getCheckStatus(), req.getCreator(),
					req.getPageNum(), req.getNumPerPage(), req.getOrderDirection(), req.getOrderField());
//			ArrayList<BsRedPacketApplyVO>bsRedPacketApplyVO = new ArrayList<BsRedPacketApplyVO>();
//			for (int i=0;i<bsRedPacketApplyList.size();i++) {
//				BsRedPacketApplyVO vo = new BsRedPacketApplyVO();
//				vo.setId(bsRedPacketApplyList.get(i).getId());
//				vo.setRpName(bsRedPacketApplyList.get(i).getRpName());
//				vo.setNote(bsRedPacketApplyList.get(i).getNote());
//				vo.setBudget(bsRedPacketApplyList.get(i).getBudget());
//				vo.setCreateTime(bsRedPacketApplyList.get(i).getCreateTime());
//				vo.setCheckStatus(bsRedPacketApplyList.get(i).getCheckStatus());
//				vo.setCheckTime(bsRedPacketApplyList.get(i).getCheckTime());
//				vo.setName(bsRedPacketApplyList.get(i).getName());
//				vo.setCheckerName(bsRedPacketApplyList.get(i).getCheckerName());
//				// 红包可用余额
//				vo.setAvailableAmount(redPacketApplyService.selectByApplyNo(bsRedPacketApplyList.get(i).getApplyNo()).getCanPutAmount());
//				bsRedPacketApplyVO.add(vo);
//			}
			ArrayList<HashMap<String, Object>> list = BeanUtils.classToArrayList(bsRedPacketApplyList);
			res.setValueList(list);
		}
		ArrayList<BsRedPacketApplyVO> creator = (ArrayList<BsRedPacketApplyVO>) redPacketApplyService.findBsRedPacketApplyCreator(new BsRedPacketApplyVO());
		ArrayList<HashMap<String, Object>> creatorValue = BeanUtils.classToArrayList(creator);
		res.setCreatorList(creatorValue);
		res.setTotalRows(totalRows);
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
	}
	
	/**
	 * 红包审核列表
	 * @param req
	 * @param res
	 */
	public void bsRedPacketApplyCheckList(ReqMsg_BsRedPacketApply_BsRedPacketApplyCheckList req, ResMsg_BsRedPacketApply_BsRedPacketApplyCheckList res) {
		int totalRows = redPacketApplyService.findBsRedPacketApplyCount(req.getRpName(), req.getCheckStatus(), req.getCreator());
		if (totalRows > 0) {
			ArrayList<BsRedPacketApplyVO> bsRedPacketApplyList = redPacketApplyService.findBsRedPacketApplyList(req.getRpName(), req.getCheckStatus(), req.getCreator(),
					req.getPageNum(), req.getNumPerPage(), req.getOrderDirection(), req.getOrderField());
			ArrayList<HashMap<String, Object>> list = BeanUtils.classToArrayList(bsRedPacketApplyList);
			res.setValueList(list);
		}
		ArrayList<BsRedPacketApplyVO> creator = (ArrayList<BsRedPacketApplyVO>) redPacketApplyService.findBsRedPacketApplyCreator(new BsRedPacketApplyVO());
		ArrayList<HashMap<String, Object>> creatorValue = BeanUtils.classToArrayList(creator);
		res.setCreatorList(creatorValue);
		res.setTotalRows(totalRows);
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		
	}
	
	/**
	 * 进入红包申请页面
	 * @param req
	 * @param res
	 */
	public void selectByPrimaryKey(ReqMsg_BsRedPacketApply_SelectByPrimaryKey req, ResMsg_BsRedPacketApply_SelectByPrimaryKey res) {
		if(req.getId() != null && req.getId() != 0) {
			BsRedPacketApply bsRedPacketApply = redPacketApplyService.findByPrimaryKey(req.getId());
			res.setId(bsRedPacketApply.getId());
			res.setRpName(bsRedPacketApply.getRpName());
			res.setApplyNo(bsRedPacketApply.getApplyNo());
			res.setBudget(bsRedPacketApply.getBudget());
			res.setStatus(bsRedPacketApply.getStatus());
			res.setCheckStatus(bsRedPacketApply.getCheckStatus());
			res.setCreator(bsRedPacketApply.getCreator());
			res.setChecker(bsRedPacketApply.getChecker());
			res.setNote(bsRedPacketApply.getNote());
			res.setCheckTime(bsRedPacketApply.getCreateTime());
			res.setUpdateTime(bsRedPacketApply.getUpdateTime());
		}
	}
	
	/**
	 * 添加
	 * @param req
	 * @param res
	 */
	public void bsRedPacketApplyModify(ReqMsg_BsRedPacketApply_BsRedPacketApplyModify req, ResMsg_BsRedPacketApply_BsRedPacketApplyModify res) {
		BsRedPacketApply bsRedPacketApply = new BsRedPacketApply();
		bsRedPacketApply.setRpName(req.getAddRpName());
		bsRedPacketApply.setBudget(req.getBudget());
		bsRedPacketApply.setNote(req.getNote());
		bsRedPacketApply.setCreator(req.getCreator());
		BsRedPacketApply result = redPacketApplyService.findBsRedPacketApply(bsRedPacketApply); // 查询红包名称是否已存在
		if (result != null && !"".equals(result)) {
			res.setFlag(ResMsg_BsRedPacketApply_BsRedPacketApplyModify.REPEAT_NAME);
		} else {
			if (result == null) {
				bsRedPacketApply.setCreateTime(new Date());
				bsRedPacketApply.setStatus(Constants.AUTO_RED_PACKET_AAPLY_STATUS_INIT); // 红包申请默认为初始状态
				bsRedPacketApply.setCheckStatus(Constants.RED_PACKET_APPLY_CHECK_STATUS_INIT); // 红包审核默认为初始状态
				bsRedPacketApply.setApplyNo(Util.getApplyNo());
				bsRedPacketApply.setCreator(req.getCreator());
				redPacketApplyService.addBsRedPacketApply(bsRedPacketApply);
				res.setFlag(ResMsg_BsRedPacketApply_BsRedPacketApplyModify.INSERT_SUCCESS);
			} else {
				res.setFlag(ResMsg_BsRedPacketApply_BsRedPacketApplyModify.REPEAT_NAME);
			}
		} 
	}
	
	/**
	 * 红包审核 PASS 通过
	 * @param req
	 * @param res
	 */
	public void bsRedPacketApplyBudgetReview(ReqMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview req, ResMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview res) {
		if (req.getId() != null && req.getId() != 0) {
			BsRedPacketApply result = redPacketApplyService.findByPrimaryKey(req.getId());
			if (result != null) {
				result.setCheckTime(new Date());
				result.setUpdateTime(new Date());
				result.setCheckStatus(Constants.RED_PACKET_APPLY_CHECK_STATUS_PASS); // PASS 通过 
				result.setStatus(Constants.AUTO_RED_PACKET_AAPLY_STATUS_USING); // 审核通过红包 USING 使用中
				result.setChecker(req.getChecker());
				int row = redPacketApplyService.updateBsRedPacketApply(result);
				if(row > 0) {
					res.setFlag(ResMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview.UPDATE_SUCCESS);
					sysSubAccountService.redPacketTopUp(result.getBudget()); // 红包预算审核通过，红包户余额增加
				}else {
					res.setFlag(ResMsg_BsRedPacketApply_BsRedPacketApplyBudgetReview.UPDATE_FAIL);
				}
			}
		}
	}
	
	/**
	 * 红包审核REFUSE 不通过
	 * @param req
	 * @param res
	 */
	public void bsRedPacketApplyRefuse(ReqMsg_BsRedPacketApply_BsRedPacketApplyRefuse req, ResMsg_BsRedPacketApply_BsRedPacketApplyRefuse res) {
		if(req.getId() != null && req.getId() != 0) {
			BsRedPacketApply result = redPacketApplyService.findByPrimaryKey(req.getId());
			if (result != null) {
				result.setCheckTime(new Date());
				result.setUpdateTime(new Date());
				result.setCheckStatus(Constants.RED_PACKET_APPLY_CHECK_STATUS_REFUSE); // REFUSE 不通过
				result.setStatus(Constants.AUTO_RED_PACKET_AAPLY_STATUS_CANCEL); // 红包审核不通过  注销
				result.setChecker(req.getChecker());
				int row = redPacketApplyService.updateBsRedPacketApply(result);
				if (row > 0) {
					res.setFlag(ResMsg_BsRedPacketApply_BsRedPacketApplyRefuse.UPDATE_SUCCESS);
				} else {
					res.setFlag(ResMsg_BsRedPacketApply_BsRedPacketApplyRefuse.UPDATE_FAIL);
				}
			}
		}
	}
	

}
