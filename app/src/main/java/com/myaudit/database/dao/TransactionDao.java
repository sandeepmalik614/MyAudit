package com.myaudit.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.myaudit.database.transaction.Transaction;

import java.util.List;

@Dao
public interface TransactionDao {

    @Insert
    void insertTransaction(Transaction transaction);

    @Query("Select * from TarnsactionTable WHERE Month=:month")
    List<Transaction> getTransactionInfo(String month);

    @Delete
    void deleteTransaction(Transaction transaction);
}
