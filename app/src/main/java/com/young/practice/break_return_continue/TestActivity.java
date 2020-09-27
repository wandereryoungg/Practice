package com.young.practice.break_return_continue;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.young.practice.R;

public class TestActivity extends AppCompatActivity {

    private static String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        //break结束break所在循环
        //continue结束本轮循环，继续下次循环
        //return结束return所在方法

        outMethod();
        Log.e(TAG, "方法执行完毕");
    }

    private void outMethod(){
        for (int i = 0; i < 10; i++) {
            Log.e(TAG, "i= " + i);
            for (int j = 0; j < 10; j++) {
                Log.e(TAG, "j= " + j);
                if (j == 5) {
                    break;
                }
                Log.e(TAG, "---------关键字-----------");
            }
        }
    }

    private void method() {

    }
}