package com.myaudit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.myaudit.R;
import com.myaudit.database.user.User;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private boolean isDoubleBackPressed = false;

    private TextView tv_userName, tv_toolbar, tv_otp_one, tv_otp_two, tv_otp_three, tv_otp_four,
            one, two, three, four, five, six, seven, eight, nine, zero;
    private RelativeLayout img_clear;
    private Button btn_login;
    public static User user;
    private boolean isAllSet = false;
    private String e_one = "", e_two = "", e_three = "", e_four = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tv_toolbar = findViewById(R.id.tv_toolbar);
        tv_userName = findViewById(R.id.textView);
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
        btn_login = findViewById(R.id.button);

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
        btn_login.setOnClickListener(this);

        tv_toolbar.setText("Sign in");

        user = SplashActivity.userDatabase.userDao().getUserInfo();

        tv_userName.setText("Welcome : " + user.getName());
    }

    @Override
    public void onBackPressed() {
        if (isDoubleBackPressed) {
            super.onBackPressed();
        } else {
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

    public void goToForgot(View view) {
        Intent intent = new Intent(this, ForgotPinActivity.class);
        intent.putExtra("pin", user.getPin());
        intent.putExtra("nickName", user.getNickName());
        startActivity(intent);
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
            case R.id.button:
                if (isAllSet) {
                    loginUser();
                } else {
                    Toast.makeText(this, "Please enter 4 digit pin", Toast.LENGTH_SHORT).show();
                }
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

    private void loginUser() {
        String enteredOtp = e_one + e_two + e_three + e_four;
        if (user.getPin().equals(enteredOtp)) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Please enter correct pin", Toast.LENGTH_SHORT).show();
        }
    }
}
