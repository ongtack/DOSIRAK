package com.example.dosirak_test2;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.CustomViewHolder> {
    private ArrayList<MainData> arrayList;
    public MainAdapter(ArrayList<MainData> arrayList) { this.arrayList = arrayList; }

    @NonNull
    @Override
    public MainAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.CustomViewHolder holder, int position) {
        holder.tv_name.setText(arrayList.get(position).getTv_name());
        holder.tv_type.setText(arrayList.get(position).getTv_type());
        holder.tv_price.setText(arrayList.get(position).getTv_price());
        holder.tv_stock.setText(arrayList.get(position).getTv_stock());

        //////버튼 클릭리스너 추가!!
        holder.btn_stockCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                //Intent intentMove = new Intent(MainActivity.this, SubActivity.class);

                Intent intentMove = new Intent(v.getContext(), ThirdActivity.class);
                //intentMove.putExtra("stock", store);
                //이 부분이 중요!
                //상품의 이름과, 카테고리, 가격을 넘겨주어야 한다.
                intentMove.putExtra("productname", arrayList.get(position).getTv_name());
                intentMove.putExtra("category", arrayList.get(position).getTv_type());
                intentMove.putExtra("price", arrayList.get(position).getTv_price());
                intentMove.putExtra("barcode", arrayList.get(position).getBarcede());
                //intentMove.putExtra("barcode", arrayList.)
                context.startActivity(intentMove);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList?arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_name;
        protected TextView tv_type;
        protected TextView tv_price;
        protected TextView tv_stock;
        //버튼 연결시키기
        protected Button btn_stockCheck;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tv_name=(TextView)itemView.findViewById(R.id.tv_name);
            this.tv_type=(TextView)itemView.findViewById(R.id.tv_type);
            this.tv_price=(TextView)itemView.findViewById(R.id.tv_price);
            this.tv_stock=(TextView)itemView.findViewById(R.id.tv_stock);
            this.btn_stockCheck=(Button)itemView.findViewById(R.id.btn_stockCheck);
        }
    }
}
