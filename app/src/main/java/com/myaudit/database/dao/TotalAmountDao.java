package com.myaudit.database.dao;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Insert;
import androidx.room.Query;

import com.myaudit.database.totalAmount.TotalAmount;
import com.myaudit.database.user.User;

import java.util.List;

@Dao
public interface TotalAmountDao {

    @Insert
    void addMonth(TotalAmount totalAmount);

    @Query("Select * from TotalAmountTable")
    List<TotalAmount> getTotalAmountList();

    @Query("Select * from TotalAmountTable WHERE Month=:month")
    TotalAmount getTotalAmount(String month);

    @Query("UPDATE TotalAmountTable SET CreditAmount=:creditAmount WHERE Month=:month")
    void updateCreditAmount(int creditAmount, String month);

    @Query("UPDATE TotalAmountTable SET DebitAmount=:debitAmount WHERE Month=:month")
    void updateDebitAmount(int debitAmount, String month);

    @Query("UPDATE TotalAmountTable SET TotalAmount=:totalAmount")
    void updateTotalAmount(int totalAmount);

}
