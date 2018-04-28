package com.candymanager.cadastro;

import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.candymanager.R;

/**
 * Created by Usuario on 18/04/2018.
 */

public class CadastroView {

    private EditText nomeEditText;
    private EditText emailEditText;
    private EditText senhaEditText;
    private EditText confirmarSenhaEditText;

    private Button cadastrarButton;

    public CadastroView(AppCompatActivity view){
        view.setContentView(R.layout.activity_cadastro_controller);
        view.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        view.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nomeEditText = (EditText) view.findViewById(R.id.nomeCadastroEditText);
        emailEditText = (EditText) view.findViewById(R.id.emailCadastroEditText);
        senhaEditText = (EditText) view.findViewById(R.id.senhaCadastroEditText);
        confirmarSenhaEditText = (EditText) view.findViewById(R.id.confirmarSenhaCadastroEditText);

        cadastrarButton = (Button) view.findViewById(R.id.cadastrarCadastroButton);


    }

    public EditText getNome() {
        return nomeEditText;
    }

    public void setNomeEditText(EditText nomeEditText) {
        this.nomeEditText = nomeEditText;
    }

    public EditText getEmailEditText() {
        return emailEditText;
    }

    public void setEmailEditText(EditText emailEditText) {
        this.emailEditText = emailEditText;
    }

    public EditText getSenhaEditText() {
        return senhaEditText;
    }

    public void setSenhaEditText(EditText senhaEditText) {
        this.senhaEditText = senhaEditText;
    }

    public EditText getConfirmarSenhaEditText() {
        return confirmarSenhaEditText;
    }

    public void setConfirmarSenhaEditText(EditText confirmarSenhaEditText) {
        this.confirmarSenhaEditText = confirmarSenhaEditText;
    }

    public Button getCadastrarButton() {
        return cadastrarButton;
    }

    public void setCadastrarButton(Button cadastrarButton) {
        this.cadastrarButton = cadastrarButton;
    }
}
