package com.pinting.business.dao;

import com.pinting.business.model.BsUserSessionConnectionInfo;
import com.pinting.business.model.BsUserSessionConnectionInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserSessionConnectionInfoMapper {
    int countByExample(BsUserSessionConnectionInfoExample example);

    int deleteByExample(BsUserSessionConnectionInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserSessionConnectionInfo record);

    int insertSelective(BsUserSessionConnectionInfo record);

    List<BsUserSessionConnectionInfo> selectByExample(BsUserSessionConnectionInfoExample example);

    BsUserSessionConnectionInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserSessionConnectionInfo record, @Param("example") BsUserSessionConnectionInfoExample example);

    int updateByExample(@Param("record") BsUserSessionConnectionInfo record, @Param("example") BsUserSessionConnectionInfoExample example);

    int updateByPrimaryKeySelective(BsUserSessionConnectionInfo record);

    int updateByPrimaryKey(BsUserSessionConnectionInfo record);

    /**
     * 总会话数量
     * @param userId
     * @return
     */
    int countTotalSessionNumber(@Param("userId") Integer userId);

    /**
     * 总IP数量（会话）
     * @param userId
     * @return
     */
    int countTotalIPSessionNumber(@Param("userId") Integer userId);


    /**
     * 总设备数量
     * @param userId
     * @return
     */
    int countTotalDeviceNumber(@Param("userId") Integer userId);

    /**
     * 总IP数量（设备）
     * @param userId
     * @return
     */
    int countTotalIPDeviceNumber(@Param("userId") Integer userId);

    /**
     * 最早登录的记录
     * @param userId
     * @return
     */
    BsUserSessionConnectionInfo selectEarliestLoginRecord(@Param("userId") Integer userId, @Param("ip") String ip);

    /**
     * 相同IP下的会话个数
     * @param userId
     * @param ip
     * @return
     */
    int countSameIPSessionNumber(@Param("userId") Integer userId, @Param("ip") String ip);

    /**
     * 相同IP下的设备个数
     * @param userId
     * @param ip
     * @return
     */
    int countSameIPDeviceNumber(@Param("userId") Integer userId, @Param("ip") String ip);

    /**
     * 最早登录的记录
     * @param userId
     * @param ip
     * @return
     */
    BsUserSessionConnectionInfo selectEarliestDeviceLoginRecord(@Param("userId") Integer userId, @Param("ip") String ip);

}