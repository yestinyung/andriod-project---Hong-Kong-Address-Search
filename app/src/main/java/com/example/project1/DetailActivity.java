package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {
    String location;
    String type;
    String name;
    String nameOther;
    String address;
    String addressOther;
    String brief;
    TextView textView_nameOther,textView_name,textView_addressOther,textView_address,textView_brief,ActionBar_TextView;
    ImageButton imageButton_nameOther,imageButton_name,imageButton_addressOther,imageButton_address,imageButton_baack;
    Button location_Button,how_to_go_Button;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        textView_nameOther=(TextView) findViewById(R.id.textView_nameOther);
        textView_name=(TextView) findViewById(R.id.textView_name);
        textView_addressOther=(TextView) findViewById(R.id.textView_addressOther);
        textView_address=(TextView) findViewById(R.id.textView_address);
        textView_brief=(TextView) findViewById(R.id.textView_brief);
        ActionBar_TextView=(TextView) findViewById(R.id.ActionBar_TextView);
        imageButton_nameOther=(ImageButton) findViewById(R.id.imageButton_nameOther);
        imageButton_name=(ImageButton) findViewById(R.id.imageButton_name);
        imageButton_addressOther=(ImageButton) findViewById(R.id.imageButton_addressOther);
        imageButton_address=(ImageButton) findViewById(R.id.imageButton_address);
        imageButton_baack=(ImageButton) findViewById(R.id.imageButton_baack);
        location_Button=(Button) findViewById(R.id.location);
        how_to_go_Button=(Button) findViewById(R.id.how_to_go);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            location=mBundle.getString("location");
            type=mBundle.getString("type");
            name=mBundle.getString("name");
            nameOther=mBundle.getString("nameOther");
            address=mBundle.getString("address");
            addressOther=mBundle.getString("addressOther");
            brief=mBundle.getString("brief");
            textView_nameOther.setText(name);
            textView_name.setText(nameOther);
            textView_addressOther.setText(address);
            textView_address.setText(addressOther);
            textView_brief.setText("\n"+brief+"\n");
            ActionBar_TextView.setText("       "+name);
        }
        imageButton_nameOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", nameOther);
                cm.setPrimaryClip(mClipData);
                Toast.makeText(DetailActivity.this, "複製成功", Toast.LENGTH_SHORT).show();
            }
        });
        imageButton_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", name);
                cm.setPrimaryClip(mClipData);
                Toast.makeText(DetailActivity.this, "複製成功", Toast.LENGTH_SHORT).show();
            }
        });
        imageButton_addressOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", addressOther);
                cm.setPrimaryClip(mClipData);
                Toast.makeText(DetailActivity.this, "複製成功", Toast.LENGTH_SHORT).show();
            }
        });
        imageButton_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData mClipData = ClipData.newPlainText("Label", address);
                cm.setPrimaryClip(mClipData);
                Toast.makeText(DetailActivity.this, "複製成功", Toast.LENGTH_SHORT).show();
            }
        });
        imageButton_baack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        location_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eng = location;
                String[] array = eng.split("\"lat\":");
                String eng1 = array[1];
                String[] array1 = eng1.split(",\"lng\":");
                Intent mIntent = new Intent(DetailActivity.this, MapsActivity.class);
                mIntent.putExtra("lng", array1[0]);
                mIntent.putExtra("lat",  array1[1].substring(0,array1[1].length()-1));
                mIntent.putExtra("nameOther", nameOther);
                startActivity(mIntent);
            }
        });
        how_to_go_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
