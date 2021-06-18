package com.example.dosirak_test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.dosirak_test2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SubActivity extends AppCompatActivity {
    //public String result = "{\"product\":[{\"price\":1000,\"id\":1,\"category\":\"즉석식품\",\"product_name\":\"김밥\",\"barcode\":\"1001\"},{\"price\":500,\"id\":2,\"category\":\"컵라면\",\"product_name\":\"육개장\",\"barcode\":\"1002\"},{\"price\":1500,\"id\":3,\"category\":\"과자\",\"product_name\":\"새우깡\",\"barcode\":\"1003\"},{\"price\":4500,\"id\":4,\"category\":\"담배\",\"product_name\":\"인생의쓴맛\",\"barcode\":\"1004\"},{\"price\":100,\"id\":5,\"category\":\"기타\",\"product_name\":\"무언가\",\"barcode\":\"1005\"},{\"price\":10,\"id\":6,\"category\":\"기타\",\"product_name\":\"something\",\"barcode\":\"1006\"},{\"price\":10000,\"id\":19,\"category\":\"기타\",\"product_name\":\"한글고친기념\",\"barcode\":\"9999\"}],\"store\":[{\"store_name\":\"한성대점\",\"location\":\"한성대학교 앞\",\"id\":1},{\"store_name\":\"삼선교점\",\"location\":\"삼선교 앞\",\"id\":2}],\"stock\":[{\"count\":-14,\"store_name\":\"한성대점\",\"id\":9,\"barcode\":\"1001\"},{\"count\":9,\"store_name\":\"한성대점\",\"id\":10,\"barcode\":\"1002\"},{\"count\":15,\"store_name\":\"삼선교점\",\"id\":11,\"barcode\":\"1003\"},{\"count\":20,\"store_name\":\"삼선교점\",\"id\":12,\"barcode\":\"1004\"}]}";
    public String result = "";
    private TextView tv_storename;
    private ListView list;

    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Button btn_find = findViewById(R.id.btn_find);
        EditText et_find = (EditText)findViewById(R.id.et_find);

        Intent intent = getIntent();
        String storeName = intent.getStringExtra("storeName");  //처음 화면에서 가져온 - 한성대점
        tv_storename = findViewById(R.id.store_name);
        tv_storename.setText(storeName);

        //리사이클러뷰 접근
        recyclerView =(RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        try {
            CustomTask task = new CustomTask();
            result = task.execute("","").get();
            Log.i("리턴 값",result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //처음 화면 넘어올 떄 리스트뷰에 보여주는 곳
        try {
            JSONObject jsonObject = new JSONObject(result);
            String find_stock = jsonObject.getString("stock");
            String find_product = jsonObject.getString("product");

            JSONArray jsonArrayStock = new JSONArray(find_stock); /////////////////////stock 저장 완료 ->jsonArray에 stock 저장
            JSONArray jsonArrayProduct = new JSONArray(find_product); /////////////////////Product 저장 완료 ->jsonArray에 Product 저장


            for (int i = 0; i < jsonArrayProduct.length(); i++) {
                JSONObject jsonObject1 = jsonArrayProduct.getJSONObject(i);
                //변수 선언
                String productName = jsonObject1.getString("product_name");
                String tv_type = jsonObject1.getString("category");
                String tv_price = jsonObject1.getString("price");
                String barcode = jsonObject1.getString("barcode");
                String tv_stock = "0";

                for (int j = 0; j < jsonArrayStock.length(); j++) {
                    if (jsonArrayStock.getJSONObject(j).getString("barcode").equals(jsonArrayProduct.getJSONObject(i).getString("barcode")) && jsonArrayStock.getJSONObject(j).getString("store_name").equals(storeName)) {
                        JSONObject jsonObject2= jsonArrayStock.getJSONObject(j);
                        tv_stock = jsonObject2.getString("count");
                        break;
                    }
                }

                MainData mainData = new MainData(productName, tv_type,tv_price,tv_stock, barcode);
                arrayList.add(mainData);
            }
            mainAdapter.notifyDataSetChanged();

            et_find.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == event.KEYCODE_ENTER) {
                        if (et_find.getText().toString().equals("")) {

                        } else  {
                            arrayList.clear(); //버튼 누를 때마다 list 다 지우기
                            for(int i=0; i<jsonArrayProduct.length(); i++ ){
                                try {
                                    JSONObject jsonObject1 = jsonArrayProduct.getJSONObject(i);
                                    JSONObject jsonObject2= jsonArrayStock.getJSONObject(i);
                                    //변수 선언
                                    String productName = jsonObject1.getString("product_name");
                                    String tv_type = jsonObject1.getString("category");
                                    String tv_price = jsonObject1.getString("price");
                                    String tv_stock = jsonObject2.getString("count");
                                    String barcode = jsonObject1.getString("barcode");

                                    if (productName.contains(et_find.getText())) {
                                        MainData mainData = new MainData(productName, tv_type, tv_price, "0", barcode);

                                        for (int j = 0; j < jsonArrayStock.length(); j++) {
                                            if (jsonArrayStock.getJSONObject(j).getString("barcode").equals(jsonArrayProduct.getJSONObject(i).getString("barcode"))
                                                    && jsonArrayStock.getJSONObject(j).getString("store_name").equals(storeName)) {
                                                //productName = productName + " : " + jsonArrayStock.getJSONObject(i).getString("count");
                                                mainData.setTv_stock(tv_stock);
                                            }
                                        }

                                        arrayList.add(mainData);
                                    }
                                    mainAdapter.notifyDataSetChanged();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return true;
                    }
                    return false;
                }
            });

            btn_find.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (et_find.getText().toString().equals("")) {
                    } else  {
                        arrayList.clear();
                        //listData.clear(); //버튼 누를 때마다 list 다 지우기
                        for(int i=0; i<jsonArrayProduct.length(); i++ ){
                            try {
                                JSONObject jsonObject = jsonArrayProduct.getJSONObject(i);
                                JSONObject jsonObject2= jsonArrayStock.getJSONObject(i);
                                //변수 선언
                                String productName = jsonObject.getString("product_name");
                                String tv_type = jsonObject.getString("category");
                                String tv_price = jsonObject.getString("price");
                                //String tv_stock = jsonArrayStock.getJSONObject(i).getString("count");
                                String tv_stock = jsonObject2.getString("count");
                                String barcode = jsonObject.getString("barcode");

                                if (productName.contains(et_find.getText())) {
                                    MainData mainData = new MainData(productName, tv_type, tv_price, "0", barcode);

                                    for (int j = 0; j < jsonArrayStock.length(); j++) {
                                        if (jsonArrayStock.getJSONObject(j).getString("barcode").equals(jsonArrayProduct.getJSONObject(i).getString("barcode"))
                                                && jsonArrayStock.getJSONObject(j).getString("store_name").equals(storeName)) {
                                            //productName = productName + " : " + jsonArrayStock.getJSONObject(i).getString("count");
                                            mainData.setTv_stock(tv_stock);
                                        }
                                    }

                                    arrayList.add(mainData);
                                }
                                mainAdapter.notifyDataSetChanged();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}