package com.cs.uangku.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cs.uangku.R;
import com.cs.uangku.api.BaseApiService;
import com.cs.uangku.api.UtilsApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterConfirmActivity extends AppCompatActivity {
    public static final String UangkuPref = "UangkuPref";
    private TextView txtName;
    BaseApiService mApiService;
    ProgressDialog loading;
    Context mContext;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_confirm);

        sharedPreferences = getSharedPreferences(UangkuPref,  Context.MODE_PRIVATE);

        mContext = this;
        mApiService = UtilsApi.getAPIService();

        // get intent
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String email = bundle.getString("email");
        String password = bundle.getString("password");

        Log.d("name", name);
        //set to ui
        txtName = findViewById(R.id.txtName);
        txtName.setText(name);

        // set intent to api
        Button btnConfirmRegister = findViewById(R.id.btnConfirmRegister);
        btnConfirmRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading = ProgressDialog.show(RegisterConfirmActivity.this, "",
                        "Sedang di proses...", true);
                requestRegister();
            }

            private void requestRegister() {
                Bundle bundle = getIntent().getExtras();
                String name = bundle.getString("name");
                String email = bundle.getString("email");
                String password = bundle.getString("password");
                Log.d("name regist", name);
                Log.d("email regist", email);
                Log.d("password regist", password);
                mApiService.register(name, email, password)
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
                                            Toast.makeText(mContext, "BERHASIL Register", Toast.LENGTH_SHORT).show();
                                            Integer id = jsonRESULTS.getJSONObject("data").getInt("id");
                                            String name = jsonRESULTS.getJSONObject("data").getString("name");
                                            String email = jsonRESULTS.getJSONObject("data").getString("email");
                                            Integer balance = jsonRESULTS.getJSONObject("data").getInt("balance");

                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putInt("user_id", id);
                                                editor.putString("name", name);
                                                editor.putString("email", email);
                                                editor.putInt("balance", balance);
                                                editor.putBoolean("is_login", true);
                                            editor.commit();

                                            startActivity(new Intent(mContext, MainActivity.class)
                                                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                                            finish();
                                        }else {
                                            Log.d("error", "error");

                                            String error_message = jsonRESULTS.getString("error_msg");
                                            Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                        }
                                    }catch (JSONException e){
                                        e.printStackTrace();
                                    }catch (IOException e){
                                        e.printStackTrace();
                                    }
                                } else {
                                    Log.d("hi", "hi");
                                    loading.dismiss();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(mContext, "Username & Password Salah!", Toast.LENGTH_SHORT).show();
                                loading.dismiss();
                            }
                        });
            }
        });


        Window window = RegisterConfirmActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(RegisterConfirmActivity.this, R.color.white_transaparent));
        }
    }

}