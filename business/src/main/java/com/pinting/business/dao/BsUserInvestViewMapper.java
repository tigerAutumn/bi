package com.pinting.business.dao;

import com.pinting.business.model.BsUserInvestView;
import com.pinting.business.model.BsUserInvestViewExample;
import com.pinting.business.model.vo.BsUserInvestViewVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserInvestViewMapper {
    int countByExample(BsUserInvestViewExample example);

    int deleteByExample(BsUserInvestViewExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserInvestView record);

    int insertSelective(BsUserInvestView record);

    List<BsUserInvestView> selectByExample(BsUserInvestViewExample example);

    BsUserInvestView selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserInvestView record, @Param("example") BsUserInvestViewExample example);

    int updateByExample(@Param("record") BsUserInvestView record, @Param("example") BsUserInvestViewExample example);

    int updateByPrimaryKeySelective(BsUserInvestView record);

    int updateByPrimaryKey(BsUserInvestView record);
    
    List<BsUserInvestView> selectUserInvestList(BsUserInvestViewVO userInvestView);
    
    int selectUserInvestAllCount(BsUserInvestViewVO userInvestView);
}