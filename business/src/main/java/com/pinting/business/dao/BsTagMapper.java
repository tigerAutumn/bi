package com.pinting.business.dao;

import com.pinting.business.model.BsTag;
import com.pinting.business.model.BsTagExample;
import com.pinting.business.model.vo.BsTagVO;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsTagMapper {
    int countByExample(BsTagExample example);

    int deleteByExample(BsTagExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsTag record);

    int insertSelective(BsTag record);

    List<BsTag> selectByExample(BsTagExample example);

    BsTag selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsTag record, @Param("example") BsTagExample example);

    int updateByExample(@Param("record") BsTag record, @Param("example") BsTagExample example);

    int updateByPrimaryKeySelective(BsTag record);

    int updateByPrimaryKey(BsTag record);
    
    /** 用户标签列表 */
    ArrayList<BsTagVO> selectBsTagInfo(BsTagVO bsTag);
    
    /** 用户标签统计 */
    int selectCountBsTag(BsTag bsTag);
    
    /** 查询用户标签内容是否已存在 */
    BsTag selectBsTag(BsTag bsTag);
    
    /** 查询 bs_tag表中是否有数据 */
    int selectCountOfTag();
    
    /** 查找 serial_id的最大值 */
    BsTag selectMaxSerialId(BsTag bsTag);
    
    /** 查找所有标签根据serial_id排序 */
    List<BsTag> selectAllTagList();
    
}