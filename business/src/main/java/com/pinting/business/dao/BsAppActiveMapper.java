package com.pinting.business.dao;

import com.pinting.business.model.BsAppActive;
import com.pinting.business.model.BsAppActiveExample;
import com.pinting.business.model.vo.AppActiveVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsAppActiveMapper {
    int countByExample(BsAppActiveExample example);

    int deleteByExample(BsAppActiveExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAppActive record);

    int insertSelective(BsAppActive record);

    List<BsAppActive> selectByExample(BsAppActiveExample example);

    BsAppActive selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAppActive record, @Param("example") BsAppActiveExample example);

    int updateByExample(@Param("record") BsAppActive record, @Param("example") BsAppActiveExample example);

    int updateByPrimaryKeySelective(BsAppActive record);

    int updateByPrimaryKey(BsAppActive record);

    List<AppActiveVO> selectAppActiveListByPage(AppActiveVO record);

    int selectAppActiveTotalRows(AppActiveVO record);

    BsAppActive selectLatestActive();

    /**
     * 活动中心-分页查询
     * @param showTerminal 活动发布端口
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsAppActive> selectActivePage(@Param("showTerminal") String showTerminal, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
}