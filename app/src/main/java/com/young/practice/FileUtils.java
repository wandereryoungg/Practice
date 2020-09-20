package com.young.practice;

import android.os.Environment;
import android.util.Log;

import androidx.core.os.EnvironmentCompat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtils {

    private static String TAG = "FileUtils";

    public static void saveFile(String msg) {
        try {
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/young";
            File file = new File(path);
            if (file.exists()) {
                boolean deleted = file.delete();
                Log.e(TAG, "deleted: " + deleted);
            }
            boolean mkdirs = file.mkdirs();
            Log.e(TAG, "mkdirs: " + mkdirs);
            FileOutputStream out = new FileOutputStream(file + "/log");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(msg);
            writer.flush();
            writer.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile() {
        try {
            StringBuilder builder = new StringBuilder();
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/young/log";
            File file = new File(path);
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            reader.close();
            in.close();
            return builder.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean deleteFile() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/young/log";
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

}
