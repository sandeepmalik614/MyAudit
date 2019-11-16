package com.myaudit.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.myaudit.R;
import com.myaudit.database.userDatabase.UserDatabase;
import com.myaudit.utils.AppPrefference;

public class SplashActivity extends AppCompatActivity {

    public static UserDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        appDatabase = Room.databaseBuilder(this, UserDatabase.class, "MyAuditDb")
                .allowMainThreadQueries()
                .build();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(AppPrefference.isUserRegister(SplashActivity.this)) {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(SplashActivity.this, RegisterActivity.class));
                    finish();
                }
            }
        }, 1500);
    }
}
