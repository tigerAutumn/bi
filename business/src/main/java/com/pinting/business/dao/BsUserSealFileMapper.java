package com.pinting.business.dao;

import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.BsUserSealFileExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserSealFileMapper {
    int countByExample(BsUserSealFileExample example);

    int deleteByExample(BsUserSealFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserSealFile record);

    int insertSelective(BsUserSealFile record);

    List<BsUserSealFile> selectByExample(BsUserSealFileExample example);

    BsUserSealFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserSealFile record, @Param("example") BsUserSealFileExample example);

    int updateByExample(@Param("record") BsUserSealFile record, @Param("example") BsUserSealFileExample example);

    int updateByPrimaryKeySelective(BsUserSealFile record);

    int updateByPrimaryKey(BsUserSealFile record);
    /**
     * 
     * @param type 类型
     * @param start 起始位置
     * @param numPerPage 分页条数
     * @return
     */
	List<BsUserSealFile> querySignRepeatData(@Param("type") String type,@Param("start") Integer start,
			@Param("numPerPage") Integer numPerPage);
	/**
	 * 
	 * @param type 类型
	 * @return
	 */
	Integer countSignRepeatData(@Param("type") String type);

    /**
     * 根据合作方借款id,用户来源 查询协议编号
     * @param partnerLoanId
     * @param userSrc 用户来源
     * @return
     */
    BsUserSealFile selectAgreementByPartnerLoanId(@Param("partnerLoanId") String partnerLoanId,
                                                  @Param("userSrc") String userSrc);

    /**
     * 查询出当前签章发送失败的最大id号
     * @return
     */
    Integer selectMaxId();
    
    /**
     * 查询依次小于signId的五条记录
     */
    List<BsUserSealFile> selectRepeatSendList(@Param("id") Integer id, @Param("selectNum") Integer selectNum);
}