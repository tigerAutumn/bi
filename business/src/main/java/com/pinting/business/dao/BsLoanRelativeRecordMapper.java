package com.pinting.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsLoanRelativeRecord;
import com.pinting.business.model.BsLoanRelativeRecordExample;
import com.pinting.business.model.vo.BsLoanRelativeAmountChangeVO;
import com.pinting.business.util.Constants;

public interface BsLoanRelativeRecordMapper {
    int countByExample(BsLoanRelativeRecordExample example);

    int deleteByExample(BsLoanRelativeRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsLoanRelativeRecord record);

    int insertSelective(BsLoanRelativeRecord record);

    List<BsLoanRelativeRecord> selectByExample(BsLoanRelativeRecordExample example);

    BsLoanRelativeRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsLoanRelativeRecord record, @Param("example") BsLoanRelativeRecordExample example);

    int updateByExample(@Param("record") BsLoanRelativeRecord record, @Param("example") BsLoanRelativeRecordExample example);

    int updateByPrimaryKeySelective(BsLoanRelativeRecord record);

    int updateByPrimaryKey(BsLoanRelativeRecord record);
    
    /**
     * 找购买了该产品的用户，在债权记录表中相应产品的，同用户的所有匹配金额不为0的记录，金额倒序
     * @param accountId
     * @param productName
     * @return
     */
    List<BsLoanRelativeRecord> selectByAccountIdProName(@Param("accountId")Integer accountId,@Param("productName")String productName);
    
    /**
     * 涉及表：bs_loan_relative_record a,bs_batch_buy b,bs_batch_buy_detail c
     * c.batch_id = b.id and b.send_batch_id = a.order_no
     * and c.sub_account_id=subAccountId and a.is_repay = isRepay
     * 找到能匹配且a.left_amount>0 的数据列表，金额倒序
     * @param subAccountId
     * @param isRepay
     * @return
     */
    List<BsLoanRelativeRecord> selectBySubAccountId(@Param("subAccountId")Integer subAccountId, @Param("isRepay")String isRepay);

    /**
     * 根据理财产品购买时间查询记录，且本地剩余匹配金额大于0，按本地剩余匹配金额倒序排，还款状态isRepay为N
     * @param buyTimeStart
     * @param buyTimeEnd
     * @return
     */
    List<BsLoanRelativeRecord> selectByBuyTime(@Param("buyTimeStart")String buyTimeStart,@Param("buyTimeEnd")String buyTimeEnd,
    		@Param("propertySymbol")String propertySymbol);

    /**
     * 
     * @param bsLoanRelativeRecordExample
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsLoanRelativeAmountChangeVO> selectByExamplePage(@Param("example") BsLoanRelativeRecordExample bsLoanRelativeRecordExample,
        @Param("start") int start, @Param("numPerPage") int numPerPage);
    
    
    /**
     * 未拉取
     * @return
     */
    List<BsLoanRelativeAmountChangeVO> selectNoPull();
    
    /**
     * 列表：查询所有本地的bs_loan_relative_record数据 localRecords
     * channel=Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW_ALL
     * isRepay=Constants.LOAN_RELATIVE_IS_REPAY_N
     * @param channel
     * @param isRepay
     * @param propertySymbol
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsLoanRelativeRecord> selectByChannelSymbolPage(@Param("channel")String channel,@Param("isRepay")String isRepay,
    		@Param("propertySymbol")String propertySymbol,@Param("start") int start, @Param("numPerPage") int numPerPage);

    /**
     * 条数：查询所有本地的bs_loan_relative_record数据 localRecords
     * channel=Constants.LOAN_RELATIVE_CANNEL_DAFY_NEW_ALL
     * isRepay=Constants.LOAN_RELATIVE_IS_REPAY_N
     * @param channel
     * @param isRepay
     * @param propertySymbol
     * @return
     */
    Integer countByChannelSymbol(@Param("channel")String channel,@Param("isRepay")String isRepay,
    		@Param("propertySymbol")String propertySymbol);

}