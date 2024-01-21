package com.pinting.business.dao;

import com.pinting.business.model.BsUserMainOperation;
import com.pinting.business.model.BsUserMainOperationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserMainOperationMapper {
    int countByExample(BsUserMainOperationExample example);

    int deleteByExample(BsUserMainOperationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserMainOperation record);

    int insertSelective(BsUserMainOperation record);

    List<BsUserMainOperation> selectByExample(BsUserMainOperationExample example);

    BsUserMainOperation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserMainOperation record, @Param("example") BsUserMainOperationExample example);

    int updateByExample(@Param("record") BsUserMainOperation record, @Param("example") BsUserMainOperationExample example);

    int updateByPrimaryKeySelective(BsUserMainOperation record);

    int updateByPrimaryKey(BsUserMainOperation record);
    
    List<BsUserMainOperation> selectUserMainOperationList(BsUserMainOperation userMainOperation);
   
    int selectUserMainOperationAllCount(BsUserMainOperation userMainOperation);
}