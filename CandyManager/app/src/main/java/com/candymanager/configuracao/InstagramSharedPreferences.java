package com.candymanager.configuracao;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Usuario on 19/04/2018.
 */

public class InstagramSharedPreferences {
    private static final String PREFERENCIA = "INSTAGRAM";
    private Context context;
    public InstagramSharedPreferences(Context context){
        this.context = context;
    }


    public void setToken(String token){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
        editor.putString("token", token);


        editor.apply();
    }

    public String getToken(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String token = prefs.getString("token", null);
        return token;
    }


    public boolean usuarioLogadoInstagram(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        String token = prefs.getString("token", null);
        if (token != null) {
            return true;
        }

        return false;
    }


}
