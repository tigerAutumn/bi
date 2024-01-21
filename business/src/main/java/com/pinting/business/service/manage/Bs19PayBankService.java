package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.Bs19payBank;
import com.pinting.business.model.vo.Bs19payBankVO;

public interface Bs19PayBankService {
	
	/**
	 * 分页（连接bs_bank）
	 * @return
	 */
	public List<Bs19payBankVO> bs19payBankPage(Bs19payBankVO record);
	/**
	 * 多少条（连接bs_bank）
	 * @param record
	 * @return
	 */
	public int bs19payBankCount(Bs19payBankVO record);
	/**
	 * 根据id修改信息
	 * @param record
	 * @return
	 */
	public int updateBs19payBankKey(Bs19payBank record);
	/**
	 * 银行列表维护新增支付通道
	 * 1、查询数据库已存在同一银行相同支付类型数据列表（需要在此基础上面新增一个）
	 * 1.1、如果查询到数据，证明是新增一个支付通道，则需要考虑
	 * 		1.1.1 判断新增数据是否被设置为主通道，如果是主通道则需要把其它支付通道设置为2(副通道)
	 * 		1.1.2 重新查询同一银行相同支付类型列表（其它支付通道数据可能在上一步被改变）
	 * 		1.1.3 设置每个支付通道的优先级
	 * 			  如果新增的数据record的优先级大于同一银行相同支付类型其它支付通道（bs19payBankList）的数量，那么需要设置
	 * 			 record的优先级在原来支付通道数量基础上+1（bs19payBankList.size()+1）；
	 * 			 如果新增的数据record的优先级小于同一银行相同支付类型其它支付通道（bs19payBankList）的数量，
	 * 			 需要把数据库其它支付通道优先级大于record的优先级的数据的优先级+1后再新增支付通道数据
	 *
	 * 1.2、如果查询不到列表，证明需要新加的数据是第一条，优先级设置为1，直接新增
	 * @return
	 */
	public int addBs19payBank(Bs19payBank bs19payBank);
	/**
	 * 根据id查询
	 * @param record
	 * @return
	 */
	public Bs19payBank selectByPrimaryKey(Integer id);
	/**
	 * 查询
	 * @param record
	 * @return
	 */
	public Bs19payBank selectBs19payBank(Bs19payBank record);
	/**
	 * 查询多条
	 * @param record
	 * @return
	 */
	public List<Bs19payBank> bs19payBankList(Bs19payBank record);

	
	/**
	 * 修改信息
	 * @param record
	 * @return
	 */
	public int updateBs19payBank(Bs19payBank record);
	
	
	/**
	 * 编辑保存银行信息
	 * 可以正常保存的数据（未修改支付通道或者修改了支付通道但是不存在相同数据）
	 *	 	查询和需要保存保存的数据record相同银行支付类型相同的支付通道信息（可能包含自己原来的信息）bs19payBanks
	 *	 	1.1 bs19payBanks不为空    
	 *	 		1.1.1 需要保存保存的数据record是否是主通道数据，如果是主通道数据，则把数据库中的数据置为副通道
	 *	 		1.1.2 判断更改后的数据record的优先级是上升、下降、不变
	 *	 		下降（1-->3）：oldBank.getChannelPriority() <  bs19payBank <= record.getChannelPriority() 区间内的数据优先级-1
	 *	 		上升（3-->1）：oldBank.getChannelPriority() >  bs19payBank >= record.getChannelPriority() 区间内的数据优先级+1
	 *	 		不变：直接保存
	 *	 	
	 *	 	1.2 bs19payBanks为空，证明编辑保存的数据修改了支付通道信息，需要存的数据数据库中不存在，直接把优先级置为1保存数据    
	 * @param record
	 * @return
	 */
	public int detailBs19payBankKey(Bs19payBank record);
	
	
	/**
	 * 编辑或新增银行列表信息发起通知
	 */
	public void sendNotice();
}
