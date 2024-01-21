package com.pinting.business.dao;

import com.pinting.business.model.BsWxAutoReply;
import com.pinting.business.model.BsWxAutoReplyExample;
import com.pinting.business.model.vo.BsWxAutoReplyVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsWxAutoReplyMapper {
    int countByExample(BsWxAutoReplyExample example);

    int deleteByExample(BsWxAutoReplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsWxAutoReply record);

    int insertSelective(BsWxAutoReply record);

    List<BsWxAutoReply> selectByExample(BsWxAutoReplyExample example);

    BsWxAutoReply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsWxAutoReply record, @Param("example") BsWxAutoReplyExample example);

    int updateByExample(@Param("record") BsWxAutoReply record, @Param("example") BsWxAutoReplyExample example);

    int updateByPrimaryKeySelective(BsWxAutoReply record);

    int updateByPrimaryKey(BsWxAutoReply record);
    
    /**
     * 管理台列表查询
     * @param bsWxAutoReplyVO
     * @return
     */
    List<BsWxAutoReply> getListByReplyVO(BsWxAutoReplyVO bsWxAutoReplyVO);
    
    /**
     * 获取列表条数
     * @param bsWxAutoReplyVO
     * @return
     */
    int getCountByReplyVO(BsWxAutoReplyVO bsWxAutoReplyVO);
    
    /**
     * 查询关键字自动回复消息列表
     * @param autoReplyMessage
     * @return
     */
    List<BsWxAutoReply> selectAutoReplyMessage(@Param("autoReplyMessage") String autoReplyMessage,
    										   @Param("replyType") String replyType);
    
}