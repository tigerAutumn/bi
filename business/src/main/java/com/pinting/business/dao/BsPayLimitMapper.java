package com.pinting.business.dao;

import com.pinting.business.model.BsPayLimit;
import com.pinting.business.model.BsPayLimitExample;
import com.pinting.business.model.vo.SysOperationalDataVO;
import com.pinting.business.model.vo.SysPayLimitVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsPayLimitMapper {
    int countByExample(BsPayLimitExample example);

    int deleteByExample(BsPayLimitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsPayLimit record);

    int insertSelective(BsPayLimit record);

    List<BsPayLimit> selectByExample(BsPayLimitExample example);

    BsPayLimit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsPayLimit record, @Param("example") BsPayLimitExample example);

    int updateByExample(@Param("record") BsPayLimit record, @Param("example") BsPayLimitExample example);

    int updateByPrimaryKeySelective(BsPayLimit record);

    int updateByPrimaryKey(BsPayLimit record);
    
    /**
     * 根据timeType查询当前时间段适用的宝付代付限制规则配置
     * @author bianyatian
     * @param timeType
     * @return
     */
    BsPayLimit selectBfDfByTimeType(@Param("timeType") String timeType);
    
    /**
     * 查询宝付代付限制规则配置列表
     * @return
     */
    List<SysPayLimitVO> findSysPayLimitList(@Param("req") SysPayLimitVO req,
    		@Param("position") Integer position, @Param("offset") Integer offset);
    
}