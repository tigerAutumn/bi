package com.pinting.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsFeedback;
import com.pinting.business.model.BsFeedbackExample;
import com.pinting.business.model.vo.BsFeedbackVO;

public interface BsFeedbackMapper {
    int countByExample(BsFeedbackExample example);

    int deleteByExample(BsFeedbackExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsFeedback record);

    int insertSelective(BsFeedback record);

    List<BsFeedback> selectByExample(BsFeedbackExample example);

    BsFeedback selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsFeedback record, @Param("example") BsFeedbackExample example);

    int updateByExample(@Param("record") BsFeedback record, @Param("example") BsFeedbackExample example);

    int updateByPrimaryKeySelective(BsFeedback record);

    int updateByPrimaryKey(BsFeedback record);
    
    /**
     * 分页查询意见反馈列表
     * @param bsFeedback
     * @return
     */
    List<BsFeedbackVO> selectByPage(BsFeedback bsFeedback);
}