package com.fiqri.gofoodsederhana.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fiqri.gofoodsederhana.R;
import com.fiqri.gofoodsederhana.helper.MyFunction;
import com.fiqri.gofoodsederhana.model.ResponseRegister;
import com.fiqri.gofoodsederhana.network.ConfigRetrofit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends MyFunction {

    @BindView(R.id.regis_name)
    TextInputEditText edtRegisName;
    @BindView(R.id.regis_alamat)
    TextInputEditText edtRegisAlamat;
    @BindView(R.id.regis_no_tlp)
    TextInputEditText edtRegisNoTlp;
    @BindView(R.id.spin_kelamin)
    Spinner spinKelamin;
    @BindView(R.id.regis_username)
    TextInputEditText edtRegisUsername;
    @BindView(R.id.regis_pass)
    TextInputEditText edtRegisPass;
    @BindView(R.id.regis_confir_pass)
    TextInputEditText edtRegisConfirPass;
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
        setSpinnerJenkel();
    }

    private void setUpUser() {
        if (rgUserAdmin.isChecked()) levelUser = "Admin";
        else levelUser = "User Biasa";
    }

    private void setSpinnerJenkel() {
        ArrayAdapter adapter = new ArrayAdapter(
                this, android.R.layout.simple_spinner_item, jenisKelamin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKelamin.setAdapter(adapter);
        spinKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int index, long id) {
                jenKel = jenisKelamin[index];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // something to do
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
                register();
                break;
            case R.id.login:
                intent(Login.class);
                break;
        }
    }

    private void register() {

        /**
         * seleksi inputan user terlebih dahulu ada/tidak ada
         * seleksi validasi password
         * kita buat statment kondisi untuk cek nya (if & else)
         * */

        name = edtRegisName.getText().toString().trim();
        alamat = edtRegisAlamat.getText().toString().trim();
        noHp = edtRegisNoTlp.getText().toString().trim();
        username = edtRegisUsername.getText().toString().trim();
        password = edtRegisPass.getText().toString().trim();
        conPassword = edtRegisConfirPass.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            edtRegisName.requestFocus();
            edtRegisName.setError(getString(R.string.isEmpyField));

        } else if (TextUtils.isEmpty(alamat)) {
            edtRegisAlamat.requestFocus();
            edtRegisAlamat.setError(getString(R.string.isEmpyField));

        } else if (TextUtils.isEmpty(noHp)) {
            edtRegisNoTlp.requestFocus();
            edtRegisNoTlp.setError(getString(R.string.isEmpyField));

        } else if (TextUtils.isEmpty(username)) {
            edtRegisUsername.requestFocus();
            edtRegisUsername.setError(getString(R.string.isEmpyField));

        } else if (TextUtils.isEmpty(password)) {
            edtRegisPass.requestFocus();
            edtRegisPass.setError(getString(R.string.isEmpyField));

        } else if (password.length() < 6) {
            edtRegisPass.setError(getString(R.string.minimum));

        } else if (TextUtils.isEmpty(conPassword)) {
            edtRegisConfirPass.requestFocus();
            edtRegisConfirPass.setError(getString(R.string.isEmpyField));

        } else if (!password.equals(conPassword)) {
            edtRegisConfirPass.setError(getString(R.string.length));

        } else {
            fetchRegister();
        }
    }

    private void fetchRegister() {
        showProgressDialog("Fetch . . .");
        ConfigRetrofit.getInstancee().registerrr(
                name,
                alamat,
                jenKel,
                noHp,
                username,
                levelUser,
                password).enqueue(new Callback<ResponseRegister>() {
            @Override
            public void onResponse(Call<ResponseRegister> call, Response<ResponseRegister> response) {

                if (response.isSuccessful()) {
                    String result = response.body().getResult();
                    String message = response.body().getMsg();

                    if (result.equals("1")) {
                        intent(Login.class);
                        finish();
                        Toast.makeText(c, message + " Silahkan Login", Toast.LENGTH_SHORT).show();
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




















