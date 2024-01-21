package com.pinting.business.service.manage.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsErrorCodeMapper;
import com.pinting.business.model.BsErrorCode;
import com.pinting.business.model.vo.BsErrorCodeVO;
import com.pinting.business.service.manage.BsErrorCodeService;

@Service
public class BsErrorCodeServiceImpl implements BsErrorCodeService {
	
	@Autowired
	private BsErrorCodeMapper errorCodeMapper;

	@Override
	public ArrayList<BsErrorCodeVO> findErrorCodeList(String errorCodeChannel, String interfaceType, String errorCodeOther, 
			String errorInMsg, String errorOutMsg, int pageNum, 
			int numPerPage, String orderDirection, String orderField) {
		BsErrorCodeVO bsErrorCode = new BsErrorCodeVO();
		if(errorCodeChannel != null && !"".equals(errorCodeChannel)) {
			bsErrorCode.setErrorCodeChannel(errorCodeChannel);
		}
		if(interfaceType != null && !"".equals(interfaceType)) {
			bsErrorCode.setInterfaceType(interfaceType);
		}
		if(errorCodeOther != null && !"".equals(errorCodeOther)) {
			bsErrorCode.setErrorCodeOther(errorCodeOther);
		}
		if(errorInMsg != null && !"".equals(errorInMsg)) {
			bsErrorCode.setErrorInMsg(errorInMsg);
		}
		if(errorOutMsg != null && !"".equals(errorOutMsg)) {
			bsErrorCode.setErrorOutMsg(errorOutMsg);
		}
		if(orderDirection != null && (!"".equals(orderDirection)) && orderField != null && (!"".equals(orderField))) {
			bsErrorCode.setOrderDirection(orderDirection);
			bsErrorCode.setOrderField(orderField);
		}
		bsErrorCode.setPageNum(pageNum);
		bsErrorCode.setNumPerPage(numPerPage);
		return errorCodeMapper.selectErrorCodeListPageInfo(bsErrorCode);
	}

	@Override
	public int findCountErrorCode(String errorCodeChannel, String interfaceType, String errorCodeOther, 
			String errorInMsg, String errorOutMsg) {
		BsErrorCodeVO bsErrorCode = new BsErrorCodeVO();
		if(errorCodeChannel != null && !"".equals(errorCodeChannel)) {
		bsErrorCode.setErrorCodeChannel(errorCodeChannel);
	    }
		if(interfaceType != null && !"".equals(interfaceType)) {
			bsErrorCode.setInterfaceType(interfaceType);
		}
		if(errorCodeOther != null && !"".equals(errorCodeOther)) {
			bsErrorCode.setErrorCodeOther(errorCodeOther);
		}
		if(errorInMsg != null && !"".equals(errorInMsg)) {
			bsErrorCode.setErrorInMsg(errorInMsg);
		}
		if(errorOutMsg != null && !"".equals(errorOutMsg)) {
			bsErrorCode.setErrorOutMsg(errorOutMsg);
		}
		return errorCodeMapper.selectCountErrorCode(bsErrorCode);
	}
	
	/**
	 * 根据id修改信息
	 */
	@Override
	public int updateBsErrorCode(BsErrorCodeVO record) {
		return errorCodeMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 添加
	 */
	@Override
	public int addBsErrorCode(BsErrorCodeVO record) {
		return errorCodeMapper.insert(record);
	}

	/**
	 * 根据id查询
	 */
	@Override
	public BsErrorCode bsErrorCodePrimaryKey(Integer id) {
		return errorCodeMapper.selectByPrimaryKey(id);
	}

	/**
	 * 根据id删除
	 */
	@Override
	public int deleteBsErrorCodeById(Integer id) {
		return errorCodeMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 查询错误码是否已存在
	 */
	@Override
	public BsErrorCode selectBsErrorCode(BsErrorCode record) {
		return errorCodeMapper.selectErrorCode(record);
	}

}
