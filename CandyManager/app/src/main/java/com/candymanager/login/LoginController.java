package com.candymanager.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.candymanager.R;
import com.candymanager.SplashScreen;
import com.candymanager.cadastro.CadastroController;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.util.Mensagem;
import com.candymanager.util.Validation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class LoginController extends AppCompatActivity {

    private LoginView loginView;
    private LoginDAO loginDAO;
    private LoginModel loginModel;

    private Validation validacao = new Validation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializaRecursos();
        inicializaListeners();


    }

    private void inicializaListeners() {
        loginView.getLoginButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LinkedHashMap<EditText, String> mapa = new LinkedHashMap<>();
                mapa.put(loginView.getEmailEditText(), "E-mail não pode ser vazio!");
                mapa.put(loginView.getSenhaEditText(), "Senha não pode ser vazia!");

                boolean sucesso = validacao.validaNaoNulo(mapa) && validacao.validaEmail(loginView.getEmailEditText(), "E-mail inválido!");

                if (sucesso) {

                    loginModel.setEmail(loginView.getEmailEditText().getText().toString());
                    loginModel.setSenha(loginView.getSenhaEditText().getText().toString());

                    boolean success = loginDAO.usuarioExiste(loginModel);

                    if(!success){
                        Mensagem.mostrarDialogo(LoginController.this, "Usuário não encontrado", "O usuário informado não foi encontrado!");
                    }
                    if(success){

                        Intent intent = new Intent(LoginController.this,
                                MenuPrincipal.class);
                        startActivity(intent);
                        finish();

                    }
                }

            }
        });

        loginView.getCriarContaTextView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginController.this,
                        CadastroController.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void inicializaRecursos() {
        loginView = new LoginView(this);
        loginDAO = new LoginDAO(this);
        loginModel = new LoginModel();
    }




}
