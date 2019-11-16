package com.myaudit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myaudit.R;
import com.myaudit.database.transaction.Transaction;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransactionActivity extends AppCompatActivity {

    private TextView tv_toolbar;
    private EditText edt_amount, edt_title, edt_desc;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);

        tv_toolbar = findViewById(R.id.tv_toolbar);
        edt_amount = findViewById(R.id.edt_amount);
        edt_title = findViewById(R.id.editText2);
        edt_desc = findViewById(R.id.editText3);

        tv_toolbar.setText("New Transaction");

        type = getIntent().getStringExtra("tranType");

    }

    public void addTransaction(View view) {
        if(edt_amount.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter amount", Toast.LENGTH_SHORT).show();
        }else if(edt_title.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show();
        }else if(edt_desc.getText().toString().isEmpty()){
            Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show();
        }else{
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy hh:mm aa");
            String date = df.format(c);
            Intent intent = new Intent(this, ConfirmPinActivity.class);
            intent.putExtra("tranAmount", edt_amount.getText().toString());
            intent.putExtra("tranTitle", edt_title.getText().toString());
            intent.putExtra("tranDate", date);
            intent.putExtra("tranDesc", edt_desc.getText().toString());
            intent.putExtra("tranType", type);
            startActivity(intent);
        }
    }
}
