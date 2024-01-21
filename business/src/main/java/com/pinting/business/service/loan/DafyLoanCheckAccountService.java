package com.pinting.business.service.loan;

/**
 * Author:      cyb
 * Date:        2017/2/6
 * Description: 达飞云贷自主放款对账文件生成服务
 */
public interface DafyLoanCheckAccountService {

    /**
     * 1. 生成借款+借款手续费对账单
     * 2. 生成还款对账单
     * 3. 成功代偿对账单
     * 4. 成功云贷营收对账单
     * 5. 三方手续费对账单-待定
     * 6. 补账对账
     */
    void checkAccount(String time);

}
