package hk.edu.ouhk.android.assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.LocaleList;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hjq.language.LanguagesManager;

import java.util.Locale;

public class Setting extends AppCompatActivity implements View.OnClickListener{
    private ImageButton imageButton_back;
    private RadioButton RadioButton_System, RadioButton_Chinese, RadioButton_English, RadioButton_Big, RadioButton_Mid, RadioButton_Small;
    private Button Button;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        imageButton_back = (ImageButton) findViewById(R.id.imageButton_back);
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

        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Setting.this, MainActivity.class);
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

        Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL  , new String[]{"admin@admin.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Report problem");
                try {
                    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    finish();
                    Log.i("Finished sending email.", "");
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(Setting.this,
                            "There is no email client installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    @Override
    public void onClick(View view) {
        boolean restart;
        switch (view.getId()) {
            case R.id.RadioButton_System:
                restart = LanguagesManager.setAppLanguage(this, Locale.TAIWAN);
                getSharedPreferences("txt", MODE_PRIVATE).edit().putString("lang", " ").apply();
                break;
            case R.id.RadioButton_Chinese:
                restart = LanguagesManager.setAppLanguage(this, Locale.TAIWAN);
                getSharedPreferences("txt", MODE_PRIVATE).edit().putString("lang", "zh").apply();
                break;
            case R.id.RadioButton_English:
                restart = LanguagesManager.setAppLanguage(this, Locale.ENGLISH);
                getSharedPreferences("txt", MODE_PRIVATE).edit().putString("lang", "en").apply();
                break;
            default:
                restart = false;
                break;
        }

        if (restart) {
            startActivity(new Intent(this, Setting.class));
            overridePendingTransition(R.anim.activity_alpha_in, R.anim.activity_alpha_out);
            finish();
        }
    }
}
