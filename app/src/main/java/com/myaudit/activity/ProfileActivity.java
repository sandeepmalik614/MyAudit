package com.myaudit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.myaudit.R;
import com.myaudit.database.user.User;

import static com.myaudit.activity.SplashActivity.appDatabase;

public class ProfileActivity extends AppCompatActivity {

    private EditText edt_name;
    private TextView tv_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tv_toolbar = findViewById(R.id.tv_toolbar);
        edt_name = findViewById(R.id.editText4);
        edt_name.setText(getIntent().getStringExtra("userName"));
        tv_toolbar.setText("Profile");

    }

    public void updateName(View view) {
        if (edt_name.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show();
        } else {
            try {
                appDatabase.userDao().updateUserName(edt_name.getText().toString());
                Toast.makeText(this, "Name updated successfully", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void goToChangePin(View view) {
        startActivity(new Intent(this, ChnagePinActivity.class));
    }

}
