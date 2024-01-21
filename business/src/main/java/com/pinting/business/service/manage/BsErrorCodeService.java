package com.pinting.business.service.manage;

import java.util.ArrayList;

import com.pinting.business.model.BsErrorCode;
import com.pinting.business.model.vo.BsErrorCodeVO;

public interface BsErrorCodeService {
	
	/**
	 * 错误码信息列表
	 * @param errorCodeChannel 渠道类型 
	 * @param interfaceType 接口类型
	 * @param errorCodeOther 错误码
	 * @param errorInMsg 错误码内部描述
	 * @param errorOutMsg 错误码外部描述
	 * @param pageNum 分页
	 * @param numPerPage
	 * @param orderDirection 排序
	 * @param orderField
	 * @return
	 */
	public ArrayList<BsErrorCodeVO> findErrorCodeList(String errorCodeChannel, String interfaceType, String errorCodeOther, 
			String errorInMsg, String errorOutMsg, int pageNum, 
			int numPerPage, String orderDirection, String orderField);
	
	/**
	 * 错误码信息记录统计
	 * @param errorCodeChannel 渠道类型 
	 * @param interfaceType 接口类型
	 * @param errorCodeOther 错误码
	 * @param errorInMsg 错误码内部描述
	 * @param errorOutMsg 错误码外部描述
	 * @return
	 */
	public int findCountErrorCode(String errorCodeChannel, String interfaceType, String errorCodeOther, 
			String errorInMsg, String errorOutMsg);
	
	/**
	 * 根据id修改信息
	 * @param record
	 * @return
	 */
	public int updateBsErrorCode(BsErrorCodeVO record);
	
	/**
	 * 添加
	 * @param record
	 * @return
	 */
	public int addBsErrorCode(BsErrorCodeVO record);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public BsErrorCode bsErrorCodePrimaryKey(Integer id);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteBsErrorCodeById(Integer id);
	
	/**
	 * 查询错误码是否已存在
	 * @param record
	 * @return
	 */
	public BsErrorCode selectBsErrorCode(BsErrorCode record);

}
