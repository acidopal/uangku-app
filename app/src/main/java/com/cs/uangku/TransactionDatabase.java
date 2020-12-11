package com.cs.uangku;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Transaction.class}, version = 1)
public abstract class TransactionDatabase extends RoomDatabase {
    private static TransactionDatabase instance;

    public abstract TransactionDAO transactionDAO();

    public static synchronized TransactionDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                     TransactionDatabase.class, "uangku_database")
                     .fallbackToDestructiveMigration()
                     .addCallback(roomCallback)
                     .build();
        }
        return instance;
    }

    public static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    public static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void>{
        private TransactionDAO transactionDAO;

        private PopulateDbAsyncTask(TransactionDatabase db){
            transactionDAO = db.transactionDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            transactionDAO.create(new Transaction(1, 20000,1, "Beli Makan"));
            transactionDAO.create(new Transaction(2, 30000,1, "Beli Minum"));
            transactionDAO.create(new Transaction(2, 20000,1, "Beli Baju"));
            return null;
        }
    }
}
