package com.example.dosirak_test2;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button data = (Button)findViewById(R.id.sendData);
        final TextView textView = (TextView)findViewById(R.id.textView);
        final LinearLayout linearLayout = findViewById(R.id.linearhoho);

        String result = "";

        //매장 버튼을 동적 생산
        StringBuffer sb = new StringBuffer();

        try {
            CustomTask task = new CustomTask();
            result = task.execute("","").get();
            Log.i("리턴 값",result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            JSONObject jsonObject = new JSONObject(result);
            String store = jsonObject.getString("store");
            JSONArray jsonArray = new JSONArray(store);
            for (int i = 0; i < jsonArray.length(); i++) {
                sb = new StringBuffer();
                JSONObject jObject = jsonArray.getJSONObject(i);
                String storeName = jObject.getString("store_name");
                sb.append(
                        "이름: " + storeName + "\n"
                );
                Button button = new Button(MainActivity.this);
                button.setText(storeName);


                linearLayout.addView(button);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Intent intent = new Intent(MainActivity.this, SubActivity.class);
                        Intent intentMove = new Intent(MainActivity.this, SubActivity.class);
                        intentMove.putExtra("storeName", storeName); //여기에 어떤 부분을 넣어야 할까?
                        intentMove.putExtra("stock", store);
                        //intent.putExtra("stock", storeName); //
                        //한성대점을 넘기면 그 안에 있는 모든 것?
                        //startActivity(intent);
                        startActivity(intentMove);
                    }
                });
            }
        } catch (JSONException e) {
        }
    }
}

