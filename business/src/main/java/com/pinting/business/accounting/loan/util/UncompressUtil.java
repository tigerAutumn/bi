package com.pinting.business.accounting.loan.util;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by 剑钊
 * 文件解压缩工具类
 * @2016/9/14 15:29.
 */
public class UncompressUtil {

    /**
     * 解压缩
     * @param sZipPathFile 要解压的文件
     * @param sDestPath 解压到某文件夹
     * @return
     */
    @SuppressWarnings("unchecked")
    public static ArrayList uncompress(String sZipPathFile, String sDestPath,String type,String merchantNo) {
        return uncompress(sZipPathFile, sDestPath, type, new Date(),merchantNo);
    }


    public static ArrayList uncompress(String sZipPathFile, String sDestPath, String type, Date time,String merchantNo) {
        ArrayList<String> allFileName = new ArrayList<String>();
        try {
            // 先指定压缩档的位置和档名，建立FileInputStream对象
            FileInputStream fins = new FileInputStream(sZipPathFile);
            // 将fins传入ZipInputStream中
            ZipInputStream zins = new ZipInputStream(fins);
            ZipEntry ze = null;
            byte[] ch = new byte[256];
            while ((ze = zins.getNextEntry()) != null) {
            	File zfile = null;
            	if (StringUtil.isBlank(merchantNo)) {
            		zfile = new File(sDestPath + DateUtil.formatYYYYMMDD(time)+"_"+type+".txt");
				}else {
					zfile = new File(sDestPath + DateUtil.formatYYYYMMDD(time)+"_"+type+"_"+merchantNo+".txt");
				}
                
                File fpath = new File(zfile.getParentFile().getPath());
                if (ze.isDirectory()) {
                    if (!zfile.exists())
                        zfile.mkdirs();
                    zins.closeEntry();
                } else {
                    if (!fpath.exists())
                        fpath.mkdirs();
                    FileOutputStream fouts = new FileOutputStream(zfile);
                    int i;
                    allFileName.add(zfile.getAbsolutePath());
                    while ((i = zins.read(ch)) != -1)
                        fouts.write(ch, 0, i);
                    zins.closeEntry();
                    fouts.close();
                }
            }
            fins.close();
            zins.close();
        } catch (Exception e) {
            System.err.println("Extract error:" + e.getMessage());
        }
        return allFileName;
    }
}
