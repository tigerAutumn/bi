package com.pinting.business.dao;

import com.pinting.business.model.BsEcup2016Supportor;
import com.pinting.business.model.BsEcup2016SupportorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsEcup2016SupportorMapper {
    int countByExample(BsEcup2016SupportorExample example);

    int deleteByExample(BsEcup2016SupportorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsEcup2016Supportor record);

    int insertSelective(BsEcup2016Supportor record);

    List<BsEcup2016Supportor> selectByExample(BsEcup2016SupportorExample example);

    BsEcup2016Supportor selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsEcup2016Supportor record, @Param("example") BsEcup2016SupportorExample example);

    int updateByExample(@Param("record") BsEcup2016Supportor record, @Param("example") BsEcup2016SupportorExample example);

    int updateByPrimaryKeySelective(BsEcup2016Supportor record);

    int updateByPrimaryKey(BsEcup2016Supportor record);
    
    /**
     * site根据用户查询助力者列表
     * @param userId
     * @param start
     * @param pageSize
     * @return
     */
    List<BsEcup2016Supportor> getEcup2016SupportorList(@Param("userId") Integer userId,
    		@Param("start") Integer start, @Param("pageSize") Integer pageSize);
    
    /**
     * site根据用户查询助力者列表-总条数
     * @param userId
     * @return
     */
    int countEcup2016SupportorList(@Param("userId") Integer userId);
}