package com.pinting.business.dao;

import com.pinting.business.model.BsAppVersion;
import com.pinting.business.model.BsAppVersionExample;
import com.pinting.business.model.vo.BsAppVersionVO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BsAppVersionMapper {
    int countByExample(BsAppVersionExample example);

    int deleteByExample(BsAppVersionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAppVersion record);

    int insertSelective(BsAppVersion record);

    List<BsAppVersion> selectByExample(BsAppVersionExample example);

    BsAppVersion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAppVersion record, @Param("example") BsAppVersionExample example);

    int updateByExample(@Param("record") BsAppVersion record, @Param("example") BsAppVersionExample example);

    int updateByPrimaryKeySelective(BsAppVersion record);

    int updateByPrimaryKey(BsAppVersion record);
    
    int insertBsAppVersion(BsAppVersion record);
    
    int selectAllAppVersionCount();

    List<BsAppVersion> selectAppVersion(BsAppVersionVO bsAppVersion);
    
    List<BsAppVersion> selectVersionByMap(Map<String,Object> map);
    
    int selectIsLastVersion(Map<String,Object> map);
    
    /**
     * 查找ios/android APP版本号的最大值
     * @return
     */
    List<BsAppVersion> selectAppVersionMaxValue();
    
}