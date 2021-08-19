package hk.edu.ouhk.android.assignment;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

import com.hjq.language.LanguagesManager;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LanguagesManager.attach(newBase));
    }
}