package com.candymanager.cadastro;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.candymanager.R;
import com.candymanager.SplashScreen;
import com.candymanager.login.LoginController;
import com.candymanager.login.LoginDAO;
import com.candymanager.login.LoginModel;
import com.candymanager.login.LoginSharedPreferences;
import com.candymanager.login.LoginView;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.util.Mensagem;
import com.candymanager.util.Validation;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

public class CadastroController extends AppCompatActivity {

    private CadastroView cadastroView;
    private CadastroDAO cadastroDAO;
    private CadastroModel cadastroModel;


    private Validation validacao = new Validation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializaRecursos();
        inicializaListeners();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(CadastroController.this,
                        LoginController.class);
                startActivity(intent);
                this.finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void inicializaRecursos() {
        cadastroView = new CadastroView(this);
        cadastroDAO = new CadastroDAO(this.getApplicationContext());
        cadastroModel = new CadastroModel();
    }

    private void inicializaListeners() {
        cadastroView.getCadastrarButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinkedHashMap<EditText, String> mapa = new LinkedHashMap<>();
                mapa.put(cadastroView.getNome(), "Nome não pode ser vazio!");
                mapa.put(cadastroView.getEmailEditText(), "E-mail não pode ser vazio!");
                mapa.put(cadastroView.getSenhaEditText(), "Senha não pode ser vazia!");
                mapa.put(cadastroView.getConfirmarSenhaEditText(), "Confirmação da Senha não pode ser vazia!");

                boolean sucesso =
                        validacao.validaNaoNulo(mapa)
                        && validacao.validaEmail(cadastroView.getEmailEditText(), "E-mail inválido!")
                        && validacao.validaSenhas(cadastroView.getSenhaEditText(), cadastroView.getConfirmarSenhaEditText());

                if (sucesso) {
                    cadastroModel.setNome(cadastroView.getNome().getText().toString());
                    cadastroModel.setEmail(cadastroView.getEmailEditText().getText().toString());
                    cadastroModel.setSenha(cadastroView.getSenhaEditText().getText().toString());

                    RespostaCadastro resposta = cadastroDAO.cadastrar(cadastroModel);

                    if(resposta == RespostaCadastro.EMAIL_ALREADY_EXIST){
                        Mensagem.mostrarDialogo(CadastroController.this, "E-mail já existe", "O e-mail informado já se encontra cadastrado!");
                    }

                    if(resposta == RespostaCadastro.EMAIL_ERROR){
                        Mensagem.mostrarErro(CadastroController.this);
                    }

                    if(resposta == RespostaCadastro.EMAIL_SUCCESS){

                        Mensagem.mostrarDialogoMudarActivity(CadastroController.this, MenuPrincipal.class, "Sucesso", "Usuário cadastrado com sucesso!");

                    }

                }

            }
        });



    }




}
