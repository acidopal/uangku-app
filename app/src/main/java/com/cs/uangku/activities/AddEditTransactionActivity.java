package com.cs.uangku.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.uangku.R;
import com.cs.uangku.api.BaseApiService;
import com.cs.uangku.api.TokenInterceptor;
import com.cs.uangku.api.UtilsApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddEditTransactionActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String UangkuPref = "UangkuPref";
    public static final String EXTRA_ID =  "com.cs.uangku.EXTRA_ID";
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
    private int userId;
    SharedPreferences sharedPreferences;

    ProgressDialog loading;
    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        amountTextField = findViewById(R.id.amountTextField);
        categoryTextField = findViewById(R.id.categoryTextField);
        descriptionTextField = findViewById(R.id.descriptionTextField);
        txtTitlePage = findViewById(R.id.titlePage);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

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
            categoryTextField.getEditText().setText(intent.getStringExtra(EXTRA_CATEGORY));
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
        sharedPreferences = getSharedPreferences(UangkuPref,  Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("user_id", 1);
        Log.d("user_id", String.valueOf(userId));
        String title = txtTitlePage.getText().toString();
        int amount = Integer.parseInt(amountTextField.getEditText().getText().toString());
        String category = categoryTextField.getEditText().getText().toString();
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

        loading = ProgressDialog.show(AddEditTransactionActivity.this, "", "Sedang di proses...", true);

        //get token user
        SharedPreferences prefs = getSharedPreferences("UangkuPref", Context.MODE_PRIVATE);
        String rememberToken = prefs.getString("rememberToken", "");


        TokenInterceptor interceptor = new TokenInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .build();

        mApiService.saveTransaction(amount, category, description)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        Log.d("response", String.valueOf(response));
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                Log.d("jsonResult", String.valueOf(jsonRESULTS));
                                Log.d("jsonResultData", jsonRESULTS.getJSONObject("data").getString("name"));
                                Log.d("here", "here");
                                if (jsonRESULTS.getString("code").equals("200")) {
                                    Toast.makeText(mContext, "Berhasil", Toast.LENGTH_SHORT).show();
                                }else {
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            }catch (JSONException e){
                                e.printStackTrace();
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(mContext, "Gagal Melakukan Transaksi!", Toast.LENGTH_SHORT).show();
                        loading.dismiss();
                    }
                });

        setResult(RESULT_OK, data);
        finish();
    }
}