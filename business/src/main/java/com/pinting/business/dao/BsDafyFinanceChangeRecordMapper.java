package com.pinting.business.dao;

import com.pinting.business.model.BsDafyFinanceChangeRecord;
import com.pinting.business.model.BsDafyFinanceChangeRecordExample;
import com.pinting.business.model.vo.BsDafyFinanceChangeRecordVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDafyFinanceChangeRecordMapper {
    int countByExample(BsDafyFinanceChangeRecordExample example);

    int deleteByExample(BsDafyFinanceChangeRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDafyFinanceChangeRecord record);

    int insertSelective(BsDafyFinanceChangeRecord record);

    List<BsDafyFinanceChangeRecord> selectByExample(BsDafyFinanceChangeRecordExample example);

    BsDafyFinanceChangeRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDafyFinanceChangeRecord record, @Param("example") BsDafyFinanceChangeRecordExample example);

    int updateByExample(@Param("record") BsDafyFinanceChangeRecord record, @Param("example") BsDafyFinanceChangeRecordExample example);

    int updateByPrimaryKeySelective(BsDafyFinanceChangeRecord record);

    int updateByPrimaryKey(BsDafyFinanceChangeRecord record);

	List<BsDafyFinanceChangeRecordVO> queryOwnershipChangeRecord(@Param("userId") Integer userId,@Param("position") Integer position, @Param("offset") Integer offset);

	Integer queryOwnershipChangeRecordCount(@Param("userId")Integer userId);

    /**
     * @param newDafyUserId
     * @param id
     * @param opId
     */
    void insertChangeOwnership(@Param("newDafyUserId")Integer newDafyUserId, @Param("id")Integer id, @Param("opId")Integer opId);
}