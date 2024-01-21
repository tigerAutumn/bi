package com.pinting.business.dao;

import com.pinting.business.model.BsWithdrawCheck;
import com.pinting.business.model.BsWithdrawCheckExample;
import com.pinting.business.model.vo.BsWithdrawCheckVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsWithdrawCheckMapper {
    int countByExample(BsWithdrawCheckExample example);

    int deleteByExample(BsWithdrawCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsWithdrawCheck record);

    int insertSelective(BsWithdrawCheck record);

    List<BsWithdrawCheck> selectByExample(BsWithdrawCheckExample example);

    BsWithdrawCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsWithdrawCheck record, @Param("example") BsWithdrawCheckExample example);

    int updateByExample(@Param("record") BsWithdrawCheck record, @Param("example") BsWithdrawCheckExample example);

    int updateByPrimaryKeySelective(BsWithdrawCheck record);

    int updateByPrimaryKey(BsWithdrawCheck record);

    /**
     * 财务确认处理查询
     * @param mobile
     * @param userName
     * @param status
     * @param pageNum
     * @param numPerPage
     */
    List<BsWithdrawCheckVO> selectWithdrawCheckByMobileAndUserNameAndStatus(@Param("mobile")String mobile, @Param("userName")String userName,
                                                         @Param("status")String status, @Param("start")Integer start,
                                                         @Param("numPerPage")Integer numPerPage);

    /**
     * 财务确认处理查询(总条数)
     * @param mobile
     * @param userName
     * @param status
     * @return
     */
    Integer countWithdrawCheckByMobileAndUserNameAndStatus(@Param("mobile")String mobile, @Param("userName")String userName,
                                                           @Param("status")String status);
}