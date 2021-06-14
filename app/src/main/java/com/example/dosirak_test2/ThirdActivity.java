package com.example.dosirak_test2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {
    private TextView third_tv_name;
    private TextView third_tv_category;
    private TextView third_tv_price;

    private ArrayList<ThirdData> arrayList;
    private ThirdAdapter thirdAdapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;

    //public String result = "{\"product\":[{\"price\":1000,\"id\":1,\"category\":\"즉석식품\",\"product_name\":\"김밥\",\"barcode\":\"1001\"},{\"price\":500,\"id\":2,\"category\":\"컵라면\",\"product_name\":\"육개장\",\"barcode\":\"1002\"},{\"price\":1500,\"id\":3,\"category\":\"과자\",\"product_name\":\"새우깡\",\"barcode\":\"1003\"},{\"price\":4500,\"id\":4,\"category\":\"담배\",\"product_name\":\"인생의쓴맛\",\"barcode\":\"1004\"},{\"price\":100,\"id\":5,\"category\":\"기타\",\"product_name\":\"무언가\",\"barcode\":\"1005\"},{\"price\":10,\"id\":6,\"category\":\"기타\",\"product_name\":\"something\",\"barcode\":\"1006\"},{\"price\":10000,\"id\":19,\"category\":\"기타\",\"product_name\":\"한글고친기념\",\"barcode\":\"9999\"}],\"store\":[{\"store_name\":\"한성대점\",\"location\":\"한성대학교 앞\",\"id\":1},{\"store_name\":\"삼선교점\",\"location\":\"삼선교 앞\",\"id\":2}],\"stock\":[{\"count\":-14,\"store_name\":\"한성대점\",\"id\":9,\"barcode\":\"1001\"},{\"count\":9,\"store_name\":\"한성대점\",\"id\":10,\"barcode\":\"1002\"},{\"count\":15,\"store_name\":\"삼선교점\",\"id\":11,\"barcode\":\"1003\"},{\"count\":20,\"store_name\":\"삼선교점\",\"id\":12,\"barcode\":\"1004\"}]}";
    public String result = "";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent = getIntent();
        String productname = intent.getStringExtra("productname");  //상품 이름
        String category = intent.getStringExtra("category");
        String price = intent.getStringExtra("price");
        String barcode = intent.getStringExtra("barcode");

        third_tv_name = findViewById(R.id.third_tv_name);
        third_tv_name.setText(productname);
        third_tv_category = findViewById(R.id.third_tv_category);
        third_tv_category.setText(category);
        third_tv_price = findViewById(R.id.third_tv_price);
        third_tv_price.setText(price);

        //final LinearLayout linearLayout = findViewById(R.id.third_linearLayout);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////여기서부터가 문제
        //리사이클러뷰 접근////////////////////////////
        recyclerView = (RecyclerView) findViewById(R.id.third_linearLayout);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        thirdAdapter = new ThirdAdapter(arrayList);
        recyclerView.setAdapter(thirdAdapter);

        try {
            CustomTask task = new CustomTask();
            result = task.execute("","").get();
            Log.i("리턴 값",result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            JSONObject jsonObject = new JSONObject(result);
            String find_stock = jsonObject.getString("stock");
            String find_product = jsonObject.getString("product");
            String where = jsonObject.getString("store");

            JSONArray jsonArrayStock = new JSONArray(find_stock); /////////////////////stock 저장 완료 ->jsonArray에 stock 저장
            JSONArray jsonArrayProduct = new JSONArray(find_product); /////////////////////Product 저장 완료 ->jsonArray에 Product 저장
            JSONArray jsonArrayStore = new JSONArray(where);

            //String barcode = jsonObject1.getString("barcode");
            //String store_name =
            String store_where = null;
            for(int i=0; i<jsonArrayStock.length(); i++){
                if (barcode.equals(jsonArrayStock.getJSONObject(i).getString("barcode"))){
                    /////여기서부터
                    //for(int j=0; j<jsonArrayStock.length(); j++){
                        JSONObject jsonObject2= jsonArrayStock.getJSONObject(i);
                        //


                        //변수 선언
                        String store_name = jsonObject2.getString("store_name");
                        String store_stock=jsonObject2.getString("count");

                        for(int j=0; j<jsonArrayStore.length();j++){
                            if(store_name.equals(jsonArrayStore.getJSONObject(j).getString("store_name"))){
                                store_where = jsonArrayStore.getJSONObject(j).getString("location");
                            }
                        }



                        ThirdData thirdData=new ThirdData(store_name,store_stock, store_where);
                        arrayList.add(thirdData);
                   // }

                    //ThirdData thirdData= new ThirdData(jsonArrayStock.getJSONObject((i)).getString("store_name"),)
                   /* TextView textView = new TextView(ThirdActivity.this);
                    textView.setText(jsonArrayStock.getJSONObject((i)).getString("store_name"));
                    linearLayout.addView(textView);*/
                }
            }
            thirdAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}