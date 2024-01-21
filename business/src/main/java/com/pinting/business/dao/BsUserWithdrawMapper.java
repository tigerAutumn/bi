package com.pinting.business.dao;

import com.pinting.business.model.BsUserWithdraw;
import com.pinting.business.model.BsUserWithdrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserWithdrawMapper {
    int countByExample(BsUserWithdrawExample example);

    int deleteByExample(BsUserWithdrawExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserWithdraw record);

    int insertSelective(BsUserWithdraw record);

    List<BsUserWithdraw> selectByExample(BsUserWithdrawExample example);
    List<BsUserWithdraw> findCustomerWithdrawByPage(BsUserWithdraw model);
    BsUserWithdraw selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserWithdraw record, @Param("example") BsUserWithdrawExample example);

    int updateByExample(@Param("record") BsUserWithdraw record, @Param("example") BsUserWithdrawExample example);

    int updateByPrimaryKeySelective(BsUserWithdraw record);

    int updateByPrimaryKey(BsUserWithdraw record);
}