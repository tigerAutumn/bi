/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.pay19.out.model.req.CheckAccountFileReq;
import com.pinting.gateway.pay19.out.model.resp.AccountDetail;
import com.pinting.gateway.pay19.out.model.resp.CheckAccountFileResp;
import com.pinting.gateway.pay19.out.model.resp.TotalAccount;
import com.pinting.gateway.pay19.out.service.FTP4AccountsServiceClient;
import com.pinting.gateway.pay19.out.util.FtpClientUtil;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: FTP4AccountsServiceClientImpl.java, v 0.1 2015-8-31 下午8:08:24 BabyShark Exp $
 */
@Service("ftp4AccountsServiceClient")
public class FTP4AccountsServiceClientImpl implements FTP4AccountsServiceClient {
    private Logger        log        = LoggerFactory.getLogger(FTP4AccountsServiceClientImpl.class);
    private static String ftpHost    = "119.254.84.6";                                              //GlobEnvUtil.get("19pay.ftp.host");
    private static String ftpPort    = "21";                                                        //GlobEnvUtil.get("19pay.ftp.port");
    private static String username   = "90003243";                                                  //GlobEnvUtil.get("19pay.ftp.username");
    private static String password   = "Le~DxtjI";                                                  //GlobEnvUtil.get("19pay.ftp.password");
    private static String ftpFileSrc = "/";
    private static String localDir   = "f:/";
    static {
        try {
            ftpHost = GlobEnvUtil.get("19pay.ftp.host");
            ftpPort = GlobEnvUtil.get("19pay.ftp.port");
            username = GlobEnvUtil.get("19pay.ftp.username");
            password = GlobEnvUtil.get("19pay.ftp.password");
            ftpFileSrc = GlobEnvUtil.get("19pay.ftp.src");
            localDir = GlobEnvUtil.get("19pay.ftp.download");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** 
     * @see com.pinting.gateway.pay19.out.service.FTP4AccountsServiceClient#downloadCheckAccountFile(com.pinting.gateway.pay19.out.model.req.CheckAccountFileReq)
     */
    @Override
    public CheckAccountFileResp downloadCheckAccountFile(CheckAccountFileReq req) {
        BufferedReader br = null;
        CheckAccountFileResp resp = null;
        try {
            //ftp下载文件
            FtpClientUtil ftpClient = new FtpClientUtil(username, password, ftpHost,
                Integer.valueOf(ftpPort));
            String ftpFileName = DateUtil.formatDateTime(req.getCheckDate(), "yyyyMMdd") + ".csv";
            boolean flag = ftpClient.download(ftpFileSrc + ftpFileName, localDir);
            if (!flag) {
            	throw new PTMessageException(PTMessageEnum.FTP_DOWNLOAD_FAIL);
            }
            //解析文件数据，保存到list列表中
            File dest = new File(localDir + ftpFileName);
            br = new BufferedReader(new InputStreamReader(new FileInputStream(dest), "GB2312"));
            resp = new CheckAccountFileResp();
            TotalAccount totalAccount = new TotalAccount();
            List<AccountDetail> accountDetails = new ArrayList<AccountDetail>();
            String temp = "";
            int count = 0;
            while ((temp = br.readLine()) != null) {
                String[] temps = temp.split(",");
                //总账
                if (count == 1) {
                    totalAccount.setAccountDate(DateUtil.parseDateTime(trimQuote(temps[2])));
                    totalAccount.setOrderCount(Integer.valueOf(StringUtil
                        .isEmpty(trimQuote(temps[3])) ? "0" : trimQuote(temps[3])));
                    totalAccount.setTotalIncome(Double.valueOf(StringUtil
                        .isEmpty(trimQuote(temps[4])) ? "0" : trimQuote(temps[4])));
                    totalAccount.setTotalDebit(Double.valueOf(StringUtil
                        .isEmpty(trimQuote(temps[5])) ? "0" : trimQuote(temps[5])));
                    totalAccount
                        .setTotalFee(Double.valueOf(StringUtil.isEmpty(trimQuote(temps[6])) ? "0"
                            : trimQuote(temps[6])));
                    totalAccount.setTotalSettle(Double.valueOf(StringUtil
                        .isEmpty(trimQuote(temps[7])) ? "0" : trimQuote(temps[7])));
                }
                //明细账
                else if (count > 2) {
                    AccountDetail detail = new AccountDetail();
                    detail.setOrderNo(trimQuote(temps[0]));
                    detail.setMpOrderNo(trimQuote(temps[1]));
                    detail.setReqTime(DateUtil.parseDateTime(trimQuote(temps[2])));
                    detail.setFinishTime(StringUtil.isEmpty(trimQuote(temps[3])) ? null : DateUtil
                        .parseDateTime(trimQuote(temps[3])));
                    detail.setTradeType(trimQuote(temps[4]));
                    
                    String income = trimQuote(temps[6]);
                    String debit = trimQuote(temps[7]);
                    Double amount = Double.valueOf(StringUtil.isEmpty(income) ? "0" : income);
                    if("0.00".endsWith(income) || StringUtil.isEmpty(income)){
                    	amount = Double.valueOf(StringUtil.isEmpty(debit) ? "0" : debit);
                    }
                    detail.setAmount(amount);
                    detail.setFee(Double.valueOf(StringUtil.isEmpty(trimQuote(temps[8])) ? "0"
                        : trimQuote(temps[8])));
                    detail
                        .setSettleAmount(Double.valueOf(StringUtil.isEmpty(trimQuote(temps[9])) ? "0"
                            : trimQuote(temps[9])));
                    //detail.setOrderRemark(trimQuote(temps[10]));
                    accountDetails.add(detail);
                }
                count++;
            }
            resp.setTotalAccount(totalAccount);
            resp.setAccountDetails(accountDetails);
            log.info("==================解析对账文件成功====================");
        } catch (Exception e) {
            log.info("==================对账文件下载或解析失败====================",e);
            throw new PTMessageException(PTMessageEnum.FTP_DOWNLOAD_FAIL);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return resp;
    }

    private String trimQuote(String str) {
        return StringUtil.replace(str, "\"", "");
    }

    public static void main(String[] args) {
        CheckAccountFileReq req = new CheckAccountFileReq();
        req.setCheckDate(DateUtil.addDays(new Date(), -1));
        CheckAccountFileResp resp = new FTP4AccountsServiceClientImpl()
            .downloadCheckAccountFile(req);
        System.out.println(resp.getTotalAccount().getTotalIncome());
    }
}
