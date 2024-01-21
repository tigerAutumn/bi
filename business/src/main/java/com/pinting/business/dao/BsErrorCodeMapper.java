package com.pinting.business.dao;

import com.pinting.business.model.BsErrorCode;
import com.pinting.business.model.BsErrorCodeExample;
import com.pinting.business.model.vo.BsErrorCodeVO;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsErrorCodeMapper {
    int countByExample(BsErrorCodeExample example);

    int deleteByExample(BsErrorCodeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsErrorCodeVO record);
    
    int insertSelective(BsErrorCode record);

    List<BsErrorCode> selectByExample(BsErrorCodeExample example);

    BsErrorCode selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsErrorCode record, @Param("example") BsErrorCodeExample example);

    int updateByExample(@Param("record") BsErrorCode record, @Param("example") BsErrorCodeExample example);

    int updateByPrimaryKeySelective(BsErrorCodeVO record);

    int updateByPrimaryKey(BsErrorCode record);
    
    ArrayList<BsErrorCodeVO> selectErrorCodeListPageInfo(BsErrorCodeVO record);
    
    int selectCountErrorCode(BsErrorCodeVO record);
    
    /** 查询错误码是否已存在 */
    BsErrorCode selectErrorCode(BsErrorCode record);
    
}