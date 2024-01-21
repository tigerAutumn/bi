package com.pinting.business.service.site;

import com.pinting.business.model.BsEcup2016Supportor;
import com.pinting.business.model.vo.BsEcup2016ActivityUserInfoVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.vo.BsProductVO;

/**
 * 2016欧洲杯活动相关
 * @author bianyatian
 * @2016-6-21 下午4:27:53
 */
public interface Ecup2016ActivityService {
	 /**
     * 查询欧洲杯相关理财产品
     * @param showTerminal  展示端口
     * @param type  类型（NORMAL：欧洲杯产品；NEW_USER：新用户产品）
     * @return
     */
    public List<BsProductVO> queryEcupProductGrid(String showTerminal, String type);
    
    /**
     * 保存助力者信息
     * @param userId 活动参与者编号
     * @param wxId  微信ID（OPENID）
     * @param wxNick    微信昵称
     */
    public void saveSupportor(Integer userId, String wxId, String wxNick);
	
	/**
	 * 用户竞猜结果查询（用户助力值查询+排名查询）
	 * @param userId
	 * @return
	 */
	public BsEcup2016ActivityUserInfoVO selectEcup2016ActivityUserInfo(Integer userId);

	
	/**
	 * 保存用户猜冠亚军记录（检查数据完整）
	 * @param userId
	 * @param champion
	 * @param silver
	 */
	public boolean saveUserChampionSilver(Integer userId,String champion,String silver);
	
	/**
	 * 冠军投票列表，支持率
	 * @return
	 */
	public  List<BsEcup2016ActivityUserInfoVO> selectEcup2016ChampionList();

	/**
	 * 亚军投票列表，支持率
	 * @return
	 */
	public  List<BsEcup2016ActivityUserInfoVO> selectEcup2016SilverList();
	
	/**
	 * 根据用户查询助力者列表
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<BsEcup2016Supportor> getEcup2016SupportorList(Integer userId, Integer pageIndex, Integer pageSize);

	/**
	 * 根据用户查询助力者列表-总条数
	 * @param userId
	 * @return
	 */
	public int countEcup2016SupportorList(Integer userId);
	
	/**
	 * 助力值排行榜
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public  List<BsEcup2016ActivityUserInfoVO> getEcup2016WinnerList(Integer pageIndex, Integer pageSize);

	/**
	 * 助力值排行榜-总条数
	 * @return
	 */
	public int countEcup2016WinnerList();
}
