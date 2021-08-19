package hk.edu.ouhk.android.assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private static final int CUSTOM_NUMBER = 1;
    String[] location;
    String[] type;
    String[] name;
    String[] nameOther;
    String[] address;
    String[] addressOther;
    String[] brief;
    String district;
    String summary;
    String[] Areas;
    String[] Streets;
    ListView listview;
    ImageButton imageButton_A, imageButton_B, imageButton_C;
    int All_data, Schools_data, Residence_data, hotels_data,building_data,facility_data;
    String JSON_Data;
    protected LocationManager locationManager;
    boolean first = true;
    double lat, lng;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String aa = getSharedPreferences("txt", MODE_PRIVATE).getString("lang", " ");
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        if (!("" == aa)) {
            switch (aa) {
                case " ":
                case "zh":
                    configuration.setLocale(Locale.SIMPLIFIED_CHINESE);
                    break;
                case "en":
                    configuration.setLocale(Locale.ENGLISH);
                    break;
            }
            resources.updateConfiguration(configuration, displayMetrics);
        }
        listview = (ListView) findViewById(R.id.list);
        imageButton_A = (ImageButton) findViewById(R.id.imageButton_A);
        imageButton_B = (ImageButton) findViewById(R.id.imageButton_B);
        imageButton_C = (ImageButton) findViewById(R.id.imageButton_C);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkFINE_LOCATION();
        getGPS();
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent mIntent = new Intent(MainActivity.this, detail.class);
                mIntent.putExtra("location", location[i]);
                mIntent.putExtra("type", type[i]);
                mIntent.putExtra("name", name[i]);
                mIntent.putExtra("nameOther", nameOther[i]);
                mIntent.putExtra("address", address[i]);
                mIntent.putExtra("addressOther", addressOther[i]);
                mIntent.putExtra("brief", brief[i]);
                startActivity(mIntent);
            }
        });
        imageButton_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOneDialog();
            }
        });
        imageButton_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, Setting.class);
                startActivity(mIntent);
            }
        });
        imageButton_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity.this, about.class);
                mIntent.putExtra("district", district);
                mIntent.putExtra("summary", summary);
                mIntent.putExtra("Areas", Areas);
                mIntent.putExtra("Streets", Streets);
                mIntent.putExtra("lat", lng);
                mIntent.putExtra("lng", lat);
                startActivity(mIntent);
            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            Log.d("XXX", "Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
            lat = location.getLatitude();
            lng = location.getLongitude();
            if (first) {
                new getData().execute();
                first = false;
            }
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class getData extends AsyncTask<String, String, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String aa = getSharedPreferences("txt", MODE_PRIVATE).getString("lang", " ");
                String bb = getSharedPreferences("txt", MODE_PRIVATE).getString("range", "l");
                String lang1;
                if (aa == " "){
                    lang1 = "zh";
                } else {
                    lang1 = aa;
                }
                url = new URL("https://findaway.hk/api/request-20200108.php?lang="+lang1+"&lat="+lat+"&lng="+lng+"&coverage="+bb);
                //url = new URL("https://www.findaway.hk/api/request-20200108.php?lang=zh&lat=22.313315&lng=114.170496&coverage=s");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.flush();
                writer.close();
                os.close();
                conn.connect();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {
                    return ("unsuccessful");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }
        }

        public void onPostExecute(String result) {
            pdLoading.dismiss();
            if (result.equals("false")) {
                return;
            }
            super.onPreExecute();
            try {
                String eng = result;
                String[] array = eng.split("\"OK\",\"candidates\": ");
                JSONArray dataArray = new JSONArray(array[1]);
                JSON_Data = array[1];
                location = new String[dataArray.length()];
                type = new String[dataArray.length()];
                name = new String[dataArray.length()];
                nameOther = new String[dataArray.length()];
                address = new String[dataArray.length()];
                addressOther = new String[dataArray.length()];
                brief = new String[dataArray.length()];
                for (int i = 0; i < dataArray.length(); i++) {
                    location[i] = dataArray.getJSONObject(i).getString("location");
                    type[i] = dataArray.getJSONObject(i).getString("type");
                    name[i] = dataArray.getJSONObject(i).getString("name");
                    nameOther[i] = dataArray.getJSONObject(i).getString("nameOther");
                    address[i] = dataArray.getJSONObject(i).getString("address");
                    addressOther[i] = dataArray.getJSONObject(i).getString("addressOther");
                    brief[i] = dataArray.getJSONObject(i).getString("brief");
                    All_data++;
                    switch (type[i]) {
                        case "school":
                            Schools_data++;
                            break;
                        case "building":
                            building_data++;
                            break;
                        case "hotel":
                            hotels_data++;
                            break;
                        case "residence":
                            Residence_data++;
                            break;
                        case "facility":
                            facility_data++;
                            break;
                    }
                }
                String[] array1 = eng.split(",\"page\":");
                JSONObject data1 = new JSONObject(array1[1]);
                district = data1.getString("district");
                summary = data1.getString("summary");
                String[] array2 = eng.split("\"places\":");
                JSONArray dataArray2 = new JSONArray(array2[1]);
                Areas = new String[dataArray2.length()];
                for (int j = 0; j < dataArray2.length(); j++) {
                    Areas[j] = dataArray2.getJSONObject(j).getString("name");
                }
                String[] array3 = eng.split("\"streets\":");
                JSONArray dataArray3 = new JSONArray(array3[1]);
                Streets = new String[dataArray3.length()];
                for (int k = 0; k < dataArray3.length(); k++) {
                    Streets[k] = dataArray3.getJSONObject(k).getString("name");
                }


            } catch (JSONException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(MainActivity.this, "internet error", Toast.LENGTH_LONG);
                toast.show();
                return;
            }
            myAdapter myAdapter = new myAdapter(MainActivity.this, name, type);
            listview.setAdapter(myAdapter);
        }
    }

    private void showOneDialog() {
        final android.app.AlertDialog build = new android.app.AlertDialog.Builder(this).create();
        View view = getLayoutInflater().inflate(R.layout.splash_dialog, null);
        build.setView(view, 0, 0, 0, 0);
        build.show();
        int width = getWindowManager().getDefaultDisplay().getWidth();
        WindowManager.LayoutParams params = build.getWindow().getAttributes();
        params.width = width - (width / 6);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        build.getWindow().setAttributes(params);
        RadioButton RadioButton_All = (RadioButton) view.findViewById(R.id.RadioButton_All);
        RadioButton RadioButton_Schools = (RadioButton) view.findViewById(R.id.RadioButton_Schools);
        RadioButton RadioButton_Residence = (RadioButton) view.findViewById(R.id.RadioButton_Residence);
        RadioButton RadioButton_Hotels = (RadioButton) view.findViewById(R.id.RadioButton_Hotels);
        RadioButton RadioButton_Building = (RadioButton) view.findViewById(R.id. RadioButton_Building);
        RadioButton RadioButton_Facility = (RadioButton) view.findViewById(R.id.RadioButton_Facility);
        TextView textView_All = (TextView) view.findViewById(R.id.textView_All);
        TextView textView_Schools = (TextView) view.findViewById(R.id.textView_Schools);
        TextView textView_Residence = (TextView) view.findViewById(R.id.textView_Residence);
        TextView textView_Hotels = (TextView) view.findViewById(R.id.textView_Hotels);
        TextView textView_Building = (TextView) view.findViewById(R.id.textView_Building);
        TextView textView_Facility = (TextView) view.findViewById(R.id.textView_Facility);
        Button cancel = (Button) view.findViewById(R.id.cancel);
        textView_All.setText("All(" + All_data + ")");
        textView_Schools.setText("Schools(" + Schools_data + ")");
        textView_Residence.setText("Residence(" + Residence_data + ")");
        textView_Hotels.setText("Hotels(" + hotels_data + ")");
        textView_Building.setText("Building(" + building_data + ")");
        textView_Facility.setText("Facility(" + facility_data + ")");
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                build.dismiss();
            }
        });
        RadioButton_All.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONDataSet("All", All_data);
            }
        });
        RadioButton_Schools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONDataSet("school", Schools_data);
            }
        });
        RadioButton_Residence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONDataSet("residence", Residence_data);
            }
        });
        RadioButton_Hotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONDataSet("hotel", hotels_data);
            }
        });
        RadioButton_Building.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONDataSet("building", building_data);
            }
        });
        RadioButton_Facility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONDataSet("facility", facility_data);
            }
        });
    }

    public void JSONDataSet(String Select, int Data_Long) {
        try {
            JSONArray dataArray = new JSONArray(JSON_Data);
            location = new String[Data_Long];
            type = new String[Data_Long];
            name = new String[Data_Long];
            nameOther = new String[Data_Long];
            address = new String[Data_Long];
            addressOther = new String[Data_Long];
            brief = new String[Data_Long];
            int a = 0;
            for (int i = 0; i < dataArray.length(); i++) {
                if (Select.equals("All")) {
                    location[a] = dataArray.getJSONObject(i).getString("location");
                    type[a] = dataArray.getJSONObject(i).getString("type");
                    name[a] = dataArray.getJSONObject(i).getString("name");
                    nameOther[a] = dataArray.getJSONObject(i).getString("nameOther");
                    address[a] = dataArray.getJSONObject(i).getString("address");
                    addressOther[a] = dataArray.getJSONObject(i).getString("addressOther");
                    brief[a] = dataArray.getJSONObject(i).getString("brief");
                    a++;
                } else if (dataArray.getJSONObject(i).getString("type").equals(Select)) {
                    location[a] = dataArray.getJSONObject(i).getString("location");
                    type[a] = dataArray.getJSONObject(i).getString("type");
                    name[a] = dataArray.getJSONObject(i).getString("name");
                    nameOther[a] = dataArray.getJSONObject(i).getString("nameOther");
                    address[a] = dataArray.getJSONObject(i).getString("address");
                    addressOther[a] = dataArray.getJSONObject(i).getString("addressOther");
                    brief[a] = dataArray.getJSONObject(i).getString("brief");
                    a++;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            new getData().execute();
            Toast toast = Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        listview.setAdapter(null);
        myAdapter myAdapter = new myAdapter(MainActivity.this, name, type);
        listview.setAdapter(myAdapter);
    }

    private void openGPSSettings() {
        LocationManager alm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            //Toast.makeText(this, "GPS模塊正常", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "please open GPS！", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, 1315);
    }

    @Override
    protected void onResume() {
        super.onResume();
        openGPSSettings();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getGPS() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFINE_LOCATION() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CUSTOM_NUMBER: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "PERMISSION_GRANTED!", Toast.LENGTH_SHORT).show();
                    getGPS();
                    return;
                } else {
                    Toast.makeText(this, "PERMISSION_DENIED!", Toast.LENGTH_SHORT).show();
                    showGPSDialog();
                }
            }
        }
    }

    private void showGPSDialog() {
        final android.app.AlertDialog build = new android.app.AlertDialog.Builder(this).create();
        View view = getLayoutInflater().inflate(R.layout.gps_dialog, null);
        build.setView(view, 0, 0, 0, 0);
        build.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                checkFINE_LOCATION();
            }
        });
        build.show();
        int width = getWindowManager().getDefaultDisplay().getWidth();
        WindowManager.LayoutParams params = build.getWindow().getAttributes();
        params.width = width - (width / 6);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        build.getWindow().setAttributes(params);
        Button Button_A = (Button) view.findViewById(R.id.Button_A);
        Button_A.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                checkFINE_LOCATION();
            }
        });
    }
}
