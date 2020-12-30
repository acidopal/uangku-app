package com.cs.uangku;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDAO {

    @Insert
    void create(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);

    @Query("DELETE FROM transaction_table")
    void deleteAllTransaction();

//        @Query("SELECT * FROM transaction_table WHERE userId = :userId ORDER BY id DESC")
//    @Query("SELECT * FROM transaction_table  WHERE userId = 4 ORDER BY id DESC")
    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    LiveData<List<Transaction>> getAllTransaction();
}
