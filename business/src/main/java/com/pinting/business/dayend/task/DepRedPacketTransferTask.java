package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.service.DepRedPacketTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:      cyb
 * Date:        2017/5/3
 * Description: 红包抵用转账定时重发
 */
@Service
public class DepRedPacketTransferTask {

    @Autowired
    private DepRedPacketTransferService depRedPacketTransferService;

    public void execute() {
        try {
            depRedPacketTransferService.redPacketTransfer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
