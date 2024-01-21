package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_BsErrorCodeDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_BsErrorCodeModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_ErrorCodeList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_BsErrorCodeDelete;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_BsErrorCodeModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_ErrorCodeList;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_SelectByPrimaryKey;
import com.pinting.business.model.BsErrorCode;
import com.pinting.business.model.vo.BsErrorCodeVO;
import com.pinting.business.service.manage.BsErrorCodeService;
import com.pinting.business.util.BeanUtils;

/**
 * 
 * @author shh
 *
 */
@Component("BsErrorCode")
public class BsErrorCodeFacade {
	
	@Autowired
	private BsErrorCodeService errorCodeService;
	
	/**
	 * 错误码信息列表
	 * @param reqMsg
	 * @param resMsg
	 */
	public void errorCodeList(ReqMsg_BsErrorCode_ErrorCodeList reqMsg, ResMsg_BsErrorCode_ErrorCodeList resMsg) {
		int totalRows = errorCodeService.findCountErrorCode(reqMsg.getErrorCodeChannel(), reqMsg.getInterfaceType(), reqMsg.getErrorCodeOther(), 
				reqMsg.getErrorInMsg(), reqMsg.getErrorOutMsg());
		if (totalRows > 0) {
			ArrayList<BsErrorCodeVO> bsErrorCodeList = errorCodeService.findErrorCodeList(reqMsg.getErrorCodeChannel(), reqMsg.getInterfaceType(), reqMsg.getErrorCodeOther(), 
					reqMsg.getErrorInMsg(), reqMsg.getErrorOutMsg(), reqMsg.getPageNum(),
					reqMsg.getNumPerPage(), reqMsg.getOrderDirection(), reqMsg.getOrderField());
			ArrayList<HashMap<String, Object>> errorList = BeanUtils.classToArrayList(bsErrorCodeList);
			resMsg.setValueList(errorList);
		}
		resMsg.setTotalRows(totalRows);
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
	}
	
	/**
	 * 进入添加/编辑页面
	 * @param req
	 * @param res
	 */
	public void selectByPrimaryKey(ReqMsg_BsErrorCode_SelectByPrimaryKey req, ResMsg_BsErrorCode_SelectByPrimaryKey res) {
		if(req.getId() != null && req.getId() != 0) {
			BsErrorCode bsErrorCode = errorCodeService.bsErrorCodePrimaryKey(req.getId());
			res.setId(bsErrorCode.getId());
			res.setChannel(bsErrorCode.getChannel());
			res.setInterfaceType(bsErrorCode.getInterfaceType());
			res.setErrorCode(bsErrorCode.getErrorCode());
			res.setErrorInMsg(bsErrorCode.getErrorInMsg());
			res.setErrorOutMsg(bsErrorCode.getErrorOutMsg());
			res.setCreateTime(bsErrorCode.getCreateTime());
			res.setUpdateTime(bsErrorCode.getUpdateTime());
		}
	}
	
	/**
	 * 添加/编辑
	 * @param req
	 * @param res
	 */
	public void bsErrorCodeModify(ReqMsg_BsErrorCode_BsErrorCodeModify req, ResMsg_BsErrorCode_BsErrorCodeModify res) {
		BsErrorCode bsErrorCode = new BsErrorCode();
		BsErrorCodeVO bsErrorCodeVO = new BsErrorCodeVO();
		if(req != null) {
			bsErrorCode.setErrorCode(req.getErrorCode());
			//查询错误码是否已存在
			BsErrorCode result =  errorCodeService.selectBsErrorCode(bsErrorCode);
			bsErrorCodeVO.setId(req.getId());
			bsErrorCodeVO.setErrorCodeChannel(req.getErrorCodeChannel());
			bsErrorCodeVO.setErrorCode(req.getErrorCode());
			bsErrorCodeVO.setInterfaceTypeOther(req.getInterfaceTypeOther());
			bsErrorCodeVO.setErrorInMsg(req.getErrorInMsg());
			bsErrorCodeVO.setErrorOutMsg(req.getErrorOutMsg());
			if(req.getId() != null && req.getId() != 0) { //编辑
				if(result != null ) {
					if(result.getId().equals(req.getId())) {
						bsErrorCodeVO.setUpdateTime(new Date());
						errorCodeService.updateBsErrorCode(bsErrorCodeVO);
						res.setFlag(ResMsg_BsErrorCode_BsErrorCodeModify.MODIFY_SUCCESS);
					} else {
						res.setFlag(ResMsg_BsErrorCode_BsErrorCodeModify.REPEAT_NAME);
					}
				} else {
					bsErrorCodeVO.setUpdateTime(new Date());
					errorCodeService.updateBsErrorCode(bsErrorCodeVO);
					res.setFlag(ResMsg_BsErrorCode_BsErrorCodeModify.MODIFY_SUCCESS);
				}
			} else { //添加
				if( result == null) {
					bsErrorCodeVO.setCreateTime(new Date());
					bsErrorCodeVO.setUpdateTime(new Date());
					errorCodeService.addBsErrorCode(bsErrorCodeVO);
					res.setFlag(ResMsg_BsErrorCode_BsErrorCodeModify.INSERT_SUCCESS);
				} else {
					res.setFlag(ResMsg_BsErrorCode_BsErrorCodeModify.REPEAT_NAME);
				}
			}
		} 
	}
	
	/**
	 * 删除
	 * @param req
	 * @param res
	 */
	public void bsErrorCodeDelete(ReqMsg_BsErrorCode_BsErrorCodeDelete req, ResMsg_BsErrorCode_BsErrorCodeDelete res) {
		if(req.getId() != null && req.getId() != 0) {
			errorCodeService.deleteBsErrorCodeById(req.getId());
		}
	}
	
}


