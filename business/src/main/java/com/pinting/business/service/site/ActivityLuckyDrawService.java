package com.pinting.business.service.site;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.vo.BsActivityLuckyDrawVO;

public interface ActivityLuckyDrawService {
	
	/**
	 * 查询中奖列表，最新中奖在前
	 * @param activityId
	 * @param limitNum 中奖条数，空则表示查询所有
	 * @return
	 */
	public  List<BsActivityLuckyDrawVO> getLuckyAllList(Integer activityId, Integer limitNum);
	
	/**
	 * 查询幸运借款人的中奖列表
	 * @param activityId
	 * @param limitNum 中奖条数，空则表示查询所有
	 * @return
	 */
	public  List<BsActivityLuckyDrawVO> getLuckyLenders(Integer activityId);
	
	/**
	 * 根据中奖日期查询幸运借款人的中奖列表
	 * @param activityId
	 * @param limitNum 中奖条数，空则表示查询所有
	 * @return
	 */
	public  List<BsActivityLuckyDrawVO> getLuckyLendersByDate(Integer activityId, String backDrawTime);
	
	/**
	 * 查询当前已抽奖人数,去重复的userId
	 * @param activityId
	 * @return
	 */
	public  int countLuckyNum(Integer activityId);
	
	
	/**
	 * luckyFlag：YES查询用户已中奖品：is_win = 'Y',is_user_draw = 'Y',user_draw_time desc
     * luckyFlag：No查询用户未抽奖列表
	 * @param activityId
	 * @param userId
	 * @param luckyFlag YES-已抽奖，No-未抽奖
	 * @param start
	 * @param page
	 * @return
	 */
	public List<BsActivityLuckyDrawVO> getUserLuckyList(Integer activityId,
    		Integer userId, String luckyFlag, Integer start, Integer page);
	
	/**
	 * luckyFlag：YES查询用户已中奖品：is_win = 'Y',is_user_draw = 'Y',user_draw_time desc
     * luckyFlag：No查询用户未抽次数
	 * @param activityId
	 * @param userId
	 * @param luckyFlag YES-已抽奖，No-未抽奖
	 * @return
	 */
	public  int countUserLucky(Integer activityId,
    		Integer userId, String luckyFlag);
	
	
	/**
	 * 用户抽奖，修改数据--抽奖,修改用户中奖状态，用户抽奖状态，抽奖时间
	 * @param activityLuckyDraw
	 */
	public void updateUserDraw(BsActivityLuckyDraw activityLuckyDraw);
	
	
	/**
	 * 核心：中奖记录概率生成（根据活动文案，按对应概率生成）
	 * @param userId 用户id
	 * @param amount 投资金额
	 * @param productName 投资产品名称
	 */
	public void addActivityLuckyDraw(Integer userId,Double amount,String productName);


	/**
	 * 获取奖品id
	 * @param amount 投资金额
	 * @param productTerm 购买的产品的期限
	 */
	int getLuckyAward(Double amount,int productTerm);

	/**
	 * 2016-双11活动
	 * 核心：中奖纪录生成
	 * @param userId 用户id
	 * @param amount 投资金额
	 * @param subAccountId 子账户id
	 */
	public void addActivityLuckyDrawDouble11(Integer userId,Double amount,Integer subAccountId);
	
	/**
	 * 周周乐-幸运出借人
	 * 核心：中奖纪录生成
	 * @param userId 用户id
	 * @param amount 投资金额
	 * @param subAccountId 子账户id
	 */
	public void addLuckyLenders(Integer userId, Integer awardId, String note);

	/**
	 * 判断用户是否领取过红包
	 * @param userId 用户id
	 * @param redPacketName红包名称
	 * @return true-已发放；false-未发放
     */
    boolean judgeUserDrawed161Packet(Integer userId,String redPacketName);

	/**
	 * 拆红包
	 * @param userId
	 * @param redPacketName红包名称
     */
	void openPacket(Integer userId, String redPacketName);

	/**
	 * 新增红包奖励
	 * @param awardId
     */
	BsActivityLuckyDraw saveRedPacketActivityLuckyDraw(Integer userId, Integer activityId, Integer awardId);

	/**
	 * 根据用户id和活动id查询中奖数据
	 * @param userId
	 * @param activityId
	 * @return
	 */
	int selectUserLuckyDraws(Integer userId, Integer activityId);
	
}
