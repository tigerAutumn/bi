package com.pinting.business.service.site.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsEcup2016ActivityUserMapper;
import com.pinting.business.dao.BsEcup2016SupportorMapper;
import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsEcup2016ActivityUser;
import com.pinting.business.model.BsEcup2016ActivityUserExample;
import com.pinting.business.model.BsEcup2016Supportor;
import com.pinting.business.model.BsEcup2016SupportorExample;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.vo.BsProductVO;
import com.pinting.core.util.MoneyUtil;
import com.pinting.business.model.vo.BsEcup2016ActivityUserInfoVO;
import com.pinting.business.service.site.Ecup2016ActivityService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;

@Service
public class Ecup2016ActivityServiceImpl implements Ecup2016ActivityService {

	@Autowired
	private BsProductMapper bsProductMapper;
	@Autowired
	private BsEcup2016ActivityUserMapper bsEcup2016ActivityUserMapper;
	@Autowired
	private BsEcup2016SupportorMapper bsEcup2016SupportorMapper;
	@Autowired
	private TransactionTemplate transactionTemplate;
	    
	 /**
     * @param showTerminal
     * @param type
     * @return
     */
    @Override
    public List<BsProductVO> queryEcupProductGrid(String showTerminal, String type) {
        List<BsProductVO> grid = new ArrayList<BsProductVO>();
        List<BsProduct> products = new ArrayList<BsProduct>();
        products = bsProductMapper.selectEcupProductGrid(showTerminal, type, null);
        if(CollectionUtils.isEmpty(products)) {
            products = bsProductMapper.selectEcupProductGrid(showTerminal, type, "not_now");
        }
        if (!CollectionUtils.isEmpty(products)) {
            for (BsProduct pro : products) {
                BsProductVO vo = new BsProductVO();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                vo.setId(pro.getId());
                vo.setTerm(String.valueOf(pro.getTerm4Day()));
                vo.setName(pro.getName());
                vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
                vo.setMinInvestAmount(pro.getMinInvestAmount());
                vo.setCurrTime(format.format(new Date()));
                vo.setStartTime(format.format(pro.getStartTime()));
                vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                vo.setStatus(pro.getStatus());
                vo.setCurrTotalAmount(pro.getCurrTotalAmount());
                vo.setMaxTotalAmount(pro.getMaxTotalAmount());
                if(pro.getEndTime() != null) {
                    vo.setEndTime(format.format(pro.getEndTime()));
                }
                if(pro.getFinishTime() != null) {
                    vo.setFinishTime(format.format(pro.getFinishTime()));
                }
                grid.add(vo);
            }
        }
        return grid;
    }

    /**
     * @param partnerId
     * @param wxId
     * @param wxNick
     */
    @Override
    public void saveSupportor(final Integer userId, final String wxId, final String wxNick) {
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                BsEcup2016ActivityUserExample activityUserExample = new BsEcup2016ActivityUserExample();
                activityUserExample.createCriteria().andUserIdEqualTo(userId);
                List<BsEcup2016ActivityUser> activityUsers = bsEcup2016ActivityUserMapper.selectByExample(activityUserExample);
                if(CollectionUtils.isEmpty(activityUsers)) {
                   throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND); 
                }
                BsEcup2016ActivityUser activityUser = activityUsers.get(0);
                // 不能为同一个人助力多次。同一个人，同一个支持者，不能超过1次
                BsEcup2016SupportorExample sameParterExample = new BsEcup2016SupportorExample();
                sameParterExample.createCriteria().andWxIdEqualTo(wxId).andParterIdEqualTo(activityUser.getId());
                int sameParterSupportorCount = bsEcup2016SupportorMapper.countByExample(sameParterExample);
                if(sameParterSupportorCount >= 1) {
                    throw new PTMessageException(PTMessageEnum.ECUP_SAME_PARTER_SUPPORTOR_MORE);
                }
                // 同一个助力者不能助力超过三次
                BsEcup2016SupportorExample moreThan3SupportorExample = new BsEcup2016SupportorExample();
                moreThan3SupportorExample.createCriteria().andWxIdEqualTo(wxId);
                int moreThan3SupportorCount = bsEcup2016SupportorMapper.countByExample(moreThan3SupportorExample);
                if(moreThan3SupportorCount >= 3) {
                    throw new PTMessageException(PTMessageEnum.ECUP_MORE_THAN_3_SUPPORTOR);
                }
                
                // 新增助力者数据
                BsEcup2016Supportor record = new BsEcup2016Supportor();
                record.setParterId(activityUser.getId());
                record.setWxId(wxId);
                try {
                    record.setWxNick(Util.filterOffUtf8Mb4(wxNick));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                record.setCreateTime(new Date());
                record.setUpdateTime(new Date());
                bsEcup2016SupportorMapper.insert(record);
                
                // 更新欧洲杯支持数（助力数）
                BsEcup2016ActivityUserExample bsEcup2016ActivityUserExample = new BsEcup2016ActivityUserExample();
                bsEcup2016ActivityUserExample.createCriteria().andUserIdEqualTo(userId);
                List<BsEcup2016ActivityUser> list = bsEcup2016ActivityUserMapper.selectByExample(bsEcup2016ActivityUserExample);
                if(!CollectionUtils.isEmpty(list)) {
                    BsEcup2016ActivityUser bsEcup2016ActivityUser = list.get(0);
                    bsEcup2016ActivityUser.setSupportNum(bsEcup2016ActivityUser.getSupportNum() == null ? 1 : bsEcup2016ActivityUser.getSupportNum()+1);
                    if(bsEcup2016ActivityUser.getSupportNum().equals(Constants.CRITICAL_SUPPORT_NUM)) {
                        bsEcup2016ActivityUser.setSupportMilestoneTime(new Date());
                    }
                    bsEcup2016ActivityUser.setUpdateTime(new Date());
                    bsEcup2016ActivityUserMapper.updateByPrimaryKey(bsEcup2016ActivityUser);
                }
                return true;
            }
        });
    }
	
	@Override
	public BsEcup2016ActivityUserInfoVO selectEcup2016ActivityUserInfo(
			Integer userId) {

		return bsEcup2016ActivityUserMapper.selectEcup2016ActivityUserInfo(userId);
	}

	@Override
	public boolean saveUserChampionSilver(Integer userId, String champion,
			String silver) {
		boolean flag = false;
		List<BsEcup2016ActivityUser> list = bsEcup2016ActivityUserMapper.selectByUserId(userId);
		if(list == null || list.size() <= 0){
			BsEcup2016ActivityUser record = new BsEcup2016ActivityUser();
			record.setChampion(champion);
			record.setCreateTime(new Date());
			record.setSilver(silver);
			record.setSupportNum(0);
			record.setUpdateTime(new Date());
			record.setUserId(userId);
			bsEcup2016ActivityUserMapper.insertSelective(record);
			flag = true;
		}
		return flag;
		
	}

	@Override
	public List<BsEcup2016ActivityUserInfoVO> selectEcup2016ChampionList() {
		
		return bsEcup2016ActivityUserMapper.selectEcup2016ChampionList();
	}

	@Override
	public List<BsEcup2016ActivityUserInfoVO> selectEcup2016SilverList() {
		
		return bsEcup2016ActivityUserMapper.selectEcup2016SilverList();
	}

	@Override
	public List<BsEcup2016Supportor> getEcup2016SupportorList(Integer userId,
			Integer pageIndex, Integer pageSize) {
		return bsEcup2016SupportorMapper.getEcup2016SupportorList(userId, pageIndex * pageSize, pageSize);
	}

	@Override
	public List<BsEcup2016ActivityUserInfoVO> getEcup2016WinnerList(
			Integer pageIndex, Integer pageSize) {
		return bsEcup2016ActivityUserMapper.getEcup2016WinnerList(pageIndex * pageSize, pageSize);
	}

	@Override
	public int countEcup2016SupportorList(Integer userId) {
		return bsEcup2016SupportorMapper.countEcup2016SupportorList(userId);
	}

	@Override
	public int countEcup2016WinnerList() {
		return bsEcup2016ActivityUserMapper.countEcup2016WinnerList();
	}

}
