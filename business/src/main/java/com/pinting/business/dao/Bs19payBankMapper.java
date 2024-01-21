package com.pinting.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.Bs19payBank;
import com.pinting.business.model.Bs19payBankExample;
import com.pinting.business.model.vo.Bs19payBankVO;
import com.pinting.business.model.vo.Pay19BankVO;

public interface Bs19payBankMapper {
    int countByExample(Bs19payBankExample example);

    int deleteByExample(Bs19payBankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bs19payBank record);

    int insertSelective(Bs19payBank record);

    List<Bs19payBank> selectByExample(Bs19payBankExample example);

    Bs19payBank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bs19payBank record, @Param("example") Bs19payBankExample example);

    int updateByExample(@Param("record") Bs19payBank record, @Param("example") Bs19payBankExample example);

    int updateByPrimaryKeySelective(Bs19payBank record);
    
    /**
     * 时间可置为null
     * @param record
     * @param example
     * @return
     */
    int updateByPrimaryKeySelective2(Bs19payBank record);

    int updateByPrimaryKey(Bs19payBank record);
    
    List<Bs19payBankVO> bs19payBankPage(Bs19payBankVO record);
    
    int bs19payBankCount(Bs19payBankVO record);
    
    Bs19payBank selectBs19payBank(Bs19payBank record);
    
    List<Pay19BankVO> select19PayBankList(@Param("payType")Integer payType, @Param("bankId")Integer bankId, @Param("pay19BankCode")String pay19BankCode);
    
    List<Pay19BankVO> selectFirstBankList(@Param("payType")Integer payType);
    
    List<Pay19BankVO> selectMainBankList(@Param("payType")Integer payType);
    
    
    
    /**
     * 根据时间和支付类型查看当前时间可用的卡的额度列表
     * @param payType
     * @return
     */
    List<Bs19payBankVO> bankLimitListByPayType(@Param("payType")Integer payType);

    /**
     * 根据支付通道和支付类型查询银行列表
     * @param channel   支付通道:PAY19-19付；REAPAL-融宝；BAOFOO-宝付
     * @param payType   支付类型:1-快捷 2-代付 3-代扣 4-网银
     * @return
     */
    List<Pay19BankVO> selectByChannelAndPayType(@Param("channel") String channel, @Param("payType") int payType);
    
    /**
     * 根据条件查询当前时间可用的银行信息，不查询 is_available是否等于1
     * @param channel 银行渠道
     * @param payType 交易类型
     * @param bankCode 银行编码 
     * @return
     */
    List<Pay19BankVO> selectByChanelPayTypeBankCode(@Param("channel") String channel, @Param("payType") int payType, @Param("bankCode") String bankCode);
    
}