package com.pinting.business.service.loan;

/**
 * Author:      shh
 * Date:        2017/9/4
 * Description: 赞时贷对账文件生成服务
 */
public interface ZsdLoanCheckAccountService {

    /**
     * 1. 生成借款+借款手续费对账单
     * 2. 生成还款对账单
     * 3. 成功代偿对账单
     * 4. 成功赞时贷营收对账单
     */
    void checkAccount(String time);

}
