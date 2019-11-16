package com.myaudit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myaudit.R;
import com.myaudit.database.user.User;
import com.myaudit.utils.AppUtils;

public class ChnagePinActivity extends AppCompatActivity {

    private EditText edt_oldPin, edt_newPin, edt_conNewPin;
    private Button btn_update;
    private User user;
    private TextView tv_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chnage_pin);

        edt_oldPin = findViewById(R.id.edt_oldPin);
        edt_newPin = findViewById(R.id.edt_newPin);
        edt_conNewPin = findViewById(R.id.edt_conNewPin);
        btn_update = findViewById(R.id.button6);
        tv_toolbar = findViewById(R.id.tv_toolbar);

        tv_toolbar.setText("Change Pin");

        user = SplashActivity.appDatabase.userDao().getUserInfo();

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_oldPin.getText().toString().isEmpty()){
                    Toast.makeText(ChnagePinActivity.this, "Please enter old pin", Toast.LENGTH_SHORT).show();
                }else if(edt_newPin.getText().toString().isEmpty()){
                    Toast.makeText(ChnagePinActivity.this, "Please enter new pin", Toast.LENGTH_SHORT).show();
                }else if(!edt_newPin.getText().toString().equals(edt_conNewPin.getText().toString())){
                    Toast.makeText(ChnagePinActivity.this, "New Pin and Confirm Pin should be same", Toast.LENGTH_SHORT).show();
                }else if(!edt_oldPin.getText().toString().equals(user.getPin())){
                    Toast.makeText(ChnagePinActivity.this, "Old pin is wrong, Please enter correct Old pin", Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        SplashActivity.appDatabase.userDao().updateUserPin(edt_newPin.getText().toString());
                        Toast.makeText(ChnagePinActivity.this, "Pin update successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ChnagePinActivity.this, MainActivity.class);
                        AppUtils.clearAllIntent(intent);
                        startActivity(intent);
                        finish();
                    }catch (Exception e){
                        Toast.makeText(ChnagePinActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
