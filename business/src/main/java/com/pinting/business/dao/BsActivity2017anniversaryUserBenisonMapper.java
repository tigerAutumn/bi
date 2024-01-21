package com.pinting.business.dao;

import com.pinting.business.model.BsActivity2017anniversaryUserBenison;
import com.pinting.business.model.BsActivity2017anniversaryUserBenisonExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsActivity2017anniversaryUserBenisonMapper {
    long countByExample(BsActivity2017anniversaryUserBenisonExample example);

    int deleteByExample(BsActivity2017anniversaryUserBenisonExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsActivity2017anniversaryUserBenison record);

    int insertSelective(BsActivity2017anniversaryUserBenison record);

    List<BsActivity2017anniversaryUserBenison> selectByExample(BsActivity2017anniversaryUserBenisonExample example);

    BsActivity2017anniversaryUserBenison selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsActivity2017anniversaryUserBenison record, @Param("example") BsActivity2017anniversaryUserBenisonExample example);

    int updateByExample(@Param("record") BsActivity2017anniversaryUserBenison record, @Param("example") BsActivity2017anniversaryUserBenisonExample example);

    int updateByPrimaryKeySelective(BsActivity2017anniversaryUserBenison record);

    int updateByPrimaryKey(BsActivity2017anniversaryUserBenison record);

    /**
     * 分页查询已经审核通过的祝福语（用户自己的祝福语无论是否审核通过都直接显示）
     * @param userId
     * @param start
     * @param numPerPage
     * @param source        manage;user
     * @return
     */
    List<BsActivity2017anniversaryUserBenison> selectByPage(@Param("userId") Integer userId, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage, @Param("source") String source);
}