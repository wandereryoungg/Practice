package com.young.practice.classloaderUtil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.young.practice.R;

public class ClassloaderActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classloader);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        tv = findViewById(R.id.tv);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                try {
                    Class clazz = Class.forName("com.android.org.chromium.android_webview.AwSettings");
                    tv.setText(clazz.getName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn2:
                try {
                    Class cls = ObjectUtils.findClass("com.android.org.chromium.android_webview.AwSettings", null);
                    tv.setText(cls.getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}