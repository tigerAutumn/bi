package com.pinting.business.dao;

import com.pinting.business.model.BsVipQuit;
import com.pinting.business.model.BsVipQuitExample;
import java.util.List;

import com.pinting.business.model.vo.VipQuitVO;
import org.apache.ibatis.annotations.Param;

public interface BsVipQuitMapper {
    long countByExample(BsVipQuitExample example);

    int deleteByExample(BsVipQuitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsVipQuit record);

    int insertSelective(BsVipQuit record);

    List<BsVipQuit> selectByExample(BsVipQuitExample example);

    BsVipQuit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsVipQuit record, @Param("example") BsVipQuitExample example);

    int updateByExample(@Param("record") BsVipQuit record, @Param("example") BsVipQuitExample example);

    int updateByPrimaryKeySelective(BsVipQuit record);

    int updateByPrimaryKey(BsVipQuit record);

    /**
     * 查询赞分期VIP退出申请-列表
     * @param record
     * @return
     */
    List<VipQuitVO> selectVipQuitList(BsVipQuit record);

    /**
     * 查询赞分期VIP退出申请-统计
     * @return
     */
    int selectVipQuitCount();
}