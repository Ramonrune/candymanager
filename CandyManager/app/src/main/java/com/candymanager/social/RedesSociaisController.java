package com.candymanager.social;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.candymanager.cadastro.CadastroController;
import com.candymanager.login.LoginController;
import com.candymanager.login.LoginDAO;
import com.candymanager.login.LoginModel;
import com.candymanager.login.LoginView;
import com.candymanager.menu.MenuPrincipal;
import com.candymanager.util.Mensagem;
import com.candymanager.util.Validation;

public class RedesSociaisController extends AppCompatActivity {

    private RedesSociaisView redesSociaisView;
    private RedesSociaisDAO redesSociaisDAO;
    private RedesSociaisModel redesSociaisModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializaRecursos();
        inicializaListeners();


    }

    private void inicializaListeners() {

    }

    private void inicializaRecursos() {
        redesSociaisView = new RedesSociaisView(this);
        redesSociaisDAO = new RedesSociaisDAO();
        redesSociaisModel = new RedesSociaisModel();
    }

    public void postar(){

    }

    public void listar(){

    }




}
