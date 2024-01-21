package com.pinting.business.dao;

import com.pinting.business.model.BsRedPacketApply;
import com.pinting.business.model.BsRedPacketApplyExample;
import com.pinting.business.model.vo.BsRedPacketApplyVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsRedPacketApplyMapper {
    int countByExample(BsRedPacketApplyExample example);

    int deleteByExample(BsRedPacketApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsRedPacketApply record);

    int insertSelective(BsRedPacketApply record);

    List<BsRedPacketApply> selectByExample(BsRedPacketApplyExample example);

    BsRedPacketApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsRedPacketApply record, @Param("example") BsRedPacketApplyExample example);

    int updateByExample(@Param("record") BsRedPacketApply record, @Param("example") BsRedPacketApplyExample example);

    int updateByPrimaryKeySelective(BsRedPacketApply record);

    int updateByPrimaryKey(BsRedPacketApply record);
    
    /**
     * 红包申请列表
     * @param record
     * @return
     */
    ArrayList<BsRedPacketApplyVO> selectByRedPacketApplyListInfo(BsRedPacketApplyVO record);
    
    /**
     * 红包申请数量
     * @param record
     * @return
     */
    int selectCountRedPacketApply(BsRedPacketApply record);
    
    /**
     * 查询红包名称是否已存
     * @param record
     * @return
     */
    BsRedPacketApply selectByRedPacketApply(BsRedPacketApply record);
    
    /**
     * 总审核通过预算
     * @return
     */
    Double selectTotalBudgetAmount();
    
    /**
     * 获取可以发放的红包
	 * 即通过审核，status = USING 使用中；check_status = PASS 已通过；申请预算金额budget > 0
     * @return
     */
    List<BsRedPacketApply> findCanPutPacket();
    
    /**
     * 根据applyNo查询BsRedPacketApplyVO
     * @param applyNo
     * @return
     */
    BsRedPacketApplyVO selectByApplyNo(@Param("applyNo")String applyNo, @Param("nowTime")Date nowTime);
    
    
    /**
     * 红包申请人
     * @return
     */
    List<BsRedPacketApplyVO> selectByRedPacketApplyCreator(BsRedPacketApplyVO record);
    
    /**
     * 当前预算总额
     * @return
     */
    Double selectTotalBudget(@Param("applyNo") String applyNo);
    
    
    /**
     * 预算余额
     * @return
     */
    Double selectBudgetAvailableAmount(@Param("applyNo") String applyNo);
    
}