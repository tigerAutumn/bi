package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.vo.LnCreditTransfer2VIPVO;
import com.pinting.business.model.vo.LnCreditTransferMQueryVO;
import com.pinting.business.model.vo.LnCreditTransferMVO;

/**
 * 管理台债权转让相关
 * @author bianyatian
 * @2016-12-2 下午2:56:01
 */
public interface MLnCreditTransferService {
	
	/**
	 * 债权转让统计查询-列表
	 * @param queryVo
	 * @return
	 */
	List<LnCreditTransferMVO> getTransferList(LnCreditTransferMQueryVO queryVo);
	
	/**
	 * 债权转让统计查询-条数
	 * @param queryVo
	 * @return
	 */
	int countTransferList(LnCreditTransferMQueryVO queryVo);
	
	/**
	 * 债权转让统计查询-债转本金总额
	 * @return
	 */
	Double transSumPrincipal(LnCreditTransferMQueryVO queryVo);
	
	/**
	 * 债权转让统计查询-投资客户总利息
	 * @return
	 */
	Double transSumInterest(LnCreditTransferMQueryVO queryVo);
	
	/**
	 * 债转支付VIP
	 * @param queryVo
	 */
	LnCreditTransfer2VIPVO getTransfer2VIP(LnCreditTransferMQueryVO queryVo);
	
	/**
	 * 债转支付VIP导出
	 * @param queryVo
	 */
	LnCreditTransfer2VIPVO getTransfer2VIP4Export(LnCreditTransferMQueryVO queryVo);
	
	/**
	 * 债转支付(存管后，云贷/赞时贷)
	 * @param queryVo
	 */
	LnCreditTransfer2VIPVO getTransferPay(LnCreditTransferMQueryVO queryVo);
	
	/**
	 * 债转支付导出(存管后，云贷/赞时贷)
	 * @param queryVo
	 */
	LnCreditTransfer2VIPVO getTransferPay4Export(LnCreditTransferMQueryVO queryVo);
	
}
