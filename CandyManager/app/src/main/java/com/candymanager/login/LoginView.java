package com.candymanager.login;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.candymanager.R;

/**
 * Created by Usuario on 18/04/2018.
 */

public class LoginView {

    private EditText emailEditText;
    private EditText senhaEditText;
    private Button loginButton;
    private TextView criarContaTextView;

    public LoginView(AppCompatActivity view){
        view.setContentView(R.layout.activity_login_controller);
        view.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        emailEditText = (EditText) view.findViewById(R.id.emailEditText);
        senhaEditText = (EditText) view.findViewById(R.id.senhaEditText);
        loginButton = (Button) view.findViewById(R.id.loginButton);
        criarContaTextView = (TextView) view.findViewById(R.id.criarContaTextView);


    }


    public EditText getEmailEditText() {
        return emailEditText;
    }

    public EditText getSenhaEditText() {
        return senhaEditText;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public TextView getCriarContaTextView() {
        return criarContaTextView;
    }
}
