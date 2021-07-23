package com.example.project1;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hjq.language.LanguagesManager;

import java.util.Locale;

public class setting extends AppCompatActivity implements View.OnClickListener{
    private ImageButton imageButton_baack;
    private RadioButton RadioButton_System, RadioButton_Chinese, RadioButton_English, RadioButton_Big, RadioButton_Mid, RadioButton_Small;
    private Button Button;
    private Bundle s;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        imageButton_baack = (ImageButton) findViewById(R.id.imageButton_baack);
        findViewById(R.id.RadioButton_System).setOnClickListener(this);
        findViewById(R.id.RadioButton_Chinese).setOnClickListener(this);
        findViewById(R.id.RadioButton_English).setOnClickListener(this);
        RadioButton_System = (RadioButton) findViewById(R.id.RadioButton_System);
        RadioButton_Chinese = (RadioButton) findViewById(R.id.RadioButton_Chinese);
        RadioButton_English = (RadioButton) findViewById(R.id.RadioButton_English);
        RadioButton_Big = (RadioButton) findViewById(R.id.RadioButton_Big);
        RadioButton_Mid = (RadioButton) findViewById(R.id.RadioButton_Mid);
        RadioButton_Small = (RadioButton) findViewById(R.id.RadioButton_Small);
        Button = (Button) findViewById(R.id.Button_A);
        String aa = getSharedPreferences("txt", MODE_PRIVATE).getString("lang", " ");
        String bb = getSharedPreferences("txt", MODE_PRIVATE).getString("range", "l");
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (!("" == aa)) {
            switch (aa) {
                case " ":
                    RadioButton_System.setChecked(true);
                    break;
                case "zh":
                    RadioButton_Chinese.setChecked(true);
                    break;
                case "en":
                    RadioButton_English.setChecked(true);
                    break;
            }
            resources.updateConfiguration(configuration, displayMetrics);
        }
        if (!("" == bb)) {
            switch (bb) {
                case "l":
                    RadioButton_Big.setChecked(true);
                    break;
                case "m":
                    RadioButton_Mid.setChecked(true);
                    break;
                case "s":
                    RadioButton_Small.setChecked(true);
                    break;
            }
            resources.updateConfiguration(configuration, displayMetrics);
        }

        imageButton_baack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(setting.this, MainActivity.class);
                startActivity(mIntent);
            }
        });
        RadioButton_Big.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RadioButton_Big.isChecked()) {
                    getSharedPreferences("txt", MODE_PRIVATE).edit().putString("range", "l").apply();
                }
            }
        });
        RadioButton_Mid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RadioButton_Mid.isChecked()) {
                    getSharedPreferences("txt", MODE_PRIVATE).edit().putString("range", "m").apply();
                }
            }
        });
        RadioButton_Small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (RadioButton_Small.isChecked()) {
                    getSharedPreferences("txt", MODE_PRIVATE).edit().putString("range", "s").apply();
                }
            }
        });


    }
    @Override
    public void onClick(View view) {
        boolean restart;
        switch (view.getId()) {
            // 跟随系统
            case R.id.RadioButton_System:
                restart = LanguagesManager.setSystemLanguage(this);
                getSharedPreferences("txt", MODE_PRIVATE).edit().putString("lang", " ").apply();
                break;
            // 繁体中文
            case R.id.RadioButton_Chinese:
                restart = LanguagesManager.setAppLanguage(this, Locale.TAIWAN);
                getSharedPreferences("txt", MODE_PRIVATE).edit().putString("lang", "zh").apply();
                break;
            // 英语
            case R.id.RadioButton_English:
                restart = LanguagesManager.setAppLanguage(this, Locale.ENGLISH);
                getSharedPreferences("txt", MODE_PRIVATE).edit().putString("lang", "en").apply();
                break;
            default:
                restart = false;
                break;
        }

        if (restart) {
            // 1.使用recreate来重启Activity的效果差，有闪屏的缺陷
            //recreate();

            // 2.使用常规startActivity来重启Activity，有从左向右的切换动画，稍微比recreate的效果好一点，但是这种并不是最佳的效果
            //startActivity(new Intent(this, LanguageActivity.class));
            //finish();

            // 3.我们可以充分运用 Activity 跳转动画，在跳转的时候设置一个渐变的效果，相比前面的两种带来的体验是最佳的
            startActivity(new Intent(this, setting.class));
            overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_alpha_out);
            finish();
        }
    }
}
