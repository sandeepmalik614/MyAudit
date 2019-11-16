package com.myaudit.database.totalAmount;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TotalAmountTable")
public class TotalAmount {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "Month")
    private String month;

    @ColumnInfo(name = "CreditAmount")
    private String creditAmount;

    @ColumnInfo(name = "DebitAmount")
    private String debitAmount;

    @ColumnInfo(name = "TotalAmount")
    private String totalAmount;

    public TotalAmount(String month, String creditAmount, String debitAmount, String totalAmount) {
        this.month = month;
        this.creditAmount = creditAmount;
        this.debitAmount = debitAmount;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(String debitAmount) {
        this.debitAmount = debitAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
