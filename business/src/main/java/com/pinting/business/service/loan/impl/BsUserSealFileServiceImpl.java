package com.pinting.business.service.loan.impl;

import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.service.loan.BsUserSealFileService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/2/9
 * Description: 云贷自主放款协议文件记录服务
 */
@Service
public class BsUserSealFileServiceImpl implements BsUserSealFileService {

    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;

    @Autowired
    private LnLoanMapper lnLoanMapper;

    private Logger log = LoggerFactory.getLogger(BsUserSealFileServiceImpl.class);

    @Override
	public String downloadSealFile(String agreementUrl, Integer id) {
        // 1. 文件下载
        log.info("获取云贷自主放款借款协议并保存本地开始。下载地址：{}", agreementUrl);
        BsUserSealFile bsUserSealFile = bsUserSealFileMapper.selectByPrimaryKey(id);
        
        File file = downLoadHttp(agreementUrl, bsUserSealFile.getAgreementNo(), Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
        // 2. 更新签章表
        if(null != file) {
            log.info("获取云贷自主放款借款协议并保存本地成功。协议ID：{}，下载地址：{}", id, agreementUrl);
            downLoadSuccess(file, agreementUrl, id);
            return file.getAbsolutePath();
        } else {
            log.error("获取云贷自主放款借款协议并保存本地失败。协议ID：{}，下载地址：{}", id, agreementUrl);
            downLoadFail(agreementUrl, id);
            return null;
            
        }
       
    }
    
    
    
	private  File downLoadHttp(String agreementUrl, String agreementNo, String partnerCode) {
    	
		try {
			log.info("协议下载地址："+agreementUrl);
			URL url;
			url = new URL(agreementUrl);
			
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
	                //设置超时间为3秒  
	        conn.setConnectTimeout(3*1000);  
	        //防止屏蔽程序抓取而返回403错误  
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	  
	        //得到输入流  
	        InputStream inputStream = conn.getInputStream();    
	        //获取自己数组  
	        byte[] getData = readInputStream(inputStream);      
	  
	        
	        
	        String  dateString = DateUtil.format(new Date(), "yyyyMM");
	        
	        //文件保存位置  
	        String saveFile = "";
	        if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(partnerCode)) {
	        	saveFile = GlobEnvUtil.get("self.loan.yundai.seal.path")+"/quartetAgree_"+dateString;
	        }else if (Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(partnerCode)) {
	        	saveFile = GlobEnvUtil.get("self.loan.7dai.seal.path")+"/quartetAgree_"+dateString;
			} else {
	        	saveFile = GlobEnvUtil.get("self.loan.yundai.seal.path")+"/quartetAgree_"+dateString;
	        }
	        log.info("文件保存地址>>>>>>>>>>>>" + saveFile);
	        File saveDir = new File(saveFile);  
	        if(!saveDir.exists()){  
	            saveDir.mkdir();  
	        }  
	        File file = new File(saveDir+"/"+agreementNo+".pdf");      
	        FileOutputStream fos = new FileOutputStream(file);       
	        fos.write(getData);   
	        if(fos!=null){  
	            fos.close();    
	        }  
	        if(inputStream!=null){  
	            inputStream.close();  
	        }  
	        
	        return file;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		return null;

    }
    
    
    /** 
     * 从输入流中获取字节数组 
     * @param inputStream 
     * @return 
     * @throws IOException 
     */  
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {    
        byte[] buffer = new byte[1024];    
        int len = 0;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        while((len = inputStream.read(buffer)) != -1) {    
            bos.write(buffer, 0, len);    
        }    
        bos.close();    
        return bos.toByteArray();    
    }   

    /**
     * 下载成功
     * @param file
     * @param agreementUrl
     * @param id
     */
    private void downLoadSuccess(File file, String agreementUrl, Integer id) {
        BsUserSealFile bsUserSealFile = bsUserSealFileMapper.selectByPrimaryKey(id);
        bsUserSealFile.setSrcAddress(agreementUrl);
        bsUserSealFile.setFileAddress(file.getAbsolutePath());
        bsUserSealFile.setUpdateTime(new Date());
        bsUserSealFile.setId(id);
        bsUserSealFileMapper.updateByPrimaryKeySelective(bsUserSealFile);
    }

    /**
     * 下载失败
     * @param agreementUrl
     * @param id
     */
    private void downLoadFail(String agreementUrl, Integer id) {
        BsUserSealFile bsUserSealFile = bsUserSealFileMapper.selectByPrimaryKey(id);
        bsUserSealFile.setSrcAddress(agreementUrl);
        bsUserSealFile.setUpdateTime(new Date());
        bsUserSealFile.setId(id);
        bsUserSealFileMapper.updateByPrimaryKeySelective(bsUserSealFile);
    }

    @Override
    public BsUserSealFile queryAgreementNo(String partnerLoanId, String userSrc) {
        // 查询借款协议
        BsUserSealFile bsUserSealFile = bsUserSealFileMapper.selectAgreementByPartnerLoanId(partnerLoanId, userSrc);
        return bsUserSealFile;
    }

    @Override
	public String downloadPartnerSealFile(String agreementUrl, Integer id,
			String partnerCode) {
		// 1. 文件下载
        log.info("获取{}借款协议并保存本地开始。下载地址：{}", partnerCode, agreementUrl);
        BsUserSealFile bsUserSealFile = bsUserSealFileMapper.selectByPrimaryKey(id);
        
        File file = downLoadHttp(agreementUrl, bsUserSealFile.getAgreementNo(), partnerCode);
        // 2. 更新签章表
        if(null != file) {
            log.info("获取{}借款协议并保存本地成功。协议ID：{}，下载地址：{}", partnerCode, id, agreementUrl);
            downLoadSuccess(file, agreementUrl, id);
            return file.getAbsolutePath();
        } else {
            log.error("获取{}借款协议并保存本地失败。协议ID：{}，下载地址：{}", partnerCode, id, agreementUrl);
            downLoadFail(agreementUrl, id);
            return null;
            
        }
	}    

}
