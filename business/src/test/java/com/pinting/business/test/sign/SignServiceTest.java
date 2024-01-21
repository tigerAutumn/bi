package com.pinting.business.test.sign;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.dao.BsUserSignSealMapper;
import com.pinting.business.enums.SealPdfPathEnum;
import com.pinting.business.model.BsUserSignSeal;
import com.pinting.business.model.vo.SignSeal4PdfInfoVO;
import com.pinting.business.model.vo.UserSealResultVO;
import com.pinting.business.test.TestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 签章定时
 *
 * @author zousheng
 * @2018-8-09 下午2:07:16
 */
public class SignServiceTest extends TestBase {

    private Logger log = LoggerFactory.getLogger(SignServiceTest.class);

    @Autowired
    private SignSealService signSealService;
    @Autowired
    private BsUserSignSealMapper bsUserSignSealMapper;

//    @Test
//    public void execute() {

        // 通知书
//        String filePath = "F:\\ftp\\debtTransConfirm";
//        File file= new File(filePath);
//
//        String[] filelist = file.list();
//        for (int i = 0; i < filelist.length; i++) {
//            String fileName = filelist[i];
//            upload2CfcaTransfer(filePath, fileName);
////            sign(fileName, "服务方（签章）：杭州币港湾科技有限公司");
//            sign(fileName, "杭州币港湾科技有限公司（签章）");
//        }
//    }

    /**
     * 确认函签章
     *
     * @param fileName
     * @return
     */
    private boolean sign(String fileName, String Keywords) {
        boolean signFlag = false;

        String fileAddress = "yundai/DEBT_TRANS_NOTICES/201808/temp/" + fileName;

        log.info("循环开始签章业务");
        int i = 0;

        //已有章，直接返回印章信息
        UserSealResultVO userSealResult = new UserSealResultVO();
        BsUserSignSeal retSeal = bsUserSignSealMapper.selectByPrimaryKey(SealPdfPathEnum.BIGANGWAN.getSealId());
        userSealResult.setSucc(true);
        userSealResult.setCertDN(retSeal.getDn());
        userSealResult.setCertSN(retSeal.getSerialNo());
        userSealResult.setSealCode(retSeal.getSealCode());
        userSealResult.setSealPassword(retSeal.getSealPassword());


        SignSeal4PdfInfoVO companySignSeal4PdfReq = new SignSeal4PdfInfoVO();
        companySignSeal4PdfReq.setCertDN(userSealResult.getCertDN());
        companySignSeal4PdfReq.setCertSN(userSealResult.getCertSN());
        companySignSeal4PdfReq.setKeyword(Keywords);
        companySignSeal4PdfReq.setPdfPath(fileAddress);
        companySignSeal4PdfReq.setSealCode(userSealResult.getSealCode());
        companySignSeal4PdfReq.setSealPassword(userSealResult.getSealPassword());


        String tempPdfPath = fileAddress;
        String sealedPdfFile = tempPdfPath.substring(0, tempPdfPath.lastIndexOf("/"))
                + tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
                tempPdfPath.length() - 4) + "-sign.pdf";

        companySignSeal4PdfReq.setSealedFileName(sealedPdfFile);
        companySignSeal4PdfReq.setIsNew("Y");

        i = i + 1;
        if (i != 1) {
            companySignSeal4PdfReq.setPdfPath(companySignSeal4PdfReq.getSealedFileName());
        }

        log.info("签章文件：{}", companySignSeal4PdfReq.getSealedFileName());

        log.info("签章业务,开始进行关键字签章：" + companySignSeal4PdfReq.getKeyword());
        boolean enterpriseSignFlag = signSealService.signSeal4PdfByKeyword(companySignSeal4PdfReq);
        log.info("关键字签章结果：" + enterpriseSignFlag);
        signFlag = enterpriseSignFlag;
        if (!enterpriseSignFlag) {
            log.error("执行签章失败......relationId=");
            return signFlag;
        }

        //3.签章成功
        if (signFlag) {
            return signFlag;
        }
        return signFlag;
    }

    /**
     * 上传到cfcaTransfer
     * 若生成上传成功，修改bsUserSealFile表状态和FileAddress
     *
     * @param fileName
     * @return
     */
    private boolean upload2CfcaTransfer(String path, String fileName) {
        boolean isSuccFlag = false;

        String cfcaTransferSavePath = "yundai/DEBT_TRANS_NOTICES/201808/temp";

        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
        try {
            String cfcaTransferUrl = "http://qa.51wenzi.com/cfcatest2/remote/pdfFileUpload"; //cfcaTransfer请求地址

            Map<String, String> fileMap = new HashMap<String, String>();
            fileMap.put(cfcaTransferSavePath, path + "/" + fileName);//cfca文件存放地址,pdf在business服务器地址

            URL url = new URL(cfcaTransferUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);

            OutputStream out = new DataOutputStream(conn.getOutputStream());

            if (fileMap != null) {
                Iterator iter = fileMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    String inputName = (String) entry.getKey();
                    String inputValue = (String) entry.getValue();
                    if (inputValue == null) {
                        continue;
                    }
                    File file = new File(inputValue);
                    String filename = file.getName();
                    String contentType = new MimetypesFileTypeMap()
                            .getContentType(file);
                    if (filename.endsWith(".png")) {
                        contentType = "image/png";
                    }
                    if (contentType == null || contentType.equals("")) {
                        contentType = "application/octet-stream";
                    }
                    StringBuffer strBuf = new StringBuffer();
                    strBuf.append("\r\n").append("--").append(BOUNDARY)
                            .append("\r\n");
                    strBuf.append("Content-Disposition: form-data; name=\""
                            + inputName + "\"; filename=\"" + filename
                            + "\"\r\n");
                    strBuf.append("Content-Type:" + contentType + "\r\n\r\n");

                    out.write(strBuf.toString().getBytes());

                    DataInputStream in = new DataInputStream(
                            new FileInputStream(file));
                    int bytes = 0;
                    byte[] bufferOut = new byte[1024];
                    while ((bytes = in.read(bufferOut)) != -1) {
                        out.write(bufferOut, 0, bytes);
                    }
                    in.close();
                }
            }

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();

            // 数据返回
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (line.equals("0000")) isSuccFlag = true;
            }
            reader.close();
            reader = null;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }

        log.info("上传待签章文件成功");
        return isSuccFlag;
    }
}
