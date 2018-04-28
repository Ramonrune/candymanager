package com.candymanager;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.candymanager.login.LoginController;
import com.candymanager.login.LoginSharedPreferences;
import com.candymanager.login.LoginView;
import com.candymanager.menu.MenuPrincipal;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarLogin();
            }
        }, 3000);
    }

    private void mostrarLogin() {
        LoginSharedPreferences loginSharedPreferences = new LoginSharedPreferences(this);
        Intent intent;
        if (loginSharedPreferences.usuarioLogado()) {
            intent = new Intent(SplashScreen.this,
                    MenuPrincipal.class);

        } else {
            intent = new Intent(SplashScreen.this,
                    LoginController.class);

        }
        startActivity(intent);
        finish();

    }
}
