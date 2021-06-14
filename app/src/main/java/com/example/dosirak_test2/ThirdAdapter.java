package com.example.dosirak_test2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ThirdAdapter extends RecyclerView.Adapter<ThirdAdapter.CustomViewHolder> {
    //
    private Context mContext;

    private ArrayList<ThirdData> arrayList;
    public ThirdAdapter(ArrayList<ThirdData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ThirdAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.third_list,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        mContext = parent.getContext();
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ThirdAdapter.CustomViewHolder holder, int position) {
            holder.third_list_name.setText(arrayList.get(position).getTv_name());
            holder.third_list_stock.setText(arrayList.get(position).getTv_stock());
            //여기서 버튼이 클릭되면 위치 알려주는 것도 설정 가능
            holder.third_btn_where.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("위치");
                    builder.setMessage(arrayList.get(position).getWhere());
                    //builder.setMessage(arrayList.get(position).getWhere());

                    builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();*/

                    //이번엔 지도 만들기
                    Intent intentMove = new Intent(mContext, goolglemap.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    // 매장의 위치좌표 넘기기
                    intentMove.putExtra("storeWhere", arrayList.get(position).getWhere()); //여기에 어떤 부분을 넣어야 할까?
                    intentMove.putExtra("storeName", arrayList.get(position).getTv_name());
                    intentMove.putExtra("storeStock", arrayList.get(position).getTv_stock());

                    mContext.startActivity(intentMove);
                }
            });
    }

    @Override
    public int getItemCount() {
        return (null != arrayList?arrayList.size():0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView third_list_name;
        protected TextView third_list_stock;
        //버튼 연결시키기
        protected Button third_btn_where;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.third_list_name = (TextView) itemView.findViewById(R.id.third_list_name);
            this.third_list_stock = (TextView) itemView.findViewById(R.id.third_list_stock);
            this.third_btn_where = (Button) itemView.findViewById(R.id.third_btn_where);
        }
    }
}
