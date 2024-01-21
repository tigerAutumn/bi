package com.pinting.business.dao;

import com.pinting.business.model.Bs19payCheckError;
import com.pinting.business.model.Bs19payCheckErrorExample;
import com.pinting.business.model.vo.BsPayCheckErrorVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Bs19payCheckErrorMapper {
    int countByExample(Bs19payCheckErrorExample example);

    int deleteByExample(Bs19payCheckErrorExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bs19payCheckError record);

    int insertSelective(Bs19payCheckError record);

    List<Bs19payCheckError> selectByExample(Bs19payCheckErrorExample example);

    Bs19payCheckError selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bs19payCheckError record, @Param("example") Bs19payCheckErrorExample example);

    int updateByExample(@Param("record") Bs19payCheckError record, @Param("example") Bs19payCheckErrorExample example);

    int updateByPrimaryKeySelective(Bs19payCheckError record);

    int updateByPrimaryKey(Bs19payCheckError record);
    
    
	int countPayCheckError(@Param("orderNo")String orderNo, @Param("partnerCode")String partnerCode, @Param("businessType")String businessType, 
			@Param("paymentChannlId")String paymentChannlId, @Param("channel")String channel,
			@Param("startTime")String startTime, @Param("endTime") String endTime);
	
	List<BsPayCheckErrorVO> findPayCheckErrorList(@Param("orderNo")String orderNo, @Param("partnerCode")String partnerCode, 
			@Param("businessType")String businessType, @Param("paymentChannlId")String paymentChannlId,
			@Param("channel")String channel,
			@Param("startTime")String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);
    
}