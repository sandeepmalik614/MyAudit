package com.myaudit.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myaudit.R;

public class ForgotPinActivity extends AppCompatActivity {

    private String pin, nickName;
    private EditText edt_nick;
    private TextView tv_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pin);

        tv_toolbar = findViewById(R.id.tv_toolbar);
        edt_nick = findViewById(R.id.editText);

        tv_toolbar.setText("Forgot pin");

        pin = getIntent().getStringExtra("pin");
        nickName = getIntent().getStringExtra("nickName");
    }

    public void getPin(View view) {
        if(edt_nick.getText().toString().isEmpty()){
            Toast.makeText(this, "Enter your nick name", Toast.LENGTH_SHORT).show();
        }else if(!nickName.equals(edt_nick.getText().toString())){
            Toast.makeText(this, "Enter correct nick name", Toast.LENGTH_SHORT).show();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Recover pin");
            builder.setMessage("Your pin is: "+pin);
            builder.setCancelable(false);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    finish();
                }
            });
            builder.show();
        }
    }
}
