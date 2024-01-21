package com.pinting.business.service.manage;

import com.pinting.business.accounting.finance.model.SysRechargeResultInfo;
import com.pinting.business.accounting.finance.model.SysWithdrawResultInfo;
import com.pinting.business.model.vo.BsHfBankSysAccountTransferVo;
import com.pinting.business.model.vo.BsHfBankSysLoanerWithdrawVo;
import com.pinting.business.model.vo.BsHfBankSysTopUpVo;
import com.pinting.business.model.vo.BsHfBankSysWithdrawVo;

/**
 * 
 * @author SHENGP
 * @date  2017年4月19日 下午4:12:21
 */
public interface BsHfBankService {

	/**
	 * 恒丰系统充值，只返回是否通讯成功，需等待异步通知结果
	 * @param bsHfBankSysTopUpVo
	 * @return
	 */
	String sysTopUp(BsHfBankSysTopUpVo bsHfBankSysTopUpVo);
	
	/**
	 * 恒丰系统提现，只返回是否通讯成功，需等待异步通知结果
	 * @param bsHfBankSysWithdrawVo
	 * @return
	 */
	String sysWithdraw(BsHfBankSysWithdrawVo bsHfBankSysWithdrawVo);
	
	/**
	 * 恒丰系统充值通知
	 * @param 
	 * @return
	 */
	void sysTopUpNotify(SysRechargeResultInfo req);
	
	/**
	 * 恒丰系统提现通知
	 * @param 
	 * @return
	 */
	void sysWithdrawNotify(SysWithdrawResultInfo req);
	
	/**
	 * 恒丰系统账户转账
	 * @param bsHfBankSysWithdrawVo
	 * @return
	 */
	String sysAccountTransfer(BsHfBankSysAccountTransferVo bsHfBankSysAccountTransferVo);
	
	/**
	 * 恒丰平台转个人
	 * @param bsHfBankSysWithdrawVo
	 * @return
	 */
	String sysAccountTransferToPerson(BsHfBankSysAccountTransferVo bsHfBankSysAccountTransferVo);
	
	/**
	 * 管理台借款人提现（不记账）
	 * @param bsHfBankSysWithdrawVo
	 * @return
	 */
	String sysLoanerWithdrawNoCharge(BsHfBankSysLoanerWithdrawVo bsHfBankSysLoanerWithdrawVo);
	
	/**
	 * 恒丰系统账户转账-管理台（垫付金）人工调帐（不记账）
	 * @param bsHfBankSysWithdrawVo
	 * @return
	 */
	String sysAccountTransferNoCharge(BsHfBankSysAccountTransferVo bsHfBankSysAccountTransferVo);
	
}
