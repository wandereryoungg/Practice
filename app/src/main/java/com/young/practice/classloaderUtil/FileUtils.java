package com.young.practice.classloaderUtil;

import android.os.Environment;
import android.os.SystemClock;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * author ：zhouzy
 * createTime : 2020-06-23 14:19:57
 * what day : 星期二
 */
public class FileUtils {


    public static String readFormFile(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static String getTempDir() {
        String dir = getAppDir() + "/Temp/";
        return mkdirs(dir);
    }

    public static String getAppDir() {
        return Environment.getExternalStorageDirectory() + "/CarAdClick";
    }

    public static String mkdirs(String dir) {
        File file = new File(dir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return dir;
    }

    public static String getShellDir() {
        String dir = getAppDir() + "/";
        return mkdirs(dir);
    }

    public static void saveToFile(String path, String content) {
        try {
            File deviceFile = new File(path);
            SystemClock.sleep(500);
            if (deviceFile == null || !deviceFile.exists()) {
                deviceFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(deviceFile, false));
            writer.write(content);
            writer.flush();
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveStrToFile(String path, String content) {
        try {
            File deviceFile = new File(path);
            if (deviceFile.exists()){
                deviceFile.delete();
            }
            SystemClock.sleep(500);
            if (deviceFile == null || !deviceFile.exists()) {
                deviceFile.createNewFile();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter(deviceFile, false));
            writer.write(content);
            writer.flush();
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readStrFromFile(String path) {
        try {
            File deviceFile = new File(path);
            if (deviceFile == null || !deviceFile.exists()) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new FileReader(deviceFile));
            StringBuilder stringBuilder = new StringBuilder();
            String s;
            while ((s = reader.readLine()) != null) {
                stringBuilder.append(s);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件夹下的文件
     *
     * @param file
     */
    public static void RecursionDeleteFile(File file) {
        try {
            if (file.isFile()) {
                file.delete();
                return;
            }
            if (file.isDirectory()) {
                if (file.getAbsolutePath().contains("AdFileDownLoad") || file.getAbsolutePath().contains("AdLogs")) {
                    return;
                }
                File[] childFile = file.listFiles();
                if (childFile == null || childFile.length == 0) {
                    file.delete();
                    return;
                }
                for (File f : childFile) {
                    RecursionDeleteFile(f);
                }
                file.delete();
            }
        } catch (Exception e) {
            Log.e("xiaojun", "文件删除失败" + e.getMessage());
        }
    }

    /**
     * 删除文件夹下的文件解决open failed: EBUSY (Device or resource busy)
     *
     * @param file
     */
    public static void RecursionDeleteFileReName(File file) {
        if (file.isFile()) {
            File toFile = new File(file.getAbsolutePath() + System.currentTimeMillis());
            String fileName = file.getAbsolutePath();
            file.renameTo(toFile);
            boolean isDelete = toFile.delete();
            Log.e("FileUtils", "删除文件 " + fileName + ": " + isDelete);
            return;
        }
        if (file.isDirectory()) {
            if (file.getAbsolutePath().contains("AdFileDownLoad") || file.getAbsolutePath().contains("AdLogs")) {
                return;
            }
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                if (file.getAbsolutePath().contains("Android")) {
                    return;
                }
                File toDir = new File(file.getAbsolutePath() + System.currentTimeMillis());
                String fileName = file.getAbsolutePath();
                file.renameTo(toDir);
                boolean isDelete = toDir.delete();
                Log.e("FileUtils", "删除空文件夹 " + fileName + ": " + isDelete);
                return;
            }
            for (File f : childFile) {
                RecursionDeleteFileReName(f);
            }
            if (file.getAbsolutePath().contains("Android")) {
                return;
            }
            File toDir = new File(file.getAbsolutePath() + System.currentTimeMillis());
            String fileName = file.getAbsolutePath();
            file.renameTo(toDir);
            boolean isDelete = toDir.delete();
            Log.e("FileUtils", "删除文件夹 " + fileName + ": " + isDelete);
        }
    }

    /**
     * 删除文件夹下的文件解决open failed: EBUSY (Device or resource busy)
     *
     * @param file
     */
    public static void RecursionDeleteAllFileReName(File file) {
        if (file.isFile()) {
            File toFile = new File(file.getAbsolutePath() + System.currentTimeMillis());
            String fileName = file.getAbsolutePath();
            file.renameTo(toFile);
            boolean isDelete = toFile.delete();
            Log.e("FileUtils", "删除文件 " + fileName + ": " + isDelete);
            return;
        }
        if (file.isDirectory()) {
            File[] childFile = file.listFiles();
            if (childFile == null || childFile.length == 0) {
                File toDir = new File(file.getAbsolutePath() + System.currentTimeMillis());
                String fileName = file.getAbsolutePath();
                file.renameTo(toDir);
                boolean isDelete = toDir.delete();
                Log.e("FileUtils", "删除空文件夹 " + fileName + ": " + isDelete);
                return;
            }
            for (File f : childFile) {
                RecursionDeleteAllFileReName(f);
            }
            File toDir = new File(file.getAbsolutePath() + System.currentTimeMillis());
            String fileName = file.getAbsolutePath();
            file.renameTo(toDir);
            boolean isDelete = toDir.delete();
            Log.e("FileUtils", "删除文件夹 " + fileName + ": " + isDelete);
        }
    }


    public static boolean logInput2File(String filePath, String input) {
        BufferedWriter bw = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            // 构造给定文件名的FileWriter对象，并使用布尔值指示是否追加写入的数据。
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(file, true), "gbk"));
            writer.write(input);
            writer.flush();
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 删除文件
     *
     * @param path 需要删除的文件或文件夹路径
     * @return 返回删除成功与否
     */
    public static boolean deleteFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 删除文件 多次删除创建可能open failed: EBUSY (Device or resource busy)
     *
     * @param path 需要删除的文件或文件夹路径
     * @return 返回删除成功与否
     */
    public static boolean deleteFileRename(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {

                File toFile = new File(file.getAbsolutePath() + System.currentTimeMillis());
                file.renameTo(toFile);
                toFile.delete();
            }
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 删除文件夹以及目录下的文件
     *
     * @param filePath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        boolean flag = false;
        //如果filePath不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        File[] files = dirFile.listFiles();
        //遍历删除文件夹下的所有文件(包括子目录)
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                //删除子文件
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } else {
                //删除子目录
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前空目录
        return dirFile.delete();
    }

    /**
     * 复制一个文件到另一个文件
     *
     * @param oldPath
     * @param newPath
     */
    public void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {                  //文件存在时
                InputStream inStream = new FileInputStream(oldPath);      //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;            //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();
        }
    }
}
