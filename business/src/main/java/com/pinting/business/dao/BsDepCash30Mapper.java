package com.pinting.business.dao;

import com.pinting.business.model.BsDepCash30;
import com.pinting.business.model.BsDepCash30Example;
import com.pinting.business.model.vo.DepCash30VO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDepCash30Mapper {
    int countByExample(BsDepCash30Example example);

    int deleteByExample(BsDepCash30Example example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDepCash30 record);

    int insertSelective(BsDepCash30 record);

    List<BsDepCash30> selectByExample(BsDepCash30Example example);

    BsDepCash30 selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDepCash30 record, @Param("example") BsDepCash30Example example);

    int updateByExample(@Param("record") BsDepCash30 record, @Param("example") BsDepCash30Example example);

    int updateByPrimaryKeySelective(BsDepCash30 record);

    int updateByPrimaryKey(BsDepCash30 record);

	void insertDepCashList(List<DepCash30VO> valueList);
}