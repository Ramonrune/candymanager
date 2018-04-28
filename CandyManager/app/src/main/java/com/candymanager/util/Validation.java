package com.candymanager.util;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Usuario on 18/04/2018.
 */

public class Validation {


    public boolean validaNaoNulo(LinkedHashMap<EditText, String> hashEditTexts) {
        boolean success = true;
        for (Map.Entry<EditText, String> entrada : hashEditTexts.entrySet()) {
            EditText chave = entrada.getKey();
            String valor = chave.getText().toString();
            if (valor != null) {
                if (TextUtils.isEmpty(valor.toString())) {
                    chave.setError(entrada.getValue());
                    success = false;
                    break;
                }

            } else {
                success = false;
                break;
            }
        }

        return success;
    }

    public boolean validaEmail(EditText emailEditText, String mensagem) {
        String valor = emailEditText.getText().toString();
        if (valor != null) {

            if ((!TextUtils.isEmpty(valor) && Patterns.EMAIL_ADDRESS.matcher(valor).matches())) {
                return true;
            }

            emailEditText.setError(mensagem);
            return false;

        } else {
            return false;
        }
    }


    public boolean validaTelefone(EditText telefoneEditText) {
        String valor = telefoneEditText.getText().toString();
        if (valor != null) {

            if ((!TextUtils.isEmpty(
                    valor.replace("(", "")
                            .replace(")", "")
                            .replace("-", "")
                            .trim()))) {
                return true;
            }

            telefoneEditText.setError("Telefone não pode ser vazio!");
            return false;

        } else {
            return false;
        }
    }

    public boolean validaSenhas(EditText senhaEditText, EditText confirmarSenhaEditText) {

        if(!senhaEditText.getText().toString().equals(confirmarSenhaEditText.getText().toString())){
            senhaEditText.setError("Ambas a senha e a confirmação devem ser iguais!");
            return false;
        }

        return true;
    }

}
