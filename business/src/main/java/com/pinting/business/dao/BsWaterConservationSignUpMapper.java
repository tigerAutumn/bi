package com.pinting.business.dao;

import com.pinting.business.model.BsWaterConservationSignUp;
import com.pinting.business.model.BsWaterConservationSignUpExample;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BsWaterConservationSignUpMapper {
    int countByExample(BsWaterConservationSignUpExample example);

    int deleteByExample(BsWaterConservationSignUpExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsWaterConservationSignUp record);

    int insertSelective(BsWaterConservationSignUp record);

    List<BsWaterConservationSignUp> selectByExample(BsWaterConservationSignUpExample example);

    BsWaterConservationSignUp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsWaterConservationSignUp record, @Param("example") BsWaterConservationSignUpExample example);

    int updateByExample(@Param("record") BsWaterConservationSignUp record, @Param("example") BsWaterConservationSignUpExample example);

    int updateByPrimaryKeySelective(BsWaterConservationSignUp record);

    int updateByPrimaryKey(BsWaterConservationSignUp record);

    BsWaterConservationSignUp selectRecentData(@Param("userId") Integer userId);

    List<BsWaterConservationSignUp> selectByPage(@Param("start") int start, @Param("numPerPage") int numPerPage);

    List<BsWaterConservationSignUp> selectBySignUpNo(@Param("signUpNo") Integer signUpNo, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    int countBySignUpNo(@Param("signUpNo") Integer signUpNo);
}