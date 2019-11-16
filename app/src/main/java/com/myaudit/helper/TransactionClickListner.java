package com.myaudit.helper;

import com.myaudit.database.transaction.Transaction;

public interface TransactionClickListner {
    void onDelete(Transaction transaction, int pos);
    void onTransactionClick(Transaction transaction);
}
