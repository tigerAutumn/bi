package com.pinting.business.dao;

import com.pinting.business.model.Bs2016AnnualMeetingEmployee;
import com.pinting.business.model.Bs2016AnnualMeetingEmployeeExample;
import com.pinting.business.model.vo.AnnualMeetingEmpVO;
import com.pinting.business.model.vo.EndOf2016CompanyAnnualVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Bs2016AnnualMeetingEmployeeMapper {
    int countByExample(Bs2016AnnualMeetingEmployeeExample example);

    int deleteByExample(Bs2016AnnualMeetingEmployeeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bs2016AnnualMeetingEmployee record);

    int insertSelective(Bs2016AnnualMeetingEmployee record);

    List<Bs2016AnnualMeetingEmployee> selectByExample(Bs2016AnnualMeetingEmployeeExample example);

    Bs2016AnnualMeetingEmployee selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bs2016AnnualMeetingEmployee record, @Param("example") Bs2016AnnualMeetingEmployeeExample example);

    int updateByExample(@Param("record") Bs2016AnnualMeetingEmployee record, @Param("example") Bs2016AnnualMeetingEmployeeExample example);

    int updateByPrimaryKeySelective(Bs2016AnnualMeetingEmployee record);

    int updateByPrimaryKey(Bs2016AnnualMeetingEmployee record);
    
    void insertAnnualMeetingEmployee(@Param("sql")String sql);
    
    /**
     * 2016公司年会抽奖列表
     * @param record
     * @return
     */
    List<AnnualMeetingEmpVO> findAnnualMeetingEmpList(AnnualMeetingEmpVO record);
    
    /**
     * 2016公司年会抽奖统计
     * @param record
     * @return
     */
    int findAnnualMeetingCount(AnnualMeetingEmpVO record);
    
    /**
     * 查询姓名是否已存在
     * @param employeeName
     * @return
     */
    Bs2016AnnualMeetingEmployee selectRecordByEmployeeName(@Param("employeeName") String employeeName);

    /**
     * 查找可以抽奖的员工相关信息
     * @return
     */
    List<EndOf2016CompanyAnnualVO> selectLotteryNameList();

    /**
     * 查找可以抽奖的员工相关信息(一等奖、二等奖)
     * @return
     */
    List<EndOf2016CompanyAnnualVO> selectLargestAward();


//    /**
//     * 查找未到奖的员工信息
//     * @param activityAwardId 奖品等级Id
//     * @return
//     */
//    List<EndOf2016CompanyAnnualVO> selectNoDrawNameList(@Param("activityAwardId") Integer activityAwardId);
    

    /**
     * 统计每个奖项的获奖人数，抽完之后提示抽奖人，剩下的抽奖次数
     * @param activityAwardId 奖品等级Id
     * @return
     */
    int selectNumberOfWinners(@Param("activityAwardId") Integer activityAwardId);

    /**
     * 重置中奖用户
     */
    void revertWin();
}