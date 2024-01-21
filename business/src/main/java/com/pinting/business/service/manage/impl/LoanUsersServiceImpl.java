package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.dto.LoanUserDTO;
import com.pinting.business.model.vo.LoanUserVO;
import com.pinting.business.service.manage.LoanUsersService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.DoubleRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 剑钊 on 2016/11/5.
 */
@Service
public class LoanUsersServiceImpl implements LoanUsersService {

    @Autowired
    private LnUserMapper lnUserMapper;


    @Override
    public List<LoanUserVO> queryLoanUserList(LoanUserDTO loanUserDTO) {

            if(StringUtils.isNotBlank(loanUserDTO.getUserId())){
                loanUserDTO.setUserid(Long.parseLong(loanUserDTO.getUserId()));
            }

            if (StringUtils.isBlank(loanUserDTO.getPartner())) {
                loanUserDTO.setPartner(null);
            }

            if (StringUtils.isNotBlank(loanUserDTO.getBreakStart())) {
                loanUserDTO.setsBreak(Double.parseDouble(loanUserDTO.getBreakStart()));
            }

            if (StringUtils.isNotBlank(loanUserDTO.getBreakEnd())) {
                loanUserDTO.seteBreak(Double.parseDouble(loanUserDTO.getBreakEnd()));
            }

            if (StringUtils.isNotBlank(loanUserDTO.getHistoryEnd())) {
                loanUserDTO.seteHistory(Double.parseDouble(loanUserDTO.getHistoryEnd()));
            }

            if (StringUtils.isNotBlank(loanUserDTO.getHistoryStart())) {
                loanUserDTO.setsHistory(Double.parseDouble(loanUserDTO.getHistoryStart()));
            }

            if (StringUtils.isNotBlank(loanUserDTO.getNoReturnStart())) {
                loanUserDTO.setsNoReturn(Double.parseDouble(loanUserDTO.getNoReturnStart()));
            }

            if (StringUtils.isNotBlank(loanUserDTO.getNoReturnEnd())) {
                loanUserDTO.seteNoReturn(Double.parseDouble(loanUserDTO.getNoReturnEnd()));
            }

        return lnUserMapper.selectByLnUserDTO(loanUserDTO);
    }

    @Override
    public int queryLoanUserListCount(LoanUserDTO loanUserDTO) {


        if(StringUtils.isNotBlank(loanUserDTO.getUserId())){
            loanUserDTO.setUserid(Long.parseLong(loanUserDTO.getUserId()));
        }

        if (StringUtils.isBlank(loanUserDTO.getPartner())) {
            loanUserDTO.setPartner(null);
        }

        if (StringUtils.isNotBlank(loanUserDTO.getBreakStart())) {
            loanUserDTO.setsBreak(Double.parseDouble(loanUserDTO.getBreakStart()));
        }

        if (StringUtils.isNotBlank(loanUserDTO.getBreakEnd())) {
            loanUserDTO.seteBreak(Double.parseDouble(loanUserDTO.getBreakEnd()));
        }

        if (StringUtils.isNotBlank(loanUserDTO.getHistoryEnd())) {
            loanUserDTO.seteHistory(Double.parseDouble(loanUserDTO.getHistoryEnd()));
        }

        if (StringUtils.isNotBlank(loanUserDTO.getHistoryStart())) {
            loanUserDTO.setsHistory(Double.parseDouble(loanUserDTO.getHistoryStart()));
        }

        if (StringUtils.isNotBlank(loanUserDTO.getNoReturnStart())) {
            loanUserDTO.setsNoReturn(Double.parseDouble(loanUserDTO.getNoReturnStart()));
        }

        if (StringUtils.isNotBlank(loanUserDTO.getNoReturnEnd())) {
            loanUserDTO.seteNoReturn(Double.parseDouble(loanUserDTO.getNoReturnEnd()));
        }

        return lnUserMapper.selectByLnUserDTOCount(loanUserDTO);
    }
}
