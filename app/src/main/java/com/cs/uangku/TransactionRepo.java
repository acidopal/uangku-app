package com.cs.uangku;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class TransactionRepo {
    private TransactionDAO transactionDAO;
    private LiveData<List<Transaction>> allTransaction;

    public TransactionRepo(Application application){
        TransactionDatabase database = TransactionDatabase.getInstance(application);
        transactionDAO = database.transactionDAO();
        allTransaction = transactionDAO.getAllTransaction();
    }

    public void create(Transaction transaction){
        new InsertTransactionAsyncTask(transactionDAO).execute(transaction);
    }

    public void update(Transaction transaction){
        new UpdateTransactionAsyncTask(transactionDAO).execute(transaction);
    }

    public void delete(Transaction transaction){
        new DeleteTransactionAsyncTask(transactionDAO).execute(transaction);
    }

    public void deleteAllTransaction(Transaction transaction){
        new DeleteTransactionAsyncTask(transactionDAO).execute();
    }

    public LiveData<List<Transaction>> getAllTransaction(){
        return allTransaction;
    }

    private static class InsertTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDAO transactionDAO;

        private InsertTransactionAsyncTask(TransactionDAO transactionDAO){
            this.transactionDAO = transactionDAO;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDAO.create(transactions[0]);
            return null;
        }
    }

    private static class UpdateTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDAO transactionDAO;

        private UpdateTransactionAsyncTask(TransactionDAO transactionDAO){
            this.transactionDAO = transactionDAO;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDAO.update(transactions[0]);
            return null;
        }
    }

    private static class DeleteTransactionAsyncTask extends AsyncTask<Transaction, Void, Void> {
        private TransactionDAO transactionDAO;

        private DeleteTransactionAsyncTask(TransactionDAO transactionDAO){
            this.transactionDAO = transactionDAO;
        }

        @Override
        protected Void doInBackground(Transaction... transactions) {
            transactionDAO.delete(transactions[0]);
            return null;
        }
    }

    private static class DeleteAllTransactionAsyncTask extends AsyncTask<Void, Void, Void> {
        private TransactionDAO transactionDAO;

        private DeleteAllTransactionAsyncTask(TransactionDAO transactionDAO){
            this.transactionDAO = transactionDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            transactionDAO.deleteAllTransaction();
            return null;
        }
    }
}
