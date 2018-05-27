package com.candymanager.configuracao;


import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Usuario on 19/04/2018.
 */

public class NotificacaoPedidosSharedPreference {
    private static final String PREFERENCIA = "NOTIFICACAO_PEDIDO";
    private Context context;
    public NotificacaoPedidosSharedPreference(Context context){
        this.context = context;
    }


    public void enable(){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
        editor.putBoolean("enabled", true);


        editor.apply();
    }

    public void disable(){
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE).edit();
        editor.putBoolean("enabled", false);


        editor.apply();
    }


    public boolean isEnabled(){
        SharedPreferences prefs = context.getSharedPreferences(PREFERENCIA, MODE_PRIVATE);
        boolean isEnabled = prefs.getBoolean("enabled", false);
        return isEnabled;
    }



}
