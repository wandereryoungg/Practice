package com.young.practice.encrypt;

import android.text.TextUtils;
import android.util.Log;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptBase64 {

    private static String TAG = "CryptBase64";
    private static byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8};
    private static final String[] KEYS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x",
            "y", "z", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
            "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    public static String initKeyDes() {
        StringBuffer kstr = new StringBuffer();
        for (int i = 0; i < 8; i++) {
            int d = new Random().nextInt(62);
            kstr.append(KEYS[d]);
        }
        return kstr.toString();
    }

    public static String initKeyAes() {
        StringBuffer kstr = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int d = new Random().nextInt(62);
            kstr.append(KEYS[d]);
        }
        return kstr.toString();
    }

    public static String encryptDES(String encryptString, String encryptKey)
            throws Exception {
        // IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());

        return Base64.encode(encryptedData);
    }

    public static String encryptDES(String encryptString) throws Exception {
        String encryptKey = initKeyDes();
        return encryptDES(encryptString, encryptKey).concat(encryptKey);
    }

    public static String decryptDES(String decryptString, String decryptKey)
            throws Exception {
        byte[] byteMi = Base64.decode(decryptString);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        // IvParameterSpec zeroIv = new IvParameterSpec(new byte[8]);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }

    public static String decryptDES(String src) {
        try {
            String urlKey = src.substring(src.length() - 8, src.length());
            String realContent = src.substring(0, src.length() - 8);
            return decryptDES(realContent, urlKey);
        } catch (Exception e) {
        }
        return null;
    }

    public static String encryptAES(String src) {
        if (TextUtils.isEmpty(src))
            return null;

        try {
            String encryptKey = initKeyAes();
            Log.e(TAG, "encryptKey: " + encryptKey);
            String result = encryptAES(src, encryptKey);
            Log.e(TAG, "Base64.encode(encryptedData): " + result);
            StringBuffer sb = new StringBuffer(encryptKey);
            return sb.append(MD5Util.stringMD5(encryptKey).toUpperCase()).append(result).toString();
        } catch (Exception e) {
//			MyLog.e("Crypt", "---encryptAES Err:" + e.getMessage(), e);
        }
        return null;
    }

    public static String encryptAES(String encryptString, String encryptKey)
            throws Exception {
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < encryptedData.length; i++) {
            builder.append(encryptedData[i]);
        }
        Log.e(TAG, "encryptedData: " + builder.toString());
        return Base64.encode(encryptedData);
    }


    public static String decryptAES(String src) {
        if (TextUtils.isEmpty(src))
            return null;
        try {
            String urlKey = src.substring(0, 16);
            String md5 = src.substring(16, 48);
            if (!MD5Util.stringMD5(urlKey).toUpperCase().equals(md5)) {
                return null;
            }
            String realContent = src.substring(48);
            return decryptAES(realContent, urlKey);
        } catch (Exception e) {
//			MyLog.e("Crypt", "---decryptAES Err:" + e.getMessage(), e);
        }
        return null;
    }

    public static String decryptAES(String decryptString, String decryptKey)
            throws Exception {
        byte[] byteMi = Base64.decode(decryptString);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }

}
