package com.pinting.business.util;

import org.springframework.util.CollectionUtils;

import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.BsSysBalanceDailyFileVO;
import com.pinting.core.util.GlobEnvUtil;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import java.io.File;
import java.util.*;

public class MailUtil {
    public final static String EMAIL_SMTP_ADDRESS = "smtp.yeah.net";// smtp服务器
    public final static String EMAIL_FROM = "insigmatest@yeah.net";// 邮件显示名称
    public final static String EMAIL_SUBJECT = "币港湾邮箱验证";// 邮件标题
    public final static String EMAIL_USERNAME = "insigmatest@yeah.net";// 发件人真实的账户名
    public final static String EMAIL_PASSWORD = "insigmajkxy";// 发件人密码

    public final static String EMAIL_SMTP_ADDRESS_QQ = "smtp.exmail.qq.com";// smtp.qq服务器
    public final static String EMAIL_FROM_YUNYING = "yunying01@dafy.com";// 运营邮件显示名称
    public final static String EMAIL_USERNAME_YUNYING = "yunying01@dafy.com";// 发件人真实的账户名
    public final static String EMAIL_PASSWORD_YUNYING = "Yunying20180706@";// 发件人密码
    public static StringBuffer EMAIL_CONTENT ;// 内容
    private MimeMessage mimeMsg;
    private Session session;
    private Properties props;
    private String username;
    private String password;
    private Multipart mp;

    public MailUtil(String smtp) {
        setSmtpHost(smtp);
        createMimeMessage();
    }

    public void setSmtpHost(String hostName) {
        if (props == null) {
            props = System.getProperties();
        }
        final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        props.put("mail.smtp.host", hostName);
        props.put("mail.smtp.port", GlobEnvUtil.get("mail.smtp.port"));
        props.put("mail.smtp.socketFactory.port", GlobEnvUtil.get("mail.smtp.port"));
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
    }

    public boolean createMimeMessage() {
        try {
            session = Session.getDefaultInstance(props, null);
        } catch (Exception e) {
            System.out.println("获取邮件会话错误！" + e);
            return false;
        }
        try {
            mimeMsg = new MimeMessage(session);
            mp = new MimeMultipart();

            return true;
        } catch (Exception e) {
            System.out.println("创建MIME邮件对象失败！" + e);
            return false;
        }
    }

    /*定义SMTP是否需要验证*/
    public void setNeedAuth(boolean need) {
        if (props == null)
            props = System.getProperties();
        if (need) {
            props.put("mail.smtp.auth", "true");
        } else {
            props.put("mail.smtp.auth", "false");
        }
    }

    public void setNamePass(String name, String pass) {
        username = name;
        password = pass;
    }

    /*定义邮件主题*/
    public boolean setSubject(String mailSubject) {
        try {
            mimeMsg.setSubject(mailSubject);
            return true;
        } catch (Exception e) {
            System.err.println("定义邮件主题发生错误！");
            return false;
        }
    }

    /*定义邮件正文*/
    public boolean setBody(String htmlContent) {
        return  setBody(htmlContent, null);
    }

    /*定义邮件附件*/
    private boolean setBody(String htmlContent, List<String> fileList) {
        try {
            // 添加邮件正文
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(htmlContent, "text/html;charset=UTF-8");
            mp.addBodyPart(contentPart);

            // 遍历添加附件
            if (!CollectionUtils.isEmpty(fileList)) {
                for (String filePath : fileList) {
                    BodyPart part = new MimeBodyPart();
                    // 根据文件名获取数据源
                    DataSource dataSource = new FileDataSource(filePath);
                    DataHandler dataHandler = new DataHandler(dataSource);
                    // 得到附件本身并至入BodyPart
                    part.setDataHandler(dataHandler);
                    // 得到文件名同样至入BodyPart
                    part.setFileName(MimeUtility.encodeText(dataSource.getName()));
                    mp.addBodyPart(part);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*设置发信人*/
    public boolean setFrom(String from) {
        try {
            String nick = "";
            nick = MimeUtility.encodeText("【币港湾服务中心】");
            mimeMsg.setFrom(new InternetAddress(nick + " <" + from + ">")); //发信人
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*定义收信人*/
    public boolean setTo(List<String> toReceivers) {
        if (toReceivers == null)
            return false;
        try {
            // 收件人(多个)
            InternetAddress[] sendTo = new InternetAddress[toReceivers.size()];
            for (int i = 0; i < toReceivers.size(); i++) {
                sendTo[i] = new InternetAddress(toReceivers.get(i));
            }
            mimeMsg.setRecipients(Message.RecipientType.TO, sendTo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*定义抄送人*/
    public boolean setCopyTo(List<String> copytos) {
        try {
            // 抄送人(多个)
            InternetAddress[] copyTo = new InternetAddress[copytos.size()];
            for (int i = 0; i < copytos.size(); i++) {
                copyTo[i] = new InternetAddress(copytos.get(i));
            }
            mimeMsg.setRecipients(Message.RecipientType.CC, copyTo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /*发送邮件模块*/
    public boolean sendOut() {
        Transport transport = null;
        try {
            mimeMsg.setContent(mp);
            mimeMsg.saveChanges();
            System.out.println("邮件发送");
            Session mailSession = Session.getInstance(props, null);
            transport = mailSession.getTransport("smtp");
            transport.connect((String) props.get("mail.smtp.host"), username, password);
            transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
            System.out.println("发送成功！");
            return true;
        } catch (Exception e) {
            System.err.println("发送邮件失败！" + e);
            return false;
        } finally {
            if (transport != null) {
                try {
                    transport.close();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 给目标邮件发送验证码，并返回随机生成的验证码
     * @param toEmail 收件人地址
     * @return 生成的验证码，如果失败，返回null
     */
    public static String sendToEmail(String toEmail) {
        //生成验证码
        Random random = new Random();
        StringBuffer sRand = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
        }

        MailUtil theMail = new MailUtil(MailUtil.EMAIL_SMTP_ADDRESS);
        theMail.setNeedAuth(true); // 验证
        if (!theMail.setSubject(MailUtil.EMAIL_SUBJECT))
            return null;
        MailUtil.EMAIL_CONTENT=new StringBuffer("这是您的验证码：<br /><u style='color:blue'>");
        MailUtil.EMAIL_CONTENT.append(sRand);
        MailUtil.EMAIL_CONTENT.append("</u><br />这个验证码和链接会在10分钟内过期。");
        MailUtil.EMAIL_CONTENT.append("<br />请使用这封邮件中的验证码来验证您在币港湾的邮箱。 请将这个验证码输入在 “绑定邮箱”页面上的对话栏中。");
        MailUtil.EMAIL_CONTENT.append("如果这不是您的操作，请不要将验证码告诉任何人。");
        if (!theMail.setBody(MailUtil.EMAIL_CONTENT.toString()))
            return null;
        if (!theMail.setTo(Arrays.asList(toEmail)))
            return null;
//		if (!theMail.setCopyTo(copyto))
//			return false;
        if (!theMail.setFrom(MailUtil.EMAIL_FROM))
            return null;
        theMail.setNamePass(MailUtil.EMAIL_USERNAME, MailUtil.EMAIL_PASSWORD);
        if (!theMail.sendOut())
            return null;
        return sRand.toString();
    }

    /**
     * 供外界调用的发送邮件接口
     *
     * @param title
     * @param htmlContent
     * @param receivers
     * @return
     */
    public static boolean sendEmail(String title, String htmlContent, String... receivers) {
        return doSendHtmlEmail(null, title, Arrays.asList(receivers), null, htmlContent, null);
    }

    /**
     * 供外界调用的发送邮件接口
     *
     * @param title
     * @param htmlContent
     * @param fileList
     * @param receivers
     * @return
     */
    public static boolean sendEmail(String title, String htmlContent, List<String> fileList, String... receivers) {
        return doSendHtmlEmail(null, title, Arrays.asList(receivers), null, htmlContent, fileList);
    }

    /**
     * 发送邮件封装实现
     *
     * @param mailUtil
     * @param title
     * @param receivers
     * @param copytos
     * @param htmlContent
     * @param fileList
     * @return
     */
    private static boolean doSendHtmlEmail(MailUtil mailUtil, String title, List<String> receivers, List<String> copytos, String htmlContent, List<String> fileList) {
        MailUtil theMail = mailUtil;
        if (theMail == null) {
            theMail = new MailUtil(MailUtil.EMAIL_SMTP_ADDRESS_QQ);
            if (!theMail.setFrom(MailUtil.EMAIL_FROM_YUNYING)) {
                return false;
            }
            theMail.setNamePass(MailUtil.EMAIL_USERNAME_YUNYING, MailUtil.EMAIL_PASSWORD_YUNYING);
        }
        theMail.setNeedAuth(true); // 验证
        if (!theMail.setSubject(title)) {
            return false;
        }
        if (!theMail.setBody(htmlContent, fileList)) {
            return false;
        }
        if (!theMail.setTo(receivers)) {
            return false;
        }
        if (!CollectionUtils.isEmpty(copytos)) {
            if (!theMail.setCopyTo(copytos)) {
                return false;
            }
        }
        if (!theMail.sendOut()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {

    	// 邮件主题
		int month = DateUtil.getPreMonth();
        String title = month+"月份用户信息报表";

        // 邮件正文
        String htmlContent = "Dear all:\r\n 上月用户数据报表已统计完毕,详情请查看附件。";
        
        // 收件人
        String [] receivers = new String[]{"shenguoping@dafy.com", "1220163545@qq.com"};
        
        StringBuffer header = new StringBuffer();
        header.append("用户ID").append(",");
        header.append("注册时间").append(",");
        header.append("地理位置").append(",");
        header.append("应用程序").append(",");
        List<String> content = new ArrayList<>();
        content.add(header.toString());
        /*if (!CollectionUtils.isEmpty(balanceList)) {
        	for (BsSysBalanceDailyFileVO vo: balanceList) {
                StringBuffer contentBuffer = new StringBuffer();
                contentBuffer.append(vo.getAccountType()).append(",");
                contentBuffer.append(vo.getCode()).append(",");
                contentBuffer.append(vo.getBalance()).append(",");
                contentBuffer.append(vo.getAvailableBalance()).append(",");
                content.add(contentBuffer.toString());
            }
        	ExportUtil.exportLocalCSV(GlobEnvUtil.get("register.user.file.path") + File.separator + "monthSnap", content, title + ".csv");
        }
*/        
        
        StringBuffer contentBuffer = new StringBuffer();
        contentBuffer.append("12345").append(",");
        contentBuffer.append("2018-07-02").append(",");
        contentBuffer.append("中国杭州").append(",");
        contentBuffer.append("抖音").append(",");
        content.add(contentBuffer.toString());
    	ExportUtil.exportLocalCSV(GlobEnvUtil.get("register.user.file.path") + File.separator + "monthSnap", content, title + ".csv");

        // 附件
        String fileName = GlobEnvUtil.get("register.user.file.path") + File.separator + "monthSnap" + File.separator + title + ".csv";
        List<String> fileList = new ArrayList<>();
        fileList.add(fileName);
        
		MailUtil.sendEmail(title, htmlContent, fileList, receivers);

      /*  //生成验证码
        Random random = new Random();
        StringBuffer sRand = new StringBuffer();
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand.append(rand);
        }
        StringBuilder content = new StringBuilder("这是您的验证码：<br /><u style='color:blue'>");
        content.append(sRand);
        content.append("</u><br />这个验证码和链接会在10分钟内过期。");
        content.append("<br />请使用这封邮件中的验证码来验证您在币港湾的邮箱。 请将这个验证码输入在 “绑定邮箱”页面上的对话栏中。");
        content.append("如果这不是您的操作，请不要将验证码告诉任何人。");

        MailUtil.sendEmail("币港湾邮箱验证", content.toString() , "zousheng@dafy.com");*/
    }
}