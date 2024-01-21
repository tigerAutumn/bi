package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsWxAutoReply;
import com.pinting.business.model.vo.BsWxAutoReplyVO;

public interface BsWxAutoReplyService {
	
	/**
	 * 新增
	 * @param bsWxAutoReply
	 */
	public void addWxAutoReply(BsWxAutoReply bsWxAutoReply);
	
	/**
	 * 修改
	 * @param bsWxAutoReply
	 */
	public void updateReply(BsWxAutoReply bsWxAutoReply);
	
	/**
	 * 管理台列表查询
	 * @param bsWxAutoReplyVO
	 * @return
	 */
	public List<BsWxAutoReply> getListByReplyVO(BsWxAutoReplyVO bsWxAutoReplyVO);
	
	/**
	 * 管理台列表总条数
	 * @param bsWxAutoReplyVO
	 * @return
	 */
	public int getCountByReplyVO(BsWxAutoReplyVO bsWxAutoReplyVO);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public BsWxAutoReply getById(Integer id);

	/**
	 * 根据id删除
	 * @param id
	 */
	public void deleteReply(Integer id);
}
