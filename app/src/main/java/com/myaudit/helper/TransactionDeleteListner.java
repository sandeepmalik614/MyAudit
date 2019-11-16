package com.myaudit.helper;

import com.myaudit.database.transaction.Transaction;

public interface TransactionDeleteListner {
    void onDelete(Transaction transaction, int pos);
}
