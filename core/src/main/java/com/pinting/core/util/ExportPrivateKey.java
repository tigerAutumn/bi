package com.pinting.core.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;

import sun.misc.BASE64Encoder;

/**
 * 
 * @Project: business
 * @Title: ExportPrivateKey.java
 * @author Zhou Changzai
 * @date 2016-1-21下午5:19:24
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class ExportPrivateKey {    
        private File keystoreFile;    
        private String keyStoreType;    
        private char[] password;   
        private char[] keyPassword;
        private String alias;    
        private File exportedFile;           
  
        public static KeyPair getPrivateKey(KeyStore keystore, String alias, char[] password) {    
                try {    
                        Key key=keystore.getKey(alias,password);    
                        if(key instanceof PrivateKey) {    
                                Certificate cert=keystore.getCertificate(alias);    
                                PublicKey publicKey=cert.getPublicKey();    
                                return new KeyPair(publicKey,(PrivateKey)key);    
                        }    
                } catch (UnrecoverableKeyException e) {    
        } catch (NoSuchAlgorithmException e) {    
        } catch (KeyStoreException e) {    
        }    
        return null;    
        }  
  
   
  
        public void export() throws Exception{    
                KeyStore keystore=KeyStore.getInstance(keyStoreType);    
                BASE64Encoder encoder=new BASE64Encoder();    
                keystore.load(new FileInputStream(keystoreFile),password);    
                KeyPair keyPair=getPrivateKey(keystore,alias,keyPassword);    
                PrivateKey privateKey=keyPair.getPrivate();    
                String encoded=encoder.encode(privateKey.getEncoded());    
                FileWriter fw=new FileWriter(exportedFile);    
                fw.write("-----BEGIN PRIVATE KEY-----\n");    
                fw.write(encoded);    
                fw.write("\n");    
                fw.write("-----END PRIVATE KEY-----");  
                fw.close();  
  
        }  
  
   
        public static void main(String args[]) throws Exception{    
                ExportPrivateKey export=new ExportPrivateKey();    
                export.keystoreFile=new File("d:/bigangwan.keystore");    
                export.keyStoreType="JKS";    
                export.password="BigangwanPinting!*&".toCharArray();
                export.keyPassword="BigangwanPinting$*!".toCharArray();
                export.alias="ZJbigangwan";    
                export.exportedFile=new File("d:/bigangwan.key");    
                export.export();    
        }    
}  

