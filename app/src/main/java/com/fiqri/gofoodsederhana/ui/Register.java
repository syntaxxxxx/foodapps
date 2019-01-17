package com.fiqri.gofoodsederhana.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.fiqri.gofoodsederhana.R;
import com.fiqri.gofoodsederhana.helper.MyFunction;
import com.fiqri.gofoodsederhana.helper.SessionManager;
import com.fiqri.gofoodsederhana.model.ResponseRegister;
import com.fiqri.gofoodsederhana.model.User;
import com.fiqri.gofoodsederhana.network.ConfigRetrofit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends MyFunction {

    @BindView(R.id.regis_name)
    TextInputEditText regisName;
    @BindView(R.id.regis_alamat)
    TextInputEditText regisAlamat;
    @BindView(R.id.regis_no_tlp)
    TextInputEditText regisNoTlp;
    @BindView(R.id.spin_kelamin)
    Spinner spinKelamin;
    @BindView(R.id.regis_username)
    TextInputEditText regisUsername;
    @BindView(R.id.regis_pass)
    TextInputEditText regisPass;
    @BindView(R.id.regis_confir_pass)
    TextInputEditText regisConfirPass;
    @BindView(R.id.rg_user_admin)
    RadioButton rgUserAdmin;
    @BindView(R.id.rg_user_biasa)
    RadioButton rgUserBiasa;
    @BindView(R.id.sign_up)
    Button signUp;
    @BindView(R.id.login)
    TextView login;

    String jenisKelamin[] = {"Laki - Laki", "Perempuan"};
    String name, alamat, noHp, username, password, conPassword, jenKel, levelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        setUpUser();
        setUpJenKel();
    }

    private void setUpUser() {
        if (rgUserAdmin.isChecked()) levelUser = "Admin";
        else levelUser = "User Biasa";
    }

    private void setUpJenKel() {
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, jenisKelamin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKelamin.setAdapter(adapter);
        spinKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenKel = jenisKelamin[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                /** nothing to do */
            }
        });
    }

    @OnClick({R.id.rg_user_admin, R.id.rg_user_biasa, R.id.sign_up, R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rg_user_admin:
                levelUser = "Admin";
                break;
            case R.id.rg_user_biasa:
                levelUser = "User Biasa";
                break;
            case R.id.sign_up:
                validasiInputan();
                break;
            case R.id.login:
                intent(Login.class);
                break;
        }
    }

    private void validasiInputan() {

        // tampung dalam variable dan ambil inputan di field nya
        name = regisName.getText().toString().trim();
        alamat = regisAlamat.getText().toString().trim();
        noHp = regisNoTlp.getText().toString().trim();
        username = regisUsername.getText().toString().trim();
        password = regisPass.getText().toString().trim();
        conPassword = regisConfirPass.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            regisName.requestFocus();
            regisName.setError("nama tidak boleh kosong");

        } else if (TextUtils.isEmpty(alamat)) {
            regisAlamat.requestFocus();
            regisAlamat.setError("alamat tidak boleh kosong");

        } else if (TextUtils.isEmpty(noHp)) {
            regisNoTlp.requestFocus();
            regisNoTlp.setError("no hp tidak boleh kosong");

        } else if (TextUtils.isEmpty(username)) {
            regisUsername.requestFocus();
            regisUsername.setError("username tidak boleh kosong");

        } else if (TextUtils.isEmpty(password)) {
            regisPass.requestFocus();
            regisPass.setError("password tidak boleh kosong");

        } else if (password.length() < 6) {
            regisPass.setError("password minimal 6 karakter");

        } else if (TextUtils.isEmpty(conPassword)) {
            regisConfirPass.requestFocus();
            regisConfirPass.setError("password confirm tidak boleh kosong");

        } else if (!password.equals(conPassword)) {
            regisConfirPass.requestFocus();
            regisConfirPass.setError("password tidak sama");

        } else {
            sendRequestRegister();
        }
    }

    private void sendRequestRegister() {
        ConfigRetrofit.getInstance().register(
                name,
                alamat,
                jenKel,
                noHp,
                username,
                levelUser,
                password
        ).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {

                if (response.isSuccessful()) {
                    String result = response.body().getResult();
                    String msg = response.body().getMsg();

                    if (result.equals("1")){
                        intent(Login.class);
                        shortToast(msg);
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
