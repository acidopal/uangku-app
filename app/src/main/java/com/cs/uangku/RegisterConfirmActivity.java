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

public class RegisterConfirmActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirm);

        Button btnConfirmRegister = findViewById(R.id.btnConfirmRegister);
        btnConfirmRegister.setOnClickListener(this);

        Window window = RegisterConfirmActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(RegisterConfirmActivity.this, R.color.white_transaparent));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnConfirmRegister:
                Intent registerIntent = new Intent(RegisterConfirmActivity.this, MainActivity.class);
                startActivity(registerIntent);
                break;
        }
    }
}