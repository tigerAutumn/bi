/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: ITextPdfUtil.java, v 0.1 2015-9-17 下午4:16:38 BabyShark Exp $
 */
public class ITextPdfUtil {

    public static String html    = "http://118.186.255.63:10040/user/regProtocolPdf?name=%E4%B8%81%E9%B9%8F%E9%A3%8E";
    public static String dest    = "f:/hero.pdf";
    public static String creator = "泓淳（杭州）科技有限公司";

    /**
     * Create a PDF
     * 
     * @param htm
     * @param dest
     * @param title
     * @param subject
     * @param no 协议号
     * @throws IOException
     * @throws DocumentException
     */
    public static void createHtm2Pdf(String htm, String dest, String title, String subject,
                                     String no) throws IOException, DocumentException {
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
            BaseFont.NOT_EMBEDDED);

        File file = new File(dest);
        file.getParentFile().mkdirs();
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        document.addCreator(creator);
        document.addCreationDate();
        document.addTitle(title);
        document.addSubject(subject);

        Font chineseTitleFont = new Font(bfChinese, 18, Font.BOLD);
        Paragraph protocolTitle = new Paragraph(title, chineseTitleFont);
        protocolTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(protocolTitle);

        Font chineseNoFont = new Font(bfChinese, 8, Font.NORMAL);
        Paragraph protocolNo = new Paragraph("编号：" + no, chineseNoFont);
        protocolNo.setAlignment(Element.ALIGN_CENTER);
        document.add(protocolNo);

        // step 4
        ITextPdfUtil.MyFontsProvider fontProvider = new ITextPdfUtil.MyFontsProvider();
        fontProvider.addFontSubstitute("lowagie", "garamond");
        fontProvider.setUseUnicode(true);
        //使用我们的字体提供器，并将其设置为unicode字体样式
        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
        Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, new HtmlPipeline(htmlContext,
            new PdfWriterPipeline(document, writer)));
        XMLWorker worker = new XMLWorker(pipeline, true);
        XMLParser xmlParser = new XMLParser(worker);
        //获得html源
        if(htm.startsWith("https")){
        	init();
        }
        URL url = new URL(htm);
        InputStream in = url.openStream();
        //开始转换
        //xmlParser.parse(new FileInputStream(new File("f:/aa.html")), Charset.forName("UTF-8"));
        xmlParser.parse(in, Charset.forName("UTF-8"));
        // step 5
        if (in != null) {
            in.close();
        }
        document.close();
    }

    /**
     * Create a PDF
     * 云贷、七贷三方协议
     * @param htm
     * @param dest
     * @param title
     * @param subject
     * @throws IOException
     * @throws DocumentException
     */
    public static void createHtm2PdfYunAndSeven(String htm, String dest, String title, String subject
                                    ) throws IOException, DocumentException {
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
            BaseFont.NOT_EMBEDDED);

        File file = new File(dest);
        file.getParentFile().mkdirs();
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        document.addCreator(creator);
        document.addCreationDate();
        document.addTitle(title);
        document.addSubject(subject);

        Font chineseTitleFont = new Font(bfChinese, 18, Font.BOLD);
        Paragraph protocolTitle = new Paragraph(title, chineseTitleFont);
        protocolTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(protocolTitle);

        // step 4
        ITextPdfUtil.MyFontsProvider fontProvider = new ITextPdfUtil.MyFontsProvider();
        fontProvider.addFontSubstitute("lowagie", "garamond");
        fontProvider.setUseUnicode(true);
        //使用我们的字体提供器，并将其设置为unicode字体样式
        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
        Pipeline<?> pipeline = new CssResolverPipeline(cssResolver, new HtmlPipeline(htmlContext,
            new PdfWriterPipeline(document, writer)));
        XMLWorker worker = new XMLWorker(pipeline, true);
        XMLParser xmlParser = new XMLParser(worker);
        //获得html源
        if(htm.startsWith("https")){
        	init();
        }
        URL url = new URL(htm);
        InputStream in = url.openStream();
        //开始转换
        //xmlParser.parse(new FileInputStream(new File("f:/aa.html")), Charset.forName("UTF-8"));
        xmlParser.parse(in, Charset.forName("UTF-8"));
        // step 5
        if (in != null) {
            in.close();
        }
        document.close();
    }

    
    
    public static class MyFontsProvider extends XMLWorkerFontProvider {
        public MyFontsProvider() {
            super(null, null);
        }

        @Override
        public Font getFont(final String fontname, String encoding, float size, final int style) {

            try {
                BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
                Font chineseFont = new Font(bfChinese, 12, Font.NORMAL);
                return chineseFont;

            } catch (Exception e) {
                e.printStackTrace();
                String fntname = fontname;
                if (fntname == null) {
                    fntname = "宋体";
                }
                return super.getFont(fntname, encoding, size, style);
            }
        }
    }
    
    /** 
     * 初始化 
     */  
    public static void init() {  
        try {  
            SSLContext sslCtx = SSLContext.getInstance("TLS");  
            sslCtx.init(null, new TrustManager[]{new MyTrustManager()}, null);  
            SSLSocketFactory sslSocketFactory = sslCtx.getSocketFactory();  
            HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);  
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {  
				@Override
				public boolean verify(String s, SSLSession sslsession) {
					return true;
				}  
            });  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  

    public static void main(String[] args) throws IOException, DocumentException {
        //createHtm2Pdf(html, dest, "注册服务协议", "注册服务协议", "000001");
        //createHtm2Pdf("http://localhost:8084/site/micro2.0/agreement/borrowingServicesZan4Pdf?loanId=2016091919210001", "D:/aaa/hero.pdf", "借款咨询与服务协议", "借款咨询与服务协议", "000001");
    }
}

/** 
 * 默认信任服务端根证书 
 */  
class MyTrustManager implements X509TrustManager {  
      
    @Override  
    public void checkClientTrusted(X509Certificate[] chain, String authType)  
            throws CertificateException {  
        return;  
    }  
      
    @Override  
    public void checkServerTrusted(X509Certificate[] chain, String authType)  
            throws CertificateException {  
        return;  
    }  
      
    @Override  
    public X509Certificate[] getAcceptedIssuers() {  
        return null;  
    }  
}  
