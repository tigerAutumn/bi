package com.pinting.business.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsAccountJnlExample;
import com.pinting.business.model.vo.BsAccountJnlVO;

public interface BsAccountJnlMapper {
    int countByExample(BsAccountJnlExample example);

    int deleteByExample(BsAccountJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAccountJnl record);

    int insertSelective(BsAccountJnl record);

    List<BsAccountJnl> selectByExample(BsAccountJnlExample example);
    List<BsAccountJnl> selectByExamplePage(Map<String, Object> data);

    BsAccountJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAccountJnl record, @Param("example") BsAccountJnlExample example);

    int updateByExample(@Param("record") BsAccountJnl record, @Param("example") BsAccountJnlExample example);

    int updateByPrimaryKeySelective(BsAccountJnl record);

    int updateByPrimaryKey(BsAccountJnl record);

	ArrayList<BsAccountJnlVO> selectAccountJnlListPageInfo(BsAccountJnlVO accountJnl);
	
	ArrayList<BsAccountJnlVO> selectAccountJnlList(BsAccountJnlVO accountJnl);
	
	int countAccountJnlList(BsAccountJnlVO accountJnl);
	
	/** 奖励金查询列表 */
	ArrayList<BsAccountJnlVO> selectUserBonusList(BsAccountJnlVO accountJnl);
	
	/** 奖励金查询金额合计 */
	double selectSumUserBonus(BsAccountJnlVO accountJnl);
	
	/** 奖励金查询记录统计 */
	int selectCoountUserBonus(BsAccountJnlVO accountJnl);
	
	/** 用户充值列表 */
	ArrayList<BsAccountJnlVO> selectUserTopUpList(BsAccountJnlVO accountJnl);
	
	/** 用户充值金额合计 */
	double selectUserSumTopUp(BsAccountJnlVO accountJnl);
	
	/** 用户充值记录统计 */
	int selectUserCountTopUp(BsAccountJnlVO accountJnl);
	
	/** 用户记账流水查询 */
	ArrayList<BsAccountJnlVO> selectUserChargeAccountList(BsAccountJnlVO accountJnl);
	
	/** 用户记账流水记录统计 */
	int selectCountUserChargeAccount(BsAccountJnlVO accountJnl);
	
	/** 用户记账流水金额合计 */
	double selectSumUserChargeAccount(BsAccountJnlVO accountJnl);
	
	/** 某用户的奖励金查询列表 */
	ArrayList<BsAccountJnl> selectUserBonusByUserId(Integer userId);
	/** 查询当月平台转个人的已使用额度*/
	Double selectUsedPlatTransAmount();
}