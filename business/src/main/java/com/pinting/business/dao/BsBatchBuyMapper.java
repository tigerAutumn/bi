package com.pinting.business.dao;

import com.pinting.business.model.BsBatchBuy;
import com.pinting.business.model.BsBatchBuyExample;
import java.util.List;
import java.util.Map;

import com.pinting.business.model.vo.SysNotReceivableVO;
import org.apache.ibatis.annotations.Param;

public interface BsBatchBuyMapper {
    int countByExample(BsBatchBuyExample example);

    int deleteByExample(BsBatchBuyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsBatchBuy record);

    int insertSelective(BsBatchBuy record);

    List<BsBatchBuy> selectByExample(BsBatchBuyExample example);

    BsBatchBuy selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsBatchBuy record, @Param("example") BsBatchBuyExample example);

    int updateByExample(@Param("record") BsBatchBuy record, @Param("example") BsBatchBuyExample example);

    int updateByPrimaryKeySelective(BsBatchBuy record);

    int updateByPrimaryKey(BsBatchBuy record);
    
    List<BsBatchBuy> selectBsBatchBuy4Repeat19Send();
    
    List<BsBatchBuy> selectBsBatchBuy4RepeatDafySend();
    
    /**
     * 查询状态为BUY_DAFY_SUCCESS的数据
     * 和状态为DAFY_RETURN_SUCCESS或DAFY_RETURN_FAIL，且send_batch_id不存在于bs_loan_relative_record表order_no字段中
     * @return list
     */
    List<BsBatchBuy> selectBsBatchBuy4LoanRelation();
    
    /**
     * 2.0 当日系统待回款产品查询（用于未回款告警定时）
     * @param paramMap 传入当前日期  currTime 精确到日
     * @return 获得当日系统待回款产品信息，以及应该回款的利息
     */
    List<Map<String, Object>> selectCurrDaySysAwaitingReturn2(Map<String, Object> paramMap);
    
    /**
     * 3.0 当日系统待回款产品查询（用于未回款告警定时）
     * @param paramMap 传入当前日期  currTime 精确到日
     * @return 获得当日系统待回款产品信息，以及应该回款的利息
     */
    List<Map<String, Object>> selectCurrDaySysAwaitingReturn3(Map<String, Object> paramMap);
    
    /**
     *  云贷2.0和3.0的某日应回款金额
     * @param paramMap
     * @return
     */
    Double selectOneDaySysAwaitingReturnYunDai(Map<String, Object> paramMap);
    
    /**
     *  7贷某日应回款金额
     * @param paramMap
     * @return
     */
    Double selectOneDaySysAwaitingReturn7Dai(Map<String, Object> paramMap);

    /**
     * 财务功能-当日系统理财未回款查询
     * @param dateTime
     * @return
     */
    List<SysNotReceivableVO> selectSysNotReceivableInfo(@Param("dateTime") String dateTime);
}