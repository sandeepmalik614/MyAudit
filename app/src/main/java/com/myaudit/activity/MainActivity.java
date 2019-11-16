package com.myaudit.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.myaudit.R;
import com.myaudit.adapter.AllTransactionAdapter;
import com.myaudit.database.totalAmount.TotalAmount;
import com.myaudit.database.transaction.Transaction;
import com.myaudit.database.user.User;
import com.myaudit.helper.TransactionDeleteListner;

import java.util.ArrayList;
import java.util.Collections;

import static com.myaudit.activity.SplashActivity.appDatabase;
import static com.myaudit.utils.AppUtils.clearAllIntent;
import static com.myaudit.utils.AppUtils.getMonth;

public class MainActivity extends AppCompatActivity {

    private TextView tv_toolbar, tv_totalAmount, tv_mainDebit, tv_mainCredit, tv_noTrans, tv_month, tv_closeAmount;
    private LinearLayout ll_mainLayout;
    private RecyclerView rv_allTrans;
    private User user;
    private ArrayList<Transaction> transList;
    private ArrayList<TotalAmount> totalAmountList;
    private ArrayList<String> monthList;
    private TotalAmount totalAmount;
    private AllTransactionAdapter transactionAdapter;
    private boolean isMonthMenuSet = false, hideDelete = false, isDoublePressedToExit = false;
    private PopupMenu monthMenu;
    private PopupMenu mainMenu;
    private ImageView addImage;
    private CardView cardCloseAmount;
    private String selectedMonth = "";

    private TransactionDeleteListner deleteListner = new TransactionDeleteListner() {
        @Override
        public void onDelete(final Transaction transaction, final int pos) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Delete " + transaction.getTitle());
            builder.setMessage("Do you want to delete this transaction");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    try {
                        if (transaction.getType().equalsIgnoreCase("Debit")) {
                            appDatabase.totalAmountDao().
                                    updateDebitAmount(Integer.parseInt(totalAmount.getDebitAmount()) +
                                            Integer.parseInt(transaction.getAmount()), transaction.getMonth());

                            appDatabase.totalAmountDao().updateTotalAmount(Integer.parseInt(totalAmount.getTotalAmount())
                                    + Integer.parseInt(transaction.getAmount()));

                        } else {
                            appDatabase.totalAmountDao()
                                    .updateCreditAmount(Integer.parseInt(totalAmount.getCreditAmount()) -
                                            Integer.parseInt(transaction.getAmount()), transaction.getMonth());
                            appDatabase.totalAmountDao().updateTotalAmount(Integer.parseInt(totalAmount.getTotalAmount())
                                    - Integer.parseInt(transaction.getAmount()));
                        }

                        appDatabase.transactionDao().deleteTransaction(transaction);
                        transList.remove(pos);
                        transactionAdapter.notifyDataSetChanged();

                        totalAmount = appDatabase.totalAmountDao().getTotalAmount(getMonth());
                        tv_totalAmount.setText("\u20B9 " + totalAmount.getTotalAmount());
                        tv_mainCredit.setText("\u20B9 " + totalAmount.getCreditAmount());
                        tv_mainDebit.setText("\u20B9 " + totalAmount.getDebitAmount());

                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });

            builder.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_toolbar = findViewById(R.id.tv_toolbar);
        tv_totalAmount = findViewById(R.id.textView5);
        rv_allTrans = findViewById(R.id.rv_allTrans);
        tv_mainDebit = findViewById(R.id.tv_mainDebit);
        tv_mainCredit = findViewById(R.id.tv_mainCredit);
        tv_month = findViewById(R.id.spinnerMain);
        ll_mainLayout = findViewById(R.id.ll_mainLayout);
        tv_noTrans = findViewById(R.id.tv_noTrans);
        addImage = findViewById(R.id.imageView6);
        cardCloseAmount = findViewById(R.id.cardCloseAmount);
        tv_closeAmount = findViewById(R.id.tv_mainClose);

        rv_allTrans.setLayoutManager(new LinearLayoutManager(this));

        transList = new ArrayList<>();
        totalAmountList = new ArrayList<>();
        monthList = new ArrayList<>();

        tv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthMenu.show();
            }
        });

        setData(getMonth());

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMenu(addImage);
            }
        });
    }

    public void openMenu(View view) {
        mainMenu = new PopupMenu(MainActivity.this, view);
        mainMenu.getMenu().add("Profile");
        if (selectedMonth.equals(getMonth())) {
            mainMenu.getMenu().add("Debit Amount");
            mainMenu.getMenu().add("Credit Amount");
        }
        mainMenu.getMenu().add("Logout");
        mainMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = null;
                if (item.getTitle().equals("Profile")) {
                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("userName", user.getName());
                } else if (item.getTitle().equals("Debit Amount")) {
                    intent = new Intent(MainActivity.this, TransactionActivity.class);
                    intent.putExtra("tranType", "Debit");
                } else if (item.getTitle().equals("Logout")) {
                   intent = new Intent(MainActivity.this, LoginActivity.class);
                   clearAllIntent(intent);
                } else {
                    intent = new Intent(MainActivity.this, TransactionActivity.class);
                    intent.putExtra("tranType", "Credit");
                }
                startActivity(intent);
                return true;
            }
        });
        mainMenu.show();
    }

    private void setMonthMenu(ArrayList<String> list) {
        Collections.reverse(list);
        monthMenu = new PopupMenu(MainActivity.this, tv_month);
        for (int i = 0; i < list.size(); i++) {
            monthMenu.getMenu().add(list.get(i));
        }
        monthMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                setData(item.getTitle().toString());
                return true;
            }
        });
    }

    private void setData(final String month) {
        tv_month.setText(month);
        selectedMonth = month;
        transList.clear();
        totalAmountList.clear();
        totalAmountList.addAll(appDatabase.totalAmountDao().getTotalAmountList());
        if (!isMonthMenuSet) {
            if (totalAmountList.size() != 0) {
                monthList.clear();
                for (int i = 0; i < totalAmountList.size(); i++) {
                    monthList.add(totalAmountList.get(i).getMonth());
                }
                setMonthMenu(monthList);
            }
        }
        totalAmount = appDatabase.totalAmountDao().getTotalAmount(month);
        transList.addAll(appDatabase.transactionDao().getTransactionInfo(month));
        if (totalAmount == null) {
            TotalAmount totalAmount1;
            if (totalAmountList.size() == 0) {
                totalAmount1 = new TotalAmount(month, "0", "0", "0");
            } else {
                totalAmount1 = new TotalAmount(month, "0", "0", totalAmountList.get((totalAmountList.size() - 1)).getTotalAmount());
            }
            appDatabase.totalAmountDao().addMonth(totalAmount1);
            totalAmount = appDatabase.totalAmountDao().getTotalAmount(month);
        }
        Collections.reverse(transList);

        if (month.equals(getMonth())) {
            cardCloseAmount.setVisibility(View.GONE);
            hideDelete = true;
        } else {
            cardCloseAmount.setVisibility(View.VISIBLE);
            hideDelete = false;
            String credtiS = totalAmount.getDebitAmount();
            credtiS = credtiS.replace("-", "");
            int debit = Integer.parseInt(credtiS);
            int credit = Integer.parseInt(totalAmount.getCreditAmount());
            tv_closeAmount.setText("\u20B9 " + String.valueOf(credit - debit));
        }

        if (totalAmountList.size() > 0) {
            ll_mainLayout.setVisibility(View.VISIBLE);
            tv_noTrans.setVisibility(View.GONE);
            if (totalAmount == null) {
                tv_totalAmount.setText("\u20B9 0");
                tv_mainCredit.setText("\u20B9 0");
                tv_mainDebit.setText("\u20B9 0");
            } else {
                tv_totalAmount.setText("\u20B9 " + totalAmount.getTotalAmount());
                tv_mainCredit.setText("\u20B9 " + totalAmount.getCreditAmount());
                tv_mainDebit.setText("\u20B9 " + totalAmount.getDebitAmount());
            }
        } else {
            ll_mainLayout.setVisibility(View.GONE);
            tv_noTrans.setVisibility(View.VISIBLE);
            tv_totalAmount.setText("\u20B9 0");
        }

        transactionAdapter = new AllTransactionAdapter(this, transList, deleteListner, hideDelete);
        rv_allTrans.setAdapter(transactionAdapter);
    }

    @Override
    protected void onResume() {
        user = appDatabase.userDao().getUserInfo();
        tv_toolbar.setText(user.getName());
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (isDoublePressedToExit) {
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Click BACK again to exit from MY Audit", Toast.LENGTH_SHORT).show();
            isDoublePressedToExit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isDoublePressedToExit = false;
                }
            }, 2000);
        }
    }
}
