package com.cs.uangku.models;

import android.app.Application;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cs.uangku.Transaction;
import com.cs.uangku.TransactionRepo;

import java.util.List;

public class TransactionViewModel extends AndroidViewModel {
    public static final String UangkuPref = "UangkuPref";
    private TransactionRepo transactionRepo;
    private LiveData<List<Transaction>> allTransaction;

    public TransactionViewModel(@NonNull Application application) {
        super(application);
        transactionRepo = new TransactionRepo(application);
        allTransaction  = transactionRepo.getAllTransaction();
    }

    public void create(Transaction transaction){
        transactionRepo.create(transaction);
    }

    public void update(Transaction transaction){
        transactionRepo.update(transaction);
    }

    public void delete(Transaction transaction){
        transactionRepo.delete(transaction);
    }

    public void deleteAllTransaction(Transaction transaction){
        transactionRepo.deleteAllTransaction(transaction);
    }

    public LiveData<List<Transaction>> getAllTransaction(){
        return allTransaction;
    }
}
