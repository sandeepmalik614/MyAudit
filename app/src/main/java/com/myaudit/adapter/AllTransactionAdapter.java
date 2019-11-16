package com.myaudit.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.myaudit.R;
import com.myaudit.database.transaction.Transaction;
import com.myaudit.helper.TransactionClickListner;

import java.util.ArrayList;

public class AllTransactionAdapter extends RecyclerView.Adapter<AllTransactionAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Transaction> arrayList;
    private TransactionClickListner clickListner;
    private boolean isDeleteShow = false;

    public AllTransactionAdapter(Context context, ArrayList<Transaction> arrayList, TransactionClickListner clickListner, boolean isDeleteShow) {
        this.context = context;
        this.arrayList = arrayList;
        this.clickListner = clickListner;
        this.isDeleteShow = isDeleteShow;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_transaction, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.title.setText(arrayList.get(position).getTitle());

        if(arrayList.get(position).getType().equals("Debit")){
            holder.title.setTextColor(Color.parseColor("#B71C1C"));
            holder.amount.setTextColor(Color.parseColor("#B71C1C"));
            holder.icon.setBackgroundResource(R.drawable.ic_debit_money);
        }else{
            holder.title.setTextColor(Color.parseColor("#33691E"));
            holder.amount.setTextColor(Color.parseColor("#33691E"));
            holder.icon.setBackgroundResource(R.drawable.ic_credit_money);
        }

        holder.amount.setText("\u20B9 "+arrayList.get(position).getAmount());
        holder.date.setText(arrayList.get(position).getDate());

        if(isDeleteShow){
            holder.delete.setVisibility(View.VISIBLE);
        }else{
            holder.delete.setVisibility(View.GONE);
        }

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListner != null){
                    clickListner.onDelete(arrayList.get(position), position);
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(clickListner != null){
                    clickListner.onTransactionClick(arrayList.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title, amount, date;
        private ImageView icon, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.textView11);
            amount = itemView.findViewById(R.id.textView12);
            date = itemView.findViewById(R.id.textView13);
            icon = itemView.findViewById(R.id.imageView5);
            delete = itemView.findViewById(R.id.imageView12);
        }
    }
}
