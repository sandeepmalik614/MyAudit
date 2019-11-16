package com.myaudit.database.transaction;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "TarnsactionTable")
public class Transaction {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "Amount")
    private String amount;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "Desc")
    private String desc;

    @ColumnInfo(name = "Date")
    private String date;

    @ColumnInfo(name = "Type")
    private String type;

    @ColumnInfo(name = "Month")
    private String month;

    public Transaction(String amount, String title, String desc, String date, String type, String month) {
        this.amount = amount;
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.type = type;
        this.month = month;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
