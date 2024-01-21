package com.pinting.business.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsBankCardExample;
import com.pinting.business.model.vo.BankCardVO;
import com.pinting.business.model.vo.BsBankCardVO;
import com.pinting.business.model.vo.BsBindBankVO;

public interface BsBankCardMapper {
    int countByExample(BsBankCardExample example);

    int deleteByExample(BsBankCardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsBankCard record);

    int insertSelective(BsBankCard record);

    ArrayList<BsBankCardVO> selectByUserId(Integer userId);

    List<BsBankCard> selectByExample(BsBankCardExample example);

    BsBankCard selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsBankCard record,
                                 @Param("example") BsBankCardExample example);

    int updateByExample(@Param("record") BsBankCard record,
                        @Param("example") BsBankCardExample example);

    int updateByPrimaryKeySelective(BsBankCard record);

    int updateByPrimaryKey(BsBankCard record);

    BsBankCardVO selectBankCardBindInfoByUserIdAndCardNo(Integer userId, String cardNo);

    int updateBankCardByUserIdAndCardNo(BsBankCard record);

    int bankCardUserCount(BsBankCardVO record);

    List<BsBankCardVO> bankCardUserPage(BsBankCardVO record);

    List<BsBankCardVO> groupByBankName();
    
    List<BsBindBankVO> selectBindBankList(@Param("userId")Integer userId, @Param("payType")Integer payType, @Param("bankId")Integer bankId);

    /**
     * 查询银行列表
     * @param userId
     * @param status
     * @return
     */
    List<BankCardVO> selectBankCardVOByStatusAndUserId(@Param("userId") Integer userId,
                                                       @Param("status") Integer status);
    
    List<BsBindBankVO> selectDefaultBank(@Param("userId")Integer userId, @Param("payType")Integer payType, @Param("time")Date time);
    
    List<BsBankCard> selectBankCards();
    
    List<BsBindBankVO> selectSafeBankCard(@Param("userId") Integer userId);

    /**
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> countBindCardUser(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 
     * @param startTime
     * @param agentIds
     * @param nonAgentId
     * @return
     */
    List<BsBankCard> countDailyBindCardUser(@Param("startTime") String startTime, @Param("agentIds") List<Object> agentIds,
        @Param("nonAgentId") String nonAgentId);
    
    /**
     * 查询绑卡成功的银行卡信息
     * @return
     */
    List<BsBindBankVO> selectCardSuccess();
    
    /**
     * 根据手机号查询绑卡成功的银行卡信息是否存在
     * @param mobiles 手机号集合
     * @return
     */
    List<BsBindBankVO> selectBankCardByMobile(@Param("mobiles") List<String> mobiles);
}