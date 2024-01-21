package com.pinting.business.dao;

import com.pinting.business.model.BsSysNews;
import com.pinting.business.model.BsSysNewsExample;
import com.pinting.business.model.vo.BsSysNewsVO;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSysNewsMapper {
    int countByExample(BsSysNewsExample example);

    int deleteByExample(BsSysNewsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSysNews record);

    int insertSelective(BsSysNews record);

    List<BsSysNews> selectByExampleWithBLOBs(BsSysNewsExample example);

    List<BsSysNews> selectByExample(BsSysNewsExample example);

    BsSysNews selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSysNews record, @Param("example") BsSysNewsExample example);

    int updateByExampleWithBLOBs(@Param("record") BsSysNews record, @Param("example") BsSysNewsExample example);

    int updateByExample(@Param("record") BsSysNews record, @Param("example") BsSysNewsExample example);

    int updateByPrimaryKeySelective(BsSysNews record);

    int updateByPrimaryKeyWithBLOBs(BsSysNews record);

    int updateByPrimaryKey(BsSysNews record);
    
    
    ArrayList<BsSysNewsVO> selectSysNewsListPageInfo(BsSysNews record);
    
    
    int selectCountSysNews(BsSysNews record);
    

    /**
     * 查询最新的新闻
     * @param receiverType  接收类型 BGW || BGW178
     * @param type  公告|新闻|公司动态
     * @return
     */
    List<BsSysNews> selectCurrentNews(@Param("receiverType") String receiverType, @Param("type") String type, @Param("showPage") Integer showPage);

    /**
     * 分页查询所有的新闻
     * @param receiverType
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsSysNews> selectNewsPage(@Param("type") String type, @Param("receiverType") String receiverType, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 查询相关来源的最新报道
     * @param source
     * @param receiverType
     * @return
     */
    BsSysNews selectNewsBySource(@Param("source") String source, @Param("receiverType") String receiverType);

    /**
     * 分页查询港湾资讯、平台公告
     * @param type
     * @param receiverType
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsSysNews> selectNewsPageInfo(@Param("type") String type, @Param("receiverType") String receiverType, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 分页查询平台公告
     * @param receiverType
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsSysNewsVO> selectNoticeInfo(@Param("receiverType") String receiverType, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 查询publish_time最晚的一条公告
     * @param receiverType  接收类型 BGW || BGW178
     * @param type  公告|新闻|公司动态
     * @return
     */
    List<BsSysNews> selectLatestNews(@Param("receiverType") String receiverType, @Param("type") String type);

}