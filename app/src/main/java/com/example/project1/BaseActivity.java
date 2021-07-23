package com.example.project1;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.hjq.language.LanguagesManager;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        // 国际化适配（绑定语种）
        super.attachBaseContext(LanguagesManager.attach(newBase));
    }
}