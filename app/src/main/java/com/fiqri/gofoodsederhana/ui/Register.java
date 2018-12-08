package com.fiqri.gofoodsederhana.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                break;
            case R.id.login:
                intent(Login.class);
                break;
        }
    }
}
