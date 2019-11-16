package com.myaudit.database.userDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.myaudit.database.dao.TotalAmountDao;
import com.myaudit.database.dao.TransactionDao;
import com.myaudit.database.dao.UserDao;
import com.myaudit.database.totalAmount.TotalAmount;
import com.myaudit.database.transaction.Transaction;
import com.myaudit.database.user.User;

@Database(entities = {User.class, Transaction.class, TotalAmount.class}, version = 1)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract TransactionDao transactionDao();

    public abstract TotalAmountDao totalAmountDao();
}
