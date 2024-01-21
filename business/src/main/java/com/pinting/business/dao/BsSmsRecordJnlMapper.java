package com.pinting.business.dao;

import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.model.BsSmsRecordJnlExample;
import com.pinting.business.model.vo.BsSmsRecordJnlReVo;
import com.pinting.business.model.vo.BsSmsRecordJnlVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BsSmsRecordJnlMapper {
    int countByExample(BsSmsRecordJnlExample example);

    int deleteByExample(BsSmsRecordJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSmsRecordJnl record);

    int insertSelective(BsSmsRecordJnl record);

    List<BsSmsRecordJnl> selectByExample(BsSmsRecordJnlExample example);

    BsSmsRecordJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSmsRecordJnl record, @Param("example") BsSmsRecordJnlExample example);

    int updateByExample(@Param("record") BsSmsRecordJnl record, @Param("example") BsSmsRecordJnlExample example);

    int updateByPrimaryKeySelective(BsSmsRecordJnl record);

    int updateByPrimaryKey(BsSmsRecordJnl record);

    /**
     * 短信发送流水记录列表查询
     * @param record
     * @return
     */
    List<BsSmsRecordJnlReVo> selectRecordJnlList(BsSmsRecordJnlVO record);

    /**
     * 查询列表总计
     * @param record
     * @return
     */
    int countRecordJnlList (BsSmsRecordJnlVO record);

    /**
     * 根据手机号和短信平台序列号修改
     * @param record
     */
    @Deprecated
    void updateJnl(BsSmsRecordJnl record);

    /**
     * 根据手机号和序列号查询
     * @param mobile
     * @param serNo
     * @return
     */
    BsSmsRecordJnl getByMobileSerNo(@Param("mobile")String mobile, @Param("serNo")String serNo);
    
    /**
     * 根据手机号、序列号、短信平台查询
     * @param mobile
     * @param serNo
     * @param platformsId
     * @return
     */
    BsSmsRecordJnl getByMobileSerNoPlatformsId(@Param("mobile")String mobile, 
    		@Param("serNo")String serNo, @Param("platformsId")Integer platformsId);
    
    /**
	 * 根据短信平台id查询bs_sms_record_jnl发送时间在定时间（分钟minutes）内且有状态的条数在特定条数（minLimit）以上，返回成功率
	 * @param platformsId 短信平台id
	 * @param minutes
	 * @param minLimit
	 * @return
	 */
    Double succRateByMinute(@Param("platformsId")int platformsId, @Param("minutes")int minutes, @Param("minLimit")int minLimit ); 
    
    /**
	 * 根据短信平台id查询查询bs_sms_record_jnl当日，最近有状态的特定（limitNum）条短信数据，返回成功率（成功/有返回码）
	 * @param platformsId 短信平台id
	 * @param limitNum 短信条数
	 * @return
	 */
    Double succRateByNum(@Param("platformsId")int platformsId, @Param("limitNum")int limitNum);
}