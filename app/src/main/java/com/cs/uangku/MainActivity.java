package com.cs.uangku;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_TRANSACTION_REQUEST = 1;
    public static final int EDIT_TRANSACTION_REQUEST = 2;
    private TransactionViewModel transactionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAddTransaction = findViewById(R.id.btn_add_transaction);
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditTransactionActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TransactionAdapter adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        transactionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TransactionViewModel.class);
        transactionViewModel.getAllTransaction().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                adapter.setTransactions(transactions);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                transactionViewModel.delete(adapter.getTransactionAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Transaction deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new TransactionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Transaction transaction) {
                Intent intent = new Intent(MainActivity.this, AddEditTransactionActivity.class);
                intent.putExtra(AddEditTransactionActivity.EXTRA_ID, transaction.getId());
                intent.putExtra(AddEditTransactionActivity.EXTRA_USER_ID, transaction.getUserId());
                intent.putExtra(AddEditTransactionActivity.EXTRA_AMOUNT, transaction.getAmount());
                intent.putExtra(AddEditTransactionActivity.EXTRA_DESCRIPTION, transaction.getDescription());
                startActivityForResult(intent, EDIT_TRANSACTION_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_TRANSACTION_REQUEST && resultCode == RESULT_OK){
            int userId = data.getIntExtra(String.valueOf(AddEditTransactionActivity.EXTRA_USER_ID), 0);
            int amount = data.getIntExtra(String.valueOf(AddEditTransactionActivity.EXTRA_AMOUNT), 0);
            int category = data.getIntExtra(String.valueOf(AddEditTransactionActivity.EXTRA_CATEGORY), 1);
            String description = data.getStringExtra(AddEditTransactionActivity.EXTRA_DESCRIPTION);

            Transaction transaction = new Transaction(userId, amount, category, description);
            transactionViewModel.create(transaction);

            Toast.makeText(this, "Transaction Success", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Failed transaction!", Toast.LENGTH_SHORT).show();
        }
    }
}