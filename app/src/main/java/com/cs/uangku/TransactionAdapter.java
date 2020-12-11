package com.cs.uangku;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder > {
    private List<Transaction> transactions = new ArrayList<>();

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);

        return new TransactionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        Transaction currentTransaction = transactions.get(position);
        holder.txtUserId.setText(String.valueOf(currentTransaction.getUserId()));
        holder.txtAmount.setText(String.valueOf(currentTransaction.getAmount()));
        holder.txtCategory.setText(String.valueOf(currentTransaction.getCategory()));
        holder.txtDescription.setText(String.valueOf(currentTransaction.getDescription()));
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    public void setTransactions(List<Transaction> transactions){
        this.transactions = transactions;
        notifyDataSetChanged();
    }

    class TransactionHolder extends RecyclerView.ViewHolder {
        private TextView txtUserId;
        private TextView txtAmount;
        private TextView txtCategory;
        private TextView txtDescription;

        public TransactionHolder(View itemView) {
            super(itemView);
            txtUserId = itemView.findViewById(R.id.txt_user_id);
            txtAmount = itemView.findViewById(R.id.txt_amount);
            txtCategory = itemView.findViewById(R.id.txt_category);
            txtDescription = itemView.findViewById(R.id.txt_description);
        }
    }
}
