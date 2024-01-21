package com.pinting.business.dao;

import com.pinting.business.model.BsUserRegistView;
import com.pinting.business.model.BsUserRegistViewExample;
import com.pinting.business.model.vo.BsUserInvestViewVO;
import com.pinting.business.model.vo.BsUserRegistViewVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserRegistViewMapper {
    int countByExample(BsUserRegistViewExample example);

    int deleteByExample(BsUserRegistViewExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserRegistView record);

    int insertSelective(BsUserRegistView record);

    List<BsUserRegistView> selectByExample(BsUserRegistViewExample example);

    BsUserRegistView selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserRegistView record, @Param("example") BsUserRegistViewExample example);

    int updateByExample(@Param("record") BsUserRegistView record, @Param("example") BsUserRegistViewExample example);

    int updateByPrimaryKeySelective(BsUserRegistView record);

    int updateByPrimaryKey(BsUserRegistView record);
    
    
    /**
	 * 根据时间查询用户注册信息-之后需要存库
	 * @param startDate
	 * @param startdate
	 * @return
	 */
	BsUserRegistView selectByTime(@Param("startDate")String startDate,@Param("endDate")String endDate);
	
	/**
	 * 后台管理-根据时间查询用户注册信息
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<BsUserRegistView> selectListByViewVO(BsUserRegistViewVO bsUserRegistViewVO);

	/**
	 * 后台管理-根据时间查询用户注册信息-总条数
	 * @param bsUserRegistViewVO
	 * @return
	 */
	int selectCountByViewVO(BsUserRegistViewVO bsUserRegistViewVO);
	
}