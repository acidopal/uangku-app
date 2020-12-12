package com.cs.uangku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class AddEditTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_ID =  "com.cs.uangku.EXTRA_ID";
    public static final int EXTRA_USER_ID = 1;
    public static final int EXTRA_AMOUNT = 0;
    public static final int EXTRA_CATEGORY = 0;
    public static final String EXTRA_DESCRIPTION = "com.cs.uangku.EXTRA_DESCRIPTION";

    private TextInputLayout amountTextField;
    private TextInputLayout categoryTextField;
    private TextInputLayout descriptionTextField;
    private ImageView btnBack;
    private FloatingActionButton btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        amountTextField = findViewById(R.id.amountTextField);
        categoryTextField = findViewById(R.id.categoryTextField);
        descriptionTextField = findViewById(R.id.descriptionTextField);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            amountTextField.getEditText().setText(intent.getIntExtra(EXTRA_AMOUNT, 1));
            categoryTextField.getEditText().setText(intent.getIntExtra(EXTRA_DESCRIPTION, 1));
            descriptionTextField.getEditText().setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle("Add Note");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                Intent mainIntent = new Intent(AddEditTransactionActivity.this, MainActivity.class);
                startActivity(mainIntent);
                break;
            case R.id.btnSave:
                //todo
                saveTransaction();
                Intent saveIntent = new Intent(AddEditTransactionActivity.this, MainActivity.class);
                startActivity(saveIntent);
                break;
        }
    }

    private void saveTransaction() {
        int userId = 1;

        int amount = Integer.parseInt(amountTextField.getEditText().getText().toString());
        int category = Integer.parseInt(categoryTextField.getEditText().getText().toString());
        String description = descriptionTextField.getEditText().getText().toString();

        if (description.isEmpty()){
            Toast.makeText(this, "Please insert Amount & Category", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(String.valueOf(EXTRA_USER_ID), userId);
        data.putExtra(String.valueOf(EXTRA_AMOUNT), amount);
        data.putExtra(String.valueOf(EXTRA_CATEGORY), category);
        data.putExtra(EXTRA_DESCRIPTION, description);

        setResult(RESULT_OK, data);
        finish();
    }
}