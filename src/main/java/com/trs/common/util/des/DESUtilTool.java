package com.trs.common.util.des;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SFTP 加密解密程序
 * @author 邹许红
 * @time   2015-09-15
 */
public class DESUtilTool {
	private static final Logger LOGGER = LoggerFactory.getLogger(DESUtilTool.class);

	 public  String passwd = "trsadmin";          //加密解密密码
	final public static   String PASSWD_TYPE="DES";//加密方式
	 public  DESUtilTool(){
		 
	 }
	 public  DESUtilTool(String passwd){
		 this.passwd=passwd;
	 }
	

	 /**
	  * @Comments ：对文件进行加密或解密
	  * @param sourcePath
	  *           要加密或解密的文件路径
	  * @param savePath
	  *           要保存的加密或解密文件路径           
	  * @param fileName
	  *            文件
	  * @param mode
	  *            加密模式 加密：Cipher.ENCRYPT_MODE 解密：Cipher.DECRYPT_MODE
	  * @return
	  */
	 public  String encoderOrdecoder(String sourcePath, String savePath,String fileName,
	   int mode) {
		 LOGGER.info("源文件地址："+sourcePath);
		 LOGGER.info("处理后文件地址："+savePath);
		 LOGGER.info("处理文件名称："+fileName);
	  InputStream is = null;
	  OutputStream out = null;
	  CipherInputStream cis = null;

	  try {
	   SecureRandom sr = new SecureRandom();
	   DESKeySpec dks = new DESKeySpec(passwd.getBytes());
	   SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PASSWD_TYPE);
	   SecretKey securekey = keyFactory.generateSecret(dks);
	   IvParameterSpec iv = new IvParameterSpec(passwd.getBytes());
	   Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	   cipher.init(mode, securekey, iv, sr);

	   File encoderFile = new File(savePath);
	   if (!encoderFile.exists()) {
	    encoderFile.mkdir();
	   }

	   is = new FileInputStream(sourcePath + File.separator + fileName);
	   out = new FileOutputStream(savePath+ File.separator + fileName);

	   cis = new CipherInputStream(is, cipher);
	   byte[] buffer = new byte[1024];
	   int r;
	   while ((r = cis.read(buffer)) > 0) {
	    out.write(buffer, 0, r);
	   }

	  } catch (Exception e) {
	   e.printStackTrace();
	  } finally {
	   try {
	    if (is != null) {
	     is.close();
	    }
	    if (cis != null) {
	     cis.close();
	    }
	    if (out != null) {
	     out.close();
	    }
	   } catch (Exception e1) {
            e1.printStackTrace();
	   }
	  }
	  return savePath + File.separator
	    + fileName;
	 }
	
	 /**
	  * @Comments ：对字符串进行加密
	  * @param src
	  *            源字符串
	  * @param mode
	  *            加密模式 加密：Cipher.ENCRYPT_MODE 解密：Cipher.DECRYPT_MODE
	  * @return
	  */
	 public  String encoderOrdecoder(String src, int mode) {
	  String tag = "";
	  InputStream is = null;
	  OutputStream out = null;
	  CipherInputStream cis = null;

	  try {
	   SecureRandom sr = new SecureRandom();
	   DESKeySpec dks = new DESKeySpec(passwd.getBytes());
	   SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PASSWD_TYPE);
	   SecretKey securekey = keyFactory.generateSecret(dks);
	   IvParameterSpec iv = new IvParameterSpec(passwd.getBytes());
	   Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
	   cipher.init(mode, securekey, iv, sr);
	   cis = new CipherInputStream(
	     new ByteArrayInputStream(src.getBytes()), cipher);
	   out = new ByteArrayOutputStream();
	   byte[] buffer = new byte[1024];
	   int r;
	   while ((r = cis.read(buffer)) > 0) {
	    out.write(buffer, 0, r);
	   }
	   tag = out.toString();
	  } catch (Exception e) {
	   e.printStackTrace();
	  } finally {
	   try {
	    if (is != null) {
	     is.close();
	    }
	    if (cis != null) {
	     cis.close();
	    }
	    if (out != null) {
	     out.close();
	    }
	   } catch (Exception e1) {
        e1.printStackTrace();
	   }
	  }
	  return tag;
	 }
	

}
