package com.cs.uangku.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.cs.uangku.R;
import com.cs.uangku.Transaction;

public class TransactionAdapter extends ListAdapter<Transaction, TransactionAdapter.TransactionHolder> {
    private OnItemClickListener listener;

    public TransactionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Transaction> DIFF_CALLBACK = new DiffUtil.ItemCallback<Transaction>() {
        @Override
        public boolean areItemsTheSame(Transaction oldItem, Transaction newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(Transaction oldItem, Transaction newItem) {
            return oldItem.getUserId().equals(newItem.getUserId()) &&
                    oldItem.getAmount().equals(newItem.getAmount()) &&
                    oldItem.getCategory().equals(newItem.getCategory()) &&
                    oldItem.getDescription() == newItem.getDescription();
        }
    };

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.transaction_item, parent, false);

        return new TransactionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {
        Transaction currentTransaction = getItem(position);
        holder.txtUserId.setText(String.valueOf(currentTransaction.getUserId()));
        holder.txtAmount.setText(String.valueOf(currentTransaction.getAmount()));
        holder.txtCategory.setText(String.valueOf(currentTransaction.getCategory()));
        holder.txtDescription.setText(String.valueOf(currentTransaction.getDescription()));
    }

    public Transaction getTransactionAt(int position) {
        return getItem(position);
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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Transaction transaction);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
