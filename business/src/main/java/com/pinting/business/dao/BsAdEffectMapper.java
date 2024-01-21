package com.pinting.business.dao;

import com.pinting.business.model.BsAdEffect;
import com.pinting.business.model.BsAdEffectExample;
import com.pinting.business.model.vo.BsAdEffectVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsAdEffectMapper {
    int countByExample(BsAdEffectExample example);

    int deleteByExample(BsAdEffectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAdEffect record);

    int insertSelective(BsAdEffect record);

    List<BsAdEffect> selectByExample(BsAdEffectExample example);

    BsAdEffect selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAdEffect record, @Param("example") BsAdEffectExample example);

    int updateByExample(@Param("record") BsAdEffect record, @Param("example") BsAdEffectExample example);

    int updateByPrimaryKeySelective(BsAdEffect record);

    int updateByPrimaryKey(BsAdEffect record);
    
	/**
	 * 
	 * @param visitTimeStart
	 * @param visitTimeEnd
	 * @param monitorType
	 * @param utmSource
	 * @param utmMedium
	 * @param utmCampaign
	 * @param start
	 * @param numPerPage
	 * @return
	 */

	List<BsAdEffectVO> selectAdEffectInfoData(@Param("visitTimeStart") String  visitTimeStart,
											  @Param("visitTimeEnd") String visitTimeEnd,
											  @Param("monitorType") String monitorType,
											  @Param("utmSource") String utmSource,
											  @Param("utmMedium") String utmMedium,
											  @Param("utmCampaign") String utmCampaign,
											  @Param("start") Integer start,
											  @Param("numPerPage") Integer numPerPage);

	Integer selectAdEffectInfoCount(@Param("visitTimeStart") String  visitTimeStart,
								  @Param("visitTimeEnd") String visitTimeEnd,
								  @Param("monitorType") String monitorType,
								  @Param("utmSource") String utmSource,
								  @Param("utmMedium") String utmMedium,
								  @Param("utmCampaign") String utmCampaign);
}