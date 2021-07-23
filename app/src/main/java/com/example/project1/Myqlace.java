package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Myqlace extends AppCompatActivity {
    private ListView listView,listView2;
    private ListAdapter listAdapter1, listAdapter2;
    private ImageButton ImageButton_A,ImageButton_B,imageButton_baack;
    boolean ButtonA=false;
    boolean ButtonB=false;
    private TextView TextView,TextView1;
    String district, summary;
    String[] name1;
    String[] name2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myqlace);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            name1 = mBundle.getStringArray("name1");
            name2 = mBundle.getStringArray("name2");
            district = mBundle.getString("district");
            summary = mBundle.getString("summary");
        }
        listView = (ListView) findViewById(R.id.list);
        listView2 = (ListView) findViewById(R.id.list2);
        ImageButton_A = (ImageButton) findViewById(R.id.image_A);
        ImageButton_B = (ImageButton) findViewById(R.id.image_B);
        imageButton_baack=(ImageButton) findViewById(R.id.imageButton_baack);
        TextView = (TextView) findViewById(R.id.TextView);
        TextView1 = (TextView) findViewById(R.id.TextView1);
        TextView.setText(district);
        TextView1.setText(summary);
        listAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name1);
        listAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, name2);
        listView.setAdapter(listAdapter2);
        listView2.setAdapter(listAdapter1);
        listView.setVisibility( View.GONE );
        listView2.setVisibility( View.GONE );
        setListViewHeightBasedOnChildren(listView);
        setListViewHeightBasedOnChildren(listView2);
        imageButton_baack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageButton_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ButtonA) {
                    ButtonA=true;
                    ImageButton_A.setImageResource(R.drawable.aaaa);
                    listView.setVisibility( View.VISIBLE );
                }else {
                    ImageButton_A.setImageResource(R.drawable.eeee);
                    ButtonA=false;
                    listView.setVisibility( View.GONE );
                }
            }
        });
        ImageButton_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ButtonB) {
                    ButtonB=true;
                    ImageButton_B.setImageResource(R.drawable.aaaa);
                    listView2.setVisibility( View.VISIBLE );
                }else {
                    ImageButton_B.setImageResource(R.drawable.eeee);
                    ButtonB=false;
                    listView2.setVisibility( View.GONE );
                }
            }
        });
    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if(listView == null) return;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
