package com.pinting.business.dao;

import com.pinting.business.model.BsAppMessage;
import com.pinting.business.model.BsAppMessageExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BsAppMessageMapper {
    int countByExample(BsAppMessageExample example);

    int deleteByExample(BsAppMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAppMessage record);

    int insertSelective(BsAppMessage record);

    List<BsAppMessage> selectByExample(BsAppMessageExample example);

    BsAppMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAppMessage record, @Param("example") BsAppMessageExample example);

    int updateByExample(@Param("record") BsAppMessage record, @Param("example") BsAppMessageExample example);

    int updateByPrimaryKeySelective(BsAppMessage record);

    int updateByPrimaryKey(BsAppMessage record);
    
    List<BsAppMessage> selectAppMessage(BsAppMessage recode);
    
    int selectAllAppNoticeCount(BsAppMessage recode);
    
    List<BsAppMessage> selectNoticeByMap(Map<String, Object> map);
    
    List<BsAppMessage> findMessageList(@Param("userId")Integer userId,@Param("start")Integer start,@Param("position")Integer position);
    
    int findMessageListCount(@Param("userId")Integer userId);
}