package com.cs.uangku.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.uangku.R;
import com.cs.uangku.Transaction;
import com.cs.uangku.adapter.TransactionAdapter;
import com.cs.uangku.models.TransactionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String UangkuPref = "UangkuPref";
    public static final String Name = "nameKey";
    public static final Integer Balance = 10000;
    public static final Boolean IsLogin = false;

    private TextView txtUser, txtBalance;
    private int userId;

    public static final int ADD_TRANSACTION_REQUEST = 1;
    public static final int EDIT_TRANSACTION_REQUEST = 2;
    private TransactionViewModel transactionViewModel;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton btnAddTransaction = findViewById(R.id.btn_add_transaction);
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditTransactionActivity.class);
                startActivityForResult(intent, ADD_TRANSACTION_REQUEST);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final TransactionAdapter adapter = new TransactionAdapter();
        recyclerView.setAdapter(adapter);

        txtUser = findViewById(R.id.txtUser);
        txtBalance = findViewById(R.id.txtBalance);

        sharedPreferences = getSharedPreferences(UangkuPref,  Context.MODE_PRIVATE);

        String name = sharedPreferences.getString("name", "Budi");
        txtUser.setText(name);

        userId = sharedPreferences.getInt("user_id", 1);
        Log.d("user_id", String.valueOf(userId));

        Integer balance = sharedPreferences.getInt("balance", 0);
        txtBalance.setText(balance.toString());

        transactionViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(TransactionViewModel.class);
        transactionViewModel.getAllTransaction().observe(this, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(List<Transaction> transactions) {
                adapter.submitList(transactions);
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
                Log.d("GET_ID", String.valueOf(transaction.getId()));

                intent.putExtra(AddEditTransactionActivity.EXTRA_ID, transaction.getId());
                intent.putExtra(AddEditTransactionActivity.EXTRA_USER_ID, transaction.getUserId());
                intent.putExtra(AddEditTransactionActivity.EXTRA_CATEGORY, transaction.getCategory());
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
            int userId = data.getIntExtra(AddEditTransactionActivity.EXTRA_USER_ID, 1);
            int amount = data.getIntExtra(AddEditTransactionActivity.EXTRA_AMOUNT, 0);
            String category = data.getStringExtra(AddEditTransactionActivity.EXTRA_CATEGORY);
            String description = data.getStringExtra(AddEditTransactionActivity.EXTRA_DESCRIPTION);

            Transaction transaction = new Transaction(userId, amount, category, description);
            transactionViewModel.create(transaction);

            Toast.makeText(this, "Transaksi berhasil", Toast.LENGTH_SHORT).show();
        }else if (requestCode == EDIT_TRANSACTION_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTransactionActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Transaksi tidak bisa diperbarui!", Toast.LENGTH_SHORT).show();
                return;
            }

            int userId = data.getIntExtra(AddEditTransactionActivity.EXTRA_USER_ID, 1);
            int amount = data.getIntExtra(AddEditTransactionActivity.EXTRA_AMOUNT, 0);
            String category = data.getStringExtra(AddEditTransactionActivity.EXTRA_CATEGORY);
            String description = data.getStringExtra(AddEditTransactionActivity.EXTRA_DESCRIPTION);

            Transaction transaction = new Transaction(userId, amount, category, description);
            transaction.setId(id);
            transactionViewModel.update(transaction);

            Toast.makeText(this, "Transaksi diperbarui", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(this, "Transaksi gagal!", Toast.LENGTH_SHORT).show();
        }
    }
}