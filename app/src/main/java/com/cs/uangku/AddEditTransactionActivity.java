package com.cs.uangku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class AddEditTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_ID =  "com.cs.uangku.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.cs.uangku.EXTRA_TITLE";
    public static final String EXTRA_USER_ID = "com.cs.uangku.EXTRA_USER_ID";
    public static final String EXTRA_AMOUNT = "com.cs.uangku.EXTRA_AMOUNT";
    public static final String EXTRA_CATEGORY = "com.cs.uangku.EXTRA_CATEGORY";
    public static final String EXTRA_DESCRIPTION = "com.cs.uangku.EXTRA_DESCRIPTION";

    private TextInputLayout amountTextField;
    private TextInputLayout categoryTextField;
    private TextInputLayout descriptionTextField;
    private ImageView btnBack;
    private FloatingActionButton btnSave;
    private TextView txtTitlePage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        amountTextField = findViewById(R.id.amountTextField);
        categoryTextField = findViewById(R.id.categoryTextField);
        descriptionTextField = findViewById(R.id.descriptionTextField);
        txtTitlePage = findViewById(R.id.titlePage);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(this);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            Log.d("EXTRA_AMOUNT", String.valueOf(intent.getIntExtra(EXTRA_AMOUNT, 1)));
            setTitle("Edit Transaction");
            txtTitlePage.setText("Edit Transaction");
            amountTextField.getEditText().setText(String.valueOf(intent.getIntExtra(EXTRA_AMOUNT, 1)));
            categoryTextField.getEditText().setText(String.valueOf(intent.getIntExtra(EXTRA_CATEGORY, 1)));
            descriptionTextField.getEditText().setText(intent.getStringExtra(EXTRA_DESCRIPTION));
        } else {
            setTitle("Add Note");
            txtTitlePage.setText("Add Transaction");
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
        String title = txtTitlePage.getText().toString();
        int amount = Integer.parseInt(amountTextField.getEditText().getText().toString());
        int category = Integer.parseInt(categoryTextField.getEditText().getText().toString());
        String description = descriptionTextField.getEditText().getText().toString();

        if (description.isEmpty()){
            Toast.makeText(this, "Please insert Amount & Category", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_USER_ID, userId);
        data.putExtra(EXTRA_AMOUNT, amount);
        data.putExtra(EXTRA_CATEGORY, category);
        data.putExtra(EXTRA_DESCRIPTION, description);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }
}