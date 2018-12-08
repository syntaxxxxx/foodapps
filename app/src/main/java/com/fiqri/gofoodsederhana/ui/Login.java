package com.fiqri.gofoodsederhana.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fiqri.gofoodsederhana.R;
import com.fiqri.gofoodsederhana.helper.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
                break;
            case R.id.register:
                intent(Register.class);
                break;
        }
    }
}
