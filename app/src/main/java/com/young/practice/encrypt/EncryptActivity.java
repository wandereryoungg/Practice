package com.young.practice.encrypt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.young.practice.R;

public class EncryptActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etInput;
    Button btnEncrypt, btnDecrypt;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encrypt);

        etInput = findViewById(R.id.et_input);
        btnEncrypt = findViewById(R.id.btn_encrypt);
        btnDecrypt = findViewById(R.id.btn_decrypt);
        tv = findViewById(R.id.tv);
        btnEncrypt.setOnClickListener(this);
        btnDecrypt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_encrypt:
                String text = etInput.getText().toString();
                //随机encryptKey---encryptAES加密---Base64.encode运算---返回encryptKey+MD5encryptKey+Base64.encode结果
                tv.setText(CryptBase64.encryptAES(text));
                break;
            case R.id.btn_decrypt:
                tv.setText(CryptBase64.decryptAES(String.valueOf(tv.getText())));
                break;
        }
    }
}