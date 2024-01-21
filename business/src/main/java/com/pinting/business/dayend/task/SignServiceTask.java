package com.pinting.business.dayend.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.dao.BsFileSealRelationMapper;
import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealPdfPathEnum;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.BsFileSealRelation;
import com.pinting.business.model.BsFileSealRelationExample;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.vo.SignSeal4PdfInfoVO;
import com.pinting.business.model.vo.UserSealFileVO;
import com.pinting.business.redis.core.model.RedisContext;
import com.pinting.business.redis.sign.model.SignRedisVO;
import com.pinting.business.redis.sign.service.SignRedisHelper;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.SpringBeanUtil;
import com.pinting.core.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 签章定时
 *
 * @author bianyatian
 * @2018-3-30 下午2:07:16
 */
@Service
public class SignServiceTask {

    private Logger log = LoggerFactory.getLogger(SignServiceTask.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;
    @Autowired
    private BsFileSealRelationMapper bsFileSealRelationMapper;
    @Autowired
    private SignSealService signSealService;
    @Autowired
    private SpecialJnlService specialJnlService;

    public void execute() {

        String queueKey = Constants.SIGN_FILE_QUEUE_KEY;

        log.info(">>>签章定时开始<<<");
        Long len = jsClientDaoSupport.llen(queueKey);
        log.info(">>>签章定时-队列大小" + len + "<<<");
        //获取redis队列数据
        String signFileStr = jsClientDaoSupport.lpop(queueKey);
        if (StringUtil.isEmpty(signFileStr)) {
            log.warn(">>>签章定时-redis数据为空");
            return;
        }
        JSONObject json = JSON.parseObject(signFileStr);
        Integer fileId = json.getInteger("file_id");
        //查询 BsUserSealFile 表
        BsUserSealFile file = bsUserSealFileMapper.selectByPrimaryKey(fileId);
        if (file == null) {
            log.warn(">>>签章定时-file_id:" + fileId + ",未查询到BsUserSealFile数据");
            return;
        }
        if (SealStatus.UNDOWNLOAD.getCode().equals(file.getSealStatus())) {
            if (!createPdf(file)) {
                //创建pdf失败，重入redis
                jsClientDaoSupport.rpush(queueKey, JSON.toJSONString(json));
                return;
            }
            if (!upload2CfcaTransfer(file)) {
                //上传文件到cfcaTransfer失败，重入redis
                jsClientDaoSupport.rpush(queueKey, JSON.toJSONString(json));
                return;
            }
            if (!sign(file)) {
                //生成签章失败，重入redis
                jsClientDaoSupport.rpush(queueKey, JSON.toJSONString(json));
                return;
            }
        } else if (SealStatus.FILE_CREATE.getCode().equals(file.getSealStatus())) {
            if (!upload2CfcaTransfer(file)) {
                //上传文件到cfcaTransfer失败，重入redis
                jsClientDaoSupport.rpush(queueKey, JSON.toJSONString(json));
                return;
            }
            if (!sign(file)) {
                //生成签章失败，重入redis
                jsClientDaoSupport.rpush(queueKey, JSON.toJSONString(json));
                return;
            }
        } else if (SealStatus.FILE_UPLOAD.getCode().equals(file.getSealStatus()) || SealStatus.FAIL.getCode().equals(file.getSealStatus())) {
            if (!sign(file)) {
                //生成签章失败，重入redis
                jsClientDaoSupport.rpush(queueKey, JSON.toJSONString(json));
                return;
            }
        }
    }

    /**
     * 调用证书申请、制章、签章
     *
     * @param file
     * @return
     */
    private boolean sign(BsUserSealFile file) {
        boolean signFlag = false;
        //校验状态
        file = bsUserSealFileMapper.selectByPrimaryKey(file.getId());
        if (!SealStatus.FILE_UPLOAD.getCode().equals(file.getSealStatus()) && !SealStatus.FAIL.getCode().equals(file.getSealStatus())) {
            return signFlag;
        }

        //1.查询BsFileSealRelation数据
        BsFileSealRelationExample bsFileSealRelationExample = new BsFileSealRelationExample();
        bsFileSealRelationExample.createCriteria().andSealFileIdEqualTo(file.getId());
        List<BsFileSealRelation> bsFileSealRelations = bsFileSealRelationMapper.selectByExample(bsFileSealRelationExample);

        if (CollectionUtils.isEmpty(bsFileSealRelations)) {
            return signFlag;
        }
        //2.循环开始签章业务

        log.info("循环开始签章业务");
        int i = 0;
        String sealedFile = file.getFileAddress();
        for (BsFileSealRelation bsFileSealRelation : bsFileSealRelations) {
            SignSeal4PdfInfoVO companySignSeal4PdfReq = signSealService.getSignSeal(file, bsFileSealRelation);
            if (companySignSeal4PdfReq == null) {
                return signFlag;
            }

            i = i + 1;
            if (i != 1) {
                companySignSeal4PdfReq.setPdfPath(companySignSeal4PdfReq.getSealedFileName());
            }
            sealedFile = companySignSeal4PdfReq.getSealedFileName();
            log.info("签章业务,开始进行关键字签章：" + companySignSeal4PdfReq.getKeyword());
            boolean enterpriseSignFlag = signSealService.signSeal4PdfByKeyword(companySignSeal4PdfReq);
            log.info("关键字签章结果：" + enterpriseSignFlag);
            signFlag = enterpriseSignFlag;
            if (!enterpriseSignFlag) {
                log.error("执行签章失败......relationId=" + bsFileSealRelation.getId());
                updateUserSealInfo(file.getId(), SealStatus.FAIL.getCode(), null);
                return signFlag;
            }
        }
        //3.签章成功
        if (signFlag) {
            updateUserSealInfo(file.getId(), SealStatus.SUCC.getCode(), sealedFile);
            return signFlag;
        }
        return signFlag;
    }

    private void updateUserSealInfo(Integer id, String sealStatus, String fileAddress) {
        UserSealFileVO userSealFile = new UserSealFileVO();
        userSealFile.setSealStatus(sealStatus);
        userSealFile.setFileAddress(fileAddress);
        userSealFile.setId(id);
        signSealService.updateUserSealFile(userSealFile);
    }

    /**
     * 创建pdf文件
     * 若生成pdf成功，修改bsUserSealFile表状态和FileAddress
     * 返回成功或失败
     *
     * @param file
     * @return 成功或失败
     */
    private boolean createPdf(BsUserSealFile file) {
        return createPdf(file, null);
    }

    /**
     * 创建pdf文件
     * 若生成pdf成功，修改bsUserSealFile表状态和FileAddress
     * 返回成功或失败
     *
     * @param file
     * @return 成功或失败
     */
    private boolean createPdf(BsUserSealFile file, String agreementPdfName) {
        boolean isSuccFlag = false;
        String htmlUrl = file.getSrcAddress();
        //创建pdf文件,初始路径+签章类型（资产方）+业务+日期(年月)+文件名（签章类型+文件id）
        String partner = StringUtil.isEmpty(file.getUserSrc()) ? SealPdfPathEnum.BIGANGWAN.getName()
                : SealPdfPathEnum.getEnumByCode(file.getUserSrc()).getName();

        String pdfPath = partner + "/" + file.getSealType()
                + "/" + DateUtil.formatDateTime(file.getCreateTime(), "yyyyMM") + "/" + getUserPdfPath(file.getUserId(), partner) + "/"
                + getAgreementPdfName(agreementPdfName, file.getAgreementNo()) + ".pdf";
        try {
            SealBusiness sealBusiness = SealBusiness.find(file.getSealType());
            if (sealBusiness != null) {
                ITextPdfUtil.createHtm2Pdf(htmlUrl, GlobEnvUtil.get("sign.task.pdfPath") + "/" + pdfPath, sealBusiness.getDescription(), sealBusiness.getDescription(),
                        file.getAgreementNo());
                isSuccFlag = true;
            } else {
                log.warn(">>>签章定时-SealType不存在:" + file.getSealType());
            }
        } catch (Exception e) {
            log.warn(">>>签章定时-file_id:" + file.getId() + ",创建pdf文件失败");
            e.printStackTrace();
        }

        if (isSuccFlag) {
            //生成pdf成功，修改bsUserSealFile表状态和FileAddress
            BsUserSealFile userSealFileTemp = new BsUserSealFile();
            userSealFileTemp.setId(file.getId());
            userSealFileTemp.setSealStatus(SealStatus.FILE_CREATE.getCode());
            userSealFileTemp.setFileAddress(pdfPath);
            userSealFileTemp.setUpdateTime(new Date());
            bsUserSealFileMapper.updateByPrimaryKeySelective(userSealFileTemp);

        }
        return isSuccFlag;

    }

    /**
     * 指定签章协议文件名
     *
     * @param agreementPdfName
     * @param agreementNo
     * @return
     */
    private String getAgreementPdfName(String agreementPdfName, String agreementNo) {
        return StringUtils.isBlank(agreementPdfName) ? agreementNo : agreementPdfName;
    }

    /**
     * 上传到cfcaTransfer
     * 若生成上传成功，修改bsUserSealFile表状态和FileAddress
     *
     * @param userSealFile
     * @return
     */
    private boolean upload2CfcaTransfer(BsUserSealFile userSealFile) {
        return upload2CfcaTransfer(userSealFile, null);
    }

    /**
     * 上传到cfcaTransfer
     * 若生成上传成功，修改bsUserSealFile表状态和FileAddress
     *
     * @param userSealFile
     * @return
     */
    private boolean upload2CfcaTransfer(BsUserSealFile userSealFile, String agreementPdfName) {
        boolean isSuccFlag = false;
        //校验状态
        userSealFile = bsUserSealFileMapper.selectByPrimaryKey(userSealFile.getId());
        if (!SealStatus.FILE_CREATE.getCode().equals(userSealFile.getSealStatus())) {
            return isSuccFlag;
        }

        //cfcaTransfer 存放路径
        String partner = StringUtil.isEmpty(userSealFile.getUserSrc()) ? SealPdfPathEnum.BIGANGWAN.getName()
                : SealPdfPathEnum.getEnumByCode(userSealFile.getUserSrc()).getName();
        String cfcaTransferSavePath = partner + "/" + userSealFile.getSealType() + "/"
                + DateUtil.formatDateTime(userSealFile.getCreateTime(), "yyyyMM") + "/" + getUserPdfPath(userSealFile.getUserId(), partner);

        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------123821742118716"; // boundary就是request头和上传文件内容的分隔符
        try {
            String cfcaTransferUrl = GlobEnvUtil.get("sign.task.fileUpload.url"); //cfcaTransfer请求地址

            Map<String, String> fileMap = new HashMap<String, String>();
            fileMap.put(cfcaTransferSavePath, GlobEnvUtil.get("sign.task.pdfPath") + "/" + userSealFile.getFileAddress());//cfca文件存放地址,pdf在business服务器地址

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
        if (isSuccFlag) {
            //生成pdf成功，修改bsUserSealFile表状态和FileAddress
            BsUserSealFile userSealFileTemp = new BsUserSealFile();
            userSealFileTemp.setId(userSealFile.getId());
            userSealFileTemp.setSealStatus(SealStatus.FILE_UPLOAD.getCode());
            userSealFileTemp.setFileAddress(cfcaTransferSavePath + "/" + getAgreementPdfName(agreementPdfName, userSealFile.getAgreementNo()) + ".pdf");
            userSealFileTemp.setUpdateTime(new Date());
            bsUserSealFileMapper.updateByPrimaryKeySelective(userSealFileTemp);
        }

        return isSuccFlag;
    }

    /**
     * 用户相关PDF路径
     *
     * @param userId
     * @param partner
     * @return
     */
    private String getUserPdfPath(Integer userId, String partner) {
        return userId != null ? userId.toString() : partner;
    }

    /**
     * 同步签章多份文件定时处理
     */
    public void executeSignFileMore() {

        String queueKey = Constants.MORE_SIGN_FILE_QUEUE_KEY;

        log.info(">>>签章多份文件定时开始<<<");
        Long len = jsClientDaoSupport.llen(queueKey);
        log.info(">>>签章多份文件定时-队列大小" + len + "<<<");
        //获取redis队列数据
        String signFileStr = jsClientDaoSupport.lpop(queueKey);
        if (StringUtil.isEmpty(signFileStr)) {
            log.warn(">>>签章多份文件定时-redis数据为空");
            return;
        }

        boolean isAllSignSucc = true;
        Integer signFileId = null;
        RedisContext<SignRedisVO> redisVO = JSONObject.parseObject(signFileStr, RedisContext.class);
        for (Object obj : redisVO.getRedisVOList()) {
            SignRedisVO signRedisVO = JSONObject.parseObject(JSONObject.toJSONString(obj), SignRedisVO.class);

            //查询 BsUserSealFile 表
            BsUserSealFile file = bsUserSealFileMapper.selectByPrimaryKey(signRedisVO.getSignFileId());
            if (file == null) {
                log.warn(">>>签章定时-file_id:" + signRedisVO.getSignFileId() + ",未查询到BsUserSealFile数据");
                continue;
            }

            if (SealStatus.SUCC.getCode().equals(file.getSealStatus())) {
                continue;
            }

            // 签章文件服务
            if (SealStatus.UNDOWNLOAD.getCode().equals(file.getSealStatus())) {
                if (!createPdf(file, signRedisVO.getAgreementPdfName())) {
                    isAllSignSucc = false;
                    signFileId = signRedisVO.getSignFileId();
                    break;
                }
                if (!upload2CfcaTransfer(file, signRedisVO.getAgreementPdfName())) {
                    isAllSignSucc = false;
                    signFileId = signRedisVO.getSignFileId();
                    break;
                }
                if (!sign(file)) {
                    isAllSignSucc = false;
                    signFileId = signRedisVO.getSignFileId();
                    break;
                }
            } else if (SealStatus.FILE_CREATE.getCode().equals(file.getSealStatus())) {
                if (!upload2CfcaTransfer(file, signRedisVO.getAgreementPdfName())) {
                    isAllSignSucc = false;
                    signFileId = signRedisVO.getSignFileId();
                    break;
                }
                if (!sign(file)) {
                    isAllSignSucc = false;
                    signFileId = signRedisVO.getSignFileId();
                    break;
                }
            } else if (SealStatus.FILE_UPLOAD.getCode().equals(file.getSealStatus()) || SealStatus.FAIL.getCode().equals(file.getSealStatus())) {
                if (!sign(file)) {
                    isAllSignSucc = false;
                    signFileId = signRedisVO.getSignFileId();
                    break;
                }
            }
        }

        if (isAllSignSucc && CollectionUtils.isNotEmpty(redisVO.getAfterProcess())) {
            // 签章完成后处理
            for (Object serviceName : redisVO.getAfterProcess()) {
                try {
                    SignRedisHelper redisHelper = (SignRedisHelper) SpringBeanUtil.getBean((String) serviceName);
                    if (redisHelper != null) {
                        redisHelper.execute(redisVO);
                    }
                } catch (Exception e) {
                    log.error("签章后处理执行失败", e);
                }
            }
        } else {
            redisVO.setExecuteCount(redisVO.getExecuteCount() + 1);
            redisVO.setLatestExecuteTime(new Date());

            // 签章执行超过4次，不再入redis队列且记录告警流水
            if (redisVO.getExecuteCount() <= 4) {
                log.info("仍有未签章成功数据，重入签章队列：{}，执行次数：{}", JSON.toJSONString(redisVO), redisVO.getExecuteCount());
                jsClientDaoSupport.rpush(queueKey, JSON.toJSONString(redisVO));
            } else {
                if (signFileId != null) {
                    String detail = "签章文件SignFileId（" + signFileId + "）执行协议签章失败";
                    specialJnlService.warn4FailNoSMS(0d, detail, signFileId.toString(), "【签章定时】");
                }
            }
        }
    }
}
