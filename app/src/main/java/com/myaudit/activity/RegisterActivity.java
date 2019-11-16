package com.myaudit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myaudit.R;
import com.myaudit.database.user.User;
import com.myaudit.database.userDatabase.UserDatabase;
import com.myaudit.utils.AppPrefference;

import static com.myaudit.activity.SplashActivity.userDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText edt_name, edt_pin, edt_cpin, edt_nick;
    private Button register;
    private boolean isDoubleBackPressed = false;
    private TextView tv_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        tv_toolbar = findViewById(R.id.tv_toolbar);
        edt_name = findViewById(R.id.edt_register_name);
        edt_pin = findViewById(R.id.edt_register_pin);
        edt_cpin = findViewById(R.id.edt_register_cpin);
        edt_nick = findViewById(R.id.edt_register_nick);
        register = findViewById(R.id.button2);

        tv_toolbar.setText("Sign up");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edt_name.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter your name", Toast.LENGTH_SHORT).show();
                } else if (edt_pin.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter your 4 digit pin", Toast.LENGTH_SHORT).show();
                } else if (!edt_cpin.getText().toString().equals(edt_pin.getText().toString())) {
                    Toast.makeText(RegisterActivity.this, "Your pin and confirm pin should be same", Toast.LENGTH_SHORT).show();
                } else if (edt_nick.getText().toString().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Enter your nick name", Toast.LENGTH_SHORT).show();
                } else {
                    registerUser(edt_name.getText().toString(), edt_pin.getText().toString(), edt_nick.getText().toString());
                }
            }
        });
    }

    private void registerUser(String name, String pin, String nick) {
        User user = new User(name, pin, nick);
        try{
            userDatabase.userDao().addUser(user);
            AppPrefference.setUserRegister(this, true);
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }catch (Exception e){
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        if(isDoubleBackPressed){
            super.onBackPressed();
        }else{
            isDoubleBackPressed = true;
            Toast.makeText(this, "Please BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isDoubleBackPressed = false;
                }
            }, 2000);
        }
    }
}
