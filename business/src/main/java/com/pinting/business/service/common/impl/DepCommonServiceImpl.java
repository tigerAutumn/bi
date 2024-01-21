package com.pinting.business.service.common.impl;

import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.dao.LnDepositionTargetJnlMapper;
import com.pinting.business.dao.LnDepositionTargetMapper;
import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnDepositionTargetJnl;
import com.pinting.business.model.LnLoan;
import com.pinting.business.service.common.DepCommonService;
import com.pinting.business.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhangbao on 2017/4/15.
 */
@Service
public class DepCommonServiceImpl implements DepCommonService {

    @Autowired
    private LnDepositionTargetMapper lnDepositionTargetMapper;
    @Autowired
    private LnDepositionTargetJnlMapper depositionTargetJnlMapper;

    /**
     * 修改ln_deposition_target状态,新增一条ln_deposition_target_jnl记录公共方法
     * @param isSuc
     * @param depTargetType
     * @param depTargetOperate
     * @param lnDepositionTarget
     * @param lnLoan
     */
    public void updateTargetStatus(boolean isSuc, String depTargetType, DepTargetEnum depTargetOperate,
                                   LnDepositionTarget lnDepositionTarget, LnLoan lnLoan, String orderNo) {
        //标的操作成功时修改ln_deposition_target状态
        if(isSuc && !DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE.getCode().equals(depTargetOperate.getCode())){
            LnDepositionTarget targetUpdate = new LnDepositionTarget();
            targetUpdate.setId(lnDepositionTarget.getId());
            targetUpdate.setUpdateTime(new Date());
            targetUpdate.setStatus(depTargetType);
            lnDepositionTargetMapper.updateByPrimaryKeySelective(targetUpdate);
        }

        //新增一条ln_deposition_target_jnl记录
        LnDepositionTargetJnl depositionTargetJnl = new LnDepositionTargetJnl();
        depositionTargetJnl.setProdId(lnDepositionTarget.getId());
        depositionTargetJnl.setOrderNo(orderNo);
        depositionTargetJnl.setTransTime(new Date());
        depositionTargetJnl.setTransType(depTargetOperate.getCode());
        depositionTargetJnl.setTransName(depTargetOperate.getDescription());
        depositionTargetJnl.setAmount(lnLoan.getApplyAmount());
        depositionTargetJnl.setProdStatus(depTargetType);
        if(isSuc){
            depositionTargetJnl.setTransStatus(Constants.DEP_TARGET_OPERATE_SUCC);
        }else{
            depositionTargetJnl.setTransStatus(Constants.DEP_TARGET_OPERATE_FAIL);
        }
        depositionTargetJnl.setCreateTime(new Date());
        depositionTargetJnl.setUpdateTime(new Date());
        depositionTargetJnlMapper.insertSelective(depositionTargetJnl);
    }

	@Override
	public void insertTargetJnl(boolean isSuc, String depTargetType,
			DepTargetEnum depTargetOperate,
			LnDepositionTarget lnDepositionTarget, LnLoan lnLoan, String orderNo) {
		//新增一条ln_deposition_target_jnl记录
        LnDepositionTargetJnl depositionTargetJnl = new LnDepositionTargetJnl();
        depositionTargetJnl.setProdId(lnDepositionTarget.getId());
        depositionTargetJnl.setOrderNo(orderNo);
        depositionTargetJnl.setTransTime(new Date());
        depositionTargetJnl.setTransType(depTargetOperate.getCode());
        depositionTargetJnl.setTransName(depTargetOperate.getDescription());
        depositionTargetJnl.setAmount(lnLoan.getApplyAmount());
        depositionTargetJnl.setProdStatus(depTargetType);
        if(isSuc){
            depositionTargetJnl.setTransStatus(Constants.DEP_TARGET_OPERATE_SUCC);
        }else{
            depositionTargetJnl.setTransStatus(Constants.DEP_TARGET_OPERATE_FAIL);
        }
        depositionTargetJnl.setCreateTime(new Date());
        depositionTargetJnl.setUpdateTime(new Date());
        depositionTargetJnlMapper.insertSelective(depositionTargetJnl);
		
	}
}
