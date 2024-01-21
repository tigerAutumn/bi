package com.pinting.business.dao;

import com.pinting.business.model.BsEcup2016ActivityUser;
import com.pinting.business.model.BsEcup2016ActivityUserExample;
import com.pinting.business.model.vo.BsEcup2016ActivityUserInfoVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsEcup2016ActivityUserMapper {
    int countByExample(BsEcup2016ActivityUserExample example);

    int deleteByExample(BsEcup2016ActivityUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsEcup2016ActivityUser record);

    int insertSelective(BsEcup2016ActivityUser record);

    List<BsEcup2016ActivityUser> selectByExample(BsEcup2016ActivityUserExample example);

    BsEcup2016ActivityUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsEcup2016ActivityUser record, @Param("example") BsEcup2016ActivityUserExample example);

    int updateByExample(@Param("record") BsEcup2016ActivityUser record, @Param("example") BsEcup2016ActivityUserExample example);

    int updateByPrimaryKeySelective(BsEcup2016ActivityUser record);

    int updateByPrimaryKey(BsEcup2016ActivityUser record);
    
    /**
     * 用户竞猜结果查询（用户助力值查询+排名查询）
     * @param userId
     * @return
     */
    BsEcup2016ActivityUserInfoVO selectEcup2016ActivityUserInfo(@Param("userId")Integer userId);
    
    /**
     * 用户竞猜条数查询
     * @param userId
     * @return
     */
    List<BsEcup2016ActivityUser> selectByUserId(@Param("userId")Integer userId);
    
    
    /**
     * 冠军投票列表，支持率
     * @return
     */
    List<BsEcup2016ActivityUserInfoVO> selectEcup2016ChampionList();
    
    
    /**
     * 亚军投票列表，支持率
     * @return
     */
    List<BsEcup2016ActivityUserInfoVO> selectEcup2016SilverList();
    
    
    /**
	 * 助力值排行榜
	 * @param start
	 * @param pageSize
	 * @return
	 */
	List<BsEcup2016ActivityUserInfoVO> getEcup2016WinnerList(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

	/**
	 * 助力值排行榜-总条数
	 * @return
	 */
	int countEcup2016WinnerList();
    
    
}