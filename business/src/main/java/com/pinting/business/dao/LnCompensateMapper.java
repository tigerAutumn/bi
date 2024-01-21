package com.pinting.business.dao;

import com.pinting.business.model.LnCompensate;
import com.pinting.business.model.LnCompensateExample;
import java.util.List;

import com.pinting.business.model.vo.DafyLateRepaySelfForCheckVO;
import org.apache.ibatis.annotations.Param;

public interface LnCompensateMapper {
    int countByExample(LnCompensateExample example);

    int deleteByExample(LnCompensateExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnCompensate record);

    int insertSelective(LnCompensate record);

    List<LnCompensate> selectByExample(LnCompensateExample example);

    LnCompensate selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnCompensate record, @Param("example") LnCompensateExample example);

    int updateByExample(@Param("record") LnCompensate record, @Param("example") LnCompensateExample example);

    int updateByPrimaryKeySelective(LnCompensate record);

    int updateByPrimaryKey(LnCompensate record);

    /**
     *
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<DafyLateRepaySelfForCheckVO> selectForCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 赞时贷代偿对账
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<DafyLateRepaySelfForCheckVO> selectForZsdCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
    /**
     * 查询代偿对账数据（云贷、赞时贷）
     * @return
     */
	List<LnCompensate> selectCompensateCheckAccountData();

    /**
     * 7贷代偿对账
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<DafyLateRepaySelfForCheckVO> selectFor7DaiCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
	 * 对账完成后， 存管代偿计数
	 * @param channel
	 * @param transType
	 * @return
	 */
	int countCheckAccountCompensate(@Param("status")String status, @Param("partnerCode") String partnerCode);
	
	/**
	 * 对账完成后， 存管代偿统计金额
	 * @param channel
	 * @param transType
	 * @return
	 */
	Double sumCheckAccountCompensate(@Param("status")String status, @Param("partnerCode") String partnerCode);
	
    /**
     * 查询所有资产方代偿对账数据
     * @return
     */
	List<LnCompensate> selectCompensateCheckAccountInfo();
	
}