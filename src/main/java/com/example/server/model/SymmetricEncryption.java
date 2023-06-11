package com.example.server.model;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class SymmetricEncryption {
    //對稱加密
    public static String encryption(String password,String byte_key) throws Exception {
        String plaintext = password;
        String key = byte_key; // 16字節密鑰

        //創建AES加密器
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        //加密
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);
//        System.out.println("加密後的文本: " + encryptedText);
        return encryptedText;

    }

}
