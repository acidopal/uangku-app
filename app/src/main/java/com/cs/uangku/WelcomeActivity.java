package com.cs.uangku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Button btnStarted = findViewById(R.id.btnStarted);
        btnStarted.setOnClickListener(this);

        TextView txtLogin = findViewById(R.id.txtLogin);
        txtLogin.setOnClickListener(this);

        Window window = WelcomeActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(WelcomeActivity.this, R.color.white_transaparent));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStarted:
                Intent registerIntent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                break;
            case R.id.txtLogin:
                Intent loginIntent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                break;
        }
    }
}