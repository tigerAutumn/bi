package com.pinting.business.dao;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.vo.HFBGWRevenueOfZanVO;
import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsSysAccountJnl;
import com.pinting.business.model.BsSysAccountJnlExample;
import com.pinting.business.model.vo.BsSysAccountJnlCheckVO;
import com.pinting.business.model.vo.BsSysAccountJnlVO;

public interface BsSysAccountJnlMapper {
    int countByExample(BsSysAccountJnlExample example);

    int deleteByExample(BsSysAccountJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSysAccountJnl record);

    int insertSelective(BsSysAccountJnl record);

    List<BsSysAccountJnl> selectByExample(BsSysAccountJnlExample example);

    BsSysAccountJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSysAccountJnl record, @Param("example") BsSysAccountJnlExample example);

    int updateByExample(@Param("record") BsSysAccountJnl record, @Param("example") BsSysAccountJnlExample example);

    int updateByPrimaryKeySelective(BsSysAccountJnl record);

    int updateByPrimaryKey(BsSysAccountJnl record);
    
    /** 系统记账流水列表查询  */
    List<BsSysAccountJnlVO> selectSysAccountList(BsSysAccountJnlVO accountJnl);
    
    /** 系统记账流水记录统计 */
    int selectCountSysAccount(BsSysAccountJnlVO accountJnl);
    
    /**
     * 根据时间查询在此时间之前，最后一条，sub_account_code1或sub_account_code2满足条件的对应的after_balance1或2
     * @param date
     * @return
     */
    BsSysAccountJnlCheckVO selectLastJnlByDate(@Param("date") Date date);

    /**
     * 查询恒丰币港湾营收（赞分期）
     * @param startTime     开始时间（可以为空）
     * @param endTime       结束时间（可以为空）
     * @param start         分页开始页码（0开始）（可以为空）
     * @param numPerPage    分页每页展示条数（可以为空）
     * @return
     */
    List<HFBGWRevenueOfZanVO> selectHFBGWRevenueOfZan(@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 查询恒丰币港湾营收（赞分期）总条数
     * @param startTime     开始时间（可以为空）
     * @param endTime       结束时间（可以为空）
     * @return
     */
    int countHFBGWRevenueOfZan(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     *币港湾营收（恒丰）总计
     * @param startTime     开始时间（可以为空）
     * @param endTime       结束时间（可以为空）
     * @return
     */
    Double sumHFBGWRevenueOfZan(@Param("startTime") String startTime, @Param("endTime") String endTime);
}