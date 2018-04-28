package com.candymanager.login;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Usuario on 19/04/2018.
 */

public class LoginSharedPreferences {
    private static final String PREFERENCIA = "LOGIN";
    private Context context;
    public LoginSharedPreferences(Context context){
        this.context = context;
    }


    public void setUsuarioLogado(String id, String nome, String email){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
        editor.putString("id", id);
        editor.putString("nome", nome);
        editor.putString("email", email);

        editor.apply();
    }

    public String getEmail(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String email = prefs.getString("email", null);
        return email == null ? "" : email;
    }

    public String getId(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String id = prefs.getString("id", null);
        return id == null ? "" : id;
    }

    public String getNome(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String nome = prefs.getString("nome", null);
        return nome == null ? "" : nome;
    }

    public boolean usuarioLogado(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String email = prefs.getString("email", null);
        if (email != null) {
           return true;
        }

        return false;
    }


}
