package com.pinting.cfcatransfer.bigangwan.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CAFileUtil {

    public static byte[] getBytesFromFile(File f) throws Exception {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(f);
            byte[] b = new byte[fileInputStream.available()];
            fileInputStream.read(b);
            return b;
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
            }
        }

    }
    
    public static void wirteDataToFile(String savePath,byte[] data) throws Exception{
    	File saveDirectory = new File(savePath.substring(0, savePath.lastIndexOf("/")));
    	if(!saveDirectory.exists()){
    		saveDirectory.mkdirs();
    	}
    	FileOutputStream fileOutputStream=null;
        try {
            fileOutputStream = new FileOutputStream(savePath);
            fileOutputStream.write(data);
            fileOutputStream.flush();
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
            }
        }
    }
    
}
