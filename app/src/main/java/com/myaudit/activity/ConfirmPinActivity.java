package com.myaudit.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myaudit.R;
import com.myaudit.database.totalAmount.TotalAmount;
import com.myaudit.database.transaction.Transaction;
import com.myaudit.database.user.User;
import com.myaudit.utils.AppUtils;

import java.util.ArrayList;

import static com.myaudit.activity.SplashActivity.userDatabase;
import static com.myaudit.utils.AppUtils.getMonth;
import static java.util.Collections.addAll;

public class ConfirmPinActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_amount, tv_toolbar, tv_otp_one, tv_otp_two, tv_otp_three, tv_otp_four,
            one, two, three, four, five, six, seven, eight, nine, zero;
    private RelativeLayout img_clear;
    private boolean isAllSet = false;
    private String e_one = "", e_two = "", e_three = "", e_four = "";
    private Transaction transaction;
    private int amount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pin);

        tv_toolbar = findViewById(R.id.tv_toolbar);
        tv_amount = findViewById(R.id.textView15);
        tv_otp_one = findViewById(R.id.tv_otp_one);
        tv_otp_two = findViewById(R.id.tv_otp_two);
        tv_otp_three = findViewById(R.id.tv_otp_three);
        tv_otp_four = findViewById(R.id.tv_otp_four);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        zero = findViewById(R.id.zero);
        img_clear = findViewById(R.id.img_clear);

        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        zero.setOnClickListener(this);
        img_clear.setOnClickListener(this);

        transaction = new Transaction(getIntent().getStringExtra("tranAmount"),
                getIntent().getStringExtra("tranTitle"),
                getIntent().getStringExtra("tranDesc"),
                getIntent().getStringExtra("tranDate"),
                getIntent().getStringExtra("tranType"),
                getMonth());
        tv_toolbar.setText(transaction.getTitle());
        tv_amount.setText("\u20B9 " + transaction.getAmount());
        amount = Integer.parseInt(transaction.getAmount());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.one:
                if (!isAllSet) {
                    setPin("1");
                }
                break;
            case R.id.two:
                if (!isAllSet) {
                    setPin("2");
                }
                break;
            case R.id.three:
                if (!isAllSet) {
                    setPin("3");
                }
                break;
            case R.id.four:
                if (!isAllSet) {
                    setPin("4");
                }
                break;
            case R.id.five:
                if (!isAllSet) {
                    setPin("5");
                }
                break;
            case R.id.six:
                if (!isAllSet) {
                    setPin("6");
                }
                break;
            case R.id.seven:
                if (!isAllSet) {
                    setPin("7");
                }
                break;
            case R.id.eight:
                if (!isAllSet) {
                    setPin("8");
                }
                break;
            case R.id.nine:
                if (!isAllSet) {
                    setPin("9");
                }
                break;
            case R.id.zero:
                if (!isAllSet) {
                    setPin("0");
                }
                break;
            case R.id.img_clear:
                isAllSet = false;
                clearPin();
                break;
        }
    }

    private void setPin(String pin) {
        if (tv_otp_one.getText().toString().isEmpty()) {
            tv_otp_one.setText(pin);
            e_one = pin;
            tv_otp_one.setBackgroundResource(R.drawable.otp_filled_color);
        } else if (tv_otp_two.getText().toString().isEmpty()) {
            tv_otp_two.setText(pin);
            e_two = pin;
            tv_otp_two.setBackgroundResource(R.drawable.otp_filled_color);
        } else if (tv_otp_three.getText().toString().isEmpty()) {
            tv_otp_three.setText(pin);
            e_three = pin;
            tv_otp_three.setBackgroundResource(R.drawable.otp_filled_color);
        } else if (tv_otp_four.getText().toString().isEmpty()) {
            tv_otp_four.setText(pin);
            e_four = pin;
            isAllSet = true;
            tv_otp_four.setBackgroundResource(R.drawable.otp_filled_color);
        }
    }

    private void clearPin() {
        if (!tv_otp_four.getText().toString().isEmpty()) {
            tv_otp_four.setText("");
            e_four = "";
            tv_otp_four.setBackgroundResource(R.drawable.otp_unfilled_color);
        } else if (!tv_otp_three.getText().toString().isEmpty()) {
            tv_otp_three.setText("");
            e_three = "";
            tv_otp_three.setBackgroundResource(R.drawable.otp_unfilled_color);
        } else if (!tv_otp_two.getText().toString().isEmpty()) {
            tv_otp_two.setText("");
            e_two = "";
            tv_otp_two.setBackgroundResource(R.drawable.otp_unfilled_color);
        } else if (!tv_otp_one.getText().toString().isEmpty()) {
            tv_otp_one.setText("");
            e_one = "";
            tv_otp_one.setBackgroundResource(R.drawable.otp_unfilled_color);
        }
    }

    public void confirmPin(View view) {
        if (isAllSet) {
            String enteredOtp = e_one + e_two + e_three + e_four;
            if (enteredOtp.equals(LoginActivity.user.getPin())) {
                try {
                    userDatabase.transactionDao().insertTransaction(transaction);
                    TotalAmount totalAmount = userDatabase.totalAmountDao().getTotalAmount(getMonth());
                    ArrayList<TotalAmount> totalAmountList = new ArrayList<>(userDatabase.totalAmountDao().getTotalAmountList());
                    if (totalAmount == null) {
                        if (totalAmountList.size() == 0) {
                            if (transaction.getType().equalsIgnoreCase("Debit")) {
                                totalAmount = new TotalAmount(getMonth(), "0", String.valueOf(0 - amount), String.valueOf(0 - amount));
                            } else {
                                totalAmount = new TotalAmount(getMonth(), transaction.getAmount(), "0", transaction.getAmount());
                            }
                        } else {
                            if (transaction.getType().equalsIgnoreCase("Debit")) {
                                totalAmount = new TotalAmount(getMonth(), "0", String.valueOf(0 - amount),
                                        String.valueOf(Integer.valueOf(totalAmountList.get(totalAmountList.size() - 1).getTotalAmount()) - amount));
                            } else {
                                totalAmount = new TotalAmount(getMonth(), transaction.getAmount(), "0",
                                        String.valueOf(Integer.valueOf(totalAmountList.get(totalAmountList.size() - 1).getTotalAmount()) + amount));
                            }
                        }
                        userDatabase.totalAmountDao().addMonth(totalAmount);
                    } else {
                        if (transaction.getType().equalsIgnoreCase("Debit")) {
                            userDatabase.totalAmountDao().updateDebitAmount(Integer.parseInt(totalAmount.getDebitAmount()) - amount,
                                    transaction.getMonth());
                            userDatabase.totalAmountDao().updateTotalAmount(Integer.parseInt(totalAmount.getTotalAmount()) - amount);
                        } else {
                            userDatabase.totalAmountDao().updateCreditAmount(Integer.parseInt(totalAmount.getCreditAmount()) + amount,
                                    transaction.getMonth());
                            userDatabase.totalAmountDao().updateTotalAmount(Integer.parseInt(totalAmount.getTotalAmount()) + amount);
                        }
                    }
                    openSuccessDailog();
                } catch (Exception e) {
                    Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Please enter correct pin", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter 4 digit pin", Toast.LENGTH_SHORT).show();
        }

    }

    private void openSuccessDailog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("New Assignment");
        builder.setMessage("Transaction \u20B9 " + transaction.getAmount() + " has been saved successfully");
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent intent = new Intent(ConfirmPinActivity.this, MainActivity.class);
                AppUtils.clearAllIntent(intent);
                startActivity(intent);
                finish();
            }
        }).show();
    }
}
