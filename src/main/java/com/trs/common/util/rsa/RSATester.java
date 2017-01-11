package com.trs.common.util.rsa;
import java.util.Map;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
 
public class RSATester {
 
    static String publicKey;
    static String privateKey;
 
    static {
        try {
            Map<String, Object> keyMap = RSAUtils.genKeyPair();
            publicKey = RSAUtils.getPublicKey(keyMap);
            privateKey = RSAUtils.getPrivateKey(keyMap);
           // System.err.println("公钥: \n\r" + publicKey);
            //System.out.println(publicKey.length());
            //System.err.println("私钥： \n\r" + privateKey);
           // System.out.println(privateKey.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    public static void main(String[] args) throws Exception {
      //test();
        testSign();
    }
 
    static void test() throws Exception {
        System.err.println("公钥加密——私钥解密");
        String source = "111111111";
        System.out.println("\r加密前文字：\r\n" + source);
        byte[] data = source.getBytes("UTF-8");
        byte[] encodedData = RSAUtils.encryptByPublicKey(data, publicKey);
        BASE64Encoder encoder = new BASE64Encoder();
		String epStr = encoder.encode(encodedData);
        System.out.println("加密后文字：\r\n" + epStr);
    	BASE64Decoder decoder = new BASE64Decoder();
		byte[] b1 = decoder.decodeBuffer(epStr);
        byte[] decodedData = RSAUtils.decryptByPrivateKey(b1, privateKey);
        String target = new String(decodedData,"UTF-8");
        System.out.println("解密后文字: \r\n" + target);
    }
 
    static void testSign() throws Exception {
        System.err.println("私钥加密——公钥解密");
        String source = "这是一行测试RSA数字签名的无意义文字";
        System.out.println("原文字：\r\n" + source);
        byte[] data = source.getBytes("UTF-8");
        byte[] encodedData = RSAUtils.encryptByPrivateKey(data, privateKey);
        BASE64Encoder encoder = new BASE64Encoder();
		String epStr = encoder.encode(encodedData);
        System.out.println("加密后：\r\n" + epStr);
    	BASE64Decoder decoder = new BASE64Decoder();
        byte[] datas = decoder.decodeBuffer(epStr);
        byte[] decodedData = RSAUtils.decryptByPublicKey(datas, publicKey);
        String target = new String(decodedData, "UTF-8");
        System.out.println("解密后: \r\n" + target);
      //  System.err.println("私钥签名——公钥验证签名");
      //  String sign = RSAUtils.sign(encodedData, privateKey);
      // System.err.println("签名:\r" + sign);
       // boolean status = RSAUtils.verify(encodedData, publicKey, sign);
       // System.err.println("验证结果:\r" + status);
    }
     
}