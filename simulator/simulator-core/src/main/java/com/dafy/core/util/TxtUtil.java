package com.dafy.core.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Txt文本处理工具类
 * @author longmao
 */
public class TxtUtil {
	private static final Logger log = LoggerFactory.getLogger(TxtUtil.class);
	
    public static String readTxtFile(String filePath){
        try {
                String encoding="GBK";
                File file=new File(filePath);
                if(file.isFile() && file.exists()){ //判断文件是否存在
                    InputStreamReader read = new InputStreamReader(
                    new FileInputStream(file),encoding);//考虑到编码格式
                    BufferedReader bufferedReader = new BufferedReader(read);
                    String lineTxt = null;
                    String text = "";
                    while((lineTxt = bufferedReader.readLine()) != null){
                    	text = text + lineTxt;
                    }
                    read.close();
                    return text;
        }else{
        	log.info("找不到指定的文件");
        }
        } catch (Exception e) {
        	log.info("读取文件内容出错");
            e.printStackTrace();
        }
        return null;
    }
}
