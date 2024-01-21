package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsDailyBonus;
import com.pinting.business.model.vo.BsDailyBonusVO;

public interface BsDailyBonusService {
	
	
	/**
	 * 分页
	 * @param record
	 * @return
	 */
	public List<BsDailyBonusVO> bsDailyBonusPage(BsDailyBonusVO record);
	/**
	 * 数量
	 * @param record
	 * @return
	 */
	public int bsDailyBonusCount(BsDailyBonusVO record);
	/**
	 * 金额总数
	 * @param record
	 * @return
	 */
	public Double bonusSum(BsDailyBonusVO record);
	/**
	 * 首月奖励金
	 * @param subAccountKey 子账号编号
	 * @return
	 */
	BsDailyBonusVO subAccountKeySumBonus(Integer subAccountKey);

	/**
	 * 客服菜单用户奖励金查询-详情查看-根据subAccountId查询
	 * @param record
	 * @return
	 */
	public List<BsDailyBonusVO> bsDailyBonus4ServiceDetail(BsDailyBonusVO record);
	
	/**
	 * 根据subAccountId查询,奖励金总金额
	 * @param record
	 * @return
	 */
	public Double bonusSum4ServiceDetail(BsDailyBonusVO record);
}
