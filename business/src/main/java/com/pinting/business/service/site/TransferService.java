package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsTransfer;
import com.pinting.business.model.vo.BsTransferVO;


public interface TransferService {
	
	/**
	 * 创建转让表记录，根据产品子账户id号更新子账户表记录
	 * @param transfer 转让表报文
	 * @param subAccount 子账户表
	 * @return 创建成功，返回true,否则返回false
	 */
	public boolean addTransfer(BsTransfer transfer, BsSubAccount subAccount);

	/**
	 * 查询当前待转让的产品列表，进行分页
	 * @param startPage
	 * @param pageSize
	 * @return 待转让的产品列表
	 */
	public List<BsTransferVO> findTransferList(Integer startPage, Integer pageSize);

	/**
	 * 当前待转让的产品条数
	 * @return 返回待转让产品条数
	 */
	public int findTransferCount();

	/**
	 * 根据id号查询转让产品
	 * @param id
	 * @return 待转让的产品
	 */
	public BsTransferVO findTransferById(Integer id);

	/**
	 * 根据id号更新转让产品记录
	 * @param bsTransfer
	 * @return 更新成功返回true，否则返回false
	 */
	public boolean updateTransferById(BsTransfer bsTransfer);

	/**
	 * 根据子账户表中的id号查找
	 * @param subAccountId
	 * @return
	 */
	public BsTransfer findTransferBySubAccountId(Integer subAccountId);

	/**
	 * 根据id号进行更新转让产品表
	 * @param transfer
	 * @return
	 */
	public boolean modifyTransferById(BsTransfer transfer);
}
