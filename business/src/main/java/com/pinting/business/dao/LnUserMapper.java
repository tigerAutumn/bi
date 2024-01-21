package com.pinting.business.dao;

import com.pinting.business.model.LnUser;
import com.pinting.business.model.LnUserExample;
import com.pinting.business.model.dto.LoanUserDTO;
import com.pinting.business.model.vo.LoanRepayVO;
import com.pinting.business.model.vo.LoanUserVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LnUserMapper {
    int countByExample(LnUserExample example);

    int deleteByExample(LnUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnUser record);

    int insertSelective(LnUser record);

    List<LnUser> selectByExample(LnUserExample example);

    LnUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnUser record, @Param("example") LnUserExample example);

    int updateByExample(@Param("record") LnUser record, @Param("example") LnUserExample example);

    int updateByPrimaryKeySelective(LnUser record);

    int updateByPrimaryKey(LnUser record);

    List<LoanUserVO> selectByLnUserDTO(LoanUserDTO loanUserDTO);

    int selectByLnUserDTOCount(LoanUserDTO loanUserDTO);

    /**
     * 币港湾实验室-借款用户还款情况查询记录数统计
     * @param record
     * @return
     */
    Integer selectLoanRepayForLnUserIdCount(LoanRepayVO record);

    /**
     * 币港湾实验室-借款用户还款情况查询记录数统计-查询的借款用户ID列表
     *
     * @param record
     * @return
     */
    List<Integer> selectLoanRepayForLnUserIdList(LoanRepayVO record);

    /**
     * 币港湾实验室-借款用户还款情况查询列表
     * @param lnUserIds
     * @return
     */
    List<LoanRepayVO> selectLoanRepayInfoByLnUserId(@Param("lnUserIds") List<Integer> lnUserIds,
                                                    @Param("orderField") String orderField,
                                                    @Param("orderDirection") String orderDirection);
}