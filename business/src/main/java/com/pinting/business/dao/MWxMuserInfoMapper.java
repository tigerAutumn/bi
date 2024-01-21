package com.pinting.business.dao;

import com.pinting.business.model.MWxMuserInfo;
import com.pinting.business.model.MWxMuserInfoExample;
import com.pinting.business.model.vo.SysOperationalDataVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MWxMuserInfoMapper {
    int countByExample(MWxMuserInfoExample example);

    int deleteByExample(MWxMuserInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MWxMuserInfo record);

    int insertSelective(MWxMuserInfo record);

    List<MWxMuserInfo> selectByExample(MWxMuserInfoExample example);

    MWxMuserInfo selectByPrimaryKey(Integer id);
    
    //根据openId 查询关注用户
    MWxMuserInfo selectByOpenId(String openId);

    int updateByExampleSelective(@Param("record") MWxMuserInfo record, @Param("example") MWxMuserInfoExample example);

    int updateByExample(@Param("record") MWxMuserInfo record, @Param("example") MWxMuserInfoExample example);

    int updateByPrimaryKeySelective(MWxMuserInfo record);

    int updateByPrimaryKey(MWxMuserInfo record);
    
    /**
     * 运营数据微信用户管理计数
     * @return
     */
    int countOperationalDataInfo(@Param("nickName") String nickName);
    /**
     * 查询运营数据微信用户管理列表
     * @return
     */
    List<SysOperationalDataVO> selectOperationalDataInfoList(@Param("nickName") String nickName,
    		@Param("position") Integer position, @Param("offset") Integer offset);
    
}