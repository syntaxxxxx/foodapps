package com.fiqri.gofoodsederhana.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fiqri.gofoodsederhana.FoodUtama;
import com.fiqri.gofoodsederhana.R;
import com.fiqri.gofoodsederhana.helper.SessionManager;
import com.fiqri.gofoodsederhana.model.ResponseRegister;
import com.fiqri.gofoodsederhana.model.User;
import com.fiqri.gofoodsederhana.network.ConfigRetrofit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends SessionManager {

    @BindView(R.id.login_username)
    TextInputEditText loginUsername;
    @BindView(R.id.login_password)
    TextInputEditText loginPassword;
    @BindView(R.id.rg_user_admin_sign)
    RadioButton rgUserAdminSign;
    @BindView(R.id.rg_user_biasa_sign)
    RadioButton rgUserBiasaSign;
    @BindView(R.id.sign_in)
    Button signIn;
    @BindView(R.id.register)
    TextView register;

    String levelUser, userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ButterKnife.bind(this);

        setUpUser();
    }

    private void setUpUser() {
        if (rgUserAdminSign.isChecked()) levelUser = "Admin";
        else levelUser = "User Biasa";
    }

    @OnClick({R.id.rg_user_admin_sign, R.id.rg_user_biasa_sign, R.id.sign_in, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rg_user_admin_sign:
                levelUser = "Admin";
                break;
            case R.id.rg_user_biasa_sign:
                levelUser = "User Biasa";
                break;
            case R.id.sign_in:
                validasiInputan();
                break;
            case R.id.register:
                intent(Register.class);
                break;
        }
    }

    private void validasiInputan() {

        // tampung dalam variable dan ambil inputan di field nya
        userName = loginUsername.getText().toString().trim();
        password = loginPassword.getText().toString().trim();

        if (TextUtils.isEmpty(userName)) {
            loginUsername.requestFocus();
            loginUsername.setError("nama tidak boleh kosong");

        } else if (TextUtils.isEmpty(password)) {
            loginPassword.requestFocus();
            loginPassword.setError("password tidak boleh kosong");

        } else {
            sendRequestLogin();
        }
    }

    private void sendRequestLogin() {
        ConfigRetrofit.getInstance().login(
                userName,
                password,
                levelUser
        ).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {

                if (response.isSuccessful()) {
                    String result = response.body().getResult();
                    String msg = response.body().getMsg();
                    String idUser = response.body().getUser().getIdUser();

                    if (result.equals("1")){
                        sessionManager.createSession(userName);
                        sessionManager.setIdUser(idUser);
                        intent(FoodUtama.class);
                        shortToast(msg);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseRegister> call, Throwable t) {
                longToast(t.getMessage());
            }
        });
    }
}

