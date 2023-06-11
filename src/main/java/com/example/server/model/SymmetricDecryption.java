package com.example.server.model;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SymmetricDecryption {
    //對稱解密
    public static String decryption(String encryptedText,String byte_key) throws Exception {
        String key = byte_key; // 16字節密鑰
        //創建AES加密器
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        //解密
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        String decryptedText = new String(decryptedBytes);
//        System.out.println("解密後的文本: " + decryptedText);
        return decryptedText;
    }
}
