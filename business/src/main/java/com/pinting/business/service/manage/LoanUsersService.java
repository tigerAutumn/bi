package com.pinting.business.service.manage;

import com.pinting.business.model.dto.LoanUserDTO;
import com.pinting.business.model.vo.LoanUserVO;

import java.util.List;

/**
 * Created by 剑钊 on 2016/11/5.
 */
public interface LoanUsersService {

    /**
     * 借款用户查询
     * @param loanUserDTO
     * @return
     */
    List<LoanUserVO> queryLoanUserList(LoanUserDTO loanUserDTO);

    /**
     * 借款用户统计
     * @param loanUserDTO
     * @return
     */
    int queryLoanUserListCount(LoanUserDTO loanUserDTO);
}
