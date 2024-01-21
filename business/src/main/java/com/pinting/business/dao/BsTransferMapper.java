package com.pinting.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsTransfer;
import com.pinting.business.model.BsTransferExample;
import com.pinting.business.model.vo.BsTransferVO;

public interface BsTransferMapper {
    int countByExample(BsTransferExample example);

    int deleteByExample(BsTransferExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsTransfer record);

    int insertSelective(BsTransfer record);

    List<BsTransfer> selectByExample(BsTransferExample example);

    BsTransfer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsTransfer record, @Param("example") BsTransferExample example);

    int updateByExample(@Param("record") BsTransfer record, @Param("example") BsTransferExample example);

    int updateByPrimaryKeySelective(BsTransfer record);

    int updateByPrimaryKey(BsTransfer record);

	List<BsTransferVO> selectByExamplePage(Map<String, Object> data);

	BsTransferVO selectTransferVOByPrimaryKey(Integer id);

}