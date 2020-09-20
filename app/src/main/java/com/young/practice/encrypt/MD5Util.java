package com.young.practice.encrypt;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具
 *
 * @author Yipd
 * @date 2016-10-18 下午2:41:26
 */
public class MD5Util {

    public static final int BUFFER_SIZE = 4096;

    private static final String MD5_ALGORITHM_NAME = "MD5";

    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 对一段String生成MD5加密信息
     *
     * @param message 要加密的String
     * @return 生成的MD5信息
     */
    public static String getMD5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5_ALGORITHM_NAME);
            byte[] b = md.digest(message.getBytes());
            return byteToHexString(b);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把byte[]数组转换成十六进制字符串表示形式
     *
     * @param tmp 要转换的byte[]
     * @return 十六进制字符串表示形式
     */
    private static String byteToHexString(byte[] tmp) {
        String s;
        // 用字节表示就是 16 个字节
        char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
        // 所以表示成 16 进制需要 32 个字符
        int k = 0; // 表示转换结果中对应的字符位置
        for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
            // 转换成 16 进制字符的转换
            byte byte0 = tmp[i]; // 取第 i 个字节
            str[k++] = HEX_CHARS[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
            // >>> 为逻辑右移，将符号位一起右移
            str[k++] = HEX_CHARS[byte0 & 0xf]; // 取字节中低 4 位的数字转换
        }
        s = new String(str); // 换后的结果转换为字符串
        return s;
    }

    public static String stringMD5(String input) {
        try {
            // 拿到一个MD5转换器(如果想要SHA1参数换成”SHA1”)
            MessageDigest messageDigest = MessageDigest.getInstance(MD5_ALGORITHM_NAME);
            // 输入的字符串转换成字节数组
            byte[] inputByteArray = input.getBytes();
            // inputByteArray是输入字符串转换得到的字节数组
            messageDigest.update(inputByteArray);
            // 转换并返回结果，也是字节数组，包含16个元素
            byte[] digest = messageDigest.digest();
            // 字符数组转换成字符串返回
            return new String(encodeHex(digest));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }

    }

    public static char[] encodeHex(byte[] bytes) {

        /*// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[bytes.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : bytes) {
            resultCharArray[index++] = HEX_CHARS[b >>> 4 & 0xf];
            resultCharArray[index++] = HEX_CHARS[b & 0xf];

        }*/

        char chars[] = new char[32];
        for (int i = 0; i < chars.length; i = i + 2) {
            byte b = bytes[i / 2];
            chars[i] = HEX_CHARS[(b >>> 0x4) & 0xf];
            chars[i + 1] = HEX_CHARS[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return chars;
    }

    public static String fileMD5(String inputFile) throws IOException {

        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;

        try {
            // 拿到一个MD5转换器（同样，这里可以换成SHA1）
            MessageDigest messageDigest = MessageDigest.getInstance(MD5_ALGORITHM_NAME);

            // 使用DigestInputStream
            fileInputStream = new FileInputStream(inputFile);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);

            // read的过程中进行MD5处理，直到读完文件
            final byte[] buffer = new byte[BUFFER_SIZE];
            while (digestInputStream.read(buffer) > 0) ;
            // 获取最终的MessageDigest
            messageDigest = digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素
            byte[] digest = messageDigest.digest();
            // 同样，把字节数组转换成字符串
            return new String(encodeHex(digest));

        } catch (NoSuchAlgorithmException e) {
            return null;
        } finally {
            try {
                digestInputStream.close();
            } catch (Exception e) {
            } finally {
                try {
                    fileInputStream.close();
                } catch (Exception e) {
                }
            }

        }

    }
    
   /* public static void main(String[] args) {
        
        long startTime = System.currentTimeMillis();  
        
        try {  
          System.out.println(fileMD5("E:/Program Files/eclipse/workspace1/carassistant/carassistant.zip"));  
        } catch (IOException e) {  
          e.printStackTrace();  
        }  
        
        long endTime = System.currentTimeMillis();  
        
        System.out.println((endTime - startTime)/1000);  
        
      }  */
}
