package com.young.practice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.young.practice.break_return_continue.TestActivity;
import com.young.practice.classloaderUtil.ClassloaderActivity;
import com.young.practice.collection.CollectionActivity;
import com.young.practice.encrypt.EncryptActivity;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSave, btnRead, btnDelete;
    Button btnLogin, btnPhoneNum;
    Button btnList, btnArray, btnJsonArray;
    TextView tvFruits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //读写文件
        btnSave = findViewById(R.id.btn_save);
        btnRead = findViewById(R.id.btn_read);
        btnDelete = findViewById(R.id.btn_delete);
        btnSave.setOnClickListener(this);
        btnRead.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        //Okhttp3保存cookie
        btnLogin = findViewById(R.id.btn_login);
        btnPhoneNum = findViewById(R.id.btn_phone_num);
        btnLogin.setOnClickListener(this);
        btnPhoneNum.setOnClickListener(this);

        //集合数组JSONArray打印值
        btnList = findViewById(R.id.btn_list);
        btnArray = findViewById(R.id.btn_array);
        btnJsonArray = findViewById(R.id.btn_json_array);
        tvFruits = findViewById(R.id.tv_fruits);
        btnList.setOnClickListener(this);
        btnArray.setOnClickListener(this);
        btnJsonArray.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Fruit apple = new Fruit("apple", "7");
        Fruit pear = new Fruit("pear", "4");
        switch (v.getId()) {
            case R.id.btn_save:
                FileUtils.saveFile("hello world");
                break;
            case R.id.btn_read:
                btnRead.setText(FileUtils.readFile());
                break;
            case R.id.btn_delete:
                FileUtils.deleteFile();
                break;
            case R.id.btn_login:
                DaMa.login();
                break;
            case R.id.btn_phone_num:
                DaMa.getPhoneNum();
                break;
            case R.id.btn_list:
                List fruitList = new ArrayList();
                fruitList.add(apple);
                fruitList.add(pear);
                tvFruits.setText(fruitList.toString());
                break;
            case R.id.btn_json_array:
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(apple);
                jsonArray.put(pear);
                tvFruits.setText(jsonArray.toString());
                break;
            case R.id.btn_array:
                Object[] objects = new Object[2];
                objects[0] = apple;
                objects[1] = pear;
                tvFruits.setText(objects.toString());
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.activity_collection:
                Intent intent = new Intent(this, CollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.activity_encrypt:
                startActivity(new Intent(this, EncryptActivity.class));
                break;
            case R.id.activity_classloader:
                startActivity(new Intent(this, ClassloaderActivity.class));
                break;
            case R.id.break_return_contine:
                startActivity(new Intent(this, TestActivity.class));
                break;
        }
        return true;
    }
}