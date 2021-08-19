package hk.edu.ouhk.android.assignment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class about extends AppCompatActivity {
    private ListView listView, listView2;
    private ListAdapter listAdapter1, listAdapter2;
    private ImageButton ImageButton_Area, ImageButton_Street, imageButton_back;
    boolean Area = false;
    boolean Street = false;
    private TextView TextView, TextView1;
    Button location;
    String district, summary;
    String[] Areas;
    String[] Streets;
    String lat;
    String lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
            Areas = mBundle.getStringArray("Areas");
            Streets = mBundle.getStringArray("Streets");
            district = mBundle.getString("district");
            summary = mBundle.getString("summary");
            lat = String.valueOf(mBundle.getDouble("lat"));
            lng = String.valueOf(mBundle.getDouble("lng"));
        }
        listView = (ListView) findViewById(R.id.list);
        listView2 = (ListView) findViewById(R.id.list2);
        ImageButton_Area = (ImageButton) findViewById(R.id.ImageButton_Area);
        ImageButton_Street = (ImageButton) findViewById(R.id.image_B);
        imageButton_back = (ImageButton) findViewById(R.id.imageButton_back);
        TextView = (android.widget.TextView) findViewById(R.id.TextView);
        TextView1 = (android.widget.TextView) findViewById(R.id.TextView1);
        location = (Button) findViewById(R.id.location);
        TextView.setText(district);
        TextView1.setText(summary);
        listAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Streets);
        listAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Areas);
        listView.setAdapter(listAdapter2);
        listView2.setAdapter(listAdapter1);
        listView.setVisibility(View.GONE);
        listView2.setVisibility(View.GONE);
        setListViewHeightBasedOnChildren(listView);
        setListViewHeightBasedOnChildren(listView2);
        imageButton_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ImageButton_Area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Area) {
                    Area = true;
                    ImageButton_Area.setImageResource(R.drawable.close);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    ImageButton_Area.setImageResource(R.drawable.more);
                    Area = false;
                    listView.setVisibility(View.GONE);
                }
            }
        });
        ImageButton_Street.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Street) {
                    Street = true;
                    ImageButton_Street.setImageResource(R.drawable.close);
                    listView2.setVisibility(View.VISIBLE);
                } else {
                    ImageButton_Street.setImageResource(R.drawable.more);
                    Street = false;
                    listView2.setVisibility(View.GONE);
                }
            }
        });
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(about.this, Maps.class);
                mIntent.putExtra("lng", lng);
                mIntent.putExtra("lat", lat);
                mIntent.putExtra("nameOther", "Your location");
                startActivity(mIntent);
            }
        });
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;

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
